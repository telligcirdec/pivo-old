package santeclair.portal.reclamation.demande.document.recherche.component;

import java.util.ArrayList;
import java.util.Dictionary;
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
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Updated;
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
            Map<Class<? extends com.vaadin.ui.Component>, com.vaadin.ui.Component> components = ComponentUtil.getVaadinComponentFromComponentInstances(componentInstances);
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

    @Updated
    public void updated(Dictionary<?,?> conf) {
        for (ComponentInstance componentInstance : componentInstances) {
            componentInstance.reconfigure(conf);
        }
    }
    

}
