package santeclair.portal.bundle.test;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI_FACTORY;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_PORTAL;

import java.util.Dictionary;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Context;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Updated;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

@Component
public class ViewUi extends ViewUiHelper {

    @Context
    private BundleContext context;

    private static final String PUBLISHER_NAME = "TestModuleUiFactoryPublisher";

    @Publishes(name = PUBLISHER_NAME, topics = TOPIC_MODULE_UI_FACTORY)
    private Publisher publisher;

    @Override
    @Bind
    public void bindLogService(LogService logService) {
        super.bindLogService(logService);
    }

    @Override
    @Validate
    public void start() {
        super.start();
    }

    @Override
    @Invalidate
    public void stop() {
        super.stop();
    }

    @Override
    @Subscriber(name = "portalStarted",
                    topics = TOPIC_PORTAL, filter = "(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_STARTED + ")")
    public void portalStarted(Event event) {
        super.portalStarted(event);
    }

    @Override
    @Updated
    public void updated(Dictionary<?, ?> conf) {
        super.updated(conf);
    }

    @Override
    @Property(name = "code", mandatory = true)
    protected void setCode(String code) {
        super.setCode(code);
    }

    @Override
    @Property(name = "isCloseable", value = "true")
    protected void setIsCloseable(Boolean isCloseable) {
        super.setIsCloseable(isCloseable);
    }

    @Override
    @Property(name = "severalInstanceAllowed", value = "false")
    protected void setSeveralInstanceAllowed(Boolean severalInstanceAllowed) {
        super.setSeveralInstanceAllowed(severalInstanceAllowed);
    }

    @Override
    @Property(name = "viewPackage", mandatory = true)
    protected void setViewPackage(String viewPackage) {
        super.setViewPackage(viewPackage);
    }

    @Override
    @Property(name = "defaultView", value = "false")
    protected void setOpenOnInitialization(Boolean openOnInitialization) {
        super.setOpenOnInitialization(openOnInitialization);
    }

    @Override
    @Property(name = "defaultView", mandatory = true)
    protected void setDefaultView(String defaultView) {
        super.setDefaultView(defaultView);
    }

    @Override
    @Property(name = "displayOrder", value = "100000")
    public void setDisplayOrder(Integer displayOrder) {
        super.setDisplayOrder(displayOrder);
    }

    @Override
    protected BundleContext getContext() {
        return context;
    }

    @Override
    protected Publisher getPublisher() {
        return publisher;
    }

}
