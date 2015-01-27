package santeclair.lunar.framework.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class NumberUtils {
    private static DecimalFormat df;

    static {
        df = new DecimalFormat("#0.00");
        df.setCurrency(Currency.getInstance(Locale.FRANCE));
    }

    /**
     * Méthode safe pour convertir un {@link Float} en {@link String}
     */
    public static String toString(Float value) {
        if (value == null) {
            return "";
        }
        return df.format(value);
    }

    /**
     * Méthode safe pour convertir un {@link Double} en {@link String}
     */
    public static String toString(Double value) {
        if (value == null) {
            return "";
        }
        return df.format(value);
    }

    /**
     * Méthode safe pour convertir un {@link BigDecimal} en {@link String}
     */
    public static String toString(BigDecimal value) {
        if (value == null) {
            return "";
        }
        return toString(value.doubleValue());
    }

    /**
     * Méthode safe pour convertir un {@link String} en {@link Double}
     */
    public static Float toFloat(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return safedToBigDecimal(value).floatValue();
    }

    /**
     * Méthode safe pour convertir un {@link String} en {@link Double}
     */
    public static Double toDouble(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return safedToBigDecimal(value).doubleValue();
    }

    /**
     * Méthode safe pour convertir un {@link String} en {@link BigDecimal}
     */
    public static BigDecimal toBigDecimal(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return safedToBigDecimal(value);
    }

    private static BigDecimal safedToBigDecimal(String value) {
        value = value.trim();
        if (value.indexOf(",") >= 0) {
            value = value.replaceAll(",", ".");
        }
        return new BigDecimal(value);
    }

    private NumberUtils() {
    }
}
