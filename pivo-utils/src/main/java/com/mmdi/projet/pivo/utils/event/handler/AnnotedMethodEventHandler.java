package com.mmdi.projet.pivo.utils.event.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotedMethodEventHandler implements EventHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AnnotedMethodEventHandler.class);

	private final Object annotedEventHandlerObject;
	private final Method annotedMethod;

	private Object[] argParameters;

	public AnnotedMethodEventHandler(Object annotedEventHandlerObject,
			Method annotedMethod) {
		this.annotedEventHandlerObject = annotedEventHandlerObject;
		this.annotedMethod = annotedMethod;
		init();
	}

	private void init() {
		Annotation[][] annotations = this.annotedMethod
				.getParameterAnnotations();
		LOGGER.info(
				"Parsing method parameters if needed (parameters.length => {})",
				annotations.length);
		argParameters = new Object[annotations.length];
		for (int i = 0; i < annotations.length; i++) {
			Object argParameter = null;
			Annotation[] annotationsOnParameter = annotations[i];
			for (Annotation annotation : annotationsOnParameter) {
				if (EventProperty.class.isAssignableFrom(annotation
						.annotationType())) {
					argParameter = annotation;
				}
			}
			if (Event.class
					.isAssignableFrom(annotedMethod.getParameterTypes()[i])) {
				LOGGER.info("Parameter arg{} of type {} found.", i,
						Event.class.getName());
				argParameter = Event.class;
			}
			argParameters[i] = argParameter;
		}
	}

	@Override
	public void handleEvent(Event event) {
		LOGGER.info("Begin handle event {}", event.toString());
		String className = annotedEventHandlerObject.getClass().getName();
		String topicName = event.getTopic();
		Object[] parameterValues = new Object[argParameters.length];
		for (int i = 0; i < argParameters.length; i++) {
			Object argParameter = argParameters[i];
			Object parameterValue = null;
			if (argParameter != null) {
				if (argParameter instanceof Annotation
						&& EventProperty.class
								.isAssignableFrom(((Annotation) argParameter)
										.annotationType())) {
					EventProperty eventProperty = EventProperty.class
							.cast(argParameter);
					String eventArgName = eventProperty.propKey();
					Boolean eventArgRequired = eventProperty.required();
					LOGGER.info(
							"EvenArg annotation found on arg{} with name : {} en required : {}",
							i, eventArgName, eventArgRequired);
					parameterValue = event.getProperty(eventArgName);
					LOGGER.debug("Parameter value : {}", parameterValue);
					if (parameterValue == null && eventArgRequired) {
						throw new IllegalArgumentException(
								"An event on the topic "
										+ topicName
										+ " has been fired. "
										+ "Your listener method "
										+ annotedMethod.getName()
										+ " from your class "
										+ className
										+ " has at least a parameter with annotation "
										+ EventProperty.class.getSimpleName()
										+ " which name is "
										+ eventArgName
										+ " required. "
										+ "You must fired this event with the property "
										+ eventArgName
										+ " set with the value you deserve or set required to false.");
					}
				}
				if (argParameter instanceof Class
						&& Event.class
								.isAssignableFrom((Class<?>) argParameter)) {
					LOGGER.info("Parameter arg{} of type {} found.", i,
							Event.class.getName());
					parameterValue = event;
				}
			}
			parameterValues[i] = parameterValue;
		}
		try {
			LOGGER.debug("Method {}.{} invocation with parameters : {} ",
					className, annotedMethod, parameterValues);
			annotedMethod.invoke(annotedEventHandlerObject, parameterValues);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
