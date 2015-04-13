package santeclair.portal.event.utils;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_ASKING_CLOSED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;

import java.util.Dictionary;
import java.util.Hashtable;

public abstract class TabsEventUtil {
    
    public static Dictionary<String, Object> getCloseTabsProps(String sessionId, Integer tabHash) {
        Dictionary<String, Object> props = new Hashtable<>();
        props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_VIEW_UI);
        props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_ASKING_CLOSED);
        props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
        props.put(PROPERTY_KEY_TAB_HASH, tabHash);
        return props;
    }

}
