package santeclair.portal.webapp.listener.service.impl;

import org.osgi.service.log.LogReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import santeclair.portal.webapp.listener.service.AbstractPortalServiceListener;

@Component
public class LogReaderServiceListener extends AbstractPortalServiceListener<LogReaderService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogReaderServiceListener.class);
    
    @Override
    public String getFilter() {
        return null;
    }

    @Override
    public void serviceRegistered(LogReaderService service) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void serviceUnregistering(LogReaderService service) {
        // TODO Auto-generated method stub
        
    }

}
