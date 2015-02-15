package santeclair.portal.webapp.event.handler;

import org.osgi.framework.BundleContext;

public interface PortalEventHandler {

    void registerEventHandlerItself(BundleContext bundleContext);

    void unregisterEventHandlerItSelf(BundleContext bundleContext);

}
