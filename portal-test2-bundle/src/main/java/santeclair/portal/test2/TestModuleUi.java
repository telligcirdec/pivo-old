package santeclair.portal.test2;

import java.util.Arrays;

import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

import com.vaadin.server.FontAwesome;

public class TestModuleUi {

    private Publisher publisher;

    private BundleContext context;

    // private static final String ROLE_ANY = "ANY";
    //
    // public LogService logService;
    //
    // private String code;
    // private String name;
    // private Integer displayOrder;
    //
    // private FontIcon fontIcon;
    // private List<String> rolesIsCloseable;
    // private List<String> rolesAllowed;
    // private List<String> rolesOpenOnInitialization;
    // private List<String> rolesSeveralInstanceAllowed;

    public TestModuleUi(BundleContext bundleContext) {
        super(bundleContext);
    }

    // @Override
    // public void start() {
    // logService.log(LogService.LOG_INFO, "TestModuleUi Starting");
    // getPublisher().send(startProperties());
    // logService.log(LogService.LOG_INFO, "TestModuleUi Started");
    // }
    //
    // @Override
    // public void stop() {
    // logService.log(LogService.LOG_INFO, "TestModuleUi Stopping");
    // getPublisher().send(stopProperties());
    // logService.log(LogService.LOG_INFO, "TestModuleUi Stopped");
    // }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    // @Override
    // public void bindLogService(LogService logService) {
    // this.logService = logService;
    // }
    //
    // @Override
    // public void portalStarted(Event event) {
    // logService.log(LogService.LOG_INFO, "A Portal is Starting");
    // PortalStartCallback portalStartCallback = PortalStartCallback.class.cast(event.getProperty(PROPERTY_KEY_EVENT_DATA));
    // @SuppressWarnings("unchecked")
    // List<String> currentUserRoles = (List<String>) event.getProperty(PROPERTY_KEY_PORTAL_USER_ROLES);
    // if ((rolesOpenOnInitialization.contains(ROLE_ANY) || currentUserRoles.contains(rolesOpenOnInitialization))
    // && (rolesAllowed.contains(ROLE_ANY) || currentUserRoles.contains(rolesAllowed))) {
    // portalStartCallback.addNewModuleUiFactory(code, name, fontIcon, displayOrder, currentUserRoles.contains(rolesIsCloseable));
    // }
    // }
    //
    // @Override
    // public void updated(Dictionary<?, ?> conf) {
    // logService.log(LogService.LOG_DEBUG, "The instance was reconfigured");
    // Enumeration<?> keysEnumeration = conf.keys();
    // while (keysEnumeration.hasMoreElements()) {
    // Object key = keysEnumeration.nextElement();
    // logService.log(LogService.LOG_DEBUG, key + " => " + conf.get(key));
    // }
    // }

    /*
    * Properties
    */

    @Override
    public void setIcon(String icon) {
        fontIcon = FontAwesome.LINUX;
        try {
            fontIcon = FontAwesome.valueOf(icon);
        } catch (IllegalArgumentException | NullPointerException e) {
            logService.log(LogService.LOG_WARNING,
                            "The icon set in the cfg module file is invalid. Please refer to vaadin font awesome enumeration to set an icon. Default icon LINUX selected.");
        }
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public void setRolesAllowed(String[] rolesAllowedArray) {
        rolesAllowed = Arrays.asList(rolesAllowedArray);
    }

    @Override
    public void setRolesSeveralInstanceAllowed(String[] rolesSeveralInstanceAllowedArray) {
        rolesSeveralInstanceAllowed = Arrays.asList(rolesSeveralInstanceAllowedArray);
    }

    @Override
    public void setRolesOpenOnInitialization(String[] rolesOpenOnInitializationArray) {
        rolesOpenOnInitialization = Arrays.asList(rolesOpenOnInitializationArray);
    }

    @Override
    public void setRolesIsCloseable(String[] rolesIsCloseableArray) {
        rolesIsCloseable = Arrays.asList(rolesIsCloseableArray);
    }
    //
    // /*
    // * Private method
    // */
    //
    // @Override
    // protected Dictionary<String, Object> startProperties() {
    // logService.log(LogService.LOG_DEBUG, "Building TestModuleUi starting dictionnary event");
    // Dictionary<String, Object> eventProps = new Hashtable<>();
    //
    // eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_STARTED);
    // eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, code);
    // eventProps.put(PROPERTY_KEY_MODULE_UI_NAME, name);
    // eventProps.put(PROPERTY_KEY_MODULE_UI_DISPLAY_ORDER, displayOrder);
    // eventProps.put(PROPERTY_KEY_MODULE_UI_ICON, fontIcon);
    //
    // return eventProps;
    // }
    //
    // @Override
    // protected Dictionary<String, Object> stopProperties() {
    // logService.log(LogService.LOG_DEBUG, "Building TestModuleUi stopping dictionnary event");
    // Dictionary<String, Object> eventProps = new Hashtable<>();
    //
    // eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_STOPPED);
    // eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, code);
    //
    // return eventProps;
    // }
}
