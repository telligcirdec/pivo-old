package santeclair.portal.reclamation.demande.document.recherche.component;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI_CODE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.Factory;
import org.apache.felix.ipojo.MissingHandlerException;
import org.apache.felix.ipojo.UnacceptableConfiguration;
import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.service.log.LogService;
import org.vaadin.viritin.layouts.MVerticalLayout;

import santeclair.portal.reclamation.demande.document.recherche.component.sub.FormComponent;
import santeclair.portal.reclamation.demande.document.recherche.component.sub.ResultatComponent;
import santeclair.portal.utils.component.ComponentUtil;

@Component(name = "santeclair.portal.reclamation.demande.document.recherche.component.MainComponent")
public class MainComponent extends MVerticalLayout {

    private static final long serialVersionUID = 1L;

    private final List<ComponentInstance> componentInstances = new ArrayList<>();

    private String sessionId;
    private Integer tabHash;
    private String moduleCode;
    private String viewCode;

    @Requires
    private LogService logService;

    /**
     * Initialise la vue principale.
     * 
     * @throws ConfigurationException
     * @throws MissingHandlerException
     * @throws UnacceptableConfiguration
     */
    @Validate
    private void init() throws UnacceptableConfiguration, MissingHandlerException, ConfigurationException {
        if (ComponentUtil.componentInstanceValid(componentInstances)) {
            logService.log(LogService.LOG_DEBUG, "Oh fuck yeah !!!!");
            Map<Class<? extends com.vaadin.ui.Component>, com.vaadin.ui.Component> components = ComponentUtil.getVaadinComponentFromComponentInstances(componentInstances, sessionId, tabHash,
                            moduleCode, viewCode);
            this.withFullWidth().withMargin(true).withSpacing(true).with(components.get(FormComponent.class), components.get(ResultatComponent.class));
        }
    }

    @Invalidate
    private void dispose() {
        for (ComponentInstance componentInstance : componentInstances) {
            componentInstance.dispose();
        }
    }

    @Bind(aggregate = true, filter = "(factory.name=santeclair.portal.reclamation.demande.document.recherche.component.sub*)")
    private void getFormComponentFactory(Factory factory) throws UnacceptableConfiguration, MissingHandlerException, ConfigurationException {
        componentInstances.add(factory.createComponentInstance(null));
    }

    @Property(name = PROPERTY_KEY_PORTAL_SESSION_ID)
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Property(name = PROPERTY_KEY_TAB_HASH)
    public void setTabHash(Integer tabHash) {
        this.tabHash = tabHash;
    }

    @Property(name = PROPERTY_KEY_MODULE_UI_CODE)
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Property(name = PROPERTY_KEY_VIEW_UI_CODE)
    public void setViewCode(String viewCode) {
        this.viewCode = viewCode;
    }

}
