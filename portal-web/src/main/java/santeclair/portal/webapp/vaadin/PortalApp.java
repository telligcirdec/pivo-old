package santeclair.portal.webapp.vaadin;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI_FACTORY;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_PORTAL;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import santeclair.portal.event.EventDictionaryConstant;
import santeclair.portal.event.handler.AbstractEventHandler;
import santeclair.portal.event.handler.EventArg;
import santeclair.portal.event.handler.PortalEventHandler;
import santeclair.portal.event.handler.Subscriber;
import santeclair.portal.event.publisher.callback.PortalStartCallback;
import santeclair.portal.listener.service.impl.EventAdminServiceListener;
import santeclair.portal.listener.service.impl.EventAdminServiceListener.Publisher;
import santeclair.portal.vaadin.module.ModuleUiFactory;
import santeclair.portal.webapp.HostActivator;
import santeclair.portal.webapp.vaadin.view.LeftSideMenu;
import santeclair.portal.webapp.vaadin.view.Main;
import santeclair.portal.webapp.vaadin.view.Tabs;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

/**
 * The Application's "verticalButtonMain" class
 */
@PreserveOnRefresh
@Title("Portail Santeclair")
@Theme("santeclair")
@Push(value = PushMode.MANUAL, transport = Transport.WEBSOCKET)
public class PortalApp extends UI implements PortalEventHandler, PortalStartCallback, ViewDisplay {

    private static final long serialVersionUID = -5547062232353913227L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PortalApp.class);

    // Container global
    private Main main;
    // Container des boutons sur le menu à gauche permettant de naviguer
    private LeftSideMenu leftSideMenu;
    // Container des onglets
    private Tabs tabs;

    private Navigator navigator;

    private Publisher<PortalApp, PortalStartCallback> portalPublisher;

    /*
     * Début du Code UI
     */
    @Override
    public void init(VaadinRequest request) {
        LOGGER.info("Initialisation de l'UI");

        ApplicationContext applicationContext = WebApplicationContextUtils.
                        getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext());
        HostActivator hostActivator = applicationContext.getBean(HostActivator.class);
        EventAdminServiceListener eventAdminServiceListener = applicationContext.getBean(EventAdminServiceListener.class);

        BundleContext context = hostActivator.getBundleContext();

        // initialisation de l'IHM
        this.setSizeFull();
        this.setErrorHandler();

        navigator = new Navigator(this, (ViewDisplay) this);

        // Création du composant contenant les tabsheet
        LOGGER.info("Initialisation du container d'onglet");
        tabs = new Tabs();
        tabs.init();

        navigator.addView("", tabs);
        navigator.addView("container", tabs);

        // Création du composant contenant le menu à gauche avec les boutons
        LOGGER.info("Initialisation du menu gauche");
        leftSideMenu = new LeftSideMenu(this, navigator);
        leftSideMenu.init(context);

        // Création du container principal
        LOGGER.info("Création du container principal");
        main = new Main(leftSideMenu, tabs);
        main.init();

        // Enregistrement des listeners d'event dans le portalEventBus
        LOGGER.info("Enregistrement des listeners d'event dans le portalEventBus");

        registerEventHandlerItself(context);

        portalPublisher = eventAdminServiceListener.registerPublisher(TOPIC_PORTAL, this);
        this.setContent(main);

        portalPublisher.publishEventDataSynchronously(EVENT_STARTED, this);
        LOGGER.debug("Fin Initialisation de l'UI");
    }

    @Override
    public void detach() {
        LOGGER.info("Detachement de l'UI");
        ApplicationContext applicationContext = WebApplicationContextUtils.
                        getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext());
        HostActivator hostActivator = applicationContext.getBean(HostActivator.class);
        EventAdminServiceListener eventAdminServiceListener = applicationContext.getBean(EventAdminServiceListener.class);

        unregisterEventHandlerItSelf(hostActivator.getBundleContext());
        eventAdminServiceListener.unregisterPublisher(portalPublisher);

        super.detach();
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

    @Subscriber(topic = TOPIC_MODULE_UI_FACTORY, filter = "(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_STARTED + ")")
    public void addModuleUiFactory(org.osgi.service.event.Event event,
                    @EventArg(name = EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY) final ModuleUiFactory<?> moduleUiFactory) {
        String moduleUiName = moduleUiFactory.getName();
        PushHelper.pushWithNotification(this, moduleUiName + " chargé", "Le module " + moduleUiName + " est désomeais disponible.");
    }

    @Subscriber(topic = TOPIC_MODULE_UI_FACTORY, filter = "(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_STOPPED + ")")
    public void removeModuleUiFactory(org.osgi.service.event.Event event,
                    @EventArg(name = EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY) final ModuleUiFactory<?> moduleUiFactory) {
        String moduleUiName = moduleUiFactory.getName();
        PushHelper.pushWithNotification(this, moduleUiName + " déchargé", "Le module " + moduleUiName + " est désomeais indisponible.");
    }

    @Override
    public void addNewModuleUiFactory(ModuleUiFactory<?> moduleUiFactory) {
        leftSideMenu.addModuleUiFactory(moduleUiFactory);
    }

    // private List<String> getCurrentUserRoles() {
    // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // LOGGER.info("Portal authentication : " + authentication);
    // @SuppressWarnings("unchecked")
    // Collection<GrantedAuthority> grantedAuthorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
    // LOGGER.debug("current user roles : " + grantedAuthorities);
    // List<String> roles = new ArrayList<>();
    // for (GrantedAuthority grantedAuthority : grantedAuthorities) {
    // roles.add(grantedAuthority.getAuthority());
    // }
    // return roles;
    // }

    private void setErrorHandler() {
        this.setErrorHandler(new DefaultErrorHandler() {
            private static final long serialVersionUID = -6689459084265117649L;

            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                String uriFragment = Page.getCurrent().getUriFragment();
                StringBuilder sbCodeErreur = new StringBuilder(uriFragment);
                sbCodeErreur.append(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
                String codeErreur = sbCodeErreur.toString();
                Throwable t = event.getThrowable();
                if (findAccesDeniedExceptionException(t) != null) {
                    Notification.show("Vous n'avez pas les droits suffisant pour accéder à la ressource demandée.", Type.WARNING_MESSAGE);
                } else {
                    LOGGER.error("Erreur inattendu dans le portail : " + codeErreur, t);
                    Notification.show("Erreur inattendue de l'application.\n",
                                    "Merci de prendre une capture d'écran et de créer une intervention avec cette capture et le code suivant :\n" + codeErreur,
                                    Type.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Fonction recursive pour rechercher une cause dans les exceptions filles
     * de type AccessDeniedException.
     * 
     * @param throwable
     *            Exception a controler
     * @return L'exception de type AccessDeniedException ou null si aucune.
     */
    private AccessDeniedException findAccesDeniedExceptionException(Throwable throwable) {
        if (throwable != null) {
            if (AccessDeniedException.class.isAssignableFrom(throwable.getClass())) {
                return AccessDeniedException.class.cast(throwable);
            } else {
                return findAccesDeniedExceptionException(throwable.getCause());
            }
        }
        return null;
    }

    @Override
    public void showView(View view) {
        LOGGER.debug("view : " + view);
    }

}
