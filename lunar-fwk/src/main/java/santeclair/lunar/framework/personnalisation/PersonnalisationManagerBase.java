package santeclair.lunar.framework.personnalisation;

import org.springframework.core.io.ByteArrayResource;

import santeclair.lunar.framework.personnalisation.bean.CharteBean;

public interface PersonnalisationManagerBase {

    /**
     * Recherche de la charte par defaut de l'organisme.
     * 
     * @param codeOrganisme
     * @return
     */
    public abstract CharteBean getCharteCourrier(String codeOrganisme);

    /**
     * Cherche la valeur associée a un code de personnalisation, si aucun contexte web n'existe la personnalisation
     * selectionnée sera Santéclair
     * 
     * @param code Code identifiant l'element de personnalisation
     * @return
     */
    public abstract String getCodeValue(String code);

    /**
     * Cherche la valeur associée a un code de personnalisation et a un identifiant de charte
     * 
     * @param code Code identifiant l'element de personnalisation
     * @param idCharte Identifiant de la charte a utiliser.
     * @return
     */
    public abstract String getCodeValue(String code, String idCharte);

    /**
     * Cherche la valeur associée a un code de personnalisation, si aucun contexte web n'existe la personnalisation
     * selectionnée sera Santéclair
     * Cette fonction sert spécifiquement a renvoyer des données correspondant a des fichiers sous forme d'inputStream
     * 
     * @param code Code identifiant l'element de personnalisation
     * @return
     */
    public abstract ByteArrayResource getInputStreamCodeValue(String code);

    /**
     * Cherche la valeur associée a un code de personnalisation et a un identifiant de charte
     * Cette fonction sert spécifiquement a renvoyer des données correspondant a des fichiers sous forme d'inputStream
     * 
     * @param code Code identifiant l'element de personnalisation
     * @param idCharte Identifiant de la charte a utiliser.
     * @return
     */
    public abstract ByteArrayResource getInputStreamCodeValue(String code, String idCharte);

    /**
     * Determine le type mime du fichier encodé en base 64, retourne null si la ressource n'est pas un dataurl.
     * 
     * @param code
     * @return
     */
    public abstract String getMimeType(String code);

    /**
     * Determine le type mime du fichier encodé en base 64, retourne null si la ressource n'est pas un dataurl.
     * 
     * @param code
     * @return
     */
    public abstract String getMimeType(String code, String idCharte);
}
