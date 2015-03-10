package santeclair.portal.bundle.test;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_DATA;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_DISPLAY_ORDER;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_ICON;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_USER_ROLES;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.event.publisher.callback.PortalStartCallback;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;

public abstract class ModuleUiHelper {

    private static final String ROLE_ANY = "ANY";

    private LogService logService;
    private String code;
    private String name;
    private Integer displayOrder;

    private FontIcon fontIcon;
    
    private List<String> rolesIsCloseable;
    private List<String> rolesAllowed;
    private List<String> rolesOpenOnInitialization;
    private List<String> rolesSeveralInstanceAllowed;

    protected void bindLogService(LogService logService) {
        this.logService = logService;
        
    }

    protected void start() {
        logService.log(LogService.LOG_INFO, "TestModuleUi Starting");
        getPublisher().send(startProperties());
        logService.log(LogService.LOG_INFO, "TestModuleUi Started");
    }

    protected void stop() {
        logService.log(LogService.LOG_INFO, "TestModuleUi Stopping");
        getPublisher().send(stopProperties());
        logService.log(LogService.LOG_INFO, "TestModuleUi Stopped");
    }

    protected void portalStarted(Event event) {
        logService.log(LogService.LOG_INFO, "A Portal is Starting");
        PortalStartCallback portalStartCallback = PortalStartCallback.class.cast(event.getProperty(PROPERTY_KEY_EVENT_DATA));
        @SuppressWarnings("unchecked")
        List<String> currentUserRoles = (List<String>) event.getProperty(PROPERTY_KEY_PORTAL_USER_ROLES);
        if ((rolesOpenOnInitialization.contains(ROLE_ANY) || currentUserRoles.contains(rolesOpenOnInitialization))
                        && (rolesAllowed.contains(ROLE_ANY) || currentUserRoles.contains(rolesAllowed))) {
            portalStartCallback.addNewModuleUiFactory(code, name, fontIcon, displayOrder, currentUserRoles.contains(rolesIsCloseable));
        }
    }

    protected void updated(Dictionary<?, ?> conf) {
        logService.log(LogService.LOG_DEBUG, "The instance was reconfigured");
        Enumeration<?> keysEnumeration = conf.keys();
        while (keysEnumeration.hasMoreElements()) {
            Object key = keysEnumeration.nextElement();
            Object value = conf.get(key);
            logService.log(LogService.LOG_DEBUG, key + " => " + value);
            if (value instanceof Object[]) {
                Object[] valueArray = (Object[]) value;
                for (Object object : valueArray) {
                    logService.log(LogService.LOG_DEBUG, "       => " + object);
                }
            }
        }
    }

    protected void setIcon(String icon) {
        try {
            fontIcon = FontAwesome.valueOf(icon);
        } catch (IllegalArgumentException | NullPointerException e) {
            logService.log(LogService.LOG_WARNING,
                            "The icon set in the cfg module file is invalid. Please refer to vaadin font awesome enumeration to set an icon. Default icon LINUX selected.");
        }
    }

    protected void setRolesAllowed(String[] rolesAllowedArray) {
        rolesAllowed = Arrays.asList(rolesAllowedArray);
    }

    protected void setRolesSeveralInstanceAllowed(String[] rolesSeveralInstanceAllowedArray) {
        rolesSeveralInstanceAllowed = Arrays.asList(rolesSeveralInstanceAllowedArray);
    }

    protected void setRolesOpenOnInitialization(String[] rolesOpenOnInitializationArray) {
        rolesOpenOnInitialization = Arrays.asList(rolesOpenOnInitializationArray);
    }

    protected void setRolesIsCloseable(String[] rolesIsCloseableArray) {
        rolesIsCloseable = Arrays.asList(rolesIsCloseableArray);
    }

    protected void setCode(String code) {
        this.code = code;
    }

    protected void setName(String name) {
        this.name = name;
    }

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
        eventProps.put(PROPERTY_KEY_MODULE_UI_NAME, name);
        eventProps.put(PROPERTY_KEY_MODULE_UI_DISPLAY_ORDER, displayOrder);
        eventProps.put(PROPERTY_KEY_MODULE_UI_ICON, fontIcon);

        return eventProps;
    }

    private Dictionary<String, Object> stopProperties() {
        logService.log(LogService.LOG_DEBUG, "Building TestModuleUi stopping dictionnary event");
        Dictionary<String, Object> eventProps = new Hashtable<>();

        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_STOPPED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, code);

        return eventProps;
    }

    /*
     * Abstract method
     */

    protected abstract BundleContext getContext();

    protected abstract Publisher getPublisher();

}
