package santeclair.lunar.framework.web.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filtre testant la pr�sence en session d'un jeton.
 * 
 * @author brey
 * @author tducloyer
 */
public class AuthentificationFilter implements Filter {

    /** Liste des pages ne n�cessitant pas d'autentification **/
    private static List<String> listePagesSansAuth;

    /** Liste des pages ne n�cessitant pas d'authentification de type wildcard **/
    private static List<String> listePagesSansAuthWildCard;

    /** Gestion des logs **/
    private static final Logger LOG = LoggerFactory.getLogger(AuthentificationFilter.class);

    /**
     * L'initialisation du filtre permet de param�trer les pages qui ne n�cessite pas d'authentification.
     */
    public void init(FilterConfig config) throws ServletException {
        // Cr�ation d'un tableau des pages ne n�cessitant pas d'authentification
        String[] tabPagesSansAuth = config.getInitParameter("pagesSansAuthentification").split(",");
        listePagesSansAuth = new ArrayList<>();
        listePagesSansAuthWildCard = new ArrayList<>();
        for (String element : tabPagesSansAuth) {
            String page = element.trim();
            if (!page.startsWith("/")) {
                page = "/" + page;
            } else if (page.contains("*")) {
                listePagesSansAuthWildCard.add(page.replace("*", ""));
            }
            listePagesSansAuth.add(page);
            LOG.debug("Ajout de la page '" + page + "' � la liste des pages ne n�cessitant pas d'authentification");
        }
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // V�rification qu'il sagit bien d'une requ�te HTTP
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            LOG.error("Ce filtre ne g�re que les requ�te HTTP");
            throw new ServletException("Ce filtre ne g�re que les requ�te HTTP");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Si la page n�cessite une authentification
        if (requireAuth(httpRequest)) {
            LOG.debug("La page n�cessite d'�tre authentifi�.");
            // Si il y a bien une session associ�e � la requ�te et qu'elle contient bien un jeton SSO et qu'il est OK
            if (httpRequest.getSession(false) != null
                            && httpRequest.getSession().getAttribute(SessionConstantes.RESULTAT_AUTHENTIFICATION_JETON) instanceof ResultatAuthentificationJeton
                            && ((ResultatAuthentificationJeton) httpRequest.getSession().getAttribute(SessionConstantes.RESULTAT_AUTHENTIFICATION_JETON)).isJetonValide()) {
                // Traitement normal de la requ�te
                LOG.debug("Utilisateur correctement authentifi�, traitement normal de la requ�te");
                chain.doFilter(request, response);
            } else {
                // Sinon, on renvoi une erreur
                LOG.info("Tentative d'acc�s sans authentification, renvoie d'une erreur " + HttpServletResponse.SC_FORBIDDEN);
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            // Sinon traitement normal de la requ�te
            LOG.debug("La page ne n�cessite pas d'�tre authentifi�.");
            chain.doFilter(request, response);
        }
    }

    /**
     * M�thode indiquant la n�cessit� d'�tre authentifi� pour acc�der � une page
     * 
     * @param httpRequest
     * @return True si la page n�cessite une authentifications
     */
    private boolean requireAuth(HttpServletRequest httpRequest) {
        // On r�cup�re l'url de la requ�te
        String uri = httpRequest.getRequestURI();

        // On nettoye l'url en ne gardant que la page demand�e
        int indexParam = uri.indexOf(';');
        if (indexParam > 0) {
            uri = uri.substring(0, indexParam);
        }
        uri = uri.substring(httpRequest.getContextPath().length(), uri.length());

        boolean wildCardAuthRequired = true;
        for (String path : listePagesSansAuthWildCard) {
            if (uri.startsWith(path)) {
                wildCardAuthRequired = false;
            }
        }

        // On renvoi false si la page demand�e correspond � une page ne n�cessitant pas d'authentification
        LOG.debug("V�rification de la n�cessit� d'�tre authentifi� pour acc�der � la page '" + uri + "'");
        return !listePagesSansAuth.contains(uri) && wildCardAuthRequired;
    }
}
