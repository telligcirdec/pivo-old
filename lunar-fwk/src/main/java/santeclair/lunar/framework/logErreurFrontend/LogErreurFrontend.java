package santeclair.lunar.framework.logErreurFrontend;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Représente un log frontend
 * 
 * @author fmokhtari
 */
@MappedSuperclass
public class LogErreurFrontend {

    @Id
    @GeneratedValue
    @Column(name = "identifiant_log_erreur_frontend", length = 10, unique = true, nullable = false)
    private Integer id;

    @Column(name = "code_organisme_log_erreur_frontend", length = 11)
    private String codeOrganisme;

    @Column(name = "idpart_log_erreur_frontend", length = 20, nullable = false)
    private String idPart;

    @Column(name = "message_log_erreur_frontend", length = 1000, nullable = false)
    private String message;

    @Column(name = "stacktrace_log_erreur_frontend", length = 5000)
    private String stacktrace;

    @Column(name = "url_log_erreur_frontend", length = 1000, nullable = false)
    private String url;

    @Column(name = "useragent_log_erreur_frontend", length = 500, nullable = false)
    private String useragent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_log_erreur_frontend", nullable = false)
    private Date dateLog;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the codeOrganisme
     */
    public String getCodeOrganisme() {
        return codeOrganisme;
    }

    /**
     * @param codeOrganisme the codeOrganisme to set
     */
    public void setCodeOrganisme(String codeOrganisme) {
        this.codeOrganisme = codeOrganisme;
    }

    /**
     * @return the idPart
     */
    public String getIdPart() {
        return idPart;
    }

    /**
     * @param idPart the idPart to set
     */
    public void setIdPart(String idPart) {
        this.idPart = idPart;
    }

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
     * @return the dateLog
     */
    public Date getDateLog() {
        return dateLog;
    }

    /**
     * @param dateLog the dateLog to set
     */
    public void setDateLog(Date dateLog) {
        this.dateLog = dateLog;
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
