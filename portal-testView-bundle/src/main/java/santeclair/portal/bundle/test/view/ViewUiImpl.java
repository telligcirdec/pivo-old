package santeclair.portal.bundle.test.view;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_PORTAL;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_VIEW_UI;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.Factory;
import org.apache.felix.ipojo.InstanceManager;
import org.apache.felix.ipojo.MissingHandlerException;
import org.apache.felix.ipojo.UnacceptableConfiguration;
import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Updated;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.bundle.test.view.component.MainComponent;
import santeclair.portal.bundle.utils.view.AbstractViewUi;
import santeclair.portal.view.ViewUi;

@Component
public class ViewUiImpl extends AbstractViewUi implements ViewUi {

    @Requires(filter = "(factory.name=MainComponent)")
    private Factory factory;

    @Publishes(name = "testViewUiPublisher", topics = TOPIC_MODULE_UI, synchronous = true)
    private Publisher publisher;

    /*
     * Lifecycle
     */

    @Override
    @Validate
    public void start() {
        super.start();
    }

    @Override
    @Invalidate
    public void stop() {
        super.stop();
    }

    /*
     * Subscriber
     */
    @Override
    @Subscriber(name = "moduleStart", topics = TOPIC_VIEW_UI, filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_MODULE_UI + ")(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_NAME_STARTED + ")(" + PROPERTY_KEY_MODULE_UI_CODE + "=*))")
    public void moduleStart(Event event) {
        super.moduleStart(event);
    }

    @Override
    @Subscriber(name = "portalStopped", topics = TOPIC_VIEW_UI,
                    filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_PORTAL + ")(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_NAME_STOPPED + ")("
                                    + PROPERTY_KEY_PORTAL_SESSION_ID + "=*))")
    public void portalStopped(Event event) {
        super.portalStopped(event);
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
     * Services
     */

    @Override
    protected com.vaadin.ui.Component getMainComponent(String sessionId, Integer tabHash) throws UnacceptableConfiguration, MissingHandlerException, ConfigurationException {
        Dictionary<String, Object> props = new Hashtable<>();
        props.put("instance.name", getCode() + "-" + MainComponent.class.getCanonicalName() + "-" + sessionId + "-" + tabHash);
        ComponentInstance instance = factory.createComponentInstance(props);
        if (instance.getState() == ComponentInstance.VALID) {
            MainComponent mainComponent =
                            (MainComponent) ((InstanceManager) instance).getPojoObject();
            return mainComponent;
        }
        return null;
    }

    /*
     * Managed Properties (setter)
     */

    @Override
    @Updated
    public void updated() {
        super.updated();
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

    @Override
    @Property(name = "openOnInitialization", value = "false")
    protected void setVisibleOnMenu(Boolean visibleOnMenu) {
        super.setVisibleOnMenu(visibleOnMenu);
    }

    @Override
    @Property(name = "rolesAllowed", value = "{NONE}")
    protected void setRolesAllowed(String[] rolesAllowed) {
        super.setRolesAllowed(rolesAllowed);
    }

    /*
     * Getter
     */

    @Override
    protected Publisher getPublisher() {
        return publisher;
    }

}
