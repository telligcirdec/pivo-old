/**
 * 
 */
package santeclair.lunar.framework.web.auth;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

/**
 * Classe de tests unitaires pour AuthentificationServlet.
 * 
 * @author jfourmond
 * 
 */
public class AuthentificationServletUnitTest {

    private AuthentificationServlet servlet;

    @Before
    public void initProperties() {
        servlet = new AuthentificationServlet();
        final Properties props = new Properties();
        props.put("webext.ws.redirection.retour_00", "/faces/accueil.jsf");
        props.put("webext.ws.redirection.retour_01", "/faces/erreur01.jsf");
        props.put("webext.ws.redirection.retour_02", "/faces/erreur02.jsf");
        props.put("webext.ws.redirection.retour_03", "/faces/erreur03.jsf");
        servlet.setValidationProperties(props);
    }

    /**
     * Teste la méthode getPathRedirection pour un code retour 00.
     */
    @Test
    public void getPathRedirectionCode00() {

        String contextPath = "devis-dentaire-web";
        ResultatAuthentificationJeton resultat = new ResultatAuthentificationJeton();
        resultat.setCodeRetour(CodeRetour._00);
        String path = servlet.getPathRedirection(contextPath, resultat);
        assertEquals("devis-dentaire-web/faces/accueil.jsf", path);
    }

    /**
     * Teste la méthode getPathRedirection pour un code retour 01.
     */
    @Test
    public void getPathRedirectionCode01() {

        String contextPath = "devis-dentaire-web";
        ResultatAuthentificationJeton resultat = new ResultatAuthentificationJeton();
        resultat.setCodeRetour(CodeRetour._01);
        String path = servlet.getPathRedirection(contextPath, resultat);
        assertEquals("devis-dentaire-web/faces/erreur01.jsf", path);
    }

    /**
     * Teste la méthode getPathRedirection pour un code retour 02.
     */
    @Test
    public void getPathRedirectionCode02() {

        String contextPath = "devis-dentaire-web";
        ResultatAuthentificationJeton resultat = new ResultatAuthentificationJeton();
        resultat.setCodeRetour(CodeRetour._02);
        String path = servlet.getPathRedirection(contextPath, resultat);
        assertEquals("devis-dentaire-web/faces/erreur02.jsf", path);
    }

    /**
     * Teste la méthode getPathRedirection pour un code retour 03.
     */
    @Test
    public void getPathRedirectionCode03() {

        String contextPath = "devis-dentaire-web";
        ResultatAuthentificationJeton resultat = new ResultatAuthentificationJeton();
        resultat.setCodeRetour(CodeRetour._03);
        String path = servlet.getPathRedirection(contextPath, resultat);
        assertEquals("devis-dentaire-web/faces/erreur03.jsf", path);
    }

    /**
     * Teste la méthode getPathRedirection pour un code retour null.
     */
    @Test
    public void getPathRedirectionCodeNull() {

        String contextPath = "devis-dentaire-web";
        ResultatAuthentificationJeton resultat = new ResultatAuthentificationJeton();
        resultat.setCodeRetour(null);
        String path = servlet.getPathRedirection(contextPath, resultat);
        assertEquals("devis-dentaire-web/faces/erreur01.jsf", path);
    }
}
