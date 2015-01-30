package santeclair.portal.webapp.listener.service.impl;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.service.log.LogReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import santeclair.portal.webapp.listener.PortalLogListener;
import santeclair.portal.webapp.listener.service.AbstractPortalServiceListener;

@Component
public class LogReaderServiceListener extends AbstractPortalServiceListener<LogReaderService> {

    @Autowired
    private PortalLogListener portalLogListener;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(LogReaderServiceListener.class);

    @Override
    public void serviceRegistered(LogReaderService service, ServiceEvent serviceEvent) {
        LOGGER.info("Enregistrement du listener de log : {}", portalLogListener);
        service.addLogListener(portalLogListener);
    }

    @Override
    public void serviceUnregistering(LogReaderService service, ServiceEvent serviceEvent) {
        LOGGER.info("Suppression du listener de log : {}", portalLogListener);
        service.removeLogListener(portalLogListener);
    }

}
