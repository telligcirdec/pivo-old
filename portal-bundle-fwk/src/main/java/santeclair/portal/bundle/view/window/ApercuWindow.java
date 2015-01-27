package santeclair.portal.bundle.view.window;

import santeclair.portal.bundle.view.stream.PdfStream;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Window;

/**
 * Fenêtre d'aperçu du PDF une fois généré avec des valeurs d'exemple.
 * 
 * @author ldelemotte
 * 
 */
public class ApercuWindow extends Window {

    private static final long serialVersionUID = 1L;

    /** Constructeur. */
    public ApercuWindow(String caption, PdfStream pdfStream) {
        super(caption);

        this.setResizable(false);
        this.setWidth("620");
        this.setHeight("835");
        this.center();

        BrowserFrame bf = new BrowserFrame();
        bf.setSizeFull();

        StreamResource resource = new StreamResource(pdfStream, "courrier.pdf?" + System.currentTimeMillis());
        resource.setMIMEType("application/pdf");

        bf.setSource(resource);
        this.setContent(bf);
    }
}
