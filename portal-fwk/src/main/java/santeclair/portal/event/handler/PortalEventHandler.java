package santeclair.portal.event.handler;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public interface PortalEventHandler {

    List<ServiceRegistration<?>> registerEventHandlerItself(BundleContext bundleContext);

}
