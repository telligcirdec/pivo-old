package santeclair.portal.event.publisher.callback;

import com.vaadin.server.FontIcon;
import com.vaadin.ui.Component;

public interface TabsCallback {

    int addView(FontIcon icon, Boolean closable, Component moduleUiView);

    void keepView();

}
