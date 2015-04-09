package santeclair.portal.utils.component;

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

    public static Map<Class<? extends com.vaadin.ui.Component>, com.vaadin.ui.Component> getVaadinComponentFromComponentInstances(List<ComponentInstance> componentInstances) {
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
                    if (subComponentInit != null && method.getParameterTypes().length == 0) {
                        try {
                            vaadinComponentMap.put(componentClass, component);
                            method.invoke(component, null);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                componentInstance.dispose();
            }
        }
        return vaadinComponentMap;
    }
}
