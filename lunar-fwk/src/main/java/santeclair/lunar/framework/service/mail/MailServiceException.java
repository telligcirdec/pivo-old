package santeclair.lunar.framework.service.mail;

@Deprecated
public class MailServiceException extends RuntimeException {

    private static final long serialVersionUID = -998419944525926442L;

    public MailServiceException() {
        super();
    }

    public MailServiceException(String arg0) {
        super(arg0);
    }

    public MailServiceException(Throwable arg0) {
        super(arg0);
    }

    public MailServiceException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
