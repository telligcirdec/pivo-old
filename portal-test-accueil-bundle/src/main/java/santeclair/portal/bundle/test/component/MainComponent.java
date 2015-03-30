package santeclair.portal.bundle.test.component;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.service.log.LogService;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@Component(name = "santeclair.portal.bundle.test.component.MainComponent", propagation = false)
public class MainComponent extends HorizontalLayout {

    private static final long serialVersionUID = 8369775167208351407L;

    @Requires
    private LogService logService;

    private String sessionId;
    private Integer tabHash;

    @Validate
    public void init() {
        this.addComponent(new Label("Maouahahahaha => " + Math.random()));
        this.addComponent(new Label("sessionId => " + sessionId));
        this.addComponent(new Label("tabHash => " + tabHash));
    }

//    @Subscriber(name = "", topics = TOPIC_COMPONENT_UI, filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_TABS + ")(" +
//                    PROPERTY_KEY_EVENT_NAME + "="
//                    + EVENT_NAME_ASKING_CLOSED + ")(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*)(" + PROPERTY_KEY_TAB_HASH + "=*)(" +
//                    PROPERTY_KEY_EVENT_DATA_TYPE + "=*)("
//                    + PROPERTY_KEY_EVENT_DATA + "=*))")
//    public void closeTabs(org.osgi.service.event.Event event) {
//        String sessionId = (String) event.getProperty(PROPERTY_KEY_PORTAL_SESSION_ID);
//        Integer tabHash = (Integer) event.getProperty(PROPERTY_KEY_TAB_HASH);
//        String dataType = (String) event.getProperty(PROPERTY_KEY_EVENT_DATA_TYPE);
//        if (sessionId.equals(this.sessionId) && tabHash.equals(this.tabHash) && dataType.equals(TabsCallback.class.getName())) {
//            final TabsCallback tabsCallback = (TabsCallback) event.getProperty(PROPERTY_KEY_EVENT_DATA);
//            tabsCallback.keepView();
//                       
//        }
//    }

    @Property(name = PROPERTY_KEY_PORTAL_SESSION_ID)
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Property(name = PROPERTY_KEY_TAB_HASH)
    public void setTabHash(Integer tabHash) {
        this.tabHash = tabHash;
    }

}
