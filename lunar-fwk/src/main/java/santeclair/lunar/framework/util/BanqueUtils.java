package santeclair.lunar.framework.util;

import java.math.BigInteger;
import java.util.Locale;

/**
 * Classe permettant la manipulation et la validation des coordonnées bancaires.
 * 
 * @author ldelemotte
 * 
 */
public abstract class BanqueUtils {
    /** Static de Modulo utilisé pour calculé les clés. */
    public static final BigInteger B_MODULO = new BigInteger("97");

    /** Static de Modulo utilisé pour calculé les clés. */
    public static final int MODULO = 97;

    public static final String CLE_IBAN_VIDE = "00";

    /** Code Pays de la france. */
    public static final String CODE_FRANCE = "FR";

    /** Liste des caractères pour le décodage du rib. */
    public static final String DECODAGE_COMPTE_RIB = "ABCDEFGHIJKLMNOPQR STUVWXYZ";

    /** Liste des caractères pour le décodage de l'iban. */
    public static final String DECODAGE_COMPTE_IBAN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Vérifie si la clé Rib saisie est conforme au Rib.
     * 
     * @param numBanque
     * @param numGuichet
     * @param cleCompte
     * @param numCompteBanque
     * @return <code>true</code> si la clé est conforme.
     */
    public static boolean validerCleRIB(String numBanque, String numGuichet, String cleCompte, String numCompteBanque) {
        long valeurCodeBanque = 89 * Long.valueOf(numBanque).longValue();
        long valeurCodeGuichet = 15 * Long.valueOf(numGuichet).longValue();
        long valeurCompte = 3 * Long.valueOf(decoderCompteRib(numCompteBanque)).longValue();

        long clefCalculee = MODULO - ((valeurCodeBanque + valeurCodeGuichet + valeurCompte) % MODULO);

        return clefCalculee == Long.valueOf(cleCompte).longValue();
    }

    /**
     * Vérifie si la clé Iban saisie est conforme à l'Iban.
     * 
     * @param codePays
     * @param numBBAN
     * @return <code>true</code> si la clé IBAN est conforme.
     */
    public static boolean validerCleIBAN(String codePays, String numBBAN) {
        long cleIban = Long.valueOf(codePays.substring(2)).longValue();
        BigInteger valeurCodeIban = new BigInteger(decoderCompteIban(numBBAN + codePays.substring(0, 2)) + CLE_IBAN_VIDE);

        return cleIban == (calculCleIban(valeurCodeIban));
    }

    /**
     * Converti un Rib en Iban
     * 
     * @param rib
     * @return
     */
    public static String converteRibToIban(String rib) {
        long cleIban = calculCleIban(new BigInteger(decoderCompteIban(rib + CODE_FRANCE + CLE_IBAN_VIDE)));
        return CODE_FRANCE + (cleIban < 10 ? "0" : "") + cleIban + rib;
    }

    /**
     * Calcul de la clé IBAN.
     * 
     * @param valeurCodeIban
     * @return
     */
    private static long calculCleIban(BigInteger valeurCodeIban) {
        return 98 - (valeurCodeIban.mod(B_MODULO).intValue());
    }

    /**
     * Renvoie une chaine de caractère correspondant au numéro de compte avec les équivalences lettre/chiffre
     * 
     * @return String
     */
    private static String decoderCompteRib(String numCompteBanque) {
        String upperNumero = numCompteBanque.toUpperCase(Locale.FRENCH);
        StringBuffer compteDecode = new StringBuffer();
        for (int indexChar = 0; indexChar < upperNumero.length(); indexChar++) {
            char caractere = upperNumero.charAt(indexChar);
            int position = DECODAGE_COMPTE_RIB.indexOf(caractere);
            if (position != -1) {
                compteDecode.append((position % 9) + 1);
            } else {
                compteDecode.append(caractere);
            }
        }
        return compteDecode.toString();
    }

    /**
     * Renvoie une chaine de caractère correspondant au numéro de compte avec les équivalences lettre/chiffre
     * 
     * @return String
     */
    private static String decoderCompteIban(String numero) {
        String upperNumero = numero.toUpperCase(Locale.FRENCH);
        StringBuffer compteDecode = new StringBuffer();
        for (int indexChar = 0; indexChar < upperNumero.length(); indexChar++) {
            char caractere = upperNumero.charAt(indexChar);
            int position = DECODAGE_COMPTE_IBAN.indexOf(caractere);
            if (position != -1) {
                compteDecode.append(position + 10);
            } else {
                compteDecode.append(caractere);
            }
        }
        return compteDecode.toString();
    }
}
