package santeclair.portal.webapp;

import javax.servlet.annotation.WebServlet;

import santeclair.portal.webapp.vaadin.PortalApp;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(value = {"/app/*","/VAADIN/*"}, asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = PortalApp.class)
public class PortalServlet extends VaadinServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 857697254564406247L;
}
