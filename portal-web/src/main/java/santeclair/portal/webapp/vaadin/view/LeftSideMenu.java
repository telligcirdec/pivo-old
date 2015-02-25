package santeclair.portal.webapp.vaadin.view;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI_FACTORY;

import java.util.TreeSet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.jouni.animator.AnimatorProxy;

import santeclair.portal.event.handler.AbstractEventHandler;
import santeclair.portal.event.handler.EventArg;
import santeclair.portal.event.handler.PortalEventHandler;
import santeclair.portal.event.handler.Subscriber;
import santeclair.portal.vaadin.module.ModuleUiFactory;
import santeclair.portal.webapp.vaadin.PushHelper;
import santeclair.portal.webapp.vaadin.view.component.MainButonModuleUiFactory;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LeftSideMenu extends CustomLayout implements PortalEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeftSideMenu.class);
    private static final long serialVersionUID = -4748523363216844520L;
    private final VerticalLayout buttonContainer;
    private final UI ui;

    public LeftSideMenu(UI ui) {
        super("sidebarLayout");
        buttonContainer = new VerticalLayout();
        this.ui = ui;
    }

    public void init(BundleContext bundleContext) {
        this.setSizeFull();
        buttonContainer.setSizeFull();
        final AnimatorProxy proxy = new AnimatorProxy();
        buttonContainer.addComponent(proxy);
        this.addComponent(buttonContainer, "buttonLayout");
        registerEventHandlerItself(bundleContext);
    }

    @Subscriber(topic = TOPIC_MODULE_UI_FACTORY, filter = "(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_STARTED + ")")
    public synchronized void addModuleUiFactory(@EventArg(name = PROPERTY_KEY_MODULE_UI_FACTORY) final ModuleUiFactory<?> moduleUiFactory) {
        LOGGER.debug("global addModuleUiFactory");
        MainButonModuleUiFactory mainButonModuleUiFactory = new MainButonModuleUiFactory(moduleUiFactory);
        TreeSet<Component> butonModuleUiFactories = new TreeSet<>();
        butonModuleUiFactories.add(mainButonModuleUiFactory);
        for (Component component : buttonContainer) {
            if (MainButonModuleUiFactory.class.isAssignableFrom(component.getClass())) {
                butonModuleUiFactories.add(component);
            }
        }
        buttonContainer.removeAllComponents();
        buttonContainer.addComponents(butonModuleUiFactories.toArray(new Component[]{}));
        PushHelper.push(ui);
    }

    @Subscriber(topic = TOPIC_MODULE_UI_FACTORY, filter = "(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_STOPPED + ")")
    public synchronized void removeModuleUiFactory(@EventArg(name = PROPERTY_KEY_MODULE_UI_FACTORY) ModuleUiFactory<?> moduleUiFactory) {
        LOGGER.debug("removeModuleUiFactory : {}", moduleUiFactory.getCode());
        for (Component component : buttonContainer) {
            if (MainButonModuleUiFactory.class.isAssignableFrom(component.getClass())) {
                MainButonModuleUiFactory currentComponent = MainButonModuleUiFactory.class.cast(component);
                if (currentComponent.getModuleUiFactory().getCode().equals(moduleUiFactory.getCode())) {
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
