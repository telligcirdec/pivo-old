package santeclair.portal.bundle.recherche.demande.document.component;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI_CODE;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.Factory;
import org.apache.felix.ipojo.InstanceManager;
import org.apache.felix.ipojo.MissingHandlerException;
import org.apache.felix.ipojo.UnacceptableConfiguration;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Requires;
import org.vaadin.viritin.layouts.MVerticalLayout;


@Component(name = "santeclair.portal.bundle.recherche.demande.document.component.MainComponent")
public class MainComponent extends MVerticalLayout {

    private static final long serialVersionUID = 1L;

    @Requires(filter = "(factory.name=santeclair.portal.bundle.recherche.demande.document.component.ResultatComponent)")
    private Factory resultatComponentFactory;
    
    @Requires(filter = "(factory.name=santeclair.portal.bundle.recherche.demande.document.component.FormComponent)")
    private Factory formComponentFactory;
    
    private String sessionId;
    private Integer tabHash;
    private String moduleCode;
    private String viewCode;
    
    /** Initialise la vue principale. 
     * @throws ConfigurationException 
     * @throws MissingHandlerException 
     * @throws UnacceptableConfiguration */
    private void init() throws UnacceptableConfiguration, MissingHandlerException, ConfigurationException {
        Dictionary<String, Object> props = new Hashtable<>();
        props.put(PROPERTY_KEY_PORTAL_SESSION_ID, new String(sessionId));
        props.put(PROPERTY_KEY_TAB_HASH, new Integer(tabHash));
        props.put(PROPERTY_KEY_MODULE_UI_CODE, moduleCode);
        props.put(PROPERTY_KEY_VIEW_UI_CODE, viewCode);
        
        ComponentInstance instanceResulat = resultatComponentFactory.createComponentInstance(props);
        
        ComponentInstance instanceForm = formComponentFactory.createComponentInstance(props);
        
        if (instanceResulat.getState() == ComponentInstance.VALID && instanceForm.getState() == ComponentInstance.VALID) {

            com.vaadin.ui.Component formComponent =
                            (com.vaadin.ui.Component) ((InstanceManager) instanceForm).getPojoObject();
            com.vaadin.ui.Component resultatComponent =
                            (com.vaadin.ui.Component) ((InstanceManager) instanceResulat).getPojoObject();
            
            this.withFullWidth().withMargin(true).withSpacing(true).with(formComponent, resultatComponent);
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
