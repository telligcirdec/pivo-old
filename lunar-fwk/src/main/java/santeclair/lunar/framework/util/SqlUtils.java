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
     * La liste des types MySQL qui doivent �tre entour�s d'apostrophes avant d'�tre ins�r�s.
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
     * Entoure de quotes (' ') la chaine en parma�tre.
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
     * Renvoie true si la chaine en param�tre correspond � un type SQL consid�r� comme une chaine de caract�res
     * (dans le sens o� il doit �tre entour� d'apostrophes pour �tre ins�r�).
     * 
     * @param type
     */
    public static boolean isTypeTextuel(String type) {
        return TYPES_TEXTUELS.contains(type);
    }

}
