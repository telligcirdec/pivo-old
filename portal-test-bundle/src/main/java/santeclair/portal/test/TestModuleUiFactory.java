package santeclair.portal.test;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CALLBACK;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_HANDLER_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI_FACTORY;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_PORTAL;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.event.publisher.callback.PortalAppEventCallback;
import santeclair.portal.vaadin.module.ModuleUiFactory;

import com.vaadin.server.FontIcon;

@Component(publicFactory = false)
@Instantiate
public class TestModuleUiFactory implements ModuleUiFactory<TestModuleUi> {

    private static final long serialVersionUID = 3710994197607738603L;

    private static final String PUBLISHER_NAME = "TestModuleUiFactoryPublisher";

    @Requires
    private LogService logService;

    @Publishes(name = PUBLISHER_NAME, topics = TOPIC_MODULE_UI_FACTORY)
    private Publisher publisher;

    @Validate
    private void start() {
        fireStarter(null);
    }

    @Invalidate
    private void stop() {
        logService.log(LogService.LOG_DEBUG, "TestModuleUiFactory stop");
        Dictionary<String, Object> eventProps = new Hashtable<>();
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_STOPPED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_FACTORY, this);
        publisher.send(eventProps);
    }

    @Subscriber(name = "portalStarted",
                    topics = TOPIC_PORTAL, filter = "(&(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_STARTED + ")(" + PROPERTY_KEY_EVENT_CALLBACK + "=*))")
    public void portalStarted(Event e) {
        PortalAppEventCallback portalAppEventCallback = (PortalAppEventCallback) e.getProperty(PROPERTY_KEY_EVENT_CALLBACK);
        portalAppEventCallback.addNewModuleUiFactory(this);
    }

    @Override
    public Boolean isSeveralModuleAllowed(List<String> roles) {
        return false;
    }

    @Override
    public String getCode() {
        return "TEST";
    }

    @Override
    public String getName() {
        return "Module de Test";
    }

    @Override
    public FontIcon getIcon() {
        return null;
    }

    @Override
    public Integer displayOrder() {
        // TODO Auto-generated method stub
        return Integer.MAX_VALUE;
    }

    @Override
    public Boolean isCloseable(List<String> roles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean openOnInitialization(List<String> roles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean securityCheck(List<String> roles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TestModuleUi buid(List<String> roles) {
        // TODO Auto-generated method stub
        return null;
    }

    private void fireStarter(String pid) {
        logService.log(LogService.LOG_DEBUG, "TestModuleUiFactory start");
        Dictionary<String, Object> eventProps = new Hashtable<>();
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_STARTED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_FACTORY, this);
        if (StringUtils.isNotBlank(pid)) {
            eventProps.put(PROPERTY_KEY_EVENT_HANDLER_ID, pid);
        }
        publisher.send(eventProps);
    }

}
