package santeclair.portal.bundle.test.view;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_VIEW_UI;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Updated;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.bundle.utils.module.ModuleUi;
import santeclair.portal.bundle.utils.view.AbstractViewUi;
import santeclair.portal.bundle.utils.view.ViewUi;

@Component
public class ViewUiImpl extends AbstractViewUi implements ViewUi {

    @Publishes(name = "testViewUiPublisher", topics = TOPIC_MODULE_UI)
    private Publisher publisher;

    @Validate
    public void start() {
        logService.log(LogService.LOG_INFO, "TestViewUi Starting");

        Dictionary<String, Object> props = new Hashtable<>(4);
        props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_VIEW_UI);
        props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STARTED);
        props.put(PROPERTY_KEY_MODULE_UI_CODE, codeModule);
        props.put(PROPERTY_KEY_VIEW_UI, this);

        publisher.send(props);
        logService.log(LogService.LOG_INFO, "TestViewUi Started");
    }

    /*
     * Subscriber
     * 
     * (" + PROPERTY_KEY_MODULE_UI_CODE + "=${codeModule})
     * 
     */
    @Subscriber(name = "moduleStart", topics = TOPIC_VIEW_UI, filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_MODULE_UI + ")(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_NAME_STARTED + "))")
    public void moduleStart(Event event) {
        String moduleCodeFromEvent = (String) event.getProperty(PROPERTY_KEY_MODULE_UI_CODE);
        if (StringUtils.isNotBlank(moduleCodeFromEvent) && moduleCodeFromEvent.equalsIgnoreCase(codeModule)) {
            ModuleUi moduleUi = (ModuleUi) event.getProperty(PROPERTY_KEY_MODULE_UI);
            logService.log(LogService.LOG_DEBUG, "ModuleUi with code " + codeModule + " is starting.");

            Dictionary<String, Object> props = new Hashtable<>(1);
            props.put(PROPERTY_KEY_VIEW_UI, this);
            props.put(PROPERTY_KEY_MODULE_UI_CODE, codeModule);
            Event registerViewUiEvent = new Event(TOPIC_MODULE_UI, props);

            moduleUi.registerViewUi(registerViewUiEvent);
        }
    }

    /*
     * Bind services
     */

    @Override
    @Bind
    public void bindLogService(LogService logService) {
        super.bindLogService(logService);
    }

    /*
     * Properties
     */

    @Override
    @Updated
    public void updated(Dictionary<?, ?> conf) {
        super.updated(conf);
    }

    @Override
    @Property(name = "codeModule", mandatory = true)
    protected void setCodeModule(String codeModule) {
        super.setCodeModule(codeModule);
    }

    @Override
    @Property(name = "code", mandatory = true)
    protected void setCode(String code) {
        super.setCode(code);
    }

    @Override
    @Property(name = "libelle", mandatory = true)
    protected void setLibelle(String libelle) {
        super.setLibelle(libelle);
    }

    @Override
    @Property(name = "icon", mandatory = true)
    protected void setIcon(String icon) {
        super.setIcon(icon);
    }

    @Override
    @Property(name = "openOnInitialization", value = "false")
    protected void setOpenOnInitialization(Boolean openOnInitialization) {
        super.setOpenOnInitialization(openOnInitialization);
    }
}
