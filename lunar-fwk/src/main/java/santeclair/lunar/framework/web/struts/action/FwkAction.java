package santeclair.lunar.framework.web.struts.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.struts.ActionSupport;

/**
 * Toutes les applications utilisant une action de type Action, vont devoir h�rit�e de cette classe.<br>
 * 
 * Factorisation de comportements communs � toutes les Actions des projets Sant�clair.<br>
 * 
 * Int�gration de Spring gr�ce � l'h�ritage, <code>extends ActionSupport</code>.<br>
 * Ceci permet d'acc�der � la fabrique de Spring ou se situe tous les services (ou Bean Spring).<br>
 * 
 * @author cazoury
 * 
 * @deprecated Utiliser les anntations Controller � la place.
 */
@Deprecated
public abstract class FwkAction extends ActionSupport {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Pr�pare le message d'erreur � afficher.
     * 
     * @param request : La requ�te qui � provoquer l'erreur.
     * @param message : Le message d'erreur � afficher.
     */
    protected void afficherErreur(HttpServletRequest request, String message) {
        afficherErreur(request, ActionMessages.GLOBAL_MESSAGE, message);
    }

    /**
     * Pr�pare le message d'erreur � afficher.
     * 
     * @param request : La requ�te qui � provoquer l'erreur.
     * @param property : Le nom de la propri�t� erron�e.
     * @param message : Le message d'erreur � afficher.
     */
    protected void afficherErreur(HttpServletRequest request, String property, String message) {
        ActionMessages erreurs = new ActionMessages();
        erreurs.add(property, new ActionMessage(message));
        addErrors(request, erreurs);
    }

}
