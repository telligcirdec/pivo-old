package santeclair.portal.webapp.listener;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import santeclair.lunar.framework.osgi.BundleUtils;

@Component
public class PortalBundleListener implements BundleListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PortalBundleListener.class);
    private static final Logger LOGGER_BUNDLE_EVENT = LoggerFactory.getLogger("santeclair.portal.BUNDLE_EVENT");

    @Value("#{generalProperties['bundle.listener.log.level']}")
    private String logLevel;

    public static enum Level {
        DEBUG, INFO, WARN, ERROR
    }

    @Override
    public void bundleChanged(BundleEvent bundleEvent) {
        if (LOGGER_BUNDLE_EVENT.isDebugEnabled() && Level.DEBUG.name().equalsIgnoreCase(logLevel)) {
            LOGGER_BUNDLE_EVENT.debug(buildLogMessage(bundleEvent));
        } else if (LOGGER_BUNDLE_EVENT.isInfoEnabled() && Level.INFO.name().equalsIgnoreCase(logLevel)) {
            LOGGER_BUNDLE_EVENT.info(buildLogMessage(bundleEvent));
        } else if (LOGGER_BUNDLE_EVENT.isWarnEnabled() && Level.WARN.name().equalsIgnoreCase(logLevel)) {
            LOGGER_BUNDLE_EVENT.warn(buildLogMessage(bundleEvent));
        } else if (LOGGER_BUNDLE_EVENT.isErrorEnabled() && Level.ERROR.name().equalsIgnoreCase(logLevel)) {
            LOGGER_BUNDLE_EVENT.error(buildLogMessage(bundleEvent));
        }
    }

    public void registerItself(BundleContext bundleContext) {
        bundleContext.addBundleListener(this);
    }

    private String buildLogMessage(BundleEvent bundleEvent) {
        StringBuffer sbf = new StringBuffer();
        if (bundleEvent != null) {
            String bundleName = BundleUtils.bundleNameWithVersion(bundleEvent.getBundle());
            if (StringUtils.isNotEmpty(bundleName)) {
                sbf.append(bundleName).append(" - ");
            }
            switch (bundleEvent.getType()) {
            case (BundleEvent.INSTALLED):
                sbf.append("INSTALLED");
                break;
            case (BundleEvent.LAZY_ACTIVATION):
                sbf.append("LAZY_ACTIVATION");
                break;
            case (BundleEvent.RESOLVED):
                sbf.append("RESOLVED");
                break;
            case (BundleEvent.STARTED):
                sbf.append("STARTED");
                break;
            case (BundleEvent.STARTING):
                sbf.append("STARTING");
                break;
            case (BundleEvent.STOPPED):
                sbf.append("STOPPED");
                break;
            case (BundleEvent.STOPPING):
                sbf.append("STOPPING");
                break;
            case (BundleEvent.UNINSTALLED):
                sbf.append("UNINSTALLED");
                break;
            case (BundleEvent.UNRESOLVED):
                sbf.append("UNRESOLVED");
                break;
            case (BundleEvent.UPDATED):
                sbf.append("UPDATED");
                break;
            default:
                LOGGER.warn("Le type d'événement n'est pas reconnu : {}", bundleEvent.getType());
                break;
            }
        } else {
            LOGGER.warn("Le message du bundle event est null.");
        }
        return sbf.toString();
    }
}
