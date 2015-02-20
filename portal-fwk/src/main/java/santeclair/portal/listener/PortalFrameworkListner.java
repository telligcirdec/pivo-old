package santeclair.portal.listener;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import santeclair.lunar.framework.osgi.BundleUtils;

@Component
public class PortalFrameworkListner implements FrameworkListener {

    private static final Logger LOGGER_FRAMEWORK_EVENT = LoggerFactory.getLogger("santeclair.portal.FRAMEWORK_EVENT");

    @Value("#{generalProperties['framework.listener.log.level']}")
    private String logLevel;

    public static enum Level {
        DEBUG, INFO, WARN, ERROR
    }

    @Override
    public void frameworkEvent(FrameworkEvent frameworkEvent) {
        if (LOGGER_FRAMEWORK_EVENT.isDebugEnabled() && Level.DEBUG.name().equalsIgnoreCase(logLevel)) {
            LOGGER_FRAMEWORK_EVENT.debug(buildLogMessage(frameworkEvent));
        } else if (LOGGER_FRAMEWORK_EVENT.isInfoEnabled() && Level.INFO.name().equalsIgnoreCase(logLevel)) {
            LOGGER_FRAMEWORK_EVENT.info(buildLogMessage(frameworkEvent));
        } else if (LOGGER_FRAMEWORK_EVENT.isWarnEnabled() && Level.WARN.name().equalsIgnoreCase(logLevel)) {
            LOGGER_FRAMEWORK_EVENT.warn(buildLogMessage(frameworkEvent));
        } else if (LOGGER_FRAMEWORK_EVENT.isErrorEnabled() && Level.ERROR.name().equalsIgnoreCase(logLevel)) {
            LOGGER_FRAMEWORK_EVENT.error(buildLogMessage(frameworkEvent));
        }
    }

    public void registerItself(BundleContext bundleContext) {
        bundleContext.addFrameworkListener(this);
    }

    private String buildLogMessage(FrameworkEvent frameworkEvent) {
        StringBuffer sbf = new StringBuffer();
        if (frameworkEvent != null) {
            String bundleName = BundleUtils.bundleNameWithVersion(frameworkEvent.getBundle());
            if (StringUtils.isNotEmpty(bundleName)) {
                sbf.append(bundleName).append(" - ");
            }
            switch (frameworkEvent.getType()) {
            case (FrameworkEvent.ERROR):
                sbf.append("ERROR");
                break;
            case (FrameworkEvent.INFO):
                sbf.append("INFO");
                break;
            case (FrameworkEvent.PACKAGES_REFRESHED):
                sbf.append("PACKAGES_REFRESHED");
                break;
            case (FrameworkEvent.STARTED):
                sbf.append("STARTED");
                break;
            case (FrameworkEvent.STARTLEVEL_CHANGED):
                sbf.append("STARTLEVEL_CHANGED");
                break;
            case (FrameworkEvent.STOPPED):
                sbf.append("STOPPED");
                break;
            case (FrameworkEvent.STOPPED_BOOTCLASSPATH_MODIFIED):
                sbf.append("STOPPED_BOOTCLASSPATH_MODIFIED");
                break;
            case (FrameworkEvent.STOPPED_UPDATE):
                sbf.append("STOPPED_UPDATE");
                break;
            case (FrameworkEvent.WAIT_TIMEDOUT):
                sbf.append("WAIT_TIMEDOUT");
                break;
            case (FrameworkEvent.WARNING):
                sbf.append("WARNING");
                break;
            default:
                LOGGER_FRAMEWORK_EVENT.warn("Le type d'événement n'est pas reconnu : {}", frameworkEvent.getType());
                break;
            }
            Throwable t = frameworkEvent.getThrowable();
            if (t != null) {
                sbf.append("\n\n").append("=========================\n")
                                .append("= EXCEPTION \n")
                                .append("=========================\n\n")
                                .append(ExceptionUtils.getStackTrace(t));
            }
        } else {
            LOGGER_FRAMEWORK_EVENT.warn("Le message du framework event est null.");
        }
        return sbf.toString();
    }
}
