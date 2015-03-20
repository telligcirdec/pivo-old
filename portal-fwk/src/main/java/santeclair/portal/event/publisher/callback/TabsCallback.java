package santeclair.portal.event.publisher.callback;

import com.vaadin.server.FontIcon;
import com.vaadin.ui.Component;

public interface TabsCallback {

    void addView(String moduleCode, String viewCode, String caption, FontIcon icon, Boolean closable, Component moduleUiView);

}
