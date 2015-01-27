package santeclair.lunar.framework.web.auth;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Classe utilitaire de gestion de session.
 * 
 * @author fmokhtari
 */
public class SessionUtils {

    /**
     * @param attributeName
     * @param clazz
     * @return attribut de nom attributeName
     */
    public static <T> T getAttribute(FacesContext context, String attributeName, Class<T> clazz) {
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        Object attribut = httpSession.getAttribute(attributeName);

        if (attribut != null) {
            return clazz.cast(attribut);
        }

        return null;
    }

    /**
     * Stocke l'attribut passé en paramètre de session (si aucune session n'existe, elle n'est PAS créée).
     * 
     * @param attributeName
     * @param attributeValue
     */
    public static void setAttribute(FacesContext context, String attributeName, Object attributeValue) {
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        httpSession.setAttribute(attributeName, attributeValue);
    }

    /**
     * Recupere un attribut en session via le Spring RequestContextHolder.
     * 
     * @param attributeName non de l'attribut
     * @param clazz classe de l'attribut
     * @return l'attribut.
     */
    public static <T> T getAttributeFromSpringRequestContextHolder(String attributeName, Class<T> clazz) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object attribut = request.getSession(false).getAttribute(attributeName);
        if (attribut != null) {
            return clazz.cast(attribut);
        }
        return null;
    }

    /**
     * Stocke l'attribut passé en paramètre de session via le Spring RequestContextHolder.
     * 
     * @param attributeName
     * @param attributeValue
     */
    public static void setAttributeSpringRequestContextHolder(String attributeName, Object attributeValue) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.getSession(false).setAttribute(attributeName, attributeValue);
    }
}
