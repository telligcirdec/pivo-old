package santeclair.lunar.framework.service.email;


public class EmailServiceException extends RuntimeException {

    private static final long serialVersionUID = -1864063613140031847L;

    public EmailServiceException() {
        super();
    }

    public EmailServiceException(Throwable throwable) {
        super(throwable);
    }

    public EmailServiceException(String message) {
        super(message);
    }

    public EmailServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
