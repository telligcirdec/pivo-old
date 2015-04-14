package santeclair.portal.event.handler;

import static org.osgi.service.event.EventConstants.EVENT_FILTER;
import static org.osgi.service.event.EventConstants.EVENT_TOPIC;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEventHandler implements PortalEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventHandler.class);

    @Override
    public final List<ServiceRegistration<?>> registerEventHandlerItself(BundleContext bundleContext) {
        List<ServiceRegistration<?>> srList = new ArrayList<>();
        try {
            srList = registerEventHandler(bundleContext, this);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
        return srList;
    }

    public static List<ServiceRegistration<?>> registerEventHandler(BundleContext bundleContext, PortalEventHandler portalEventHandler) throws InvalidSyntaxException {
        List<ServiceRegistration<?>> srList = new ArrayList<>();
        if (bundleContext != null) {
            Method[] methodToChecks = portalEventHandler.getClass().getDeclaredMethods();
            for (Method method : methodToChecks) {
                if (method.isAnnotationPresent(Subscriber.class)) {
                    Subscriber subscriber = method.getAnnotation(Subscriber.class);
                    String topic = subscriber.topic();
                    LOGGER.info("Registering event handler method {} from class {} on topic {} with filter '{}'", method.getName(),
                                    portalEventHandler.getClass().getName(),
                                    topic, subscriber.filter());
                    Dictionary<String, Object> props = new Hashtable<>();
                    props.put(EVENT_TOPIC, topic);
                    String filterStr = subscriber.filter();
                    if (StringUtils.isNotBlank(filterStr)) {
                        Filter filter = bundleContext.createFilter(subscriber.filter());
                        props.put(EVENT_FILTER, filter.toString());
                    }

                    org.osgi.service.event.EventHandler annotedMethodEventHandler = new AnnotedMethodEventHandler(portalEventHandler, method);

                    ServiceRegistration<?> sr = bundleContext.registerService(org.osgi.service.event.EventHandler.class.getName(), annotedMethodEventHandler, props);
                    srList.add(sr);
                    LOGGER.info("Event handler registered : {}.{} on topic {} with filter {}.", portalEventHandler.getClass(), method.getName(), topic, subscriber.filter());
                }
            }
        } else {
            LOGGER.error("L'handler d'event {} ne sera pas enregistré => le bundle context est null.", portalEventHandler.getClass().getName());
        }
        return srList;
    }

}
