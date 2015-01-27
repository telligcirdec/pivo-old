/**
 * 
 */
package santeclair.lunar.framework.formatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * @author fmokhtari
 * 
 */
public class DecimalFormatter {

    /**
     * Formatte un BigDecimal avec deux chiffres apr�s la virgule (31.265333 => 31.26)
     * Sert notamment pour le formattage des prix dans les emails.
     * (remplace le formatter de velocity pour se d�barasser de velocity tools qui r�cup�re la terre enti�re).
     * */
    public String format(BigDecimal toFormat) {
        if (toFormat == null) {
            return "";
        }
        if (toFormat.intValue() == 0) {
            return "0,00";
        }
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');
        DecimalFormat formatter = new DecimalFormat("###,###.00", symbols);
        String result = formatter.format(toFormat);
        return result;
    }
}
