package santeclair.lunar.framework.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;

/**
 * Classe utilitaire permettant d'interagir avec du code SQL plus facilment.
 * 
 * @author jfourmond
 * 
 */
public class SqlUtils {

    public static final Set<String> TYPES_TEXTUELS = new HashSet<String>();

    public final static String NULL = "NULL";
    public final static String QUOTE = "'";
    public final static String DOUBLE_QUOTE = "''";

    /**
     * La liste des types MySQL qui doivent être entourés d'apostrophes avant d'être insérés.
     */
    static {
        TYPES_TEXTUELS.add("char");
        TYPES_TEXTUELS.add("varchar");
        TYPES_TEXTUELS.add("text");
        TYPES_TEXTUELS.add("longtext");
        TYPES_TEXTUELS.add("date");
        TYPES_TEXTUELS.add("datetime");
        TYPES_TEXTUELS.add("time");
        TYPES_TEXTUELS.add("timestamp");
    }

    /**
     * Entoure de quotes (' ') la chaine en parmaètre.
     */
    public static String quote(String valeur) {
        Preconditions.checkArgument(valeur != null);
        String valeurSansApostrophe = StringUtils.replace(valeur, QUOTE, DOUBLE_QUOTE);
        int taille = valeurSansApostrophe.length();
        StringBuilder sb = new StringBuilder(taille + 2);
        sb.append(QUOTE);
        sb.append(valeurSansApostrophe);
        sb.append(QUOTE);
        String valeurEntreQuote = sb.toString();
        return valeurEntreQuote;
    }

    /**
     * Renvoie true si la chaine en paramètre correspond à un type SQL considéré comme une chaine de caractères
     * (dans le sens où il doit être entouré d'apostrophes pour être inséré).
     * 
     * @param type
     */
    public static boolean isTypeTextuel(String type) {
        return TYPES_TEXTUELS.contains(type);
    }

}
