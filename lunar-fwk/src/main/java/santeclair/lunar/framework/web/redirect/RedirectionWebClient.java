package santeclair.lunar.framework.web.redirect;

import santeclair.lunar.framework.web.auth.JetonSSO;
import santeclair.lunar.framework.web.auth.ResultatUrlRedirection;

/**
 * Interroge le web service de redirection.
 * 
 * @author fmokhtari
 */
public interface RedirectionWebClient {

    /**
     * Recuperation de l'url de redirection.
     * Accessible avec la méthode HTTP POST.
     * 
     * Les id d'applications en paramètre correspondent à la colonne idappli de la table wsparam.tr_application.
     * 
     * @param idAppliCible Ex : "OWOP"
     * @param idAppliOrigine Ex : "DEVISDENTAIRE"
     * 
     * @param perso ex : "MAAF" correspond à la colonne code_charte de la table referentiel_gestionnaire.tr_charte.
     * @param listeOptions Ex : new String[]{SessionConstantes.OPTION_DIETETIQUE}
     * 
     */
    ResultatUrlRedirection recupererUrlRedirection(String idAppliCible, String idAppliOrigine, JetonSSO jetonSSO,
                    String idPart, String perso, String[] listeOptions);

}
