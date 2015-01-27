package santeclair.portal.vaadin.deprecated.module.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import santeclair.lunar.framework.web.security.UtilisateurSanteclair;

/**
 * Helper pour tester les roles.
 * 
 * @author tsensebe
 * 
 */
@Deprecated
public class SecurityCheck {

	public static boolean hasRole(String role) {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(role));
	}

	/**
	 * @return l'utilisateur Santéclair identifié, ou null si aucune
	 *         authentification n'a été effectuée.
	 */
	public static UtilisateurSanteclair getUtilisateurSanteclair() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal != null && UtilisateurSanteclair.class.isAssignableFrom(principal.getClass())) {
				UtilisateurSanteclair utilisateurAuthentifie = (UtilisateurSanteclair) principal;
				return utilisateurAuthentifie;
			}
		}
		return null;
	}

}
