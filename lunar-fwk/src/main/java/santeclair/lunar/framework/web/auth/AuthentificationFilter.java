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
 * Filtre testant la présence en session d'un jeton.
 * 
 * @author brey
 * @author tducloyer
 */
public class AuthentificationFilter implements Filter {

    /** Liste des pages ne nécessitant pas d'autentification **/
    private static List<String> listePagesSansAuth;

    /** Liste des pages ne nécessitant pas d'authentification de type wildcard **/
    private static List<String> listePagesSansAuthWildCard;

    /** Gestion des logs **/
    private static final Logger LOG = LoggerFactory.getLogger(AuthentificationFilter.class);

    /**
     * L'initialisation du filtre permet de paramétrer les pages qui ne nécessite pas d'authentification.
     */
    public void init(FilterConfig config) throws ServletException {
        // Création d'un tableau des pages ne nécessitant pas d'authentification
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
            LOG.debug("Ajout de la page '" + page + "' à la liste des pages ne nécessitant pas d'authentification");
        }
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Vérification qu'il sagit bien d'une requête HTTP
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            LOG.error("Ce filtre ne gère que les requête HTTP");
            throw new ServletException("Ce filtre ne gère que les requête HTTP");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Si la page nécessite une authentification
        if (requireAuth(httpRequest)) {
            LOG.debug("La page nécessite d'être authentifié.");
            // Si il y a bien une session associée à la requête et qu'elle contient bien un jeton SSO et qu'il est OK
            if (httpRequest.getSession(false) != null
                            && httpRequest.getSession().getAttribute(SessionConstantes.RESULTAT_AUTHENTIFICATION_JETON) instanceof ResultatAuthentificationJeton
                            && ((ResultatAuthentificationJeton) httpRequest.getSession().getAttribute(SessionConstantes.RESULTAT_AUTHENTIFICATION_JETON)).isJetonValide()) {
                // Traitement normal de la requête
                LOG.debug("Utilisateur correctement authentifié, traitement normal de la requête");
                chain.doFilter(request, response);
            } else {
                // Sinon, on renvoi une erreur
                LOG.info("Tentative d'accès sans authentification, renvoie d'une erreur " + HttpServletResponse.SC_FORBIDDEN);
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            // Sinon traitement normal de la requête
            LOG.debug("La page ne nécessite pas d'être authentifié.");
            chain.doFilter(request, response);
        }
    }

    /**
     * Méthode indiquant la nécessité d'être authentifié pour accéder à une page
     * 
     * @param httpRequest
     * @return True si la page nécessite une authentifications
     */
    private boolean requireAuth(HttpServletRequest httpRequest) {
        // On récupère l'url de la requête
        String uri = httpRequest.getRequestURI();

        // On nettoye l'url en ne gardant que la page demandée
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

        // On renvoi false si la page demandée correspond à une page ne nécessitant pas d'authentification
        LOG.debug("Vérification de la nécessité d'être authentifié pour accéder à la page '" + uri + "'");
        return !listePagesSansAuth.contains(uri) && wildCardAuthRequired;
    }
}
