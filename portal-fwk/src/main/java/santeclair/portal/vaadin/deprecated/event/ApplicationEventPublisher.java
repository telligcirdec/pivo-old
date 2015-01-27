package santeclair.portal.vaadin.deprecated.event;


@Deprecated
public interface ApplicationEventPublisher {
	/**
	 * Enregistre un listener dans l'ordonanceur
	 * 
	 * @param listener
	 */
	void registerModule(ModuleListener moduleListener);

	/**
	 * Supprime le listener de l'ordonanceur
	 * 
	 * @param moduleListener
	 */
	void unregisterModule(ModuleListener moduleListener);

	/**
	 * Déclenche un évenement
	 * 
	 * @param event
	 */
	void publishEvent(UIEvent<?, ?> event);
}
