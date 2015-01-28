package santeclair.portal.webapp.listener.service;

import java.lang.reflect.ParameterizedType;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPortalServiceListener<T> implements PortalServiceListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPortalServiceListener.class);

    protected BundleContext bundleContext;

    public AbstractPortalServiceListener(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public void serviceChanged(ServiceEvent serviceEvent) {
        if (serviceEvent != null) {
            @SuppressWarnings("unchecked")
            T service = (T) bundleContext.getService(serviceEvent.getServiceReference());
            if (service != null) {
                switch (serviceEvent.getType()) {
                case ServiceEvent.MODIFIED:
                    serviceModified(service, serviceEvent);
                    break;
                case ServiceEvent.MODIFIED_ENDMATCH:
                    serviceModifiedEndmatch(service, serviceEvent);
                    break;
                case ServiceEvent.REGISTERED:
                    serviceRegistered(service, serviceEvent);
                    break;
                case ServiceEvent.UNREGISTERING:
                    serviceUnregistering(service, serviceEvent);
                    break;
                default:
                    LOGGER.warn("Le type d'event levé ({})n'est pas géré", serviceEvent.getType());
                    break;
                }
            } else {
                LOGGER.warn("Le service de type {} n'a pas été trouvé dans le contexte OSGi / FILTRE : [{}] ", getServiceType(), getFilter());
            }

        } else {
            LOGGER.warn("Le service event levé est null");
        }
    }

    @Override
    public void serviceModified(T service, ServiceEvent serviceEvent) {
        LOGGER.warn("Implementation par défaut. Ne fait rien.");
    }

    @Override
    public void serviceModifiedEndmatch(T service, ServiceEvent serviceEvent) {
        LOGGER.warn("Implementation par défaut. Ne fait rien.");
    }

    @Override
    public void serviceRegistered(T service, ServiceEvent serviceEvent) {
        LOGGER.warn("Implementation par défaut. Ne fait rien.");
    }

    @Override
    public void serviceUnregistering(T service, ServiceEvent serviceEvent) {
        LOGGER.warn("Implementation par défaut. Ne fait rien.");
    }

    @Override
    public String getFilter() {
        Class<T> clazz = getServiceType();
        String filter = "(objectClass=" + clazz.getName() + ")";
        LOGGER.debug("getFilter() => {}", filter);
        return filter;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getServiceType() {
        return ((Class<T>) ((ParameterizedType) getClass()
                        .getGenericSuperclass()).getActualTypeArguments()[0]);
    }
}
