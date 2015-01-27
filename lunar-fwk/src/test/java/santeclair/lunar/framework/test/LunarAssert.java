/**
 * 
 */
package santeclair.lunar.framework.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Date;

/**
 * Classe définissant des assertions à utiliser en complément de la classe Assert de JUnit.
 * 
 * @author jfourmond
 * 
 */
public class LunarAssert {

    /**
     * Vérifie que la Collection en paramètre contient au moins 1 élément.
     */
    public static void assertNotEmpty(Collection<?> collection) {
        if (collection == null) {
            fail("La collection est null");
        }
        if (collection.isEmpty()) {
            fail("La collection est vide");
        }
    }

    /**
     * Vérifie que la Date en 1er paramètre est strictement plus récente que la Date en 2ème paramètre.
     */
    public static void assertDatePlusRecente(Date date1, Date date2) {
        if (date1 == null) {
            fail("La 1ère date est null");
        }
        if (date2 == null) {
            fail("La 2ème date est null");
        }
        assertTrue(date1.compareTo(date2) > 0);
    }

    /**
     * Vérifie que la Date en 1er paramètre est plus récente ou égale à la Date en 2ème paramètre.
     */
    public static void assertDatePlusRecenteOuEgale(Date date1, Date date2) {
        if (date1 == null) {
            fail("La 1ère date est null");
        }
        if (date2 == null) {
            fail("La 2ème date est null");
        }
        assertTrue(date1.compareTo(date2) >= 0);
    }

}
