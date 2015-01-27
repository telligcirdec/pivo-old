package santeclair.lunar.framework.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * Classe de tests unitaires pour DateUtils.
 * 
 * @author jfourmond
 * 
 */
public class DateUtilsUnitTest {

    /**
     * Teste la m�thode newDate.
     */
    @Test
    public void newDate() {
        Date date1 = DateUtils.newDate("25112002");
        Date date2 = DateUtils.newDate("251102");
        Date date3 = DateUtils.newDate("25/11/2002");
        Date date4 = DateUtils.newDate("2002-11-25T00:00:00");

        assertEquals(date1, date2);
        assertEquals(date2, date3);
        assertEquals(date3, date4);
    }

    /**
     * Teste la m�thode newDate avec un nombre de caract�res non autoris�.
     */
    @Test(expected = IllegalArgumentException.class)
    public void newDateMauvaisNbCaracteres() {
        DateUtils.newDate("2511");
    }

    /**
     * Teste la m�thode newDate avec une date mal format�e.
     */
    @Test(expected = IllegalArgumentException.class)
    public void newDateMalFormatee() {
        DateUtils.newDate("JJMMAA");
    }

    /**
     * Teste la m�thode formatDate
     */
    @Test
    public void formatDate() {
        DateUtils.format(new Date(), DateUtils.DDMMYY);
        assertTrue(true);
    }

    /**
     * Teste la m�thode getAgeFromDateOfBirth
     */
    @Test
    public void getAgeFromDateOfBirth() {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR) - 28, 4, 23);
        int age = DateUtils.getAgeFromDateOfBirth(c.getTime());
        assertEquals(27, age);
    }

}
