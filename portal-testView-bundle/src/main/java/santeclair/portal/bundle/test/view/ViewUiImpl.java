package santeclair.portal.bundle.test.view;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_VIEW_UI;

import java.util.List;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Updated;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.bundle.utils.view.AbstractViewUi;
import santeclair.portal.view.ViewUi;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@Component
public class ViewUiImpl extends AbstractViewUi implements ViewUi {

    @Publishes(name = "testViewUiPublisher", topics = TOPIC_MODULE_UI, synchronous=true)
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
                    + EVENT_NAME_STARTED + ")("+PROPERTY_KEY_MODULE_UI_CODE+"=*))")
    public void moduleStart(Event event) {
        super.moduleStart(event);
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

    @Override
    public com.vaadin.ui.Component getRootComponent(List<String> currentUserRoles) {
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Label("Mouahahaha !!!!"));
        
        return horizontalLayout;
    }
}
