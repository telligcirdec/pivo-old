package santeclair.lunar.framework.personnalisation.impl;

import java.util.List;

import javax.faces.application.ProjectStage;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import santeclair.lunar.framework.personnalisation.PersonnalisationManagerBase;
import santeclair.lunar.framework.personnalisation.bean.CharteBean;
import santeclair.lunar.framework.personnalisation.bean.ElementPersonnalisationResponseBean;
import santeclair.lunar.framework.personnalisation.bean.ListePersonnalisationResponseBean;
import santeclair.lunar.framework.personnalisation.bean.PartenaireResponseBean;
import santeclair.lunar.framework.personnalisation.bean.RetourPersonnalisationResponseBean;
import santeclair.lunar.framework.personnalisation.client.PersonnalisationWebserviceClient;
import santeclair.lunar.framework.util.Base64DataUrlTools;

@Component
public class PersonnalisationManagerBaseImpl implements PersonnalisationManagerBase {

    transient protected ListePersonnalisationResponseBean listePersonnalisation;

    transient protected ListePersonnalisationResponseBean listeCommonPersonnalisation;

    protected static final Logger LOGGER = LoggerFactory.getLogger(PersonnalisationManagerBaseImpl.class);

    protected ProjectStage projectStage;

    protected final PersonnalisationWebserviceClient personnalisationWebserviceClient;

    /** id application cible */
    @Value("#{validationProperties['idApplication']}")
    protected String idApplication;

    @Autowired
    public PersonnalisationManagerBaseImpl(PersonnalisationWebserviceClient personnalisationWebserviceClient) {
        this.personnalisationWebserviceClient = personnalisationWebserviceClient;
    }

    /**
     * {@inheritDoc}
     */
    public CharteBean getCharteCourrier(String codeOrganisme) {
        return personnalisationWebserviceClient.rechercheCharteCourrierOrganisme(codeOrganisme);
    }

    /**
     * {@inheritDoc}
     */
    public String getCodeValue(String code) {
        return getCodeValueWithIdCharte(code, null);
    }

    /**
     * {@inheritDoc}
     */
    public String getCodeValue(String code, String idCharte) {
        return getCodeValueWithIdCharte(code, idCharte);
    }

    /**
     * {@inheritDoc}
     */
    public ByteArrayResource getInputStreamCodeValue(String code) {
        return getInputStreamCodeValue(code, null);
    }

    /**
     * {@inheritDoc}
     */
    public String getMimeType(String code) {
        return getMimeType(code, null);
    }

    /**
     * {@inheritDoc}
     */
    public String getMimeType(String code, String idCharte) {
        String Base64String = getCodeValue(code, idCharte);
        return Base64DataUrlTools.getMimeTypeFromDataUrl(Base64String);
    }

    /**
     * {@inheritDoc}
     */
    public ByteArrayResource getInputStreamCodeValue(String code, String idCharte) {
        String base64 = getElementPersonnalisationValue(code, idCharte);
        base64 = Base64DataUrlTools.getBase64FileFromDataUrl(base64);
        byte[] imageByte = Base64.decodeBase64(base64);
        ByteArrayResource in = new ByteArrayResource(imageByte);
        return in;
    }

    /**
     * Appel des webservice pour initialiser la cache de donnée de personnalisation concernant cette application
     */
    protected void init() {
        LOGGER.info("INITIALISATION du perso manager pour " + idApplication);
        this.listePersonnalisation = this.personnalisationWebserviceClient.getListePersonnalisation(idApplication);
        this.listeCommonPersonnalisation = this.personnalisationWebserviceClient.getListePersonnalisation("commun");
    }

    protected String getElementPersonnalisationValue(String code, String idCharte) {
        RetourPersonnalisationResponseBean personnalisation = getPersonnalisation(idCharte);
        List<ElementPersonnalisationResponseBean> listeCharteElelement = personnalisation.getElementPersonnalisationResponseBeans();

        RetourPersonnalisationResponseBean commonPersonnalisation = getCommonPersonnalisation(idCharte);
        List<ElementPersonnalisationResponseBean> listeCommonCharteElelement = commonPersonnalisation.getElementPersonnalisationResponseBeans();
        // On cherche dans les éléments de personnalisation spécifique a l'application avant de chercher dans les paramètres communs
        String elementPersonnalisationValue = findPersonnalisationElement(code, listeCharteElelement);
        if (elementPersonnalisationValue == null) {
            elementPersonnalisationValue = findPersonnalisationElement(code, listeCommonCharteElelement);
        }
        return elementPersonnalisationValue;
    }

    private String findPersonnalisationElement(String code, List<ElementPersonnalisationResponseBean> listeCharteElelement) {
        String elementPersonnalisationValue = null;
        for (ElementPersonnalisationResponseBean elementPersonnalisationResponseBean : listeCharteElelement) {
            LOGGER.debug("elementPersonnalisationResponseBean.getCode() : {0}", elementPersonnalisationResponseBean.getCode());
            if (elementPersonnalisationResponseBean.getCode().equals(code)) {
                elementPersonnalisationValue = elementPersonnalisationResponseBean.getValue();
                if ("COLOR_HEX".equals(elementPersonnalisationResponseBean.getType())) {
                    LOGGER.info("COLOR_HEX !");
                    return "#" + elementPersonnalisationValue;
                } else {
                    LOGGER.info("Other value");
                    return elementPersonnalisationValue;
                }
            }
        }
        return elementPersonnalisationValue;
    }

    /**
     * méthode racine d'acces au éléments de personnalisation
     * 
     * @param code Code identifiant l'element de personnalisation
     * @param idCharte Identifiant de la charte a utiliser.
     * @return
     */
    private String getCodeValueWithIdCharte(String code, String idCharte) {
        return getElementPersonnalisationValue(code, idCharte);
    }

    /**
     * Cherche dans la liste des personnalisation stockées celle correspondant a la charte courante sinon retourne la
     * charte par defaut (celle
     * vide) est retourne
     * 
     * @param String idCharte identifiant de la charte
     * @param ListePersonnalisationResponseBean liste de personnalisation
     * 
     * @return retourPersonnalisationResponseBean
     */
    private RetourPersonnalisationResponseBean getPersonnalisation(String idCharte) {
        if (this.listePersonnalisation == null || this.listeCommonPersonnalisation == null) {
            init();
        }
        List<RetourPersonnalisationResponseBean> listePersonnalisation = this.listePersonnalisation.getListePersonnalisation();
        return findRightRetourPersonnalisationResponseBean(idCharte, listePersonnalisation);
    }

    /**
     * 
     * @param idCharte
     * @return
     */
    private RetourPersonnalisationResponseBean getCommonPersonnalisation(String idCharte) {
        if (this.listePersonnalisation == null || this.listeCommonPersonnalisation == null) {
            init();
        }
        List<RetourPersonnalisationResponseBean> listePersonnalisation = this.listeCommonPersonnalisation.getListePersonnalisation();
        return findRightRetourPersonnalisationResponseBean(idCharte, listePersonnalisation);
    }

    /**
     * 
     * @param idCharte
     * @param listePersonnalisation
     * @return
     */
    private RetourPersonnalisationResponseBean findRightRetourPersonnalisationResponseBean(String idCharte, List<RetourPersonnalisationResponseBean> listePersonnalisation) {
        RetourPersonnalisationResponseBean defaultPersonnalisation = null;
        if (idCharte != null) {
            defaultPersonnalisation = findRetourResponseBeanFromIdCharte(idCharte, listePersonnalisation);
            if (defaultPersonnalisation == null) {
                defaultPersonnalisation = findRetourResponseBeanFromIdPart(idCharte, listePersonnalisation);
            }
            if (defaultPersonnalisation == null) {
                defaultPersonnalisation = findSanteclair(listePersonnalisation);
            }
        } else {
            defaultPersonnalisation = findSanteclair(listePersonnalisation);
        }

        if (defaultPersonnalisation != null) {
            reloadPersonalisation(defaultPersonnalisation);
            return defaultPersonnalisation;
        } else {
            throw new RuntimeException("findRightRetourPersonnalisationResponseBean : aucune perso trouvée (idCharte=" + idCharte + ").");
        }
    }

    /**
     * Retrouve une charte dans le cache via son IdCharte, renvoie null si aucune est trouvée.
     * 
     * @param idCharte
     * @param listePersonnalisation
     * @return
     */
    private RetourPersonnalisationResponseBean findRetourResponseBeanFromIdCharte(String idCharte, List<RetourPersonnalisationResponseBean> listePersonnalisation) {
        RetourPersonnalisationResponseBean retourResponseBeanFromIdCharte = null;
        for (RetourPersonnalisationResponseBean retourPersonnalisationResponseBean : listePersonnalisation) {
            String idCharteFromPersonnalisation = retourPersonnalisationResponseBean.getIdCharte();
            if (idCharte.equalsIgnoreCase(idCharteFromPersonnalisation)) {
                retourResponseBeanFromIdCharte = retourPersonnalisationResponseBean;
                break;
            }
        }
        return retourResponseBeanFromIdCharte;
    }

    /**
     * Retrouve la charte associée a un IdPart, renvoie null si aucun élément n'est trouvé.
     * 
     * @param idCharte
     * @param listePersonnalisation
     * @return
     */
    private RetourPersonnalisationResponseBean findRetourResponseBeanFromIdPart(String idCharte, List<RetourPersonnalisationResponseBean> listePersonnalisation) {
        // Soit on parcours la liste des chartes possédant des éléments de perso pour cette appli et on retourne la
        // premiere qui a pour
        // partenaire dont l'identifiant correspond a l'idCharte

        RetourPersonnalisationResponseBean retourResponseBeanFromIdPart = null;
        for (RetourPersonnalisationResponseBean retourPersonnalisationResponseBean : listePersonnalisation) {
            retourResponseBeanFromIdPart = findChartByIdPart(idCharte, retourPersonnalisationResponseBean);
            if (retourResponseBeanFromIdPart != null) {
                break;
            }
        }

        return retourResponseBeanFromIdPart;

    }

    /**
     * Verifie si la charte est associée a cet idPart
     * 
     * @param idCharte
     * @param retourPersonnalisationResponseBean
     * @return
     */
    private RetourPersonnalisationResponseBean findChartByIdPart(String idCharte, RetourPersonnalisationResponseBean retourPersonnalisationResponseBean) {
        RetourPersonnalisationResponseBean retourResponseBeanFromIdPart = null;
        for (PartenaireResponseBean partenaire : retourPersonnalisationResponseBean.getPartenaires()) {
            if (partenaire.getIdentifiant().equalsIgnoreCase(idCharte)) {
                reloadPersonalisation(retourPersonnalisationResponseBean);
                retourResponseBeanFromIdPart = retourPersonnalisationResponseBean;
            }
        }
        return retourResponseBeanFromIdPart;
    }

    /**
     * Recharge la charte si le context d'execution est un environnement de developpement.
     * 
     * @param retourPersonnalisationResponseBeanToReload La personnalisation a eventuellement recharger.
     */
    private void reloadPersonalisation(RetourPersonnalisationResponseBean retourPersonnalisationResponseBeanToReload) {
        String idCharte = retourPersonnalisationResponseBeanToReload.getIdCharte();
        if (ProjectStage.Development.equals(this.projectStage)) {
            retourPersonnalisationResponseBeanToReload =
                            this.personnalisationWebserviceClient.getDetailPersonnalisation(idApplication, idCharte);
        }
    }

    /**
     * Recherche la perso Santeclair
     * 
     * @param listePersonnalisation
     * @return
     */
    private RetourPersonnalisationResponseBean findSanteclair(List<RetourPersonnalisationResponseBean> listePersonnalisation) {
        return this.findRetourResponseBeanFromIdCharte("Santeclair", listePersonnalisation);
    }

}
