package santeclair.portal.module;

import java.util.List;
import java.util.Map;

import santeclair.portal.view.ViewUi;

import com.vaadin.server.FontIcon;

public interface ModuleUi {

    String getCode();

    String getLibelle();

    FontIcon getIcon();

    Boolean isCloseable();

    Boolean isSeveralTabsAllowed();

    Integer getDisplayOrder();

    Map<String, ViewUi> getViewUis(List<String> currentUserRoles);

    void registerViewUi(ViewUi viewUi);

    //void unregisterViewUi(ViewUi viewUi);

}
