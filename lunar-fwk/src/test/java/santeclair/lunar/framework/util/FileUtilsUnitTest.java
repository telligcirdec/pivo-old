package santeclair.lunar.framework.util;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de tests unitaires pour FileUtils.
 * 
 * @author fmokhtari
 * 
 */
public class FileUtilsUnitTest {

    @Test
    public void genererFichier() {
        File file = null;
        try {
            String content = "toto";
            file = FileUtils.genererFichier(content.getBytes(), "toto");
            Assert.assertNotNull(file);
        } catch (Exception e) {
            Assert.fail("Problème de génération du fichier");
        } finally {
            if (file != null && file.exists()) {
                file.delete();
            }
        }

    }

    @Test
    public void genererFichierNull() {
        File file = FileUtils.genererFichier(null, "titi");
        Assert.assertNull(file);
    }
}
