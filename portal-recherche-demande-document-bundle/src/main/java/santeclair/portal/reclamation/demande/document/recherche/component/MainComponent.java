package santeclair.portal.reclamation.demande.document.recherche.component;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI_CODE;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private static com.vaadin.ui.Component[] getVaadinComponentFromComponentInstances(List<ComponentInstance> componentInstances, String sessionId, Integer tabHash,
                    String moduleCode,
                    String viewCode) {
        List<OrderedComponent> vaadinOrderedComponents = new ArrayList<>();

        for (ComponentInstance componentInstance : componentInstances) {
            com.vaadin.ui.Component component =
                            (com.vaadin.ui.Component) ((InstanceManager) componentInstance).getPojoObject();
            SubComponent subComponent = component.getClass().getAnnotation(SubComponent.class);
            if (subComponent != null) {
                int displayOrder = subComponent.displayOrder();
                Method[] methods = component.getClass().getMethods();
                for (Method method : methods) {
                    SubComponentInit subComponentInit = method.getAnnotation(SubComponentInit.class);
                    if (subComponentInit != null) {
                        Annotation[][] annotations = method.getParameterAnnotations();
                        Object[] parameterValues = new Object[annotations.length];
                        for (int i = 0; i < annotations.length; i++) {
                            Annotation[] annotationsOnParameter = annotations[i];
                            for (Annotation annotation : annotationsOnParameter) {
                                if (SubComponentInitProperty.class.isAssignableFrom(annotation.annotationType())) {
                                    SubComponentInitProperty subComponentInitProperty = SubComponentInitProperty.class.cast(annotation);
                                    if (subComponentInitProperty.name().equalsIgnoreCase(PROPERTY_KEY_PORTAL_SESSION_ID)) {
                                        parameterValues[i] = sessionId;
                                    } else if (subComponentInitProperty.name().equalsIgnoreCase(PROPERTY_KEY_TAB_HASH)) {
                                        parameterValues[i] = tabHash;
                                    } else if (subComponentInitProperty.name().equalsIgnoreCase(PROPERTY_KEY_MODULE_UI_CODE)) {
                                        parameterValues[i] = moduleCode;
                                    } else if (subComponentInitProperty.name().equalsIgnoreCase(PROPERTY_KEY_VIEW_UI_CODE)) {
                                        parameterValues[i] = viewCode;
                                    }
                                }
                            }
                        }
                        try {
                            vaadinOrderedComponents.add(new OrderedComponent(displayOrder, component));
                            method.invoke(component, parameterValues);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            } else {
                componentInstance.dispose();
            }

        }
        Collections.sort(vaadinOrderedComponents);
        List<com.vaadin.ui.Component> vaadinComponents = new ArrayList<>();
        for (OrderedComponent orderedComponent : vaadinOrderedComponents) {
            vaadinComponents.add(orderedComponent.getComponent());
        }
        return vaadinComponents.toArray(new com.vaadin.ui.Component[]{});
    }

    public static final class OrderedComponent implements Comparable<OrderedComponent> {

        private final int displayOrder;
        private final com.vaadin.ui.Component component;

        public OrderedComponent(int displayOrder, com.vaadin.ui.Component component) {
            super();
            this.displayOrder = displayOrder;
            this.component = component;
        }

        public int getDisplayOrder() {
            return displayOrder;
        }

        public com.vaadin.ui.Component getComponent() {
            return component;
        }

        @Override
        public int compareTo(OrderedComponent o) {
            int x = this.getDisplayOrder();
            int y = o == null ? Integer.MAX_VALUE : o.getDisplayOrder();
            return (x < y) ? -1 : ((x == y) ? 0 : 1);
        }

    }
}
