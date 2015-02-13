package santeclair.portal.webapp.vaadin;

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
import santeclair.portal.webapp.HostActivator;
import santeclair.portal.webapp.event.AbstractEventHandler;
import santeclair.portal.webapp.event.EventArg;
import santeclair.portal.webapp.event.EventHandler;
import santeclair.portal.webapp.event.Subscriber;
import santeclair.portal.webapp.vaadin.view.LeftSideMenu;
import santeclair.portal.webapp.vaadin.view.Main;
import santeclair.portal.webapp.vaadin.view.Tabs;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
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
public class PortalApp extends UI implements EventHandler {

    private static final long serialVersionUID = -5547062232353913227L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PortalApp.class);

    // Container global
    private Main main;
    // Container des boutons sur le menu à gauche permettant de naviguer
    private LeftSideMenu leftSideMenu;
    // Container des onglets
    private Tabs tabs;

    private ApplicationContext applicationContext;

    /*
     * Début du Code UI
     */
    @Override
    public void init(VaadinRequest request) {
        LOGGER.info("Initialisation de l'UI");
        // initialisation de l'IHM
        this.setSizeFull();
        this.setErrorHandler();

        // Création du composant contenant le menu à gauche avec les boutons
        LOGGER.info("Initialisation du menu gauche");
        leftSideMenu = new LeftSideMenu();
        leftSideMenu.init();

        // Création du composant contenant les tabsheet
        LOGGER.info("Initialisation du container d'onglet");
        tabs = new Tabs();
        tabs.init();

        // Création du container principal
        LOGGER.info("Création du container principal");
        main = new Main(leftSideMenu, tabs);

        // Enregistrement des listeners d'event dans le portalEventBus
        LOGGER.info("Enregistrement des listeners d'event dans le portalEventBus");

        applicationContext = WebApplicationContextUtils.
                        getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext());

        HostActivator hostActivator = applicationContext.getBean(HostActivator.class);
        registerEventHandlerItself(hostActivator.getBundleContext());

        this.setContent(main);

        LOGGER.debug("Fin Initialisation de l'UI");
    }

    @Override
    public void detach() {
        LOGGER.info("Detachement de l'UI");
        HostActivator hostActivator = applicationContext.getBean(HostActivator.class);
        unregisterEventHandlerItSelf(hostActivator.getBundleContext());
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

    @Subscriber(topic = EventDictionaryConstant.TOPIC_MODULE_UI_FACTORY, filter = "(" + EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME + "="
                    + EventDictionaryConstant.EVENT_STARTED + ")")
    public void addModuleUiFactory(org.osgi.service.event.Event event, @EventArg(name = EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE) final String moduleUiCode) {
        if (getPushConfiguration() != null && getPushConfiguration().getPushMode() != null && !getPushConfiguration().getPushMode().equals(PushMode.DISABLED)) {
            access(new Runnable() {
                @Override
                public void run() {
                    Notification notification = new Notification(moduleUiCode + " chargé", "Le module " + moduleUiCode + " est désomeais disponible.", Type.TRAY_NOTIFICATION);
                    notification.show(Page.getCurrent());
                    push();
                }
            });
        }
    }

    @Subscriber(topic = EventDictionaryConstant.TOPIC_MODULE_UI_FACTORY, filter = "(" + EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME + "="
                    + EventDictionaryConstant.EVENT_STOPPED + ")")
    public void removeModuleUiFactory(org.osgi.service.event.Event event, @EventArg(name = EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE) final String moduleUiCode) {
        if (getPushConfiguration() != null && getPushConfiguration().getPushMode() != null && !getPushConfiguration().getPushMode().equals(PushMode.DISABLED)) {
            access(new Runnable() {
                @Override
                public void run() {
                    Notification notification = new Notification(moduleUiCode + " déchargé", "Le module " + moduleUiCode + " est désomeais indisponible.", Type.TRAY_NOTIFICATION);
                    notification.show(Page.getCurrent());
                    push();
                }
            });
        }
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

}
