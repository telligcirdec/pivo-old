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
     * Création de la pièce jointe avec un contenu de fichier au format différent (PDF, CSV...)
     * d'ou le byte[] à la place du String
     * ATTENTION : les applications utilisant cette méthode doivent supprimer le fichier généré.
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
