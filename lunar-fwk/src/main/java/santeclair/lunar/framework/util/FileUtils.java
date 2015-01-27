/**
 * 
 */
package santeclair.lunar.framework.util;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;

/**
 * @author fmokhtari
 * 
 */
public class FileUtils {

    /**
     * Cr�ation de la pi�ce jointe avec un contenu de fichier au format diff�rent (PDF, CSV...)
     * d'ou le byte[] � la place du String
     * ATTENTION : les applications utilisant cette m�thode doivent supprimer le fichier g�n�r�.
     */
    public static File genererFichier(byte[] content, String nomFichier) {
        File fichierAGenerer = null;
        FileOutputStream fos = null;
        try {
            if (content != null) {
                fichierAGenerer = new File(nomFichier);
                fos = new FileOutputStream(fichierAGenerer);
                fos.write(content);
                fos.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (fos != null) {
                IOUtils.closeQuietly(fos);
            }
        }

        return fichierAGenerer;
    }

    /**
     * Supprime un fichier
     */
    public static void deleteFileSafely(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }
}
