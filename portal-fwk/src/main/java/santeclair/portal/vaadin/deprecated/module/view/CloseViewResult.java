package santeclair.portal.vaadin.deprecated.module.view;


@Deprecated
public class CloseViewResult {
	/** Message à afficher en cas d'erreur ou de demande de confirmation. */
	private String message;
	/** Type de résultat de fermeture. */
	private CloseMessageType type;

	public CloseViewResult() {
		this.type = CloseMessageType.OK;
		this.message = null;
	}

	public CloseViewResult(CloseMessageType type) {
		this.type = type;
		switch (type) {
		case ERROR:
			this.message = "Impossible de fermer l'onglet";
			break;
		case CONFIRMATION_NEEDED:
			this.message = "Etes-vous sûr de vouloir fermer l'onglet ?";
		default:
			this.message = null;
			break;
		}
	}

	public CloseViewResult(CloseMessageType type, String message) {
		this.type = type;
		this.message = message;
	}

	/**
	 * Méthode de lecture de l'attribut : {@link CloseViewResult#message}
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Méthode de lecture de l'attribut : {@link CloseViewResult#type}
	 * 
	 * @return type
	 */
	public CloseMessageType getType() {
		return type;
	}

	public enum CloseMessageType {
		CONFIRMATION_NEEDED, ERROR, OK;
	}
}
