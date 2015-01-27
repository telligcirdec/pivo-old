/**
 * 
 */
package santeclair.lunar.framework.service.impl;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import santeclair.lunar.framework.service.ImpressionService;

/**
 * Implémentation du service d'impression de fichiers.
 * 
 * @author jfourmond
 * 
 */
@Service
public class ImpressionServiceImpl implements ImpressionService {

    /**
     * {@inheritDoc}
     */
    @Override
    public void imprimerFichierPdf(File fichierAImprimer, String nomImprimante) throws IOException, PrinterException {
        PrintService imprimante = getImprimante(nomImprimante);
        PDDocument pdfBoxDocument = PDDocument.load(fichierAImprimer);
        PrinterJob tacheImprimante = PrinterJob.getPrinterJob();
        tacheImprimante.setPrintService(imprimante);
        tacheImprimante.setJobName(fichierAImprimer.getName());
        tacheImprimante.setPageable(pdfBoxDocument);
        pdfBoxDocument.silentPrint(tacheImprimante);
    }

    /**
     * Renvoie l'imprimante correspondant au nom en paramètre.
     */
    private PrintService getImprimante(String nomImprimanteDemandee) {
        PrintService[] imprimantesTrouvees = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService imprimanteTrouvee : imprimantesTrouvees) {
            String nomImprimanteTrouvee = imprimanteTrouvee.getName();
            if (nomImprimanteTrouvee.equals(nomImprimanteDemandee)) {
                return imprimanteTrouvee;
            }
        }
        return null;
    }

}
