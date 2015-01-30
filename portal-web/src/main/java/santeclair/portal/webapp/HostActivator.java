package santeclair.portal.webapp;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import santeclair.portal.event.RootEventBusService;
import santeclair.portal.webapp.listener.PortalBundleListener;
import santeclair.portal.webapp.listener.PortalFrameworkListner;
import santeclair.portal.webapp.listener.service.impl.LogReaderServiceListener;

@Component
public class HostActivator implements BundleActivator {

    private static final Logger LOGGER = LoggerFactory.getLogger(HostActivator.class);

    @Autowired
    private PortalBundleListener bundleListener;
    @Autowired
    private PortalFrameworkListner frameworkListner;
    @Autowired
    private RootEventBusService rootEventBusService;
    @Autowired
    private LogReaderServiceListener logReaderServiceListener;

    private BundleContext bundleContext;

    @Override
    public void start(BundleContext bundleContext)
                    throws InvalidSyntaxException {
        LOGGER.info("Starting HostActivator");
        this.bundleContext = bundleContext;

        /*
         * Ajout du listener de log au service de log
         */
        logReaderServiceListener.setBundleContext(bundleContext);
        bundleContext.addServiceListener(logReaderServiceListener, logReaderServiceListener.getFilter());

        bundleContext.addBundleListener(bundleListener);
        bundleContext.addFrameworkListener(frameworkListner);

        // Enregistrement du service de gestion des �v�nements globlaux
        bundleContext.registerService(RootEventBusService.class.getName(), rootEventBusService, null);
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
