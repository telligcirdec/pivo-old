package santeclair.lunar.framework.web.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe de cryptage / décryptage d'une chaîne de caractère
 * */
public class CipherUtils
{

    static Logger log = LoggerFactory.getLogger(CipherUtils.class);

    // Clé de chiffrement
    private static byte[] key = {
            0x22, 0x65, 0x69, 0x73, 0x49, 0x73, 0x41, 0x48, 0x65, 0x63, 0x72, 0x68, 0x74, 0x68, 0x65, 0x79
    };

    /**
     * Cryptage de la chaîne en paramètre
     * */
    public static String encrypt(String strToEncrypt)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            final String encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
            return encryptedString;
        } catch (Exception e) {
            log.error("Error while encrypting", e);
        }
        return null;

    }

    /**
     * Décryptage de la chaîne en paramètre
     * */
    public static String decrypt(String strToDecrypt)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
            return decryptedString;
        } catch (Exception e) {
            log.error("Error while decrypting", e);

        }
        return null;
    }
}
