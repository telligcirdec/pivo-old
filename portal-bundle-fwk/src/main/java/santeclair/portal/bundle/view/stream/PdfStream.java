package santeclair.portal.bundle.view.stream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.server.StreamResource.StreamSource;

/**
 * Objet PDF généré depuis un flux html
 * 
 * @author ldelemotte
 * 
 */
public class PdfStream implements StreamSource {
    /** Serial version UID. */
    private static final long serialVersionUID = 2821235869783600218L;

    private InputStream stream;

    /** Constructeur avec le flux HTML. */
    public PdfStream(byte[] pdf) {
        stream = new ByteArrayInputStream(pdf);
    }

    @Override
    public InputStream getStream() {
        return stream;
    }
}
