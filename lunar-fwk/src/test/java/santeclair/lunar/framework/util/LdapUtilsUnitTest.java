package santeclair.lunar.framework.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import santeclair.lunar.framework.bean.LdapAttributeBean;

/**
 * Classe de tests unitaires pour JourUtils.
 * 
 * @author jfourmond
 * 
 */
public class LdapUtilsUnitTest {

    /**
     * Teste la méthode encodeMotDePasseLdap.
     */
    @Test
    public void encodeMotDePasseLdap() {

        String mdpEncode = LdapUtils.encodeMotDePasseLdap("password");
        assertEquals("{MD5}X03MO1qnZdYdgyfeuILPmQ==", mdpEncode);
    }

    /**
     * Teste la méthode getAttributsLdap avec un objet qui a 2 annotations @LdapAttribute.
     */
    @Test
    public void getAttributsLdap2() {
        TestLdapObject testLdapObject = new TestLdapObject();
        List<LdapAttributeBean> attributsLdap = LdapUtils.getAttributsLdap(testLdapObject);
        assertEquals(2, attributsLdap.size());
        
        int count = 0;
        for (LdapAttributeBean attribut : attributsLdap) {
            if (attribut.getNom().equals("nom")) {
                assertEquals("ABITBOL", attribut.getValeur());
                count++;
            }
            if (attribut.getNom().equals("age")) {
                assertEquals(50, attribut.getValeur());
                count++;
            }
        }
        assertEquals(2, count);
    }

    /**
     * Teste la méthode getAttributsLdap avec un objet qui a 0 annotations @LdapAttribute.
     */
    @Test
    public void getAttributsLdap0() {
        List<LdapAttributeBean> attributsLdap = LdapUtils.getAttributsLdap(new Object());
        assertEquals(0, attributsLdap.size());
    }
}
