package santeclair.portal.webapp.event;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEventHandler implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventHandler.class);

    @Override
    public final void registerEventHandlerItself(BundleContext bundleContext) {
        registerEventHandler(bundleContext, this);
    }

    @Override
    public String getFilter() {
        return null;
    }

    @Override
    public abstract String[] getTopics();

    public static void registerEventHandler(BundleContext bundleContext, EventHandler eventHandler) {
        if (bundleContext != null) {
            LOGGER.info("Registering event handler : {}", eventHandler.getClass().getName());
            Dictionary<String, Object> props = new Hashtable<>();
            props.put(EventConstants.EVENT_TOPIC, eventHandler.getTopics());
            if (StringUtils.isNotBlank(eventHandler.getFilter())) {
                props.put(EventConstants.EVENT_FILTER, eventHandler.getFilter());
            }
            bundleContext.registerService(org.osgi.service.event.EventHandler.class.getName(), eventHandler, props);
            LOGGER.info("Event handler registered : {}", eventHandler.getClass().getName());
        } else {
            LOGGER.error("L'handler d'event {} ne sera pas enregistré => le bundle context est null.", eventHandler.getClass().getName());
        }
    }

}
