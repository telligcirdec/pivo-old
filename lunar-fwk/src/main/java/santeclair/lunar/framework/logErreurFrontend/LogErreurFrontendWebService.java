package santeclair.lunar.framework.logErreurFrontend;

/**
 * Interface de log frontend
 * 
 * @author fmokhtari
 */
public interface LogErreurFrontendWebService {

    /**
     * Met à jour les informations de l'assuré en session
     */
    void log(LogErreurFrontendDto logErreurFrontendDto);

}
