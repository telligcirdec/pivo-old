package santeclair.lunar.framework.service;

/**
 * Déprécié en faveur de classes héritant de java.lang.RuntimeException.
 * 
 * @author jfourmond
 * 
 */
@Deprecated
public class ServiceException extends Exception {

    private static final long serialVersionUID = -4835156023992042143L;

    public ServiceException() {
        super();
    }

    public ServiceException(String arg0) {
        super(arg0);
    }

    public ServiceException(Throwable arg0) {
        super(arg0);
    }

    public ServiceException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
