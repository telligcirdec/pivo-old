package santeclair.lunar.framework.util;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Classe de test de la vérificaiton du DNS de l'email.
 * 
 * @author ldelemotte
 * 
 */
@Ignore
public class VerificationDnsDomainUtilsTest {
    /**
     * Test de la méthode {@link VerificationDnsDomainUtils#checkDnsMailDomain(String)}
     */
    @Test
    public void testCheckDnsMailDomainZeroParam() {
        Assert.assertTrue(VerificationDnsDomainUtils.checkDnsMailDomain("gmail.com"));
    }

    /**
     * Test de la méthode {@link VerificationDnsDomainUtils#checkDnsMailDomain(String, String)}
     */
    @Test
    public void testCheckDnsMailDomainUnaram() {
        Assert.assertTrue(VerificationDnsDomainUtils.checkDnsMailDomain("santeclair.fr", "2000"));
    }

    /**
     * Test de la méthode {@link VerificationDnsDomainUtils#checkDnsMailDomain(String, String, String)}
     */
    @Test
    public void testCheckDnsMailDomainDeuxParam() {
        Assert.assertTrue(VerificationDnsDomainUtils.checkDnsMailDomain("santeclair.fr", "2", "3000"));
    }

    /**
     * Test de la méthode {@link VerificationDnsDomainUtils#checkDnsMailDomain(String).<br/>
     * Echec de la recherche.}
     */
    @Test
    public void testCheckDnsMailDomainFail() {
        Assert.assertFalse(VerificationDnsDomainUtils.checkDnsMailDomain("santefail.fr"));
    }

}
