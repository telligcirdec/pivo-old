package santeclair.portal.bundle.utils.view;

import com.vaadin.server.FontIcon;

public interface ViewUi {

    FontIcon getIcon();

    String getCode();

    String getLibelle();

    Boolean getOpenOnInitialization();
    
}
