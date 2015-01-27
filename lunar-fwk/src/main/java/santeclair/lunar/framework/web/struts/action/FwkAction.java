package santeclair.lunar.framework.web.struts.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.struts.ActionSupport;

/**
 * Toutes les applications utilisant une action de type Action, vont devoir héritée de cette classe.<br>
 * 
 * Factorisation de comportements communs à toutes les Actions des projets Santéclair.<br>
 * 
 * Intégration de Spring grâce à l'héritage, <code>extends ActionSupport</code>.<br>
 * Ceci permet d'accéder à la fabrique de Spring ou se situe tous les services (ou Bean Spring).<br>
 * 
 * @author cazoury
 * 
 * @deprecated Utiliser les anntations Controller à la place.
 */
@Deprecated
public abstract class FwkAction extends ActionSupport {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Prépare le message d'erreur à afficher.
     * 
     * @param request : La requête qui à provoquer l'erreur.
     * @param message : Le message d'erreur à afficher.
     */
    protected void afficherErreur(HttpServletRequest request, String message) {
        afficherErreur(request, ActionMessages.GLOBAL_MESSAGE, message);
    }

    /**
     * Prépare le message d'erreur à afficher.
     * 
     * @param request : La requête qui à provoquer l'erreur.
     * @param property : Le nom de la propriété erronée.
     * @param message : Le message d'erreur à afficher.
     */
    protected void afficherErreur(HttpServletRequest request, String property, String message) {
        ActionMessages erreurs = new ActionMessages();
        erreurs.add(property, new ActionMessage(message));
        addErrors(request, erreurs);
    }

}
