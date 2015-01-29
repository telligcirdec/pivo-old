package santeclair.portal.webapp.vaadin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import santeclair.portal.event.ModuleUiFactoryBundleStarted;
import santeclair.portal.event.ModuleUiFactoryBundleStopped;
import santeclair.portal.event.impl.RootEventBusImpl;
import santeclair.portal.webapp.vaadin.view.LeftSideMenu;
import santeclair.portal.webapp.vaadin.view.Main;
import santeclair.portal.webapp.vaadin.view.Tabs;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
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
public class PortalApp extends UI {

    private static final long serialVersionUID = -5547062232353913227L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PortalApp.class);

    // Bundle systeme permettant de relier l'environnement Tomcat avec
    // l'environnement OSGi / Managé et injecté par Spring
    // private HostActivator hostActivator;

    // Bus d'event permettant de gérer les événements d'une session utilisateur (variable de classe -
    // prototype - un event bus par instance d'UI)
    private final EventBus portalAppEventBus = new EventBus();

    // Container global
    private Main main;
    // Container des boutons sur le menu à gauche permettant de naviguer
    private LeftSideMenu leftSideMenu;
    // Container des onglets
    private Tabs tabs;

    private RootEventBusImpl rootEventBusImpl;

    /*
     * Début du Code UI
     */
    @Override
    public void init(VaadinRequest request) {
        LOGGER.info("Initialisation de l'UI");
        // Récupération du context Spring
        ApplicationContext context = WebApplicationContextUtils.
                        getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext());
        // Injection des singletons
        rootEventBusImpl = context.getBean(RootEventBusImpl.class);
        // hostActivator = context.getBean(HostActivator.class);

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
        portalAppEventBus.register(main);
        portalAppEventBus.register(leftSideMenu);
        portalAppEventBus.register(tabs);

        setContent(main);

        LOGGER.debug("Fin Initialisation de l'UI");
        rootEventBusImpl.register(this);
    }

    @Override
    public void detach() {
        LOGGER.info("Detachement de l'UI");
        portalAppEventBus.unregister(main);
        portalAppEventBus.unregister(leftSideMenu);
        portalAppEventBus.unregister(tabs);
        rootEventBusImpl.unregister(this);
        super.detach();
    }

    @Subscribe
    public void moduleUiFactoryBundleStarted(ModuleUiFactoryBundleStarted<?> moduleUiFactoryBundleStarted) {
        LOGGER.info("Démarrage d'un bundle contenant une factory de module ui");

    }

    @Subscribe
    public void moduleUiFactoryBundleStopped(ModuleUiFactoryBundleStopped<?> moduleUiFactoryBundleStopped) {
        LOGGER.info("Arrêt d'un bundle contenant une factory de module ui");

    }

    /*
     * Code de gestion de l'enregistrement/desenregistrement des factory de
     * modules
     */

    private List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("Portal authentication : " + authentication);
        @SuppressWarnings("unchecked")
        Collection<GrantedAuthority> grantedAuthorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        LOGGER.debug("current user roles : " + grantedAuthorities);
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            roles.add(grantedAuthority.getAuthority());
        }
        return roles;
    }

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
