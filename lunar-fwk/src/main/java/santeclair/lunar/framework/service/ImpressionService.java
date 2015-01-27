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
     * Envoie le fichier PDF � l'imprimante en param�tre.
     * 
     * @param nomImprimante Ex : "\\\\HYPERION\\LJ 4250N - Informatique D�veloppement"
     * 
     * @throws IOException si le fichier en param�tre n'est pas trouv�.
     * @throws PrinterException si le fichier ne peut �tre imprim�.
     */
    void imprimerFichierPdf(File fichierAImprimer, String nomImprimante) throws IOException, PrinterException;

}
