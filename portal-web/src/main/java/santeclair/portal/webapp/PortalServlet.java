package santeclair.portal.webapp;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import santeclair.portal.webapp.vaadin.PortalApp;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(value = {"/app/*", "/VAADIN/*"}, initParams = {
        @WebInitParam(name = "widgetset", value = "santeclair.portal.webapp.vaadin.widgetset.PortalWidgetSet")})
@VaadinServletConfiguration(productionMode = false, ui = PortalApp.class, closeIdleSessions=true)
public class PortalServlet extends VaadinServlet {

    /**
	 * 
	 */
    private static final long serialVersionUID = 857697254564406247L;
}
