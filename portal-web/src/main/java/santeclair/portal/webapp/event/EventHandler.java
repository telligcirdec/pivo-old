package santeclair.portal.webapp.event;

import org.osgi.framework.BundleContext;

public interface EventHandler {

    void registerEventHandlerItself(BundleContext bundleContext);

    void unregisterEventHandlerItSelf(BundleContext bundleContext);

}
