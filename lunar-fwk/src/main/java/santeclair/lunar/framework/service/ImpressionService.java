/**
 * 
 */
package santeclair.lunar.framework.service;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;

/**
 * Interface du service d'impression de fichiers.
 * 
 * @author jfourmond
 * 
 */
public interface ImpressionService {

    /**
     * Envoie le fichier PDF à l'imprimante en paramètre.
     * 
     * @param nomImprimante Ex : "\\\\HYPERION\\LJ 4250N - Informatique Développement"
     * 
     * @throws IOException si le fichier en paramètre n'est pas trouvé.
     * @throws PrinterException si le fichier ne peut être imprimé.
     */
    void imprimerFichierPdf(File fichierAImprimer, String nomImprimante) throws IOException, PrinterException;

}
