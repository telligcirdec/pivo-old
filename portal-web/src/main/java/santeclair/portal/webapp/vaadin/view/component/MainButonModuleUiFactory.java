package santeclair.portal.webapp.vaadin.view.component;

import santeclair.portal.vaadin.module.ModuleUiFactory;

import com.google.common.base.Preconditions;
import com.vaadin.ui.Button;

public class MainButonModuleUiFactory extends Button implements Comparable<MainButonModuleUiFactory>, Button.ClickListener {

    private static final long serialVersionUID = 2401506522651257986L;

    private final ModuleUiFactory<?> moduleUiFactory;

    public MainButonModuleUiFactory(ModuleUiFactory<?> moduleUiFactory) {
        Preconditions.checkNotNull(moduleUiFactory, "You must set a module ui factory to create a button on left side menu.");
        this.moduleUiFactory = moduleUiFactory;
    }

    public ModuleUiFactory<?> getModuleUiFactory() {
        return moduleUiFactory;
    }

    @Override
    public int compareTo(MainButonModuleUiFactory mainButonModuleUiFactoryToCompare) {
        ModuleUiFactory<?> moduleUiFactoryToCompare = mainButonModuleUiFactoryToCompare.getModuleUiFactory();
        Integer displayOrderToCompare = moduleUiFactoryToCompare.displayOrder();
        if (displayOrderToCompare == null || Integer.signum(displayOrderToCompare) < 1) {
            displayOrderToCompare = Integer.MAX_VALUE;
        }
        ModuleUiFactory<?> moduleUiFactory = this.getModuleUiFactory();
        Integer displayOrder = moduleUiFactory.displayOrder();
        if (displayOrder == null || Integer.signum(displayOrder) < 1) {
            displayOrder = Integer.MAX_VALUE;
        }
        if (displayOrder.equals(displayOrderToCompare)) {
            return moduleUiFactory.getName().compareTo(moduleUiFactoryToCompare.getName());
        }
        return displayOrder.compareTo(displayOrderToCompare);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if{
            
        }else{
            
        }
    }
}
