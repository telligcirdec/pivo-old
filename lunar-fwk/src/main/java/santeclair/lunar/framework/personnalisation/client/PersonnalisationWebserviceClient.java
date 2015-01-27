package santeclair.lunar.framework.personnalisation.client;

import santeclair.lunar.framework.personnalisation.bean.CharteBean;
import santeclair.lunar.framework.personnalisation.bean.ListePersonnalisationResponseBean;
import santeclair.lunar.framework.personnalisation.bean.RetourPersonnalisationResponseBean;

public interface PersonnalisationWebserviceClient {

    /**
     * interroge le webservice owap-ws avec en paramètre l'idApp pour connaitre l'ensemble des persos existantes pour cette application.
     * 
     * @param idApp
     * @return ListePersonnalisationResponseBean
     */
    public ListePersonnalisationResponseBean getListePersonnalisation(String idApp);

    /**
     * Fournis les éléments de personnalisation pour une application et un partenaire donné
     * 
     * @param idPart
     * @param idApp
     * @return RetourPersonnalisationResponseBean
     */
    public RetourPersonnalisationResponseBean getDetailPersonnalisation(String idApp, String idCharte);

    /**
     * Retourne la charte par défaut d'un organisme.
     * 
     * @param codeOrganisme
     * @return
     */
    public CharteBean rechercheCharteCourrierOrganisme(String codeOrganisme);
}
