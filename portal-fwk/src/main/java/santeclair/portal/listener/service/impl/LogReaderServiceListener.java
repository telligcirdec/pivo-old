package santeclair.portal.listener.service.impl;

import org.osgi.framework.ServiceEvent;
import org.osgi.service.log.LogReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import santeclair.portal.listener.PortalLogListener;
import santeclair.portal.listener.service.AbstractPortalServiceListener;

@Component
public class LogReaderServiceListener extends AbstractPortalServiceListener<LogReaderService> {

    /**
     * 
     */
    private static final long serialVersionUID = 5912052764997483805L;

    @Autowired
    private PortalLogListener portalLogListener;

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
