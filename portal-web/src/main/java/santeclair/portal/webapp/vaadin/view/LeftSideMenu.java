package santeclair.portal.webapp.vaadin.view;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.jouni.animator.AnimatorProxy;

import santeclair.portal.vaadin.module.ModuleUiFactory;
import santeclair.portal.webapp.vaadin.view.component.MainButonModuleUiFactory;

import com.vaadin.ui.Component;
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

    public synchronized void addModuleUiFactory(String moduleUiCode, Integer displayOrder) {
        LOGGER.debug("addButtonModuleUiFactory : {}", moduleUiCode);
        MainButonModuleUiFactory mainButonModuleUiFactory = new MainButonModuleUiFactory(moduleUiCode, displayOrder);
        if (buttonContainer.getComponentCount() > 0) {
            Iterator<Component> ite = buttonContainer.iterator();
            while (ite.hasNext()) {
                Component mainButton = ite.next();
                if (MainButonModuleUiFactory.class.isAssignableFrom(mainButton.getClass())) {
                    mainButton.getClass();
                } else {
                    LOGGER.warn("Un composant haut niveu attaché au menu n'est pas du type : " + MainButonModuleUiFactory.class.getName());
                }
            }
        } else {
            buttonContainer.addComponent(mainButonModuleUiFactory);
        }
    }

    public void removeModuleUiFactory(ModuleUiFactory<?> moduleUiFactory) {
        LOGGER.debug("removeModuleUiFactory : {}", moduleUiFactory.getCode());
    }

}
