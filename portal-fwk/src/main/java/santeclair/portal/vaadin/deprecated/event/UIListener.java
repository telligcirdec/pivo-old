package santeclair.portal.vaadin.deprecated.event;


@Deprecated
public interface UIListener<E extends UIEvent<?, ?>> {
	/**
	 * Réception de l'évenement
	 * 
	 * @param event
	 */
	void onApplicationEvent(E event);

	/**
	 * Retour pouvant être employé par un listener.
	 * 
	 * @param event
	 */
	void callbackEvent(E event);

	/**
	 * Ordre d'exécution des listeners
	 * 
	 * @return
	 */
	public Integer order();
}
