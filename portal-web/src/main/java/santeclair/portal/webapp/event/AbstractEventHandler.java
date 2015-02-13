package santeclair.portal.webapp.event;

import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEventHandler implements EventHandler {

    private static final String EVENT_HANDLER_ID = "event.handler.id";

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventHandler.class);

    @Override
    public final void registerEventHandlerItself(BundleContext bundleContext) {
        try {
            registerEventHandler(bundleContext, this);
        } catch (InvalidSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void unregisterEventHandlerItSelf(BundleContext bundleContext) {
        try {
            unregisterEventHandler(bundleContext, this);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void registerEventHandler(BundleContext bundleContext, EventHandler eventHandler) throws InvalidSyntaxException {
        if (bundleContext != null) {
            Method[] methodToChecks = eventHandler.getClass().getDeclaredMethods();
            for (Method method : methodToChecks) {
                if (method.isAnnotationPresent(Subscriber.class)) {
                    Subscriber subscriber = method.getAnnotation(Subscriber.class);
                    String topic = subscriber.topic();
                    String filterStr = subscriber.filter();
                    
                    String eventHandlerId = Integer.toHexString(eventHandler.hashCode());
                    LOGGER.info("Registering event handler method {} from class {} on topic {} with filter '{}' and event handler id {}", method.getName(), eventHandler.getClass()
                                    .getName(),
                                    topic, subscriber.filter(), eventHandlerId);
                    Dictionary<String, Object> props = new Hashtable<>();
                    props.put(EventConstants.EVENT_TOPIC, topic);
                    props.put(EVENT_HANDLER_ID, eventHandlerId);
                    if (StringUtils.isNotBlank(filterStr)) {
                        Filter filter = bundleContext.createFilter(filterStr);
                        props.put(EventConstants.EVENT_FILTER, filter.toString());
                    }
                    AnnotedMethodEventHandler annotedMethodEventHandler = new AnnotedMethodEventHandler(eventHandler, method);
                    bundleContext.registerService(org.osgi.service.event.EventHandler.class.getName(), annotedMethodEventHandler, props);
                    LOGGER.info("Event handler registered : {}.{} on topic {} with filter {}.", eventHandler.getClass(), method.getName(), topic, subscriber.filter());
                }
            }
        } else {
            LOGGER.error("L'handler d'event {} ne sera pas enregistré => le bundle context est null.", eventHandler.getClass().getName());
        }
    }

    public static void unregisterEventHandler(BundleContext bundleContext, EventHandler eventHandler) throws InvalidSyntaxException {
        if (bundleContext != null) {
            String eventHandlerId = Integer.toHexString(eventHandler.hashCode());

            String filterStr = "(" + EVENT_HANDLER_ID + "=" + eventHandlerId + ")";
            Filter filter = bundleContext.createFilter(filterStr);
            ServiceReference<?>[] serviceReferences = bundleContext.getAllServiceReferences(null, filter.toString());
            LOGGER.debug("{}Service references found : {}", "", serviceReferences);
            for (ServiceReference<?> serviceReference : serviceReferences) {
                LOGGER.info("Unregister event handler {} for event handler id : {}", serviceReference.getClass().getName(), eventHandlerId);
                bundleContext.ungetService(serviceReference);
            }
        } else {
            LOGGER.error("L'handler d'event {} ne sera pas enregistré => le bundle context est null.", eventHandler.getClass().getName());
        }
    }
}
