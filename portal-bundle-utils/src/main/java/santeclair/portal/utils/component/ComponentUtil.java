package santeclair.portal.utils.component;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI_CODE;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.InstanceManager;

public class ComponentUtil {
    
    public static boolean componentInstanceValid(List<ComponentInstance> componentInstances) {
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

    public static Map<Class<? extends com.vaadin.ui.Component>, com.vaadin.ui.Component> getVaadinComponentFromComponentInstances(List<ComponentInstance> componentInstances,
                    String sessionId, Integer tabHash,
                    String moduleCode,
                    String viewCode) {
        Map<Class<? extends com.vaadin.ui.Component>, com.vaadin.ui.Component> vaadinComponentMap = new HashMap<>();

        for (ComponentInstance componentInstance : componentInstances) {
            com.vaadin.ui.Component component =
                            (com.vaadin.ui.Component) ((InstanceManager) componentInstance).getPojoObject();
            Class<? extends com.vaadin.ui.Component> componentClass = component.getClass();
            SubComponent subComponent = componentClass.getAnnotation(SubComponent.class);
            if (subComponent != null) {
                Method[] methods = componentClass.getMethods();
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
                            vaadinComponentMap.put(componentClass, component);
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
        return vaadinComponentMap;
    }
}
