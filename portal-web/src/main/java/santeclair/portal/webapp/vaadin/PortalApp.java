package santeclair.portal.webapp.vaadin;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_CONTEXT_PORTAL;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_STOPPED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_PORTAL;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_VIEW_UI;

import java.util.Arrays;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import santeclair.portal.event.handler.AbstractEventHandler;
import santeclair.portal.event.handler.EventArg;
import santeclair.portal.event.handler.PortalEventHandler;
import santeclair.portal.event.handler.Subscriber;
import santeclair.portal.event.publisher.callback.PortalStartCallback;
import santeclair.portal.listener.service.impl.EventAdminServiceListener;
import santeclair.portal.listener.service.impl.EventAdminServiceListener.DataPublisher;
import santeclair.portal.listener.service.impl.EventAdminServiceListener.Publisher;
import santeclair.portal.module.ModuleUi;
import santeclair.portal.view.ViewUi;
import santeclair.portal.webapp.HostActivator;
import santeclair.portal.webapp.vaadin.navigator.NavigatorEventHandler;
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

    private NavigatorEventHandler navigatorEventHandler;

    private DataPublisher<PortalApp, PortalStartCallback> moduleUiTopicDataPublisher;
    
    private Publisher<PortalApp> viewUiTopicPublisher;

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

        Navigator navigator = new Navigator(this, (ViewDisplay) this);
        String sessionId = this.getSession().getSession().getId();
        navigatorEventHandler = new NavigatorEventHandler(navigator, sessionId);
        navigatorEventHandler.registerEventHandlerItself(context);

        // Création du composant contenant les tabsheet
        LOGGER.info("Initialisation du container d'onglet");
        tabs = new Tabs(eventAdminServiceListener, sessionId, getCurrentUserRoles());
        tabs.init();

        navigator.addView("", tabs);
        navigator.addView("container", tabs);

        // Création du composant contenant le menu à gauche avec les boutons
        LOGGER.info("Initialisation du menu gauche");
        leftSideMenu = new LeftSideMenu(eventAdminServiceListener, sessionId);
        leftSideMenu.init(context);

        // Création du container principal
        LOGGER.info("Création du container principal");
        main = new Main(leftSideMenu, tabs);
        main.init();

        // Enregistrement des listeners d'event dans le portalEventBus
        LOGGER.info("Enregistrement des listeners d'event dans le portalEventBus");

        registerEventHandlerItself(context);

        moduleUiTopicDataPublisher = eventAdminServiceListener.registerDataPublisher(this, TOPIC_MODULE_UI);
        viewUiTopicPublisher = eventAdminServiceListener.registerDataPublisher(this, TOPIC_VIEW_UI);
        
        this.setContent(main);

        Dictionary<String, Object> props = new Hashtable<>(3);
        props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_PORTAL);
        props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STARTED);
        props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);

        moduleUiTopicDataPublisher.publishEventDataAndDictionnarySynchronously(this, props);
        LOGGER.debug("Fin Initialisation de l'UI");

    }

    @Override
    public void detach() {
        LOGGER.info("Detachement de l'UI");
        ApplicationContext applicationContext = WebApplicationContextUtils.
                        getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext());
        HostActivator hostActivator = applicationContext.getBean(HostActivator.class);
        EventAdminServiceListener eventAdminServiceListener = applicationContext.getBean(EventAdminServiceListener.class);

        BundleContext context = hostActivator.getBundleContext();

        String sessionId = this.getSession().getSession().getId();

        Dictionary<String, Object> props = new Hashtable<>(3);
        props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_PORTAL);
        props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STOPPED);
        props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);

        moduleUiTopicDataPublisher.publishEventDataAndDictionnarySynchronously(this, props);
        viewUiTopicPublisher.publishEventSynchronously(props);
        
        unregisterEventHandlerItSelf(context);

        eventAdminServiceListener.unregisterPublisher(moduleUiTopicDataPublisher, viewUiTopicPublisher);
        navigatorEventHandler.unregisterEventHandlerItSelf(context);

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

    @Override
    @Subscriber(topic = TOPIC_PORTAL, filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_MODULE_UI + ")(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_NAME_STARTED + ")(" + PROPERTY_KEY_MODULE_UI + "=*)(" + PROPERTY_KEY_MODULE_UI_CODE + "=*))")
    public void addModuleUi(@EventArg(name = PROPERTY_KEY_MODULE_UI) final ModuleUi moduleUi, @EventArg(name = PROPERTY_KEY_MODULE_UI_CODE) final String moduleCode) {
        String moduleLibelle = moduleUi.getLibelle();
        LOGGER.info("Adding module {} ({}) => Check view roles allowed", moduleLibelle, moduleCode);
        Map<String, ViewUi> viewUiMap = moduleUi.getViewUis(getCurrentUserRoles());
        if (!viewUiMap.isEmpty()) {
            LOGGER.info("Adding module {} ({}) => At least one view is allowed for current user", moduleLibelle, moduleCode);
            leftSideMenu.addModuleUi(moduleUi, viewUiMap);
            LOGGER.info("Module {} ({}) has been added => push notification is fired", moduleLibelle, moduleCode);
            PushHelper.pushWithNotification(this, moduleLibelle + " chargé", "Le module " + moduleLibelle + " est désormais disponible.");
        } else {
            LOGGER.info("Removing module {} ({}) during adding process because no views are associated to this module anymore.", moduleLibelle, moduleCode);
            boolean moduleHasBeenRemoved = leftSideMenu.removeModuleUi(moduleCode);
            if (moduleHasBeenRemoved) {
                LOGGER.info("Module {} ({}) has been removed => push notification is fired", moduleLibelle, moduleCode);
                PushHelper.pushWithNotification(this, moduleUi.getLibelle() + " déchargé", "Le module " + moduleUi.getLibelle() + " est désormais indisponible.");
            }
        }
    }

    @Subscriber(topic = TOPIC_PORTAL, filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT + "=" + EVENT_CONTEXT_MODULE_UI + ")(" + PROPERTY_KEY_EVENT_NAME + "="
                    + EVENT_NAME_STOPPED + ")(" + PROPERTY_KEY_MODULE_UI + "=*))")
    public void removeModuleUi(@EventArg(name = PROPERTY_KEY_MODULE_UI) final ModuleUi moduleUi) {
        String moduleCode = moduleUi.getCode();
        String moduleLibelle = moduleUi.getLibelle();
        LOGGER.info("Removing module {} ({})", moduleLibelle, moduleCode);
        boolean moduleHasBeenRemoved = leftSideMenu.removeModuleUi(moduleUi.getCode());
        if (moduleHasBeenRemoved) {
            LOGGER.info("Module {} ({}) has been removed => push notification is fired", moduleLibelle, moduleCode);
            PushHelper.pushWithNotification(this, moduleLibelle + " déchargé", "Le module " + moduleLibelle + " est désormais indisponible.");
        }
    }

    private List<String> getCurrentUserRoles() {
        return Arrays.asList(new String[]{"ADMIN", "USER"});
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

    @Override
    public void showView(View view) {
        LOGGER.debug("view : " + view);
    }

}
