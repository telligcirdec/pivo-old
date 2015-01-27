package santeclair.portal.vaadin.deprecated.module.view;


@Deprecated
public class CloseViewResult {
	/** Message � afficher en cas d'erreur ou de demande de confirmation. */
	private String message;
	/** Type de r�sultat de fermeture. */
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
			this.message = "Etes-vous s�r de vouloir fermer l'onglet ?";
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
	 * M�thode de lecture de l'attribut : {@link CloseViewResult#message}
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * M�thode de lecture de l'attribut : {@link CloseViewResult#type}
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
