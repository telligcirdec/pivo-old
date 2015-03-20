package santeclair.portal.webapp.vaadin.navigator;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_NAVIGATION;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_NAVIGATOR_URI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_NAVIGATOR;

import java.util.Dictionary;
import java.util.Hashtable;

import santeclair.portal.event.handler.AbstractEventHandler;
import santeclair.portal.event.handler.EventArg;
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
    public void globalNavigatorEvent(@EventArg(name = PROPERTY_KEY_NAVIGATOR_URI, required = true) final String uri) {
        navigator.navigateTo(uri);
    }

    @Subscriber(topic = TOPIC_NAVIGATOR, filter = "(&(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_NAVIGATION + ")(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*)("
                    + PROPERTY_KEY_NAVIGATOR_URI + "=*))")
    public void navigatorEvent(@EventArg(name = PROPERTY_KEY_NAVIGATOR_URI) final String uri, @EventArg(name = PROPERTY_KEY_PORTAL_SESSION_ID, required = true) String sessionId) {
        if (this.sessionId.equals(sessionId)) {
            navigator.navigateTo(uri);
        }
    }

    public static Dictionary<String, Object> getNavigateToProps(String uri, String sessionId) {
        Dictionary<String, Object> props = getGlobalNavigateToProps(uri);
        props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
        return props;
    }

    public static Dictionary<String, Object> getGlobalNavigateToProps(String uri) {

        Dictionary<String, Object> props = new Hashtable<>();
        props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_NAVIGATION);
        props.put(PROPERTY_KEY_NAVIGATOR_URI, uri);
        
        return props;
    }
}