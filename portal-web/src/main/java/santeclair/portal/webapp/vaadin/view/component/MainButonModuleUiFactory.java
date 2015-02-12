package santeclair.portal.webapp.vaadin.view.component;

import com.google.common.base.Preconditions;
import com.vaadin.ui.Button;

public class MainButonModuleUiFactory extends Button implements Comparable<MainButonModuleUiFactory>, Button.ClickListener {

    private static final long serialVersionUID = 2401506522651257986L;

    private final String moduleUiCode;
    private final Integer displayOrder;

    public MainButonModuleUiFactory(String moduleUiCode, Integer displayOrder) {
        Preconditions.checkNotNull(moduleUiCode, "You must set a module ui factory code to create a button on left side menu.");
        this.moduleUiCode = moduleUiCode;
        this.displayOrder = displayOrder;
    }

    public String getModuleUiCode() {
        return moduleUiCode;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    @Override
    public int compareTo(MainButonModuleUiFactory mainButonModuleUiFactoryToCompare) {
        String moduleUiFactoryCodeToCompare = mainButonModuleUiFactoryToCompare.getModuleUiCode();
        Integer displayOrderToCompare = mainButonModuleUiFactoryToCompare.getDisplayOrder();
        Integer displayOrderSource = displayOrder;
        if (displayOrderToCompare == null || Integer.signum(displayOrderToCompare) < 1) {
            displayOrderToCompare = Integer.MAX_VALUE;
        }
        if (displayOrder == null || Integer.signum(displayOrder) < 1) {
            displayOrderSource = Integer.MAX_VALUE;
        }
        if (displayOrderSource.equals(displayOrderToCompare)) {
            return moduleUiCode.compareTo(moduleUiFactoryCodeToCompare);
        }
        return displayOrder.compareTo(displayOrderToCompare);
    }

    @Override
    public void buttonClick(ClickEvent event) {

    }
}
