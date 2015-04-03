package santeclair.portal.bundle.recherche.demande.document.component;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI_CODE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
        if (componentInstanceValid(componentInstances)) {
            logService.log(LogService.LOG_DEBUG, "Oh fuck yeah !!!!");
            this.withFullWidth().withMargin(true).withSpacing(true).with(getVaadinComponentFromComponentInstances(componentInstances, sessionId, tabHash, moduleCode, viewCode));
        }
    }

    @Invalidate
    private void dispose() {
        for (ComponentInstance componentInstance : componentInstances) {
            componentInstance.dispose();
        }
    }

    @Bind(aggregate = true, filter = "(factory.name=santeclair.portal.bundle.recherche.demande.document.component.sub*)")
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

    private static boolean componentInstanceValid(List<ComponentInstance> componentInstances) {
        boolean isValid = false;
        if (CollectionUtils.isNotEmpty(componentInstances)) {
            for (ComponentInstance componentInstance : componentInstances) {
                isValid = componentInstance.getState() == ComponentInstance.VALID;
                if (!isValid) {
                    break;
                }
            }
        }
        return isValid;
    }

    private static InitComponent[] getVaadinComponentFromComponentInstances(List<ComponentInstance> componentInstances, String sessionId, Integer tabHash, String moduleCode,
                    String viewCode) {
        List<InitComponent> vaadinComponents = new ArrayList<>();

        for (ComponentInstance componentInstance : componentInstances) {
            InitComponent component =
                            (InitComponent) ((InstanceManager) componentInstance).getPojoObject();
            vaadinComponents.add(component);
            component.init(sessionId, tabHash, moduleCode, viewCode);
        }
        Collections.sort(vaadinComponents);
        return vaadinComponents.toArray(new InitComponent[]{});
    }

}
