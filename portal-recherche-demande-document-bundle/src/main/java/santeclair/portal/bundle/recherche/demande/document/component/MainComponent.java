package santeclair.portal.bundle.recherche.demande.document.component;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI_CODE;

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
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.service.log.LogService;
import org.vaadin.viritin.layouts.MVerticalLayout;

@Component(name = "santeclair.portal.bundle.recherche.demande.document.component.MainComponent")
public class MainComponent extends MVerticalLayout {

    private static final long serialVersionUID = 1L;

    private ComponentInstance formComponentInstance;
    private ComponentInstance resultatComponentInstance;

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
        if (formComponentInstance.getState() == ComponentInstance.VALID && resultatComponentInstance.getState() == ComponentInstance.VALID) {
            logService.log(LogService.LOG_DEBUG, "Oh fuck yeah !!!!");
            FormComponent formComponent =
                            (FormComponent) ((InstanceManager) formComponentInstance).getPojoObject();
            ResultatComponent resultatComponent =
                            (ResultatComponent) ((InstanceManager) resultatComponentInstance).getPojoObject();

            formComponent.init(sessionId, tabHash);
            resultatComponent.init(sessionId, tabHash, moduleCode, viewCode);
            this.withFullWidth().withMargin(true).withSpacing(true).with(formComponent, resultatComponent);
        }
    }

    @Invalidate
    private void dispose() {
        formComponentInstance.dispose();
        resultatComponentInstance.dispose();
    }

    @Bind(aggregate = true, filter = "(|(factory.name=santeclair.portal.bundle.recherche.demande.document.component.FormComponent)(factory.name=santeclair.portal.bundle.recherche.demande.document.component.ResultatComponent))")
    private void getFormComponentFactory(Factory factory) throws UnacceptableConfiguration, MissingHandlerException, ConfigurationException {
        if (factory.getName().equals("santeclair.portal.bundle.recherche.demande.document.component.FormComponent")) {
            formComponentInstance = factory.createComponentInstance(null);
        } else if (factory.getName().equals("santeclair.portal.bundle.recherche.demande.document.component.ResultatComponent")) {
            resultatComponentInstance = factory.createComponentInstance(null);
        }
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
