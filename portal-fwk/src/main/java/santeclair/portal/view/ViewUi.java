package santeclair.portal.view;

import java.util.List;

import com.vaadin.server.FontIcon;
import com.vaadin.ui.Component;

public interface ViewUi {

    FontIcon getIcon();

    String getCode();

    String getLibelle();

    Boolean getOpenOnInitialization();

    Boolean getVisibleOnMenu();

    List<String> getRolesAllowed();

    Component getViewMainComponent(String sessionId, Integer tabHash, Boolean severalTabsAllowed, List<String> currentUserRoles);
}
