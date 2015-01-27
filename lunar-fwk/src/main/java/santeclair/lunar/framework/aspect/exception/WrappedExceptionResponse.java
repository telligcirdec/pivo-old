/**
 * 
 */
package santeclair.lunar.framework.aspect.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Cet objet permet de transporter une exception lors de la sérialisation dans
 * un objet Reponse.
 * 
 * @author fmokhtari
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WrappedExceptionResponse {

    private String info = "! Default Value : this exception does not implement santeclair.lunar.framework.aspect.exception.InfoException";

    @XmlJavaTypeAdapter(ThrowableAdapter.class)
    private Throwable exception;

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public Throwable getException() {
        return exception;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
