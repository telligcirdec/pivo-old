/**
 * 
 */
package santeclair.lunar.framework.web.auth.client;

import santeclair.lunar.framework.web.auth.JetonSSO;

/**
 * @author jfourmond
 * 
 */
public interface AuthentificationWebClient {

    /**
     * Authentifie l'utilisateur à partir de l'ID de jeton et de l'ID du partenaire en paramètre.
     * 
     * @param idPart : L'identifiant du partenaire ayant généré le jeton à valider.
     * @param idJeton : L'identifiant du jeton à valider.
     * @param idAppli : L'identifiant de l'application demandant à vérifier le jeton.
     * @return le JetonSSO valide.
     */
    JetonSSO validerJetonMobile(String idPart, String idJeton, String idAppli);
}
