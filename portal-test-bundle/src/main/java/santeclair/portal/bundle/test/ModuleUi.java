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
public class ModuleUi extends ModuleUiHelper {

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
    @Property(name = "icon", mandatory = true)
    public void setIcon(String icon) {
        super.setIcon(icon);
    }

    @Override
    @Property(name = "code", mandatory = true)
    public void setCode(String code) {
        super.setCode(code);
    }

    @Override
    @Property(name = "name", mandatory = true)
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    @Property(name = "displayOrder", value = "100000", mandatory = true)
    public void setDisplayOrder(Integer displayOrder) {
        super.setDisplayOrder(displayOrder);
    }

    @Override
    @Property(name = "rolesAllowed", mandatory = true, value = "{NONE}")
    public void setRolesAllowed(String[] rolesAllowedArray) {
        super.setRolesAllowed(rolesAllowedArray);
    }

    @Override
    @Property(name = "rolesSeveralInstanceAllowed", mandatory = true, value = "{NONE}")
    public void setRolesSeveralInstanceAllowed(String[] rolesSeveralInstanceAllowedArray) {
        super.setRolesSeveralInstanceAllowed(rolesSeveralInstanceAllowedArray);
    }

    @Override
    @Property(name = "rolesOpenOnInitialization", mandatory = true, value = "{NONE}")
    public void setRolesOpenOnInitialization(String[] rolesOpenOnInitializationArray) {
        super.setRolesOpenOnInitialization(rolesOpenOnInitializationArray);
    }

    @Override
    @Property(name = "rolesIsCloseable", mandatory = true, value = "{NONE}")
    public void setRolesIsCloseable(String[] rolesIsCloseableArray) {
        super.setRolesIsCloseable(rolesIsCloseableArray);
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
