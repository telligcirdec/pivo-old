package santeclair.portal.webapp.vaadin.view;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_MENU;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;

import java.util.TreeSet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import santeclair.portal.event.handler.AbstractEventHandler;
import santeclair.portal.event.handler.EventArg;
import santeclair.portal.event.handler.PortalEventHandler;
import santeclair.portal.event.handler.Subscriber;
import santeclair.portal.menu.MenuModule;
import santeclair.portal.webapp.vaadin.PushHelper;
import santeclair.portal.webapp.vaadin.view.component.MainButonModuleUi;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LeftSideMenu extends CustomLayout implements PortalEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeftSideMenu.class);
    private static final long serialVersionUID = -4748523363216844520L;
    private final VerticalLayout buttonContainer;
    private final UI ui;
    private final Navigator navigator;

    public LeftSideMenu(final UI ui, final Navigator navigator) {
        super("sidebarLayout");
        buttonContainer = new VerticalLayout();
        this.ui = ui;
        this.navigator = navigator;
    }

    public void init(BundleContext bundleContext) {
        this.setSizeFull();
        buttonContainer.setSizeFull();
        this.addComponent(buttonContainer, "buttonLayout");
        registerEventHandlerItself(bundleContext);
    }

    @Subscriber(topic = TOPIC_MODULE_UI, filter = "(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_STARTED + ")")
    public synchronized void addModuleUi(@EventArg(name = PROPERTY_KEY_MODULE_UI_MENU, required = true) final MenuModule menuModule) {
        LOGGER.debug("global addModuleUi");
        MainButonModuleUi mainButonModuleUi = new MainButonModuleUi(menuModule.getCodeModuleUi(), menuModule.getLibelleModuleUi(),
                        menuModule.getIconModuleUi(), menuModule.getDisplayOrderModuleUi(), null, navigator);
        TreeSet<Component> butonModuleUi = new TreeSet<>();
        butonModuleUi.add(mainButonModuleUi);
        for (Component component : buttonContainer) {
            if (MainButonModuleUi.class.isAssignableFrom(component.getClass())) {
                butonModuleUi.add(component);
            }
        }
        buttonContainer.removeAllComponents();
        buttonContainer.addComponents(butonModuleUi.toArray(new Component[]{}));
        PushHelper.push(ui);
    }

    @Subscriber(topic = TOPIC_MODULE_UI, filter = "(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_STOPPED + ")")
    public synchronized void removeModuleUi(@EventArg(name = PROPERTY_KEY_MODULE_UI_MENU, required = true) final MenuModule menuModule) {
        LOGGER.debug("removeModuleUi : {}", menuModule.getCodeModuleUi());
        for (Component component : buttonContainer) {
            if (MainButonModuleUi.class.isAssignableFrom(component.getClass())) {
                MainButonModuleUi currentComponent = MainButonModuleUi.class.cast(component);
                if (currentComponent.getModuleUiCode().equals(menuModule.getCodeModuleUi())) {
                    buttonContainer.removeComponent(component);
                }
            }
        }
        PushHelper.push(ui);
    }

    @Override
    public void registerEventHandlerItself(BundleContext bundleContext) {
        try {
            AbstractEventHandler.registerEventHandler(bundleContext, this);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unregisterEventHandlerItSelf(BundleContext bundleContext) {
        try {
            AbstractEventHandler.unregisterEventHandler(bundleContext, this);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
    }

}
