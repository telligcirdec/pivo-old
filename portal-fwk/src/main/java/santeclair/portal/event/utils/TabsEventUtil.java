package santeclair.portal.event.utils;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_ASKING_CLOSED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_NAVIGATION;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_NAVIGATOR_URI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;

import java.util.Dictionary;
import java.util.HashMap;
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

    public static Dictionary<String, Object> getNewViewUiProps(String sessionId, String moduleCode, String viewCode, HashMap<String, Object> mapParams) {
        Dictionary<String, Object> props = new Hashtable<>();
        props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_NAVIGATION);
        String uri = "container/new/modules/" + moduleCode + "/views/" + viewCode + "/";
        if (null != mapParams && !mapParams.isEmpty()) {
            uri += "params";
            for (String key : mapParams.keySet()) {
                uri += "/" + key + "/" + mapParams.get(key);
            }
        }
        props.put(PROPERTY_KEY_NAVIGATOR_URI, uri);
        props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
        
        return props;
    }
}
