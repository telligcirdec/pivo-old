package santeclair.lunar.framework.web.jsf;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

public class NoCachePhaseListener implements PhaseListener {

	private static final long serialVersionUID = -3544826165001289694L;

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	public void afterPhase(PhaseEvent event) {
	}

	/**
	 * @TODO : a rendre plus intelligent ! ne pas filtrer sur des ressources
	 *       comme des images, fichiers js, css
	 */
	public void beforePhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache");
		// Stronger according to blog comment below that references HTTP spec
		response.addHeader("Cache-Control", "no-store");
		response.addHeader("Cache-Control", "must-revalidate");
		// some date in the past
		response.addHeader("Expires", "Mon, 8 Aug 2006 10:00:00 GMT");
	}
}
