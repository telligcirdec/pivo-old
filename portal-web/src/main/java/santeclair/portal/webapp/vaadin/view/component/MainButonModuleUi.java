package santeclair.portal.webapp.vaadin.view.component;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontIcon;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class MainButonModuleUi extends Button implements Comparable<MainButonModuleUi>, Button.ClickListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainButonModuleUi.class);

    private static final long serialVersionUID = 2401506522651257986L;

    private static final String MAIN_BUTTON_MODULE_UI_STYLE_NAME = "button-heading";

    private final String moduleUiCode;
    private final String moduleUiName;
    private final Integer moduleUiDisplayOrder;
    private final Map<String, String> moduleUiView;

    private final Navigator navigator;

    public MainButonModuleUi(final String moduleUiCode, final String moduleUiName, final FontIcon moduleUiIcon, final Integer moduleUiDisplayOrder,
                                    final Map<String, String> moduleUiView, final Navigator navigator) {
        Preconditions.checkArgument(moduleUiCode != null && moduleUiName != null, "You must set a module ui code to create a button on left side menu.");

        this.moduleUiCode = moduleUiCode;
        this.moduleUiName = moduleUiName;
        this.moduleUiDisplayOrder = moduleUiDisplayOrder;
        this.moduleUiView = moduleUiView;
        this.navigator = navigator;
        init(moduleUiIcon);

    }

    private void init(final FontIcon moduleUiIcon) {
        this.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        this.addStyleName(MAIN_BUTTON_MODULE_UI_STYLE_NAME);
        this.setCaption(moduleUiName);
        this.setIcon(moduleUiIcon);
        this.setWidth(100, Unit.PERCENTAGE);
        this.addClickListener(this);
    }

    @Override
    public int compareTo(MainButonModuleUi mainButonModuleUiFactoryToCompare) {
        String moduleUiNameToCompare = mainButonModuleUiFactoryToCompare.getModuleUiName();
        String moduleUiNameSource = this.getModuleUiName();
        Integer displayOrderToCompare = mainButonModuleUiFactoryToCompare.getModuleUiDisplayOrder();
        Integer displayOrderSource = this.getModuleUiDisplayOrder();
        if (displayOrderToCompare == null || Integer.signum(displayOrderToCompare) < 1) {
            displayOrderToCompare = Integer.MAX_VALUE;
        }
        if (displayOrderSource == null || Integer.signum(displayOrderSource) < 1) {
            displayOrderSource = Integer.MAX_VALUE;
        }
        if (displayOrderSource.equals(displayOrderToCompare)) {
            return moduleUiNameSource.compareTo(moduleUiNameToCompare);
        }
        return displayOrderSource.compareTo(displayOrderToCompare);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        LOGGER.debug("Click event : {}", event);
        navigator.navigateTo("container/new/modules/" + moduleUiCode);
    }

    public String getModuleUiName() {
        return moduleUiName;
    }

    public Integer getModuleUiDisplayOrder() {
        return moduleUiDisplayOrder;
    }

    public String getModuleUiCode() {
        return moduleUiCode;
    }

}
