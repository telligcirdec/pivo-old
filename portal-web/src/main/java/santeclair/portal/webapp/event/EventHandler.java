package santeclair.portal.webapp.event;

import org.osgi.framework.BundleContext;

public interface EventHandler extends org.osgi.service.event.EventHandler {

    void registerEventHandlerItself(BundleContext bundleContext);

    String getFilter();

    String[] getTopics();

}
