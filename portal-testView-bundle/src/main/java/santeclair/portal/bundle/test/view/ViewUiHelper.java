package santeclair.portal.bundle.test.view;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_DATA;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.event.publisher.callback.PortalStartCallback;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;

public abstract class ViewUiHelper {

    private static final String ROLE_ANY = "ANY";

    protected LogService logService;
    
    private String codeModule;
    private String code;
    private String libelle;
    private FontIcon icon;
    private Boolean openOnInitialization;

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
    }

    protected void updated(Dictionary<?, ?> conf) {
        logService.log(LogService.LOG_DEBUG, "The instance was reconfigured");
        Enumeration<?> keysEnumeration = conf.keys();
        while (keysEnumeration.hasMoreElements()) {
            String key = (String)keysEnumeration.nextElement();
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
    
    protected void bindLogService(LogService logService) {
        this.logService = logService;
    }

    protected void setCodeModule(String codeModule) {
        this.codeModule = codeModule;
    }

    protected void setCode(String code) {
        this.code = code;
    }
    
    protected void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    protected void setIcon(String icon) {
        if(icon != null && !icon.isEmpty()){
            this.icon = FontAwesome.valueOf(icon);
            
        }
    }

    protected void setOpenOnInitialization(Boolean openOnInitialization) {
        this.openOnInitialization = openOnInitialization;
    }

    /*
     * Private method
     */
    private Dictionary<String, Object> startProperties() {
        logService.log(LogService.LOG_DEBUG, "Building TestModuleUi starting dictionnary event");
        Dictionary<String, Object> eventProps = new Hashtable<>();

        /*eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_STARTED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, code);
        eventProps.put(PROPERTY_KEY_MODULE_UI_NAME, name);
        eventProps.put(PROPERTY_KEY_MODULE_UI_DISPLAY_ORDER, displayOrder);
        eventProps.put(PROPERTY_KEY_MODULE_UI_ICON, fontIcon);*/

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
