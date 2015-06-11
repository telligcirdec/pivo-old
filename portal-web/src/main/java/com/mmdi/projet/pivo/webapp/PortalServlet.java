package com.mmdi.projet.pivo.webapp;

import com.mmdi.projet.pivo.webapp.vaadin.PortalApp;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@VaadinServletConfiguration(productionMode = false, ui = PortalApp.class, closeIdleSessions = true)
public class PortalServlet extends VaadinServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 857697254564406247L;
}
