package santeclair.lunar.framework.logErreurFrontend;

/**
 * Interface de service de log des erreurs frontend
 * 
 * @author fmokhtari
 */
public interface LogErreurFrontendService<T extends LogErreurFrontend> {

    /**
     * @param logErreurFrontend
     */
    void log(T logErreurFrontend);

}
