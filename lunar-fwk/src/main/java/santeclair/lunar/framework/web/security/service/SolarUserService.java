/**
 * 
 */
package santeclair.lunar.framework.web.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;

/**
 * Vérifie les accès et les rôles de l'utilisateur préalablement authentifié.
 * Cette vérification est faite en utilisant la base de données solar.
 * 
 * @author jfourmond
 * 
 */
public interface SolarUserService<T extends Authentication> extends AuthenticationUserDetailsService<Authentication> {

}
