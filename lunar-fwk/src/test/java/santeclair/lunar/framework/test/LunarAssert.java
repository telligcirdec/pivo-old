/**
 * 
 */
package santeclair.lunar.framework.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Date;

/**
 * Classe d�finissant des assertions � utiliser en compl�ment de la classe Assert de JUnit.
 * 
 * @author jfourmond
 * 
 */
public class LunarAssert {

    /**
     * V�rifie que la Collection en param�tre contient au moins 1 �l�ment.
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
     * V�rifie que la Date en 1er param�tre est strictement plus r�cente que la Date en 2�me param�tre.
     */
    public static void assertDatePlusRecente(Date date1, Date date2) {
        if (date1 == null) {
            fail("La 1�re date est null");
        }
        if (date2 == null) {
            fail("La 2�me date est null");
        }
        assertTrue(date1.compareTo(date2) > 0);
    }

    /**
     * V�rifie que la Date en 1er param�tre est plus r�cente ou �gale � la Date en 2�me param�tre.
     */
    public static void assertDatePlusRecenteOuEgale(Date date1, Date date2) {
        if (date1 == null) {
            fail("La 1�re date est null");
        }
        if (date2 == null) {
            fail("La 2�me date est null");
        }
        assertTrue(date1.compareTo(date2) >= 0);
    }

}
