package santeclair.portal.webapp.vaadin.view.component;

import santeclair.portal.vaadin.module.ModuleUiFactory;

import com.google.common.base.Preconditions;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class MainButonModuleUiFactory extends Button implements Comparable<MainButonModuleUiFactory>, Button.ClickListener {

    private static final long serialVersionUID = 2401506522651257986L;

    private static final String MAIN_BUTTON_MODULE_UI_STYLE_NAME = "button-heading";

    private final ModuleUiFactory<?> moduleUiFactory;

    public MainButonModuleUiFactory(ModuleUiFactory<?> moduleUiFactory) {
        Preconditions.checkNotNull(moduleUiFactory, "You must set a module ui factory to create a button on left side menu.");
        this.moduleUiFactory = moduleUiFactory;
        this.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        this.addStyleName(MAIN_BUTTON_MODULE_UI_STYLE_NAME);
        this.setCaption(moduleUiFactory.getName());
        this.setIcon(moduleUiFactory.getIcon());
        this.setWidth(100, Unit.PERCENTAGE);
    }

    @Override
    public int compareTo(MainButonModuleUiFactory mainButonModuleUiFactoryToCompare) {
        String moduleUiFactoryCodeToCompare = mainButonModuleUiFactoryToCompare.getModuleUiFactory().getCode();
        Integer displayOrderToCompare = mainButonModuleUiFactoryToCompare.getModuleUiFactory().displayOrder();
        Integer displayOrderSource = this.getModuleUiFactory().displayOrder();
        if (displayOrderToCompare == null || Integer.signum(displayOrderToCompare) < 1) {
            displayOrderToCompare = Integer.MAX_VALUE;
        }
        if (this.getModuleUiFactory().displayOrder() == null || Integer.signum(this.getModuleUiFactory().displayOrder()) < 1) {
            displayOrderSource = Integer.MAX_VALUE;
        }
        if (displayOrderSource.equals(displayOrderToCompare)) {
            return this.getModuleUiFactory().getCode().compareTo(moduleUiFactoryCodeToCompare);
        }
        return displayOrderSource.compareTo(displayOrderToCompare);
    }

    @Override
    public void buttonClick(ClickEvent event) {

    }

    public ModuleUiFactory<?> getModuleUiFactory() {
        return moduleUiFactory;
    }
}
