package santeclair.portal.webapp.vaadin.view.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import santeclair.portal.vaadin.module.ModuleUiFactory;

import com.google.common.base.Preconditions;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class MainButonModuleUiFactory extends Button implements Comparable<MainButonModuleUiFactory>, Button.ClickListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainButonModuleUiFactory.class);

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
        this.addClickListener(this);
    }

    @Override
    public int compareTo(MainButonModuleUiFactory mainButonModuleUiFactoryToCompare) {
        String moduleUiFactoryCodeToCompare = mainButonModuleUiFactoryToCompare.getModuleUiFactory().getCode();
        String moduleUiFactoryCodeSource = this.getModuleUiFactory().getCode();
        Integer displayOrderToCompare = mainButonModuleUiFactoryToCompare.getModuleUiFactory().displayOrder();
        Integer displayOrderSource = this.getModuleUiFactory().displayOrder();
        if (displayOrderToCompare == null || Integer.signum(displayOrderToCompare) < 1) {
            displayOrderToCompare = Integer.MAX_VALUE;
        }
        if (displayOrderSource == null || Integer.signum(displayOrderSource) < 1) {
            displayOrderSource = Integer.MAX_VALUE;
        }
        if (displayOrderSource.equals(displayOrderToCompare)) {
            return moduleUiFactoryCodeSource.compareTo(moduleUiFactoryCodeToCompare);
        }
        return displayOrderSource.compareTo(displayOrderToCompare);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        LOGGER.debug("Click event : {}", event);

    }

    public ModuleUiFactory<?> getModuleUiFactory() {
        return moduleUiFactory;
    }
}
