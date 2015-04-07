package santeclair.portal.bundle.view;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_PORTAL;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_TABS;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_CLOSED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_VIEW_UI;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.Factory;
import org.apache.felix.ipojo.InstanceManager;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Updated;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.module.ModuleUi;
import santeclair.portal.utils.SessionIdTabHashKey;
import santeclair.portal.view.ViewUi;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;

@Component
public class ViewUiImpl implements ViewUi {

    @Requires
    private LogService logService;
    @Requires(filter = "(factory.name=${mainComponentFactoryName})")
    private Factory mainComponentFactory;

    private String oldCodeModule;
    private String codeModule;
    private String oldCode;
    private String code;
    private String libelle;
    private FontIcon icon;
    private Boolean openOnInitialization;
    private Boolean newMainComponentOnEachAction;
    private Boolean visibleOnMenu;
    private List<String> rolesAllowed;
    private String mainComponentFactoryName;

    private final Map<String, com.vaadin.ui.Component> onlyOneViewMainComponentMap = new HashMap<>();
    private final Map<SessionIdTabHashKey, ComponentInstance> viewMainComponentInstanceManagerMap = new HashMap<>();

    @Publishes(name = "viewUiPublisherToModuleUiTopic", topics = TOPIC_MODULE_UI, synchronous = true)
    private Publisher publisherToModuleUiTopic;

    /*
     * Lifecycle
     */

    @Validate
    public void start() {
        logService.log(LogService.LOG_INFO, "ViewUi " + this.libelle + " (" + this.code + ") is starting");

        publisherToModuleUiTopic.send(startProperties(this));

        logService.log(LogService.LOG_INFO, "ViewUi " + this.libelle + " (" + this.code + ") started");
    }

    @Invalidate
    public void stop() {
        logService.log(LogService.LOG_INFO, "ViewUi " + this.libelle + " (" + this.code + ") is stopping");
        clear();
        publisherToModuleUiTopic.send(stopProperties(this));
        logService.log(LogService.LOG_INFO, "ViewUi " + this.libelle + " (" + this.code + ") stopped");
    }

    /*
     * Subscriber
     */
    @Subscriber(name = "moduleStart", topics = TOPIC_VIEW_UI, filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_MODULE_UI + ")(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_NAME_STARTED + ")(" + PROPERTY_KEY_MODULE_UI_CODE + "=*))")
    public void moduleStart(Event event) {
        String moduleCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_MODULE_UI_CODE);
        if (moduleCodeFromEvent.equalsIgnoreCase(codeModule)) {
            ModuleUi moduleUi = (ModuleUi) event.getProperty(PROPERTY_KEY_MODULE_UI);
            logService.log(LogService.LOG_DEBUG, "From ViewUi " + this.libelle + " (" + this.code + ") (moduleStart(event)) => ModuleUi with code " + codeModule + " is starting.");
            moduleUi.registerViewUi(this);
        }
    }

    @Subscriber(name = "portalStopped", topics = TOPIC_VIEW_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_PORTAL + ")(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_STOPPED + ")("
                                    + PROPERTY_KEY_PORTAL_SESSION_ID + "=*))")
    public void portalStopped(Event event) {
        String sessionId = (String) event.getProperty(PROPERTY_KEY_PORTAL_SESSION_ID);
        logService.log(LogService.LOG_DEBUG, "From ViewUi " + this.libelle + " (" + this.code + ") (portalStopped(event)) => A Portal is stopping (" + sessionId + ")");
        onlyOneViewMainComponentMap.remove(sessionId);
        Set<SessionIdTabHashKey> keySet = viewMainComponentInstanceManagerMap.keySet();
        for (SessionIdTabHashKey key : keySet) {
            if (key.isSessionIdEquals(sessionId)) {
                disposeComponentInstance(key);
            }
        }
    }

    @Subscriber(name = "closeTabs", topics = TOPIC_VIEW_UI, filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_TABS + ")(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_NAME_CLOSED + ")(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*)(" + PROPERTY_KEY_TAB_HASH + "=*))")
    public void closeTabs(org.osgi.service.event.Event event) {
        String sessionId = (String) event.getProperty(PROPERTY_KEY_PORTAL_SESSION_ID);
        Integer tabHash = (Integer) event.getProperty(PROPERTY_KEY_TAB_HASH);
        disposeComponentInstance(sessionId, tabHash);
    }

    /*
     * Managed Properties (setter)
     */

    @Updated
    private void updated() {
        logService.log(LogService.LOG_DEBUG, "ViewUi " + this.libelle + " (" + this.code + ") has been modified.");
        clear();
        publisherToModuleUiTopic.send(stopProperties(this, oldCode, oldCodeModule));
        publisherToModuleUiTopic.send(startProperties(this));
    }

    @Property(name = "codeModule", mandatory = true)
    private void setCodeModule(String codeModule) {
        this.oldCodeModule = this.codeModule == null ? codeModule : this.codeModule;
        this.codeModule = codeModule;
    }

    @Property(name = "code", mandatory = true)
    private void setCode(String code) {
        this.oldCode = this.code == null ? code : this.code;
        this.code = code;
    }

    @Property(name = "libelle", mandatory = true)
    private void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Property(name = "icon", mandatory = true)
    private void setIcon(String icon) {
        if (icon != null && !icon.isEmpty()) {
            this.icon = FontAwesome.valueOf(icon);
        }
    }

    @Property(name = "openOnInitialization", value = "false")
    private void setOpenOnInitialization(Boolean openOnInitialization) {
        this.openOnInitialization = openOnInitialization;
    }

    @Property(name = "visibleOnMenu", value = "false")
    private void setVisibleOnMenu(Boolean visibleOnMenu) {
        this.visibleOnMenu = visibleOnMenu;
    }

    @Property(name = "newMainComponentOnEachAction", value = "true")
    private void setNewMainComponentOnEachAction(Boolean newMainComponentOnEachAction) {
        this.newMainComponentOnEachAction = newMainComponentOnEachAction;
    }

    @Property(name = "rolesAllowed", value = "{NONE}")
    private void setRolesAllowed(String[] rolesAllowed) {
        this.rolesAllowed = Arrays.asList(rolesAllowed);
    }

    @Property(name = "mainComponentFactoryName", mandatory = true)
    private void setMainComponentFactoryName(String mainComponentFactoryName) {
        this.mainComponentFactoryName = mainComponentFactoryName;
    }

    /*
     * Services
     */

    @Override
    public com.vaadin.ui.Component getViewMainComponent(String sessionId, Integer tabHash, List<String> currentUserRoles) {
        logService.log(LogService.LOG_DEBUG, "From viewUi " + getLibelle() + " (" + getCode() + ") => getViewMainComponent(sessionId : " + sessionId + " / tabHash : " + tabHash
                        + " / currentUserRoles : " + currentUserRoles + ")");
        com.vaadin.ui.Component viewMainComponent = null;
        try {
            logService.log(LogService.LOG_DEBUG, "From viewUi " + getLibelle() + " (" + getCode() + ") => newMainComponentOnEachAction is set to " + newMainComponentOnEachAction);
            if (!newMainComponentOnEachAction) {
                viewMainComponent = onlyOneViewMainComponentMap.get(sessionId);
                if (viewMainComponent == null) {
                    disposeComponentInstance(sessionId, tabHash);
                    viewMainComponent = getMainComponent(sessionId, tabHash);
                    onlyOneViewMainComponentMap.put(sessionId, viewMainComponent);
                }
            } else {
                disposeComponentInstance(sessionId, tabHash);
                viewMainComponent = getMainComponent(sessionId, tabHash);
            }
        } catch (Exception e) {
            logService.log(LogService.LOG_ERROR, "From viewUi " + getLibelle() + " (" + getCode() + ") => Error during creation of the main component.", e);
        }
        if (viewMainComponent == null) {
            logService.log(LogService.LOG_WARNING, "From viewUi " + getLibelle() + " (" + getCode() + ") => No Main component declared.");
        }
        return viewMainComponent;
    }

    /*
     * Getter
     */

    @Override
    public FontIcon getIcon() {
        return icon;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLibelle() {
        return libelle;
    }

    @Override
    public Boolean getOpenOnInitialization() {
        return openOnInitialization;
    }

    @Override
    public Boolean getVisibleOnMenu() {
        return visibleOnMenu;
    }

    @Override
    public List<String> getRolesAllowed() {
        return rolesAllowed;
    }

    /*
     * Private method
     */

    private com.vaadin.ui.Component getMainComponent(String sessionId, Integer tabHash) throws Exception {
        Dictionary<String, Object> props = new Hashtable<>();
        props.put("instance.name", getCode() + "-" + mainComponentFactoryName + "-" + sessionId + "-" + tabHash);
        props.put(PROPERTY_KEY_PORTAL_SESSION_ID, new String(sessionId));
        props.put(PROPERTY_KEY_TAB_HASH, new Integer(tabHash));
        props.put(PROPERTY_KEY_MODULE_UI_CODE, codeModule);
        props.put(PROPERTY_KEY_VIEW_UI_CODE, code);
        
        ComponentInstance instance = mainComponentFactory.createComponentInstance(props);
        viewMainComponentInstanceManagerMap.put(new SessionIdTabHashKey(sessionId, tabHash), instance);
        if (instance.getState() == ComponentInstance.VALID) {
            com.vaadin.ui.Component mainComponent =
                            (com.vaadin.ui.Component) ((InstanceManager) instance).getPojoObject();
            return mainComponent;
        }
        return null;
    }

    private void disposeComponentInstance(String sessionId, Integer tabHash) {
        SessionIdTabHashKey key = new SessionIdTabHashKey(sessionId, tabHash);
        disposeComponentInstance(key);
    }

    private void disposeComponentInstance(SessionIdTabHashKey key) {
        ComponentInstance componentInstance = viewMainComponentInstanceManagerMap.get(key);
        if (componentInstance != null) {
            componentInstance.dispose();
            viewMainComponentInstanceManagerMap.remove(key);
            componentInstance = null;
        }
    }

    private void clear() {
        Set<SessionIdTabHashKey> keySet = viewMainComponentInstanceManagerMap.keySet();
        for (SessionIdTabHashKey key : keySet) {
            disposeComponentInstance(key);
        }
        viewMainComponentInstanceManagerMap.clear();
        onlyOneViewMainComponentMap.clear();
    }

    /*
     * Static methods
     */

    private static Dictionary<String, Object> startProperties(final ViewUiImpl viewUi) {

        Dictionary<String, Object> eventProps = new Hashtable<>(4);
        eventProps.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_VIEW_UI);
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STARTED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, viewUi.codeModule);
        eventProps.put(PROPERTY_KEY_VIEW_UI, viewUi);

        return eventProps;
    }

    private static Dictionary<String, Object> stopProperties(final ViewUiImpl viewUi) {
        return stopProperties(viewUi, viewUi.codeModule, viewUi.code);
    }

    private static Dictionary<String, Object> stopProperties(final ViewUiImpl viewUi, final String codeView, final String codeModule) {

        Dictionary<String, Object> eventProps = new Hashtable<>(4);
        eventProps.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_VIEW_UI);
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STOPPED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, codeModule);
        eventProps.put(PROPERTY_KEY_VIEW_UI_CODE, codeView);
        eventProps.put(PROPERTY_KEY_VIEW_UI, viewUi);

        return eventProps;
    }

}
