package santeclair.portal.bundle.test.module;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_PORTAL;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_DATA;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_PORTAL;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_VIEW_UI;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
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
import santeclair.portal.module.ModuleUi;
import santeclair.portal.view.ViewUi;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;

@Component
@Provides(specifications = ModuleUi.class)
public class ModuleUiImpl implements ModuleUi {

    @Publishes(name = "moduleUiPublisherView", topics = TOPIC_VIEW_UI, synchronous = true)
    private Publisher publisherView;

    @Publishes(name = "moduleUiPublisherPortal", topics = TOPIC_PORTAL, synchronous = true)
    private Publisher publisherPortal;

    @Requires
    private LogService logService;

    /*
     * Properties
     */
    private String code;
    private String libelle;
    private FontIcon icon;
    private Boolean isCloseable;
    private Boolean severalTabsAllowed;
    private Integer displayOrder;

    private final Map<String, ViewUi> viewUis = new HashMap<>();

    /*
     * Lifecycle
     */

    @Validate
    private void start() {
        logService.log(LogService.LOG_INFO, "ModuleUi " + this.libelle + " (" + this.code + ") is starting");
        Dictionary<String, Object> props = startProperties(this);
        publisherView.send(props);
        publisherPortal.send(props);
        logService.log(LogService.LOG_INFO, "ModuleUi " + this.libelle + " (" + this.code + ") started");
    }

    @Invalidate
    private void stop() {
        logService.log(LogService.LOG_INFO, "ModuleUi " + this.libelle + " (" + this.code + ") is stopping");
        viewUis.clear();
        Dictionary<String, Object> props = stopProperties(this);
        publisherPortal.send(props);
        logService.log(LogService.LOG_INFO, "ModuleUi " + this.libelle + " (" + this.code + ") stopped");
    }

    /*
     * @Subscriber
     */

    @Subscriber(name = "portalStarted", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_PORTAL + ")(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_STARTED + "))")
    private void portalStarted(Event event) {
        logService.log(LogService.LOG_INFO, "From ModuleUi " + libelle + " (" + code + ") / portalStarted(event) => A Portal is Starting");
        PortalStartCallback portalStartCallback = PortalStartCallback.class.cast(event.getProperty(PROPERTY_KEY_EVENT_DATA));
        portalStartCallback.addModuleUi(this);
    }

    @Subscriber(name = "viewStarted", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_VIEW_UI + ")(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_STARTED + ")("
                                    + PROPERTY_KEY_MODULE_UI_CODE + "=*))")
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
                                    + PROPERTY_KEY_MODULE_UI_CODE + "=*))")
    private void unregisterViewUi(Event event) {
        String moduleCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_MODULE_UI_CODE);
        if (moduleCodeFromEvent.equalsIgnoreCase(code)) {
            ViewUi viewUi = (ViewUi) event.getProperty(PROPERTY_KEY_VIEW_UI);
            logService.log(LogService.LOG_INFO,
                            "From ModuleUi " + libelle + " (" + code + ") / unregisterViewUi(event) => A ViewUi " + viewUi.getLibelle() + " (" + viewUi.getCode()
                                            + ") fired an event 'stopping'.");
            unregisterViewUi(viewUi);
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
        publisherPortal.send(startProperties(this));
    }

    /*
     * Managed Properties (setter)
     */

    @Updated
    private void updated() {
        viewUis.clear();
        Dictionary<String, Object> props = startProperties(this);
        publisherView.send(props);
        publisherPortal.send(props);
    }

    @Property(name = "code", mandatory = true)
    private void setCode(String code) {
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

    @Property(name = "isCloseable", value = "true")
    private void setIsCloseable(Boolean isCloseable) {
        this.isCloseable = isCloseable;
    }

    @Property(name = "severalTabsAllowed", value = "false")
    private void setSeveralTabsAllowed(Boolean severalTabsAllowed) {
        this.severalTabsAllowed = severalTabsAllowed;
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
    public Boolean getIsCloseable() {
        return isCloseable;
    }

    @Override
    public Boolean getSeveralTabsAllowed() {
        return severalTabsAllowed;
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
                    if (currentUserRoles.contains(roleAllowed) || roleAllowed.equals("ANY")) {
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
    
    private void unregisterViewUi(ViewUi viewUi) {
        String codeView = viewUi.getCode();
        logService.log(LogService.LOG_INFO, "From ModuleUi " + libelle + " (" + code + ") / unregisterViewUi(viewUi) => A ViewUi " + viewUi.getLibelle() + " (" + codeView
                        + ") is stopping.");
        viewUis.remove(codeView);
        logService.log(LogService.LOG_DEBUG, "From ModuleUi " + libelle + " (" + code + ") => An event 'moduleUi stopping' is going to be send on portal topic");
        publisherPortal.send(startProperties(this));
    }
    
    /*
     * Private static methods
     */

    private static Dictionary<String, Object> startProperties(final ModuleUiImpl moduleUi) {

        Dictionary<String, Object> eventProps = new Hashtable<>(4);
        eventProps.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_MODULE_UI);
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STARTED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, moduleUi.getCode());
        eventProps.put(PROPERTY_KEY_MODULE_UI, moduleUi);

        return eventProps;
    }

    private static Dictionary<String, Object> stopProperties(final ModuleUiImpl moduleUi) {

        Dictionary<String, Object> eventProps = new Hashtable<>(4);
        eventProps.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_MODULE_UI);
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STOPPED);
        eventProps.put(PROPERTY_KEY_MODULE_UI, moduleUi);

        return eventProps;
    }

}
