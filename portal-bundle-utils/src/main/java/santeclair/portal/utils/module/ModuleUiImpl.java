package santeclair.portal.utils.module;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_PORTAL;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_TABS;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_CLOSED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_NEW;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_DATA;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PARAMS;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_CURRENT_USER_ROLES;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_CALLBACK;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_PORTAL;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_VIEW_UI;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Updated;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.event.publisher.callback.PortalStartCallback;
import santeclair.portal.event.publisher.callback.TabsCallback;
import santeclair.portal.module.ModuleUi;
import santeclair.portal.view.ViewUi;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;

@Component
@Provides(specifications = ModuleUi.class)
public class ModuleUiImpl implements ModuleUi {

    @Publishes(name = "moduleUiPublisherView", topics = TOPIC_VIEW_UI, synchronous = true)
    private Publisher publisherToViewUiTopic;

    @Publishes(name = "moduleUiPublisherPortal", topics = TOPIC_PORTAL, synchronous = true)
    private Publisher publisherToPortalTopic;

    @Requires
    private LogService logService;

    /*
     * Properties
     */
    private String code;
    private String oldCode;
    private String libelle;
    private FontIcon icon;
    private Boolean closable;
    private Boolean onlyOneTabAllowed;
    private Boolean keepModuleUiOnTabClose;
    private Integer displayOrder;

    private final Map<String, ViewUi> viewUis = new HashMap<>();

    private final List<ModuleUiCustomComponent> moduleUiCustomComponentList = new ArrayList<>();

    /*
     * Lifecycle
     */

    @Validate
    private void start() {
        logService.log(LogService.LOG_INFO, "ModuleUi " + this.libelle + " (" + this.code + ") is starting");
        Dictionary<String, Object> props = startProperties(this);
        publisherToViewUiTopic.send(props);
        publisherToPortalTopic.send(props);
        logService.log(LogService.LOG_INFO, "ModuleUi " + this.libelle + " (" + this.code + ") started");
    }

    @Invalidate
    private void stop() {
        logService.log(LogService.LOG_INFO, "ModuleUi " + this.libelle + " (" + this.code + ") is stopping");
        clear();
        Dictionary<String, Object> props = stopProperties(this);
        publisherToPortalTopic.send(props);
        logService.log(LogService.LOG_INFO, "ModuleUi " + this.libelle + " (" + this.code + ") stopped");
    }

    /*
     * @Subscriber
     */

    @Subscriber(name = "portalStarted", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_PORTAL + ")(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_STARTED + ")("
                                    + PROPERTY_KEY_EVENT_DATA + "=*)(" + PROPERTY_KEY_TAB_CALLBACK + "=*)(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*))")
    private void portalStarted(Event event) {
        logService.log(LogService.LOG_INFO, "From ModuleUi " + libelle + " (" + code + ") / portalStarted(event) => A Portal is Starting");
        PortalStartCallback portalStartCallback = PortalStartCallback.class.cast(event.getProperty(PROPERTY_KEY_EVENT_DATA));
        portalStartCallback.addModuleUi(this, this.code);
        TabsCallback tabsCallback = TabsCallback.class.cast(event.getProperty(PROPERTY_KEY_TAB_CALLBACK));
        String sessionId = String.class.cast(event.getProperty(PROPERTY_KEY_PORTAL_SESSION_ID));
        for (ModuleUiCustomComponent moduleUiCustomComponentFromList : moduleUiCustomComponentList) {
            if (sessionId.equals(moduleUiCustomComponentFromList.getSessionId())) {
                int tabHash = tabsCallback.addView(icon, closable, moduleUiCustomComponentFromList);
                moduleUiCustomComponentFromList.setTabHash(tabHash);
            }
        }
    }

    @Subscriber(name = "portalStopped", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_PORTAL + ")(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_STOPPED + ")("
                                    + PROPERTY_KEY_PORTAL_SESSION_ID + "=*))")
    private void portalStopped(Event event) {
        String sessionId = (String) event.getProperty(PROPERTY_KEY_PORTAL_SESSION_ID);
        logService.log(LogService.LOG_INFO, "From ModuleUi " + libelle + " (" + code + ") / portalStopped(event) => A Portal is stooping (" + sessionId + ")");
        Iterator<ModuleUiCustomComponent> ite = moduleUiCustomComponentList.iterator();
        while (ite.hasNext()) {
            ModuleUiCustomComponent moduleUiCustomComponentFromList = ite.next();
            if (sessionId.equals(moduleUiCustomComponentFromList.getSessionId())) {
                clearModuleUiCustomComponent(moduleUiCustomComponentFromList);
                ite.remove();
            }

        }
    }

    @Subscriber(name = "viewStarted", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_VIEW_UI + ")(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_STARTED + ")("
                                    + PROPERTY_KEY_MODULE_UI_CODE + "=*)(" + PROPERTY_KEY_VIEW_UI + "=*))")
    private void registerViewUi(Event event) {
        String moduleCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_MODULE_UI_CODE);
        if (moduleCodeFromEvent.equalsIgnoreCase(code)) {
            ViewUi viewUi = (ViewUi) event.getProperty(PROPERTY_KEY_VIEW_UI);
            logService.log(LogService.LOG_INFO,
                            "From ModuleUi " + libelle + " (" + code + ") / registerViewUi(event) => A ViewUi " + viewUi.getLibelle() + " (" + viewUi.getCode()
                                            + ") fired an event 'starting'.");
            registerViewUi(viewUi);
        }
    }

    @Subscriber(name = "viewStopped", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_VIEW_UI + ")(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_STOPPED + ")("
                                    + PROPERTY_KEY_MODULE_UI_CODE + "=*)(" + PROPERTY_KEY_VIEW_UI_CODE + "=*)(" + PROPERTY_KEY_VIEW_UI + "=*))")
    private void unregisterViewUi(Event event) {
        String moduleCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_MODULE_UI_CODE);
        if (moduleCodeFromEvent.equalsIgnoreCase(code)) {
            String viewCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_VIEW_UI_CODE);
            ViewUi viewUi = (ViewUi) event.getProperty(PROPERTY_KEY_VIEW_UI);
            logService.log(LogService.LOG_INFO,
                            "From ModuleUi " + libelle + " (" + code + ") / unregisterViewUi(event) => A ViewUi " + viewUi.getLibelle() + " (" + viewUi.getCode()
                                            + ") fired an event 'stopping'.");
            unregisterViewUi(viewCodeFromEvent, viewUi.getLibelle());
        }
    }

    @SuppressWarnings("unchecked")
    @Subscriber(name = "newViewUi", topics = TOPIC_MODULE_UI, filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_TABS + ")(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_NAME_NEW + ")(" + PROPERTY_KEY_MODULE_UI_CODE + "=*)(" + PROPERTY_KEY_VIEW_UI_CODE + "=*)(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*)("
                    + PROPERTY_KEY_EVENT_DATA + "=*)(" + PROPERTY_KEY_PARAMS + "=*)(" + PROPERTY_KEY_PORTAL_CURRENT_USER_ROLES + "=*))")
    private void newViewUi(Event event) {
        String moduleCodeFromEvent = String.class.cast(event.getProperty(PROPERTY_KEY_MODULE_UI_CODE));
        String viewCodeFromEvent = String.class.cast(event.getProperty(PROPERTY_KEY_VIEW_UI_CODE));
        List<String> currentUserRoles = List.class.cast(event.getProperty(PROPERTY_KEY_PORTAL_CURRENT_USER_ROLES));
        String sessionId = String.class.cast(event.getProperty(PROPERTY_KEY_PORTAL_SESSION_ID));
        TabsCallback tabsCallback = TabsCallback.class.cast(event.getProperty(PROPERTY_KEY_EVENT_DATA));
        Map<String, Object> mapParams = Map.class.cast(event.getProperty(PROPERTY_KEY_PARAMS));

        newViewUi(moduleCodeFromEvent, viewCodeFromEvent, currentUserRoles, sessionId, tabsCallback, mapParams);
    }

    private void newViewUi(String moduleCodeFromEvent, String viewCodeFromEvent, List<String> currentUserRoles, String sessionId, TabsCallback tabsCallback,
                    Map<String, Object> mapParams) {
        if (moduleCodeFromEvent.equalsIgnoreCase(code) && viewUis.containsKey(viewCodeFromEvent)) {
            ViewUi viewUi = viewUis.get(viewCodeFromEvent);
            if (currentUserRoles.contains(viewUi.getRolesAllowed()) || viewUi.getRolesAllowed().contains("ANY")) {
                ModuleUiCustomComponent moduleUiCustomComponent = null;
                if (onlyOneTabAllowed) {
                    moduleUiCustomComponent = checkModuleUiInstanceExistForSessionId(sessionId);
                } else {
                    if (viewUi.getSeveralTabsAllowed()) {
                        moduleUiCustomComponent = new ModuleUiCustomComponent(sessionId);
                        moduleUiCustomComponentList.add(moduleUiCustomComponent);
                    } else {
                        moduleUiCustomComponent = checkModuleUiInstanceExistForSessionAndViewUiCodeId(sessionId, viewUi.getCode());
                    }
                }
                String title = this.libelle + " - " + viewUi.getLibelle();
                moduleUiCustomComponent.setCaption(title);
                moduleUiCustomComponent.setCodeViewUi(viewUi.getCode());
                int tabHash = tabsCallback.addView(icon, closable, moduleUiCustomComponent);
                com.vaadin.ui.Component component = viewUi.getViewMainComponent(sessionId, tabHash, null, mapParams);
                moduleUiCustomComponent.setCompositionRoot(component);
                moduleUiCustomComponent.setTabHash(tabHash);
            }
        }
    }

    @Subscriber(name = "closeTabs", topics = TOPIC_MODULE_UI, filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_TABS + ")(" +
                    PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_NAME_CLOSED + ")(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*)(" + PROPERTY_KEY_TAB_HASH + "=*))")
    public void closeTabs(org.osgi.service.event.Event event) {
        if ((!keepModuleUiOnTabClose && onlyOneTabAllowed) || (!onlyOneTabAllowed)) {
            String sessionId = (String) event.getProperty(PROPERTY_KEY_PORTAL_SESSION_ID);
            Integer tabHash = (Integer) event.getProperty(PROPERTY_KEY_TAB_HASH);
            Iterator<ModuleUiCustomComponent> ite = moduleUiCustomComponentList.iterator();
            while (ite.hasNext()) {
                ModuleUiCustomComponent moduleUiCustomComponentFromList = ite.next();
                if (sessionId.equals(moduleUiCustomComponentFromList.getSessionId()) && tabHash.equals(moduleUiCustomComponentFromList.getTabHash())) {
                    clearModuleUiCustomComponent(moduleUiCustomComponentFromList);
                    ite.remove();
                }
            }
        }
    }

    /*
     * Callback
     */

    @Override
    public void registerViewUi(ViewUi viewUi) {
        String codeView = viewUi.getCode();
        logService.log(LogService.LOG_INFO, "From ModuleUi " + libelle + " (" + code + ") / registerViewUi(viewUi) => A ViewUi " + viewUi.getLibelle() + " (" + codeView
                        + ") is starting.");
        viewUis.put(codeView, viewUi);
        logService.log(LogService.LOG_DEBUG, "From ModuleUi " + libelle + " (" + code + ") => An event 'moduleUi starting' is going to be send on portal topic");
        publisherToPortalTopic.send(startProperties(this));
    }

    /*
     * Managed Properties (setter)
     */

    @Updated
    private void updated() {
        logService.log(LogService.LOG_INFO, "From ModuleUi " + libelle + " (" + code + ") => updtating module");
        if (keepModuleUiOnTabClose && !onlyOneTabAllowed) {
            logService.log(LogService.LOG_WARNING, "From ModuleUi " + libelle + " (" + code
                            + ") => module parameters keepModuleUiOnTabClose are set to true and severalInstanceAllowed are set to false. keepModuleUiOnTabClose will be ignored.");
        }
        clear();
        Dictionary<String, Object> propsStartView = startProperties(this);
        publisherToViewUiTopic.send(propsStartView);
        Dictionary<String, Object> propsStartPortal = startProperties(this, oldCode);
        publisherToPortalTopic.send(propsStartPortal);
        logService.log(LogService.LOG_INFO, "From ModuleUi " + libelle + " (" + code + ") => module updated");
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
        try {
            this.icon = FontAwesome.valueOf(icon);
        } catch (Exception e) {
            logService.log(LogService.LOG_WARNING, "Converting icon named " + icon + " in FontAwesome component failed. Default LINUX icon will be set.", e);
            this.icon = FontAwesome.LINUX;
        }
    }

    @Property(name = "closable", value = "true")
    private void setCloseable(Boolean closable) {
        this.closable = closable;
    }

    @Property(name = "onlyOneTabAllowed", value = "false")
    private void setOnlyOneTabAllowed(Boolean onlyOneTabAllowed) {
        this.onlyOneTabAllowed = onlyOneTabAllowed;
    }

    @Property(name = "keepModuleUiOnTabClose", value = "false")
    private void setKeepModuleUiOnTabClose(Boolean keepModuleUiOnTabClose) {
        this.keepModuleUiOnTabClose = keepModuleUiOnTabClose;
    }

    @Property(name = "displayOrder", value = "100000")
    private void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    /*
     * Getter
     */

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLibelle() {
        return libelle;
    }

    @Override
    public FontIcon getIcon() {
        return icon;
    }

    @Override
    public Boolean isClosable() {
        return closable;
    }

    @Override
    public Boolean isOnlyOneTabAllowed() {
        return onlyOneTabAllowed;
    }

    @Override
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, ViewUi> getViewUis(final List<String> currentUserRoles) {
        Map<String, ViewUi> viewMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(currentUserRoles)) {
            for (String viewKey : viewUis.keySet()) {
                ViewUi viewUi = viewUis.get(viewKey);
                for (String roleAllowed : viewUi.getRolesAllowed()) {
                    if ((currentUserRoles.contains(roleAllowed) || roleAllowed.equals("ANY")) && viewUi.getVisibleOnMenu()) {
                        viewMap.put(viewKey, viewUi);
                        break;
                    }
                }
            }
        }
        return MapUtils.unmodifiableMap(viewMap);
    }

    /*
     * Private instance methods
     */

    private void unregisterViewUi(String codeView, String libelleView) {
        logService.log(LogService.LOG_INFO, "From ModuleUi " + libelle + " (" + code + ") / unregisterViewUi(viewUi) => A ViewUi " + libelleView + " (" + codeView
                        + ") is stopping.");
        viewUis.remove(codeView);
        logService.log(LogService.LOG_DEBUG, "From ModuleUi " + libelle + " (" + code + ") => An event 'moduleUi stopping' is going to be send on portal topic");

        if (viewUis.isEmpty()) {
            publisherToPortalTopic.send(stopProperties(this));
        } else {
            publisherToPortalTopic.send(startProperties(this));
        }
    }

    private void clearModuleUiCustomComponent(ModuleUiCustomComponent moduleUiCustomComponent) {
        if (moduleUiCustomComponent != null) {
            moduleUiCustomComponent.getCompositionRoot().setParent(null);
            moduleUiCustomComponent = null;
        }
    }

    private void clear() {
        Iterator<ModuleUiCustomComponent> ite = moduleUiCustomComponentList.iterator();
        while (ite.hasNext()) {
            ModuleUiCustomComponent moduleUiCustomComponent = ite.next();
            clearModuleUiCustomComponent(moduleUiCustomComponent);
            ite.remove();
        }
        viewUis.clear();
        moduleUiCustomComponentList.clear();
    }

    private ModuleUiCustomComponent checkModuleUiInstanceExistForSessionId(String sessionId) {
        for (ModuleUiCustomComponent moduleUiCustomComponentFromList : moduleUiCustomComponentList) {
            if (moduleUiCustomComponentFromList.getSessionId() != null && moduleUiCustomComponentFromList.getSessionId().equals(sessionId)) {
                return moduleUiCustomComponentFromList;
            }
        }
        ModuleUiCustomComponent moduleUiCustomComponent = new ModuleUiCustomComponent(sessionId);
        moduleUiCustomComponentList.add(moduleUiCustomComponent);
        return moduleUiCustomComponent;
    }

    private ModuleUiCustomComponent checkModuleUiInstanceExistForSessionAndViewUiCodeId(String sessionId, String viewUiCode) {
        for (ModuleUiCustomComponent moduleUiCustomComponentFromList : moduleUiCustomComponentList) {
            if (moduleUiCustomComponentFromList.getSessionId() != null && moduleUiCustomComponentFromList.getSessionId().equals(sessionId)
                            && moduleUiCustomComponentFromList.getCodeViewUi() != null
                            && moduleUiCustomComponentFromList.getCodeViewUi().equals(viewUiCode)) {
                return moduleUiCustomComponentFromList;
            }
        }
        ModuleUiCustomComponent moduleUiCustomComponent = new ModuleUiCustomComponent(sessionId);
        moduleUiCustomComponentList.add(moduleUiCustomComponent);
        return moduleUiCustomComponent;
    }

    /*
     * Private static methods
     */

    private static Dictionary<String, Object> startProperties(final ModuleUiImpl moduleUi) {
        return startProperties(moduleUi, moduleUi.getCode());
    }

    private static Dictionary<String, Object> startProperties(final ModuleUiImpl moduleUi, String moduleCode) {

        Dictionary<String, Object> eventProps = new Hashtable<>(4);
        eventProps.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_MODULE_UI);
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STARTED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, moduleCode);
        eventProps.put(PROPERTY_KEY_MODULE_UI, moduleUi);

        return eventProps;
    }

    private static Dictionary<String, Object> stopProperties(final ModuleUiImpl moduleUi) {

        Dictionary<String, Object> eventProps = new Hashtable<>(3);
        eventProps.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_MODULE_UI);
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STOPPED);
        eventProps.put(PROPERTY_KEY_MODULE_UI, moduleUi);

        return eventProps;
    }

}
