/**
 * 
 */
package santeclair.lunar.framework.web.security;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

/**
 * Classe de tests unitaires pour UtilisateurSanteclair.
 * 
 * @author jfourmond
 * 
 */
public class UtilisateurSanteclairUnitTest {

    /**
     * Teste la méthode getPrenomNomTrigramme.
     */
    @Test
    public void getPrenomNomTrigramme() {
        UtilisateurSanteclair utilisateurSanteclair = new UtilisateurSanteclair("blabla", "blabla", "GAB", "Abitbol", "Georges", true, new ArrayList<GrantedAuthority>());
        assertEquals("Georges Abitbol (GAB)", utilisateurSanteclair.getPrenomNomTrigramme());
    }

}
