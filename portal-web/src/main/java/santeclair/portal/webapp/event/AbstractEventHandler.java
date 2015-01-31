package santeclair.portal.webapp.event;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEventHandler implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventHandler.class);

    public final void registerItself(BundleContext bundleContext) {
        if (bundleContext != null) {
            LOGGER.info("Registering event handler : {}", this.getClass().getName());
            Dictionary<String, Object> props = new Hashtable<>();
            props.put(EventConstants.EVENT_TOPIC, getTopics());
            if (StringUtils.isNotBlank(getFilter())) {
                props.put(EventConstants.EVENT_FILTER, getFilter());
            }
            bundleContext.registerService(EventHandler.class.getName(), this, props);
            LOGGER.info("Event handler registered : {}", this.getClass().getName());
        } else {
            LOGGER.error("L'handler d'event {} ne sera pas enregistré => le bundle context est null.", this.getClass().getName());
        }

    }

    public String getFilter() {
        return null;
    }

    public abstract String[] getTopics();

}
