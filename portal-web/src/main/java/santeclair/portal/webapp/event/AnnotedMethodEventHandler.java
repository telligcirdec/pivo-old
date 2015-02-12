package santeclair.portal.webapp.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotedMethodEventHandler implements org.osgi.service.event.EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotedMethodEventHandler.class);

    private final Object annotedEventHandlerObject;
    private final Method annotedMethod;

    public AnnotedMethodEventHandler(Object annotedEventHandlerObject, Method annotedMethod) {
        this.annotedEventHandlerObject = annotedEventHandlerObject;
        this.annotedMethod = annotedMethod;
    }

    @Override
    public void handleEvent(Event event) {
        LOGGER.info("Begin handle event {}", event.toString());
        String className = annotedEventHandlerObject.getClass().getName();
        String topicName = event.getTopic();
        Parameter[] parameters = this.annotedMethod.getParameters();
        List<Object> parametersList = new ArrayList<>();
        LOGGER.info("Parsing method parameters if needed (parameters.length => {})", parameters.length);
        for (int i = 0; i < parameters.length; i++) {
            Object arg = null;
            Parameter parameter = parameters[i];
            EventArg eventArg = parameter.getAnnotation(EventArg.class);
            if (eventArg != null) {
                String eventArgName = eventArg.name();
                Boolean eventArgRequired = eventArg.required();
                LOGGER.info("EvenArg annotation found on arg{} with name : {} en required : {}", i, eventArgName, eventArgRequired);
                arg = event.getProperty(eventArgName);
                LOGGER.debug("Arg value : {}", arg);
                if (arg == null && eventArgRequired) {
                    throw new IllegalArgumentException("An event on the topic " + topicName + " has been fired. "
                                    + "Your listener method " + annotedMethod.getName() + " from your class " + className
                                    + " has at least a parameter with annotation " + EventArg.class.getSimpleName() + " which name is " + eventArgName + " required. "
                                    + "You must fired this event with the property " + eventArgName + " set with the value you deserve or set required to false.");
                }
            } else if (Event.class.isAssignableFrom(parameter.getType())) {
                LOGGER.info("Parameter arg{} of type {} found.", i, Event.class.getName());
                arg = event;
            }
            parametersList.add(arg);
        }
        try {
            LOGGER.debug("Method {}.{} invocation with parameters : {} ", className, annotedMethod, parametersList);
            annotedMethod.invoke(annotedEventHandlerObject, parametersList.toArray());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
