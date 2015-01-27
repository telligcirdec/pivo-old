package santeclair.lunar.framework.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Classe de tests unitaires pour SqlUtils.
 * 
 * @author jfourmond
 * 
 */
public class SqlUtilsUnitTest {

    /**
     * Teste la méthode quote.
     */
    @Test
    public void quote() {

        String param = "test";
        String resultat = SqlUtils.quote(param);
        assertEquals("'test'", resultat);
    }

    /**
     * Teste la méthode quote avec une chaine null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void quoteNull() {

        SqlUtils.quote(null);
    }

    /**
     * Teste la méthode isTypeTextuel.
     */
    @Test
    public void isTypeTextuel() {
        assertFalse(SqlUtils.isTypeTextuel(null));
        assertFalse(SqlUtils.isTypeTextuel("int"));
        assertTrue(SqlUtils.isTypeTextuel("varchar"));
        assertTrue(SqlUtils.isTypeTextuel("datetime"));
    }

}
