package santeclair.portal.webapp.vaadin.view.old;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;

import santeclair.portal.vaadin.deprecated.event.ApplicationEventPublisher;
import santeclair.portal.vaadin.deprecated.event.impl.ApplicationEventPublisherImpl;
import santeclair.portal.vaadin.deprecated.module.ModuleMapKey;
import santeclair.portal.vaadin.deprecated.module.ModuleState;
import santeclair.portal.vaadin.deprecated.module.ModuleUi;
import santeclair.portal.vaadin.deprecated.module.api.ModuleFactory;
import santeclair.portal.vaadin.deprecated.module.uri.ContainerUriFragment;
import santeclair.portal.vaadin.deprecated.module.uri.ViewUriFragment;
import santeclair.portal.vaadin.deprecated.module.view.PresenterName;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Template avec le menu du portail sur le coté.
 * 
 * @author tsensebe
 * 
 */
public class SideMenu implements Button.ClickListener, Serializable {

    private static final long serialVersionUID = -283950529916636684L;

    private static final Logger LOGGER = LoggerFactory
                    .getLogger(SideMenu.class);

    private final CustomLayout sidebarLayout;
    private final HorizontalLayout mainLayout;
    private final VerticalLayout buttonContainer;
    private final UI ui;
    private final Map<ModuleMapKey, ModuleFactory<? extends ModuleUi>> moduleFactories;
    private final Map<String, Component> moduleFactoryButtons = new HashMap<>();
    private final Collection<GrantedAuthority> roles;

    private Navigator navigator;
    private final TabsView tabsView;
    private ApplicationEventPublisher applicationEventPublisher;

    public SideMenu(
                    UI ui,
                    Map<ModuleMapKey, ModuleFactory<? extends ModuleUi>> moduleFactories,
                    Collection<GrantedAuthority> roles) {
        this.ui = ui;
        this.moduleFactories = moduleFactories;
        this.roles = roles;
        tabsView = new TabsView(this);
        sidebarLayout = new CustomLayout("sidebarLayout");
        buttonContainer = new VerticalLayout();
        mainLayout = new HorizontalLayout();
    }

    public void init() {
        applicationEventPublisher = new ApplicationEventPublisherImpl();
        navigator = new Navigator(ui, tabsView);
        navigator.addView("", tabsView);
        navigator.addView("container", tabsView);

        setErrorHandler();

        for (ModuleMapKey moduleCode : moduleFactories.keySet()) {
            try {
                addModuleButton(moduleFactories.get(moduleCode));
            } catch (Exception e) {
                LOGGER.warn(
                                "Exception lors de l'ajout d'un module au menu du portail.",
                                e);
            }
        }
        mainLayout.setSizeFull();
        sidebarLayout.setSizeFull();
        sidebarLayout.addComponent(buttonContainer, "buttonLayout");
        buttonContainer.setSizeFull();
        mainLayout.addComponent(sidebarLayout);
        mainLayout.addComponent(tabsView.getModuleContainer());
        mainLayout.setExpandRatio(sidebarLayout, 0.14f);
        mainLayout.setExpandRatio(tabsView.getModuleContainer(), 0.86f);
    }

    public void addModuleButton(ModuleFactory<?> moduleFactory) {
        String moduleCode = moduleFactory.getCode();
        final Button menuComponent;
        final VerticalLayout secoundaryButonVerticalLayout = new VerticalLayout();
        secoundaryButonVerticalLayout.setStyleName("menu-button-layout");
        final List<PresenterName> menuView = null;// moduleFactory.getMenuView(roles);

        if (menuView != null && menuView.size() > 1) {
            for (PresenterName presenterName : menuView) {
                Button secoundaryButton = new Button(presenterName.getLibelle());
                secoundaryButton.addClickListener(this);
                secoundaryButton.setId(moduleCode + "|" + presenterName);
                secoundaryButton.setSizeFull();
                secoundaryButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
                secoundaryButonVerticalLayout.addComponent(secoundaryButton);
                secoundaryButonVerticalLayout.setComponentAlignment(
                                secoundaryButton, Alignment.MIDDLE_RIGHT);
            }
            // final AnimatorProxy proxy = new AnimatorProxy();
            // buttonContainer.addComponent(proxy);

            menuComponent = new Button();

            menuComponent.addClickListener(new Button.ClickListener() {
                private static final long serialVersionUID = -283950529916638684L;

                private Boolean open = false;

                @Override
                public void buttonClick(ClickEvent event) {
                    if (!open) {
                        int index = buttonContainer
                                        .getComponentIndex(menuComponent);
                        int layoutIndex = buttonContainer
                                        .getComponentIndex(secoundaryButonVerticalLayout);
                        if (layoutIndex == -1) {
                            buttonContainer.addComponent(
                                            secoundaryButonVerticalLayout, ++index);

                        }
                        menuComponent.removeStyleName("btn-menu-close");
                        menuComponent.addStyleName("btn-menu-open");
                        // proxy.animate(secoundaryButonVerticalLayout,
                        // AnimType.ROLL_DOWN_OPEN_POP);
                        open = true;
                    } else {
                        menuComponent.removeStyleName("btn-menu-open");
                        menuComponent.addStyleName("btn-menu-close");
                        // proxy.animate(secoundaryButonVerticalLayout,
                        // AnimType.ROLL_UP_CLOSE_REMOVE);
                        open = false;
                    }

                }
            });
            buttonContainer.addComponent(menuComponent);
        } else {
            menuComponent = new Button();
            menuComponent.addClickListener(this);
            menuComponent.setId(moduleCode);
            // Utilie uniquement pour que tous les boutons soient séparé par un
            // même espace
            // final AnimatorProxy proxy = new AnimatorProxy();
            // buttonContainer.addComponent(proxy);
            buttonContainer.addComponent(menuComponent);
        }

        menuComponent.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        menuComponent.addStyleName("button-heading");
        menuComponent.setCaption(moduleFactory.getName());
        menuComponent.setIcon(moduleFactory.getIconName());
        menuComponent.setWidth(100, Unit.PERCENTAGE);
        buttonContainer.setComponentAlignment(menuComponent,
                        Alignment.MIDDLE_CENTER);
        moduleFactoryButtons.put(moduleCode, menuComponent);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        String[] moduleId = event.getButton().getId().split("\\|");
        String moduleCode = moduleId[0];
        String moduleView = (moduleId.length > 1 ? moduleId[1] : "");
        viewButtonClick(moduleCode, moduleView);
    }

    private void viewButtonClick(String moduleCode, String moduleView) {
        ViewUriFragment viewUriFragment = null;
        if (tabsView.getModuleOnlyOneInstance().contains(moduleCode)) {
            viewUriFragment = tabsView.findUniqueModuleByModuleCode(moduleCode)
                            .getViewUriFragment();
            viewUriFragment.setViewCode(moduleView);
        } else {
            viewUriFragment = new ViewUriFragment(ContainerUriFragment.NEW,
                            moduleCode, moduleView);
        }

        navigator.navigateTo(viewUriFragment.toString());
    }

    private void setErrorHandler() {
        UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
            private static final long serialVersionUID = -6689459084265117649L;

            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                String uriFragment = Page.getCurrent().getUriFragment();

                StringBuilder sbCodeErreur = new StringBuilder();
                if (uriFragment != null) {
                    ViewUriFragment view = ViewUriFragment
                                    .newViewUriFragment(uriFragment);
                    if (view.getContainerId() != null
                                    && "new".equals(view.getContainerId())) {
                        sbCodeErreur.append("new/");
                    }
                    if (StringUtils.isNotBlank(view.getModuleCode())) {
                        sbCodeErreur.append(view.getModuleCode()).append("/");
                    }
                    if (StringUtils.isNotBlank(view.getViewCode())) {
                        sbCodeErreur.append(view.getViewCode()).append("/");
                    }
                }
                sbCodeErreur.append(DateFormatUtils.format(new Date(),
                                "yyyyMMddHHmmss"));

                String codeErreur = sbCodeErreur.toString();

                Throwable t = event.getThrowable();
                if (findAccesDeniedExceptionException(t) != null) {
                    Notification
                                    .show("Vous n'avez les droits suffisant pour accéder à la ressource demandée.",
                                                    Type.WARNING_MESSAGE);
                } else {
                    LOGGER.error(
                                    "Erreur inattendu de l'application Santéclair : "
                                                    + codeErreur, t);
                    Notification
                                    .show("Erreur inattendue de l'application Santéclair\n",
                                                    "Merci de prendre une capture d'écran et de créer une intervention avec cette capture et le code suivant :\n"
                                                                    + codeErreur, Type.ERROR_MESSAGE);
                }
            }
        });
    }

    public ComponentContainer getButtonMenuContainer() {
        return buttonContainer;
    }

    public Layout getMainLayout() {
        return mainLayout;
    }

    // Abstract

    public synchronized void removeModuleFactory(ModuleFactory<?> moduleFactory) {
        moduleFactories.remove(moduleFactory.getModuleMapKey());
        tabsView.changeModuleState(moduleFactory.getCode(), ModuleState.STOPPED);
        Component buttonToRemoved = moduleFactoryButtons.get(moduleFactory
                        .getCode());
        getButtonMenuContainer().removeComponent(buttonToRemoved);
        moduleFactoryButtons.remove(moduleFactory.getCode());
    }

    public synchronized void addModuleFactory(ModuleFactory<?> moduleFactory) {
        if (true) {// moduleFactory.securityCheck(roles)) {
            moduleFactories.put(moduleFactory.getModuleMapKey(), moduleFactory);
            getButtonMenuContainer().removeAllComponents();
            for (ModuleMapKey key : moduleFactories.keySet()) {
                addModuleButton(moduleFactories.get(key));
            }
        }
    }

    public Map<ModuleMapKey, ModuleFactory<? extends ModuleUi>> getModuleFactories() {
        return moduleFactories;
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    /**
     * Fonction recursive pour rechercher une cause dans les exceptions filles
     * de type AccessDeniedException.
     * 
     * @param throwable
     *            Exception a controler
     * @return L'exception de type AccessDeniedException ou null si aucune.
     */
    private AccessDeniedException findAccesDeniedExceptionException(
                    Throwable throwable) {
        if (throwable != null) {
            if (AccessDeniedException.class.isAssignableFrom(throwable
                            .getClass())) {
                return (AccessDeniedException) throwable;
            } else {
                return findAccesDeniedExceptionException(throwable.getCause());
            }
        }
        return null;
    }
}
