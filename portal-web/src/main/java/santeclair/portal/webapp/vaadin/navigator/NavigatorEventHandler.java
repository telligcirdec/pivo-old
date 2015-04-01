package santeclair.portal.webapp.vaadin.navigator;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_NAVIGATION;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_NAVIGATOR_URI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_NAVIGATOR;

import java.util.Dictionary;
import java.util.Hashtable;

import santeclair.portal.event.handler.AbstractEventHandler;
import santeclair.portal.event.handler.EventProperty;
import santeclair.portal.event.handler.Subscriber;

import com.vaadin.navigator.Navigator;

public class NavigatorEventHandler extends AbstractEventHandler {

    private final String sessionId;
    private final Navigator navigator;

    public NavigatorEventHandler(final Navigator navigator, final String uiId) {
        super();
        this.navigator = navigator;
        this.sessionId = uiId;
    }

    @Subscriber(topic = TOPIC_NAVIGATOR, filter = "(&(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_NAVIGATION + ")(!(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*))("
                    + PROPERTY_KEY_NAVIGATOR_URI + "=*))")
    public void globalNavigatorEvent(@EventProperty(propKey = PROPERTY_KEY_NAVIGATOR_URI, required = true) final String uri) {
        navigator.navigateTo(uri);
    }

    @Subscriber(topic = TOPIC_NAVIGATOR, filter = "(&(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_NAVIGATION + ")(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*)("
                    + PROPERTY_KEY_NAVIGATOR_URI + "=*))")
    public void navigatorEvent(@EventProperty(propKey = PROPERTY_KEY_NAVIGATOR_URI) final String uri, @EventProperty(propKey = PROPERTY_KEY_PORTAL_SESSION_ID, required = true) String sessionId) {
        if (this.sessionId.equals(sessionId)) {
            navigator.navigateTo(uri);
        }
    }

    public static Dictionary<String, Object> getNavigateEventProperty(String uri, String sessionId) {
        Dictionary<String, Object> props = getGlobalNavigateEventProperties(uri);
        props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
        return props;
    }

    public static Dictionary<String, Object> getGlobalNavigateEventProperties(String uri) {

        Dictionary<String, Object> props = new Hashtable<>();
        props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_NAVIGATION);
        props.put(PROPERTY_KEY_NAVIGATOR_URI, uri);
        
        return props;
    }
}
