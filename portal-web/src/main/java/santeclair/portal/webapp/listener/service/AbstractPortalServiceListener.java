package santeclair.portal.webapp.listener.service;

import org.osgi.framework.ServiceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPortalServiceListener<T> implements PortalServiceListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPortalServiceListener.class);
    
    @Override
    public void serviceChanged(ServiceEvent arg0) {

    }

    @Override
    public void serviceModified(T service) {

    }

    @Override
    public void serviceModifiedEndmatch(T service) {

    }

    @Override
    public void serviceRegistered(T service) {

    }

    @Override
    public void serviceUnregistering(T service) {

    }
    
}
