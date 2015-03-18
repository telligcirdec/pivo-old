package santeclair.portal.bundle.utils.view;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI_CODE;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.module.ModuleUi;
import santeclair.portal.view.ViewUi;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;

public abstract class AbstractViewUi implements ViewUi {

    private LogService logService;

    private String oldCodeModule;
    private String codeModule;
    private String oldCode;
    private String code;
    private String libelle;
    private FontIcon icon;
    private Boolean openOnInitialization;
    private List<String> rolesAllowed;

    /*
     * Lifecycle
     */

    protected void start() {
        logService.log(LogService.LOG_INFO, "ViewUi " + this.libelle + " (" + this.code + ") is starting");

        getPublisher().send(startProperties(this));

        logService.log(LogService.LOG_INFO, "ViewUi " + this.libelle + " (" + this.code + ") started");
    }

    protected void stop() {
        logService.log(LogService.LOG_INFO, "ViewUi " + this.libelle + " (" + this.code + ") is stopping");

        getPublisher().send(stopProperties(this));

        logService.log(LogService.LOG_INFO, "ViewUi " + this.libelle + " (" + this.code + ") stopped");
    }

    /*
     * Subscriber
     */

    protected void moduleStart(Event event) {
        String moduleCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_MODULE_UI_CODE);
        if (StringUtils.isNotBlank(moduleCodeFromEvent) && moduleCodeFromEvent.equalsIgnoreCase(codeModule)) {
            ModuleUi moduleUi = (ModuleUi) event.getProperty(PROPERTY_KEY_MODULE_UI);
            logService.log(LogService.LOG_DEBUG, "From ViewUi " + this.libelle + " (" + this.code + ") (moduleStart(event)) => ModuleUi with code " + codeModule + " is starting.");
            moduleUi.registerViewUi(this);
        }
    }

    // protected void moduleStop(Event event) {
    // String moduleCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_MODULE_UI_CODE);
    // if (moduleCodeFromEvent.equalsIgnoreCase(codeModule)) {
    // ModuleUi moduleUi = (ModuleUi) event.getProperty(PROPERTY_KEY_MODULE_UI);
    // logService.log(LogService.LOG_DEBUG, "From ViewUi " + this.libelle + " (" + this.code + ") (moduleStop(event)) => ModuleUi with code " +
    // codeModule + " is stopping.");
    // moduleUi.unregisterViewUi(this);
    // }
    // }

    /*
     * Bind services
     */

    protected void bindLogService(LogService logService) {
        logService.log(LogService.LOG_DEBUG, "ViewUi is binding logService.");
        this.logService = logService;
    }

    /*
     * Managed Properties (setter)
     */

    protected void updated() {
        logService.log(LogService.LOG_DEBUG, "ViewUi " + this.libelle + " (" + this.code + ") has been modified.");
        getPublisher().send(stopProperties(this, oldCodeModule, oldCode));
        getPublisher().send(startProperties(this));
    }

    protected void setCodeModule(String codeModule) {
        this.oldCodeModule = this.codeModule == null ? codeModule : this.codeModule;
        this.codeModule = codeModule;
    }

    protected void setCode(String code) {
        this.oldCode = this.code == null ? code : this.code;
        this.code = code;
    }

    protected void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    protected void setIcon(String icon) {
        if (icon != null && !icon.isEmpty()) {
            this.icon = FontAwesome.valueOf(icon);
        }
    }

    protected void setRolesAllowed(String[] rolesAllowed) {
        this.rolesAllowed = Arrays.asList(rolesAllowed);
    }

    protected void setOpenOnInitialization(Boolean openOnInitialization) {
        this.openOnInitialization = openOnInitialization;
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
    public List<String> getRolesAllowed() {
        return rolesAllowed;
    }

    /*
     * Abstract method
     */

    protected abstract Publisher getPublisher();

    /*
     * Static methods
     */

    private static Dictionary<String, Object> startProperties(final AbstractViewUi viewUi) {

        Dictionary<String, Object> eventProps = new Hashtable<>(4);
        eventProps.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_VIEW_UI);
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STARTED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, viewUi.codeModule);
        eventProps.put(PROPERTY_KEY_VIEW_UI, viewUi);

        return eventProps;
    }

    private static Dictionary<String, Object> stopProperties(final AbstractViewUi viewUi) {
        return stopProperties(viewUi, viewUi.codeModule, viewUi.code);
    }

    private static Dictionary<String, Object> stopProperties(final AbstractViewUi viewUi, final String codeModule, final String codeView) {

        Dictionary<String, Object> eventProps = new Hashtable<>(4);
        eventProps.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_VIEW_UI);
        eventProps.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STOPPED);
        eventProps.put(PROPERTY_KEY_MODULE_UI_CODE, codeModule);
        eventProps.put(PROPERTY_KEY_VIEW_UI_CODE, codeView);
        eventProps.put(PROPERTY_KEY_VIEW_UI, viewUi);

        return eventProps;
    }

}
