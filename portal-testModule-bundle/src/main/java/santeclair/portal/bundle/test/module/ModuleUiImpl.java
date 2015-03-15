package santeclair.portal.bundle.test.module;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_UPDATED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_DATA;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_MENU;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_PORTAL;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_VIEW_UI;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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

    @Publishes(name = "moduleUiPublisher", topics = TOPIC_VIEW_UI + ", " + TOPIC_PORTAL, synchronous = true)
    private Publisher publisher;

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

    private Map<String, ViewUi> viewUis;

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
        publisher.send(startProperties());
        logService.log(LogService.LOG_INFO, this.libelle + " (" + this.code + ") Started");
    }

    @Invalidate
    public void stop() {
        logService.log(LogService.LOG_INFO, this.libelle + " (" + this.code + ") Stopping");
        publisher.send(stopProperties());
        logService.log(LogService.LOG_INFO, this.libelle + " (" + this.code + ") Stopped");
    }

    /*
     * @Subscriber
     */

    @Subscriber(name = "portalStarted", topics = TOPIC_MODULE_UI,
                    filter = "(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_STARTED + ")")
    public void portalStarted(Event event) {
        logService.log(LogService.LOG_INFO, "A Portal is Starting");
        PortalStartCallback portalStartCallback = PortalStartCallback.class.cast(event.getProperty(PROPERTY_KEY_EVENT_DATA));
        portalStartCallback.addMenuModule(menuModule);
    }

    @Subscriber(name = "viewStarted", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_STARTED + ")(" + PROPERTY_KEY_MODULE_UI_CODE + " = ${code}))",
                    dataKey = PROPERTY_KEY_VIEW_UI)
    public void viewStarted(ViewUi viewUi) {
        String codeView = viewUi.getCode();
        logService.log(LogService.LOG_INFO, "A View is Starting => " + codeView);
        viewUis.put(codeView, viewUi);
    }

    @Subscriber(name = "viewUpdated", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_UPDATED + ")(" + PROPERTY_KEY_MODULE_UI_CODE + " = ${code}))",
                    dataKey = PROPERTY_KEY_VIEW_UI)
    public void viewUpdated(ViewUi viewUi) {
        String codeView = viewUi.getCode();
        logService.log(LogService.LOG_INFO, "A View is Updating => " + codeView);
        viewUis.put(codeView, viewUi);
    }

    @Subscriber(name = "viewStopped", topics = TOPIC_MODULE_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_STOPPED + ")(" + PROPERTY_KEY_MODULE_UI_CODE + " = ${code}))",
                    dataKey = PROPERTY_KEY_VIEW_UI)
    public void viewStopped(ViewUi viewUi) {
        String codeView = viewUi.getCode();
        logService.log(LogService.LOG_INFO, "A View is Stopping => " + codeView);
        viewUis.remove(codeView);
    }

    /*
     * Properties
     */

    @Updated
    public void updated(Dictionary<?, ?> conf) {

    }

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
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_STARTED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, code);
        eventProps.put(PROPERTY_KEY_MODULE_UI_MENU, menuModule);

        return eventProps;
    }

    private Dictionary<String, Object> stopProperties() {
        logService.log(LogService.LOG_DEBUG, "Building TestModuleUi stopping dictionnary event");

        Dictionary<String, Object> eventProps = new Hashtable<>();
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_STOPPED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, code);
        eventProps.put(PROPERTY_KEY_MODULE_UI_MENU, menuModule);

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
