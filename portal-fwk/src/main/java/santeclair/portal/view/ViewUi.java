package santeclair.portal.view;

import java.util.List;

import com.vaadin.server.FontIcon;

public interface ViewUi {

    FontIcon getIcon();

    String getCode();

    String getLibelle();

    Boolean getOpenOnInitialization();

    List<String> getRolesAllowed();

    ViewUiRootComponent getViewComponent(String sessionId, Boolean severalTabsAllowed, List<String> currentUserRoles) ;
}
