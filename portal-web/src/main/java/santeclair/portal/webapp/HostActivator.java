package santeclair.portal.webapp;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import santeclair.portal.listener.PortalBundleListener;
import santeclair.portal.listener.PortalFrameworkListner;
import santeclair.portal.listener.service.impl.EventAdminServiceListener;
import santeclair.portal.listener.service.impl.LogReaderServiceListener;

@Component
public class HostActivator implements BundleActivator {

    private static final Logger LOGGER = LoggerFactory.getLogger(HostActivator.class);

    /*
     * Bundle listener
     */
    @Autowired
    private PortalBundleListener bundleListener;

    /*
     * Framework listener
     */
    @Autowired
    private PortalFrameworkListner frameworkListner;

    /*
     * Services listener
     */
    @Autowired
    private LogReaderServiceListener logReaderServiceListener;
    @Autowired
    private EventAdminServiceListener eventAdminServiceListener;

    private BundleContext bundleContext;

    @Override
    public void start(BundleContext bundleContext)
                    throws InvalidSyntaxException {
        LOGGER.info("Begin starting HostActivator");
        this.bundleContext = bundleContext;

        // Ajout du listener de log au service de log
        logReaderServiceListener.registerItself(bundleContext);
        // Ajout du listener sur le service de gestion des event
        eventAdminServiceListener.registerItself(bundleContext);
        
        bundleListener.registerItself(bundleContext);
        frameworkListner.registerItself(bundleContext);

        LOGGER.info("Ending starting HostActivator");
    }

    @Override
    public void stop(BundleContext bundleContext) {
        LOGGER.debug("Stopping HostActivator");
        this.bundleContext = null;
    }

    public Bundle[] getBundles() {
        LOGGER.debug("HostActivator.getBundles");
        if (bundleContext != null) {
            return bundleContext.getBundles();
        }
        return null;
    }

    public BundleContext getBundleContext() {
        LOGGER.debug("HostActivator.getBundleContext");
        return bundleContext;
    }

}
