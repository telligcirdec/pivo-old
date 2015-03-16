package santeclair.portal.bundle.test.module;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_PORTAL;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_UPDATED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_DATA;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_MENU;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_PORTAL;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_VIEW_UI;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.bundle.utils.module.ModuleUi;
import santeclair.portal.bundle.utils.view.ViewUi;
import santeclair.portal.event.publisher.callback.PortalStartCallback;
import santeclair.portal.menu.MenuModule;
import santeclair.portal.menu.MenuView;

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
    private String codeOldValue;
    private String code;
    private String libelle;
    private FontIcon icon;
    private Boolean isCloseable;
    private Boolean severalTabsAllowed;
    private Integer displayOrder;

    private final Map<String, ViewUi> viewUis = new HashMap<>();

    /*
     * Instance var
     */
    private MenuModule menuModule;

    /*
     * Lifecycle
     */

    @Validate
    public void start() {
        logService.log(LogService.LOG_INFO, this.libelle + " (" + this.code + ") Starting");
        menuModule = constructMenuModule(code, libelle, icon, displayOrder, isCloseable, severalTabsAllowed, viewUis);
        Dictionary<String, Object> props = startProperties();
        publisherView.send(props);
        publisherPortal.send(props);
        logService.log(LogService.LOG_INFO, this.libelle + " (" + this.code + ") Started");
    }

    @Invalidate
    public void stop() {
        logService.log(LogService.LOG_INFO, this.libelle + " (" + this.code + ") Stopping"); 
        Dictionary<String, Object> props = stopProperties();
        publisherView.send(props);
        publisherPortal.send(props);
        logService.log(LogService.LOG_INFO, this.libelle + " (" + this.code + ") Stopped");
    }

    /*
     * @Subscriber
     */

    @Subscriber(name = "portalStarted", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_PORTAL + ")(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_STARTED + "))")
    public void portalStarted(Event event) {
        logService.log(LogService.LOG_INFO, "A Portal is Starting");
        PortalStartCallback portalStartCallback = PortalStartCallback.class.cast(event.getProperty(PROPERTY_KEY_EVENT_DATA));
        if (null != menuModule.getMenuViews() && !menuModule.getMenuViews().isEmpty()) {
            portalStartCallback.addMenuModule(menuModule);
        }
    }

    @Override
    @Subscriber(name = "viewStarted", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_VIEW_UI + ")(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_STARTED + "))")
    public void registerViewUi(Event event) {
        String moduleCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_MODULE_UI_CODE);
        if (StringUtils.isNotBlank(moduleCodeFromEvent) && moduleCodeFromEvent.equalsIgnoreCase(code)) {
            ViewUi viewUi = (ViewUi) event.getProperty(PROPERTY_KEY_VIEW_UI);
            String codeView = viewUi.getCode();
            logService.log(LogService.LOG_INFO, "A View is Starting => " + codeView);
            viewUis.put(codeView, viewUi);
            menuModule.getMenuViews().add(new MenuView(viewUi.getCode(), viewUi.getLibelle(), viewUi.getIcon(), viewUi.getOpenOnInitialization()));

            publisherPortal.send(startProperties());
            
        }
    }

    @Subscriber(name = "viewUpdated", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_VIEW_UI + ")(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_UPDATED + "))")
    public void viewUpdated(Event event) {
        String moduleCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_MODULE_UI_CODE);
        if (StringUtils.isNotBlank(moduleCodeFromEvent) && moduleCodeFromEvent.equalsIgnoreCase(code)) {
            ViewUi viewUi = (ViewUi) event.getProperty(PROPERTY_KEY_VIEW_UI);
            String codeView = viewUi.getCode();
            logService.log(LogService.LOG_INFO, "A View is Updating => " + codeView);
            viewUis.put(codeView, viewUi); 
        }
    }

    @Override
    @Subscriber(name = "viewStopped", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_VIEW_UI + ")(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_STOPPED + "))")
    public void unregisterViewUi(Event event) {
        String moduleCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_MODULE_UI_CODE);
        if (StringUtils.isNotBlank(moduleCodeFromEvent) && moduleCodeFromEvent.equalsIgnoreCase(code)) {
            ViewUi viewUi = (ViewUi) event.getProperty(PROPERTY_KEY_VIEW_UI);
            String codeView = viewUi.getCode();
            logService.log(LogService.LOG_INFO, "A View is Stopping => " + codeView);
            viewUis.remove(codeView);
            menuModule.removeMenuView(codeView);
            publisherPortal.send(stopProperties());
        }
    }

    /*
     * Properties
     */

    @Property(name = "code", mandatory = true)
    protected void setCode(String code) {
        this.codeOldValue = this.code;
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Property(name = "libelle", mandatory = true)
    protected void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Property(name = "icon", mandatory = true)
    protected void setIcon(String icon) {
        try {
            this.icon = FontAwesome.valueOf(icon);
        } catch (Exception e) {
            logService.log(LogService.LOG_WARNING, "Converting icon named " + icon + " in FontAwesome component failed. Default LINUX icon will be set.", e);
            this.icon = FontAwesome.LINUX;
        }
    }

    @Property(name = "isCloseable", value = "true")
    protected void setIsCloseable(Boolean isCloseable) {
        this.isCloseable = isCloseable;
    }

    @Property(name = "severalTabsAllowed", value = "false")
    protected void setSeveralTabsAllowed(Boolean severalTabsAllowed) {
        this.severalTabsAllowed = severalTabsAllowed;
    }

    @Property(name = "displayOrder", value = "100000")
    protected void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    /*
     * Private method
     */
    private Dictionary<String, Object> startProperties() {
        logService.log(LogService.LOG_DEBUG, "Building TestModuleUi starting dictionnary event");

        Dictionary<String, Object> eventProps = new Hashtable<>();
        eventProps.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_MODULE_UI);
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STARTED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, code);
        eventProps.put(PROPERTY_KEY_MODULE_UI_MENU, menuModule);
        eventProps.put(PROPERTY_KEY_MODULE_UI, this);

        return eventProps;
    }

    private Dictionary<String, Object> stopProperties() {
        logService.log(LogService.LOG_DEBUG, "Building TestModuleUi stopping dictionnary event");

        Dictionary<String, Object> eventProps = new Hashtable<>();
        eventProps.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_MODULE_UI);
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STOPPED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, code);
        eventProps.put(PROPERTY_KEY_MODULE_UI_MENU, menuModule);
        eventProps.put(PROPERTY_KEY_MODULE_UI, this);

        return eventProps;
    }

    /**
     * 
     */
    private static MenuModule constructMenuModule(final String code, final String libelle, final FontIcon icon, final Integer displayOrder, final Boolean isCloseable,
                    final Boolean severalTabsAllowed, final Map<String, ViewUi> viewsUi) {
        List<MenuView> menuViews = new ArrayList<MenuView>();
        if (null != viewsUi) {
            for (ViewUi viewUi : viewsUi.values()) {
                menuViews.add(new MenuView(viewUi.getCode(), viewUi.getLibelle(), viewUi.getIcon(), viewUi.getOpenOnInitialization()));
            }
        }
        return new MenuModule(code, libelle, icon, displayOrder, isCloseable, severalTabsAllowed, menuViews);
    }

}
