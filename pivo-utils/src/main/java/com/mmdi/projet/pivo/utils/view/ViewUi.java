package com.mmdi.projet.pivo.utils.view;

import java.util.List;
import java.util.Map;

import com.vaadin.server.FontIcon;
import com.vaadin.ui.Component;

public interface ViewUi {

	FontIcon getIcon();

	String getCode();

	String getLibelle();

	Boolean getOpenOnInitialization();

	Boolean getVisibleOnMenu();

	Boolean getSeveralTabsAllowed();

	List<String> getRolesAllowed();

	Component getViewMainComponent(String sessionId, Integer tabHash,
			List<String> currentUserRoles, Map<String, Object> mapParams);
}
