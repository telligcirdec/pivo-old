package santeclair.portal.webapp.vaadin.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.jouni.animator.AnimatorProxy;

import santeclair.portal.vaadin.module.ModuleUiFactory;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.VerticalLayout;

public class LeftSideMenu extends CustomLayout {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeftSideMenu.class);

    private static final long serialVersionUID = -4748523363216844520L;

    private final VerticalLayout buttonContainer;

    public LeftSideMenu() {
        super("sidebarLayout");
        buttonContainer = new VerticalLayout();
    }

    public void init() {
        this.setSizeFull();
        buttonContainer.setSizeFull();
        final AnimatorProxy proxy = new AnimatorProxy();
        buttonContainer.addComponent(proxy);
    }

    public void addModuleUiFactory(ModuleUiFactory<?> moduleUiFactory) {
        LOGGER.debug("addButtonModuleUiFactory : {}", moduleUiFactory.getCode());
    }

}
