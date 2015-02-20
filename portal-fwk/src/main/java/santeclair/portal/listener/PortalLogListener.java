package santeclair.portal.listener;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import santeclair.lunar.framework.osgi.BundleUtils;

@Component
public class PortalLogListener implements LogListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PortalLogListener.class);
    private static final Logger LOGGER_OSGI = LoggerFactory.getLogger("santeclair.portal.BUNDLE");

    @Override
    public void logged(LogEntry entry) {
        switch (entry.getLevel()) {
        case LogService.LOG_DEBUG:
            if (LOGGER_OSGI.isDebugEnabled()) {
                LOGGER_OSGI.debug(buildLogMessage(entry).toString());
            }
            break;
        case LogService.LOG_INFO:
            if (LOGGER_OSGI.isInfoEnabled()) {
                LOGGER_OSGI.info(buildLogMessage(entry).toString());
            }
            break;
        case LogService.LOG_WARNING:
            if (LOGGER_OSGI.isWarnEnabled()) {
                LOGGER_OSGI.warn(buildLogMessage(entry).toString());
            }
            break;
        case LogService.LOG_ERROR:
            if (LOGGER_OSGI.isErrorEnabled()) {
                LOGGER_OSGI.error(buildLogMessage(entry).toString());
            }
            break;
        default:
            LOGGER.warn("Le level de log ({}) de l'entry n'est pas reconnu.",
                            entry.getLevel());
            LOGGER_OSGI.debug(buildLogMessage(entry).toString());
            break;
        }
    }

    private StringBuffer buildLogMessage(LogEntry entry) {
        StringBuffer sbf = new StringBuffer();
        if (entry != null) {
            String bundleName = BundleUtils.bundleNameWithVersion(entry.getBundle());
            if (StringUtils.isNotEmpty(bundleName)) {
                sbf.append(bundleName).append(" - ");
            }
            sbf.append(entry.getMessage());
            Throwable t = entry.getException();
            if (t != null) {
                sbf.append("\n\n").append("=========================\n")
                                .append("= EXCEPTION \n")
                                .append("=========================\n\n")
                                .append(ExceptionUtils.getStackTrace(t));
            }
        } else {
            LOGGER.warn("L'entry de log est null.");
        }
        return sbf;
    }
}
