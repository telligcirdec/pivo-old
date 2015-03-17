package santeclair.portal.bundle.utils.view;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI;

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

    private String codeModule;
    private String code;
    private String libelle;
    private FontIcon icon;
    private Boolean openOnInitialization;
    private List<String> rolesAllowed;

    protected void start() {
        logService.log(LogService.LOG_INFO, "ViewUi " + this.code + " is starting");

        Dictionary<String, Object> props = new Hashtable<>(4);
        props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_VIEW_UI);
        props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STARTED);
        props.put(PROPERTY_KEY_MODULE_UI_CODE, codeModule);
        props.put(PROPERTY_KEY_VIEW_UI, this);
        getPublisher().send(props);

        logService.log(LogService.LOG_INFO, "ViewUi " + this.code + " started");
    }

    protected void stop() {
        logService.log(LogService.LOG_INFO, "ViewUi " + this.code + " is stopping");

        Dictionary<String, Object> props = new Hashtable<>(4);
        props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_VIEW_UI);
        props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STOPPED);
        props.put(PROPERTY_KEY_MODULE_UI_CODE, codeModule);
        props.put(PROPERTY_KEY_VIEW_UI, this);
        getPublisher().send(props);

        logService.log(LogService.LOG_INFO, "ViewUi " + this.code + " stopped");
    }

    protected void moduleStart(Event event) {
        String moduleCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_MODULE_UI_CODE);
        if (StringUtils.isNotBlank(moduleCodeFromEvent) && moduleCodeFromEvent.equalsIgnoreCase(codeModule)) {
            ModuleUi moduleUi = (ModuleUi) event.getProperty(PROPERTY_KEY_MODULE_UI);
            logService.log(LogService.LOG_DEBUG, "From ViewUi (moduleStart(event)) " + this.code + " => ModuleUi with code " + codeModule + " is starting.");
            moduleUi.registerViewUi(this);
        }
    }

    protected void moduleStop(Event event) {
        String moduleCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_MODULE_UI_CODE);
        if (moduleCodeFromEvent.equalsIgnoreCase(codeModule)) {
            ModuleUi moduleUi = (ModuleUi) event.getProperty(PROPERTY_KEY_MODULE_UI);
            logService.log(LogService.LOG_DEBUG, "From ViewUi (moduleStop(event)) " + this.code + " => ModuleUi with code " + codeModule + " is stopping.");
            moduleUi.unregisterViewUi(this);
        }
    }

    protected void updated(Dictionary<?, ?> conf) {
        logService.log(LogService.LOG_DEBUG, "ViewUi " + this.code + " has been modified.");
    }

    protected void bindLogService(LogService logService) {
        logService.log(LogService.LOG_DEBUG, "ViewUi is binding logService.");
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

}
