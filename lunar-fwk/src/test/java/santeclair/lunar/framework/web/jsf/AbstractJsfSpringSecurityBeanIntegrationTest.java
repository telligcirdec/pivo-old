package santeclair.lunar.framework.web.jsf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import santeclair.lunar.framework.web.security.UtilisateurSanteclair;

/**
 * Classe mère pour les classes de test d'intégration des backing beans JSF ayant besoin d'un utiliser Santéclair identifié.
 * 
 * @author jfourmond
 * 
 */
public class AbstractJsfSpringSecurityBeanIntegrationTest extends AbstractFacesContextBeanIntegrationTest {

    /**
     * Identifie l'utilisateur en tant qu'admin dans le contexte Spring Security.
     * 
     * @param login le login Santéclair complet. Ex : "SANTECLAIR\\JFOURMOND"
     */
    protected void logIn(String identifiant, String login, String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));
        UtilisateurSanteclair principal = new UtilisateurSanteclair(identifiant, login, null, null, null, true, authorities);
        Authentication token = new PreAuthenticatedAuthenticationToken(principal, "N/A");
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    /**
     * Identifie l'utilisateur en tant qu'admin dans le contexte Spring Security.
     * 
     * @param login le login Santéclair complet. Ex : "SANTECLAIR\\JFOURMOND"
     */
    protected void logIn(String login, String role) {
        logIn(null, login, role);
    }

    /**
     * Supprime l'utilisateur identifié du contexte Spring Security.
     */
    protected void logOut() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
