/**
 * 
 */
package santeclair.lunar.framework.util;

import santeclair.lunar.framework.bean.LdapAttribute;

/**
 * Objet de test pour LdapUtilsUnitTest.
 * 
 * @author jfourmond
 * 
 */
public class TestLdapObject {

    public String getPrenom() {
        return "Georges";
    }

    @LdapAttribute("nom")
    public String getNom() {
        return "ABITBOL";
    }

    @LdapAttribute("age")
    public int getAge() {
        return 50;
    }

}
