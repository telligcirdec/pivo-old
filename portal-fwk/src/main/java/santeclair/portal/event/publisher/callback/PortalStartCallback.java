package santeclair.portal.event.publisher.callback;

import com.vaadin.server.FontIcon;

public interface PortalStartCallback {

    public void addNewModuleUiFactory(final String moduleUiCode,
                    final String moduleUiName,
                    final FontIcon moduleUiIcon,
                    final Integer moduleUiDisplayOrder,
                    final Boolean isCloseable);

}
