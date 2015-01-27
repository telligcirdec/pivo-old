package santeclair.lunar.framework.logErreurFrontend;

/**
 * DTO du webservice de log frontend
 * 
 * @author fmokhtari
 */

public class LogErreurFrontendDto {

    private String message;
    private String stacktrace;
    private String url;
    private String useragent;

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the stacktrace
     */
    public String getStacktrace() {
        return stacktrace;
    }

    /**
     * @param stacktrace the stacktrace to set
     */
    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the useragent
     */
    public String getUseragent() {
        return useragent;
    }

    /**
     * @param useragent the useragent to set
     */
    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }
}
