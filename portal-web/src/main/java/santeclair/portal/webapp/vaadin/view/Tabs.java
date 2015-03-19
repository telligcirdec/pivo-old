package santeclair.portal.webapp.vaadin.view;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_TABS;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_NEW;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_VIEW_UI;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import santeclair.portal.event.publisher.callback.TabsCallback;
import santeclair.portal.listener.service.impl.EventAdminServiceListener;
import santeclair.portal.listener.service.impl.EventAdminServiceListener.DataPublisher;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents.ComponentDetachListener;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;

public class Tabs extends TabSheet implements View, SelectedTabChangeListener, CloseHandler,
                ComponentDetachListener, TabsCallback{

    private static final long serialVersionUID = 5672663000761618207L;

    private static final Logger LOGGER = LoggerFactory.getLogger(Tabs.class);

    // private static final Map<Integer, Tab> tabContainerNumber = new HashMap<>();

    private DataPublisher<Tabs, TabsCallback> tabsDataPublisher;

    private final EventAdminServiceListener eventAdminServiceListener;

    public Tabs(final EventAdminServiceListener eventAdminServiceListener) {
        this.eventAdminServiceListener = eventAdminServiceListener;
    }

    public void init() {
        tabsDataPublisher = eventAdminServiceListener.registerDataPublisher(this, TOPIC_VIEW_UI);
    }

    @Override
    public void detach() {
        super.detach();
        eventAdminServiceListener.unregisterPublisher(tabsDataPublisher);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        LOGGER.debug("enter : " + event);
        StringTokenizer st = new StringTokenizer(event.getParameters(), "/");
        String container = "";
        String moduleCode = "";
        String moduleView = "";

        int count = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (count == 0) {
                container = token;
            }
            if (count == 2) {
                moduleCode = token;
            }
            if (count == 4) {
                moduleView = token;
            }
            count++;
        }
        if ("NEW".equalsIgnoreCase(container)) {
            Dictionary<String,Object> props = new Hashtable<>();
            
            props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_TABS);
            props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_NEW);
            props.put(PROPERTY_KEY_MODULE_UI_CODE, moduleCode);
            props.put(PROPERTY_KEY_VIEW_UI_CODE, moduleView);
            
            tabsDataPublisher.publishEventDataAndDictionnarySynchronously(this, props);
        } else {

        }
    }

    public void callbackModule(Component moduleUiView) {
        Tab moduleUiViewAlreadyAdded = this.getTab(moduleUiView);
        if (moduleUiViewAlreadyAdded != null) {
            
        } else {

        }
    }

    @Override
    public void componentDetachedFromContainer(ComponentDetachEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabClose(TabSheet tabsheet, Component tabContent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void selectedTabChange(SelectedTabChangeEvent event) {
        // TODO Auto-generated method stub

    }
}
