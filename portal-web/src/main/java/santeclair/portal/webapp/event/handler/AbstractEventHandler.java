package santeclair.portal.webapp.event.handler;

import static org.osgi.service.event.EventConstants.EVENT_FILTER;
import static org.osgi.service.event.EventConstants.EVENT_TOPIC;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_HANDLER_ID;

import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEventHandler implements PortalEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventHandler.class);

    @Override
    public final void registerEventHandlerItself(BundleContext bundleContext) {
        try {
            registerEventHandler(bundleContext, this);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void unregisterEventHandlerItSelf(BundleContext bundleContext) {
        try {
            unregisterEventHandler(bundleContext, this);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void registerEventHandler(BundleContext bundleContext, PortalEventHandler portalEventHandler) throws InvalidSyntaxException {
        if (bundleContext != null) {
            Method[] methodToChecks = portalEventHandler.getClass().getDeclaredMethods();
            for (Method method : methodToChecks) {
                if (method.isAnnotationPresent(Subscriber.class)) {
                    Subscriber subscriber = method.getAnnotation(Subscriber.class);
                    String topic = subscriber.topic();
                    String eventHandlerId = Integer.toHexString(portalEventHandler.hashCode());
                    LOGGER.info("Registering event handler method {} from class {} on topic {} with filter '{}' and event handler id {}", method.getName(),
                                    portalEventHandler.getClass().getName(),
                                    topic, subscriber.filter(), eventHandlerId);
                    Dictionary<String, Object> props = new Hashtable<>();
                    props.put(EVENT_TOPIC, topic);
                    props.put(PROPERTY_KEY_EVENT_HANDLER_ID, eventHandlerId);
                    String filterStr = subscriber.filter();
                    if (StringUtils.isNotBlank(filterStr)) {
                        Filter filter = bundleContext.createFilter(subscriber.filter());
                        props.put(EVENT_FILTER, filter.toString());
                    }

                    org.osgi.service.event.EventHandler annotedMethodEventHandler = new AnnotedMethodEventHandler(portalEventHandler, method);

                    bundleContext.registerService(org.osgi.service.event.EventHandler.class.getName(), annotedMethodEventHandler, props);
                    LOGGER.info("Event handler registered : {}.{} on topic {} with filter {}.", portalEventHandler.getClass(), method.getName(), topic, subscriber.filter());
                }
            }
        } else {
            LOGGER.error("L'handler d'event {} ne sera pas enregistré => le bundle context est null.", portalEventHandler.getClass().getName());
        }
    }

    public static void unregisterEventHandler(BundleContext bundleContext, PortalEventHandler portalEventHandler) throws InvalidSyntaxException {
        if (bundleContext != null) {
            String eventHandlerId = Integer.toHexString(portalEventHandler.hashCode());

            String filterStr = "(" + PROPERTY_KEY_EVENT_HANDLER_ID + "=" + eventHandlerId + ")";
            Filter filter = bundleContext.createFilter(filterStr);
            ServiceReference<?>[] serviceReferences = bundleContext.getAllServiceReferences(null, filter.toString());
            LOGGER.debug("{}Service references found : {}", "", serviceReferences);
            for (ServiceReference<?> serviceReference : serviceReferences) {
                LOGGER.info("Unregister event handler {} for event handler id : {}", serviceReference.getClass().getName(), eventHandlerId);
                bundleContext.ungetService(serviceReference);
            }
        } else {
            LOGGER.error("L'handler d'event {} ne sera pas enregistré => le bundle context est null.", portalEventHandler.getClass().getName());
        }
    }
}
