package santeclair.portal.vaadin.deprecated.event;


@Deprecated
public interface UIListener<E extends UIEvent<?, ?>> {
	/**
	 * R�ception de l'�venement
	 * 
	 * @param event
	 */
	void onApplicationEvent(E event);

	/**
	 * Retour pouvant �tre employ� par un listener.
	 * 
	 * @param event
	 */
	void callbackEvent(E event);

	/**
	 * Ordre d'ex�cution des listeners
	 * 
	 * @return
	 */
	public Integer order();
}
