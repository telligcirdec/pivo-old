/**
 * 
 */
package santeclair.lunar.framework.formatter;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test des format de nombres
 * 
 */
public class DecimalFormatterTest {

    /**
     * Teste la méthode format
     */
    @Test
    public void format() {
        DecimalFormatter d = new DecimalFormatter();
        Assert.assertEquals("236,26", d.format(new BigDecimal("236.259633")));
        Assert.assertEquals("964 236,00", d.format(new BigDecimal("964236.00")));
        Assert.assertEquals("236,00", d.format(new BigDecimal("236")));
        Assert.assertEquals("4 852 236,01", d.format(new BigDecimal("4852236.01")));
        Assert.assertEquals("1,20", d.format(new BigDecimal("1.2")));
        Assert.assertEquals("0,00", d.format(BigDecimal.ZERO));
        Assert.assertEquals("", d.format(null));
    }

}
