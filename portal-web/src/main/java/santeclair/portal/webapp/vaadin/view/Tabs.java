package santeclair.portal.webapp.vaadin.view;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_TABS;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_NEW;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_CURRENT_USER_ROLES;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_NAVIGATOR;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import santeclair.portal.event.publisher.callback.TabsCallback;
import santeclair.portal.listener.service.impl.EventAdminServiceListener;
import santeclair.portal.listener.service.impl.EventAdminServiceListener.DataPublisher;
import santeclair.portal.listener.service.impl.EventAdminServiceListener.Publisher;
import santeclair.portal.webapp.vaadin.navigator.NavigatorEventHandler;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontIcon;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents.ComponentDetachListener;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.themes.ValoTheme;

public class Tabs extends TabSheet implements View, SelectedTabChangeListener, CloseHandler,
                ComponentDetachListener, TabsCallback {

    private static final long serialVersionUID = 5672663000761618207L;

    private static final Logger LOGGER = LoggerFactory.getLogger(Tabs.class);

    private static final String PARAMS_URI_FRAGMENT = "params";

    private final DataPublisher<Tabs, TabsCallback> tabsDataPublisher;
    private final Publisher<Tabs> navigationPublisher;

    private final EventAdminServiceListener eventAdminServiceListener;
    private final String sessionId;
    private final List<String> currentUserRoles;

    public Tabs(final EventAdminServiceListener eventAdminServiceListener, final String sessionId, final List<String> currentUserRoles) {
        this.eventAdminServiceListener = eventAdminServiceListener;
        this.sessionId = sessionId;
        this.currentUserRoles = currentUserRoles;
        this.tabsDataPublisher = eventAdminServiceListener.registerDataPublisher(this, TOPIC_MODULE_UI);
        this.navigationPublisher = eventAdminServiceListener.registerPublisher(this, TOPIC_NAVIGATOR);
    }

    public void init() {
        this.setSizeFull();
        this.addSelectedTabChangeListener(this);
        this.setCloseHandler(this);
        this.addStyleName(ValoTheme.TABSHEET_FRAMED);
        this.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
    }

    @Override
    public void detach() {
        super.detach();
        eventAdminServiceListener.unregisterPublisher(tabsDataPublisher, navigationPublisher);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        LOGGER.debug("enter : " + event);
        String parameters = event.getParameters();
        StringTokenizer st = new StringTokenizer(parameters, "/");
        String container = null;
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
            Dictionary<String, Object> props = new Hashtable<>();

            props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_TABS);
            props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_NEW);
            props.put(PROPERTY_KEY_MODULE_UI_CODE, moduleCode);
            props.put(PROPERTY_KEY_VIEW_UI_CODE, moduleView);
            props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
            props.put(PROPERTY_KEY_PORTAL_CURRENT_USER_ROLES, currentUserRoles);
            addExtractedParams(parameters, props);

            tabsDataPublisher.publishEventDataAndDictionnarySynchronously(this, props);
        } else {
            Integer containerHash = new Integer(container);
            Integer numberOfTab = this.getComponentCount();
            for (int i = 0; i < numberOfTab; i++) {
                Tab tab = this.getTab(i);
                if (tab != null && tab.hashCode() == containerHash) {
                    this.setSelectedTab(tab);
                    break;
                }
            }
        }
    }

    @Override
    public void addView(String moduleCode, String viewCode, String caption, FontIcon icon, Boolean closable, Component moduleUiView) {
        Tab moduleUiViewAlreadyAdded = this.getTab(moduleUiView);
        if (moduleUiViewAlreadyAdded != null) {
            navigationPublisher.publishEventSynchronously(NavigatorEventHandler.getNavigateToProps("container/" + moduleUiViewAlreadyAdded.hashCode() + "/modules/" + moduleCode
                            + "/views/" + viewCode, sessionId));
        } else {
            Tab tab = this.addTab(moduleUiView, caption);
            tab.setIcon(icon);
            tab.setClosable(closable);
            navigationPublisher.publishEventSynchronously(NavigatorEventHandler.getNavigateToProps("container/" + tab.hashCode() + "/modules/" + moduleCode
                            + "/views/" + viewCode, sessionId));
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

    private void addExtractedParams(String fragment, Dictionary<String, Object> props) {
        if (fragment != null && fragment.contains(PARAMS_URI_FRAGMENT)) {
            String paramsUriFragement = fragment.split(PARAMS_URI_FRAGMENT
                            + "/")[1];
            if (StringUtils.isNotBlank(paramsUriFragement)) {
                addParsedParameters(paramsUriFragement, props);
            }
        }
    }

    /**
     * @param parameters
     * @param paramsAsArray
     */
    private void addParsedParameters(String paramsFromUri, Dictionary<String, Object> props) {
        String[] paramsAsArray = paramsFromUri.split("/");
        String currentKey = null;
        for (int i = 0; i < paramsAsArray.length; i++) {
            if (i % 2 == 0) {
                currentKey = paramsAsArray[i];
            } else {
                props.put(currentKey, paramsAsArray[i]);
            }
        }
    }

}
