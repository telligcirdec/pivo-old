package santeclair.portal.vaadin.deprecated.event.impl;

import java.util.HashSet;
import java.util.Set;

import santeclair.portal.vaadin.deprecated.event.ApplicationEventPublisher;
import santeclair.portal.vaadin.deprecated.event.ModuleListener;
import santeclair.portal.vaadin.deprecated.event.UIEvent;

@Deprecated
public class ApplicationEventPublisherImpl implements ApplicationEventPublisher {
	private Set<ModuleListener> moduleListeners;

	public ApplicationEventPublisherImpl() {
		moduleListeners = new HashSet<ModuleListener>();
	}

	/**
	 * Enregistre un listener dans l'ordonanceur
	 * 
	 * @param listener
	 */
	@Override
	public void registerModule(ModuleListener moduleListener) {
		moduleListeners.add(moduleListener);
	}

	@Override
	public void unregisterModule(ModuleListener moduleListener) {
		moduleListeners.remove(moduleListener);
	}

	/**
	 * Déclenche un évenement
	 * 
	 * @param event
	 */
	@Override
	public void publishEvent(UIEvent<?, ?> event) {
		for (ModuleListener moduleListener : moduleListeners) {
			moduleListener.publishEvent(event);
		}
	}
}
