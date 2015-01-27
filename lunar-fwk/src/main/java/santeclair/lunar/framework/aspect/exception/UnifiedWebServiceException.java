/**
 * 
 */
package santeclair.lunar.framework.aspect.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebFault;

/**
 * Permet de gérer les exceptions SOAP en même temps que les exceptions REST.
 * 
 * */
@WebFault
public class UnifiedWebServiceException extends WebApplicationException {

    private static final long serialVersionUID = 1L;
    private String message;

    public UnifiedWebServiceException(String message, Response response) {
        super(response);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
