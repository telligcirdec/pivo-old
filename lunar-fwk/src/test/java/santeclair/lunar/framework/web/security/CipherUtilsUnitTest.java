/**
 * 
 */
package santeclair.lunar.framework.web.security;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de tests unitaires pour EncryptDecrypt
 * 
 * @author fmokhtari
 * 
 */
public class CipherUtilsUnitTest {

    @Test
    public void testEncryptDecypt() {
        String text = "J'ai développé un programme en Java_7 !!!";

        try {
            String encrypted = CipherUtils.encrypt(text).toString();
            Assert.assertNotNull(encrypted);

            String decrypted = CipherUtils.decrypt(encrypted).toString();
            Assert.assertEquals(text, decrypted);
        } catch (Exception e) {
            Assert.fail("Erreur de cryptage/décryptage du texte");
        }

    }

}
