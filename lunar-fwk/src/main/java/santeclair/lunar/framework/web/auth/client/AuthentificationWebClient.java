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
     * Authentifie l'utilisateur � partir de l'ID de jeton et de l'ID du partenaire en param�tre.
     * 
     * @param idPart : L'identifiant du partenaire ayant g�n�r� le jeton � valider.
     * @param idJeton : L'identifiant du jeton � valider.
     * @param idAppli : L'identifiant de l'application demandant � v�rifier le jeton.
     * @return le JetonSSO valide.
     */
    JetonSSO validerJetonMobile(String idPart, String idJeton, String idAppli);
}
