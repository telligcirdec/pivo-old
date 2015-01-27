/**
 * 
 */
package santeclair.lunar.framework.web.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;

/**
 * V�rifie les acc�s et les r�les de l'utilisateur pr�alablement authentifi�.
 * Cette v�rification est faite en utilisant la base de donn�es solar.
 * 
 * @author jfourmond
 * 
 */
public interface SolarUserService<T extends Authentication> extends AuthenticationUserDetailsService<Authentication> {

}
