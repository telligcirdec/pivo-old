package com.mmdi.projet.pivo.utils.module;

import java.util.List;
import java.util.Map;

import com.mmdi.projet.pivo.utils.view.ViewUi;
import com.vaadin.server.FontIcon;

public interface ModuleUi {

	String getCode();

	String getLibelle();

	FontIcon getIcon();

	Boolean isClosable();

	Boolean isOnlyOneTabAllowed();

	Integer getDisplayOrder();

	Map<String, ViewUi> getViewUis(List<String> currentUserRoles);

	void registerViewUi(ViewUi viewUi);

	// void unregisterViewUi(ViewUi viewUi);

}
