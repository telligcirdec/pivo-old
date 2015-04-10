package santeclair.portal.reclamation.demande.document.detail.component;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
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
import org.apache.felix.ipojo.annotations.Updated;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.log.LogService;
import org.vaadin.viritin.layouts.MVerticalLayout;

import santeclair.portal.reclamation.demande.document.detail.EventConstant;
import santeclair.portal.reclamation.demande.document.detail.component.sub.DescriptifDemandeComponent;
import santeclair.portal.reclamation.demande.document.detail.component.sub.SuiviEnvoisComponent;
import santeclair.portal.reclamation.demande.document.detail.component.sub.TraitementDocumentsComponent;
import santeclair.portal.utils.component.ComponentUtil;

@Component(name = "santeclair.portal.reclamation.demande.document.detail.component.MainComponent")
public class MainComponent extends MVerticalLayout {

    private static final long serialVersionUID = -8877289835840595912L;

    private final List<ComponentInstance> componentInstances = new ArrayList<>();

    @Requires
    private LogService logService;
    
    @Publishes(name = "mainComponentPublisher", topics = EventConstant.TOPIC_DEMANDE_DOCUMENT, synchronous = true)
    private Publisher mainComponentPublisher;

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
            Map<Class<? extends com.vaadin.ui.Component>, com.vaadin.ui.Component> components = ComponentUtil.getVaadinComponentFromComponentInstances(componentInstances);

            this.withFullWidth().withMargin(true).withSpacing(true).with(components.get(DescriptifDemandeComponent.class), components.get(SuiviEnvoisComponent.class), components.get(TraitementDocumentsComponent.class));
            
            Dictionary<String, Object> props = new Hashtable<>();
            props.put(PROPERTY_KEY_EVENT_NAME, EventConstant.EVENT_RECUPERER_DEMANDE_DOCUMENT);
            props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
            props.put(PROPERTY_KEY_TAB_HASH, tabHash);
            props.put(EventConstant.PROPERTY_KEY_ID_DEMANDE_DOCUMENT, idDemandeDocument);
            mainComponentPublisher.send(props);
        }
    }

    @Invalidate
    private void dispose() {
        for (ComponentInstance componentInstance : componentInstances) {
            componentInstance.dispose();
        }
    }

    @Bind(aggregate = true, filter = "(factory.name=santeclair.portal.reclamation.demande.document.detail.component.sub*)")
    private void getFormComponentFactory(Factory factory) throws UnacceptableConfiguration, MissingHandlerException, ConfigurationException {
        componentInstances.add(factory.createComponentInstance(null));
    }

    @Updated
    public void updated(Dictionary<?, ?> conf) {
        for (ComponentInstance componentInstance : componentInstances) {
            componentInstance.reconfigure(conf);
        }
    }

    @Property(name = EventConstant.PROPERTY_KEY_ID_DEMANDE_DOCUMENT) 
    private Integer idDemandeDocument;
    
    @Property(name = PROPERTY_KEY_PORTAL_SESSION_ID) 
    private String sessionId;
    
    @Property(name = PROPERTY_KEY_TAB_HASH) 
    private Integer tabHash;
}
