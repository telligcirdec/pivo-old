/**
 * 
 */
package santeclair.lunar.framework.test;

import static santeclair.lunar.framework.util.DateUtils.newDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de tests unitaires pour LunarAssert.
 * 
 * @author jfourmond
 * 
 */
public class LunarAssertUnitTest {

    /**
     * Teste la méthode assertNotEmpty.
     */
    @Test
    public void assertNotEmpty() {

        try {
            LunarAssert.assertNotEmpty(null);
            Assert.fail();
        } catch (Throwable e) {
            /* normal */
        }

        List<String> collec = new ArrayList<String>();
        try {
            LunarAssert.assertNotEmpty(collec);
            Assert.fail();
        } catch (Throwable e) {
            /* normal */
        }

        collec.add("élément");
        try {
            LunarAssert.assertNotEmpty(collec);
            /* normal */
        } catch (Throwable e) {
            Assert.fail();
        }
    }

    /**
     * Teste la méthode assertDatePlusRecente.
     */
    @Test
    public void assertDatePlusRecente() {
        Date date1 = newDate("01011970");
        Date date2 = newDate("01011971");

        try {
            LunarAssert.assertDatePlusRecente(date2, date1);
            /* normal */
        } catch (Throwable e) {
            Assert.fail();
        }

        try {
            LunarAssert.assertDatePlusRecente(date1, date2);
            Assert.fail();
        } catch (Throwable e) {
            /* normal */
        }

        try {
            LunarAssert.assertDatePlusRecente(date1, date1);
            Assert.fail();
        } catch (Throwable e) {
            /* normal */
        }
    }

    /**
     * Teste la méthode assertDatePlusRecenteOuEgale.
     */
    @Test
    public void assertDatePlusRecenteOuEgale() {
        Date date1 = newDate("01011970");
        Date date2 = newDate("01011971");

        try {
            LunarAssert.assertDatePlusRecenteOuEgale(date2, date1);
            /* normal */
        } catch (Throwable e) {
            Assert.fail();
        }

        try {
            LunarAssert.assertDatePlusRecenteOuEgale(date1, date2);
            Assert.fail();
        } catch (Throwable e) {
            /* normal */
        }

        try {
            LunarAssert.assertDatePlusRecenteOuEgale(date1, date1);
            /* normal */
        } catch (Throwable e) {
            Assert.fail();
        }
    }

}
