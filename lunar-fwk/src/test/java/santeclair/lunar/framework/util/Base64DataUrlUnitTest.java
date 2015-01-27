package santeclair.lunar.framework.util;

import org.junit.Assert;
import org.junit.Test;

public class Base64DataUrlUnitTest {

    /**
     * Teste la r�cup�ration de la partie File de la dataUrl pass� en param�tre.
     */
    @Test
    public void testDataUrlSpliting() {

        String dataUrl = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIwAAABYCAYAAAAnbx8HAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAKT2lDQ1BQaG90b3Nob3AgSUNDIHByb2ZpbGUAAHjanVNnVFPpFj333vRCS4iAlEtvUhUIIFJCi4AUkSYqIQkQSoghodkVUcERRUUEG8igiAOOjoCMFVEsDIoK2AfkIaKOg6OIisr74Xuja9a89";

        String resultat = Base64DataUrlTools.getBase64FileFromDataUrl(dataUrl);

        Assert.assertEquals(
                        "iVBORw0KGgoAAAANSUhEUgAAAIwAAABYCAYAAAAnbx8HAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAKT2lDQ1BQaG90b3Nob3AgSUNDIHByb2ZpbGUAAHjanVNnVFPpFj333vRCS4iAlEtvUhUIIFJCi4AUkSYqIQkQSoghodkVUcERRUUEG8igiAOOjoCMFVEsDIoK2AfkIaKOg6OIisr74Xuja9a89",
                        resultat);
    }

    /**
     * Test la r�cup�ration du mimeType depuis une dataUrl
     */
    @Test
    public void testGetMimeFromDataUrl() {

        String dataUrl = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIwAAABYCAYAAAAnbx8HAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAKT2lDQ1BQaG90b3Nob3AgSUNDIHByb2ZpbGUAAHjanVNnVFPpFj333vRCS4iAlEtvUhUIIFJCi4AUkSYqIQkQSoghodkVUcERRUUEG8igiAOOjoCMFVEsDIoK2AfkIaKOg6OIisr74Xuja9a89";

        String resultat = Base64DataUrlTools.getMimeTypeFromDataUrl(dataUrl);

        Assert.assertEquals(
                        "image/png",
                        resultat);
    }

    /**
     * R�cup�re l'extension de fichier depuis un type mime
     */
    @Test
    public void testGetFileExtensionFromMime() {
        String mimeType = "image/png";

        String resultat = Base64DataUrlTools.getFileExtensionFromMimeType(mimeType);

        Assert.assertEquals("png", resultat);
    }

}
