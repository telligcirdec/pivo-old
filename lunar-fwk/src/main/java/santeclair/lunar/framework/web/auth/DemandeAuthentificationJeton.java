/**
 * 
 */
package santeclair.lunar.framework.web.auth;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Demande d'authentification d'un jeton à destination du web service webext.
 * 
 * @author jfourmond
 * 
 */
@XmlRootElement
public class DemandeAuthentificationJeton {

    /** L'id du jeton SSO. */
    private String idJeton;

    /** L'application appelée. Correspond à idappli dans wsparam.tr_application. */
    private String idAppliCible;

    /** L'assureur. Correspond à idpart dans referentiel.tr_partenaire. */
    private String idPartenaire;

    /**
     * La perso (facultatif). Correspond à idcharte dans
     * referentiel_gestionnaire.tr_charte.
     */
    private String idCharteGraphique;

    /**
     * L'application appelante (facultatif). Correspond à idappli dans
     * wsparam.tr_application.
     */
    private String idAppliOrigine;

    /**
     * Une liste facultative d'options séparées par des "-". (Exemple :
     * dentaire-audio-novideo)
     */
    private String options;

    /*
     * =======================================================* constructeur
     * =======================================================
     */

    /**
     * Constructeur avec tous les arguments.
     */
    public DemandeAuthentificationJeton(String idJeton, String idAppliCible,
                                        String idPartenaire, String idCharteGraphique,
                                        String idAppliOrigine, String options) {
        super();
        this.idJeton = idJeton;
        this.idAppliCible = idAppliCible;
        this.idPartenaire = idPartenaire;
        this.idCharteGraphique = idCharteGraphique;
        this.idAppliOrigine = idAppliOrigine;
        this.options = options;
    }

    /**
     * Constructeur par défaut.
     */
    public DemandeAuthentificationJeton() {
    }

    /**
     * Constructeur pour l'athentification mobile.
     * 
     * @param idJeton
     * @param idAppliCible
     * @param idPartenaire
     */
    public DemandeAuthentificationJeton(String idJeton, String idAppliCible,
                                        String idPartenaire) {
        super();
        this.idJeton = idJeton;
        this.idAppliCible = idAppliCible;
        this.idPartenaire = idPartenaire;
    }

    /*
     * =======================================================* getters &
     * setters=======================================================
     */

    /**
     * @return the idJeton
     */
    public String getIdJeton() {
        return idJeton;
    }

    /**
     * @param idJeton
     *            the idJeton to set
     */
    public void setIdJeton(String idJeton) {
        this.idJeton = idJeton;
    }

    /**
     * @return the idAppliCible
     */
    public String getIdAppliCible() {
        return idAppliCible;
    }

    /**
     * @param idAppliCible
     *            the idAppliCible to set
     */
    public void setIdAppliCible(String idAppliCible) {
        this.idAppliCible = idAppliCible;
    }

    /**
     * @return the idPartenaire
     */
    public String getIdPartenaire() {
        return idPartenaire;
    }

    /**
     * @param idPartenaire
     *            the idPartenaire to set
     */
    public void setIdPartenaire(String idPartenaire) {
        this.idPartenaire = idPartenaire;
    }

    /**
     * @return the idCharteGraphique
     */
    public String getIdCharteGraphique() {
        return idCharteGraphique;
    }

    /**
     * @param idCharteGraphique
     *            the idCharteGraphique to set
     */
    public void setIdCharteGraphique(String idCharteGraphique) {
        this.idCharteGraphique = idCharteGraphique;
    }

    /**
     * @return the idAppliOrigine
     */
    public String getIdAppliOrigine() {
        return idAppliOrigine;
    }

    /**
     * @param idAppliOrigine
     *            the idAppliOrigine to set
     */
    public void setIdAppliOrigine(String idAppliOrigine) {
        this.idAppliOrigine = idAppliOrigine;
    }

    /**
     * @return the options
     */
    public String getOptions() {
        return options;
    }

    /**
     * @param options
     *            the options to set
     */
    public void setOptions(String options) {
        this.options = options;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DemandeAuthentificationJeton [idJeton=" + idJeton
                        + ", idAppliCible=" + idAppliCible + ", idPartenaire="
                        + idPartenaire + ", idCharteGraphique=" + idCharteGraphique
                        + ", idAppliOrigine=" + idAppliOrigine + ", options=" + options
                        + "]";
    }

}
