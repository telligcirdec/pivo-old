package santeclair.lunar.framework.util;

import java.text.Collator;
import java.util.Locale;

/**
 * Classe utilitaire de manipulation de String.
 * 
 * @author fmokhtari
 * 
 */
public class StringUtils {

    /**
     * Remplace les caractères accentuées par les caractères sans accent et enlève la cédille
     * 
     * @see
     * @return String la chaine mise à jour.
     */
    public static String remplacerAccentsEtEnleverCedille(String chaine) {

        if (chaine == null) {
            return null;
        }
        return chaine.replaceAll("[àâäáãåÀÂÄÁÃÅ]", "a")
                        .replaceAll("[îïìíÎÏÌÍ]", "i")
                        .replaceAll("[ôöòóõøÔÖÒÓÕØ]", "o")
                        .replaceAll("[ùûüúÙÛÜÚ]", "u")
                        .replaceAll("[éèêëÉÈÊË]", "e")
                        .replaceAll("[çÇ]", "c")
                        .replaceAll("[ÿ¾]", "y")
                        .replaceAll("[ñÑ]", "n");
    }

    /**
     * Remplace les caracteres et met en majuscule.
     * 
     * @return String la chaine mise à jour.
     */
    public static String formaterEtMettreEnMajuscule(String chaine) {

        if (chaine == null) {
            return null;
        }

        return remplacerAccentsEtEnleverCedille(chaine).toUpperCase(Locale.FRANCE);
    }

    /**
     * Comparaison en prenant en compte la LOCAL Francaise.
     * 
     * @return 0 si comparaison ok.
     */
    public static boolean comparerAvecLocal(String chaine1, String chaine2) {

        if (chaine1 == null) {
            return chaine2 == null;
        }
        else if (chaine2 == null) {
            return false;
        }
        else {
            Collator collactor = Collator.getInstance(Locale.FRANCE);
            collactor.setStrength(Collator.PRIMARY);
            return collactor.compare(chaine1, chaine2) == 0;
        }
    }

}
