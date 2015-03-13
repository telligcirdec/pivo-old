package santeclair.portal.bundle.test.module;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_DATA;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_MENU;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_PORTAL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

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

@Component
@Provides(specifications = ModuleUi.class)
public class ModuleUiImpl implements ModuleUi {

    private static final String PUBLISHER_NAME = "TestModuleUiPublisher";
    
    @Publishes(name = PUBLISHER_NAME, topics = TOPIC_MODULE_UI)
    private Publisher publisher;
    
    @Requires
    private LogService logService;
    
    /*
     * Properties
     */
    private String code;
    private String libelle;
    private String icon;
    private Boolean isCloseable;
    private Boolean severalTabsAllowed;
    private Integer displayOrder;
    
    private final List<ViewUi> viewsUi = new ArrayList<ViewUi>();
    
    /*
     * Instance var
     */
    private MenuModule menuModule;
    
    @Validate
    public void start() {
        logService.log(LogService.LOG_INFO, "TestModuleUi Starting");
        List<MenuView> menuViews = new ArrayList<MenuView>();
        for (ViewUi viewUi : viewsUi) {
            menuViews.add(new MenuView(viewUi.getCode(), viewUi.getLibelle(), viewUi.getIcon(), viewUi.getOpenOnInitialization()));
        }
        menuModule = new MenuModule(code, libelle, icon, displayOrder, isCloseable, severalTabsAllowed, menuViews);
        
        publisher.send(startProperties());
        logService.log(LogService.LOG_INFO, "TestModuleUi Started");
    }

    @Invalidate
    public void stop() {
        logService.log(LogService.LOG_INFO, "TestModuleUi Stopping");
        publisher.send(stopProperties());
        logService.log(LogService.LOG_INFO, "TestModuleUi Stopped");
    }

    @Subscriber(name = "portalStarted", topics = TOPIC_PORTAL, filter = "(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_STARTED + ")")
    public void portalStarted(Event event) {
        logService.log(LogService.LOG_INFO, "A Portal is Starting");
        PortalStartCallback portalStartCallback = PortalStartCallback.class.cast(event.getProperty(PROPERTY_KEY_EVENT_DATA));
        portalStartCallback.addNewModuleUi(menuModule);
    }

    @Property(name = "code", mandatory = true)
    protected void setCode(String code) {
        this.code = code;
    }

    @Property(name = "libelle", mandatory = true)
    protected void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Property(name = "icon", mandatory = true)
    protected void setIcon(String icon) {
        this.icon = icon;
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

    public String getCode() {
        return code;
    }
    
    public String getLibelle() {
        return libelle;
    }

    public String getIcon() {
        return icon;
    }

    public Boolean getIsCloseable() {
        return isCloseable;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    @Override
    public void registerView(ViewUi view) {
        this.viewsUi.add(view);
    }
    
    /*
     * Private method
     */
    private Dictionary<String, Object> startProperties() {
        logService.log(LogService.LOG_DEBUG, "Building TestModuleUi starting dictionnary event");

        Dictionary<String, Object> eventProps = new Hashtable<>();
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_STARTED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_MENU, menuModule);
        
        return eventProps;
    }

    private Dictionary<String, Object> stopProperties() {
        logService.log(LogService.LOG_DEBUG, "Building TestModuleUi stopping dictionnary event");
        
        Dictionary<String, Object> eventProps = new Hashtable<>();
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_STOPPED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_MENU, menuModule);

        return eventProps;
    }
}
