package com.mmdi.projet.pivo.webapp.vaadin;

import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.EVENT_CONTEXT_MODULE_UI;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.EVENT_CONTEXT_PIVO;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.EVENT_NAME_EXCEPTION;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.EVENT_NAME_STARTED;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.EVENT_NAME_STOPPED;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_EVENT_CONTEXT;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_EVENT_NAME;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_EXCEPTION_MESSAGE;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_EXCEPTION_SUMMARY;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_EXCEPTION_THROWABLE;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_MODULE_UI;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_MODULE_UI_CODE;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_PORTAL_SESSION_ID;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_TAB_CALLBACK;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.TOPIC_MODULE_UI;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.TOPIC_PIVO;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.TOPIC_VIEW_UI;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mmdi.projet.pivo.utils.event.handler.AbstractEventHandler;
import com.mmdi.projet.pivo.utils.event.handler.EventProperty;
import com.mmdi.projet.pivo.utils.event.handler.PivoEventHandler;
import com.mmdi.projet.pivo.utils.event.handler.Subscriber;
import com.mmdi.projet.pivo.utils.event.publisher.callback.PortalStartCallback;
import com.mmdi.projet.pivo.utils.listener.service.impl.EventAdminServiceListener;
import com.mmdi.projet.pivo.utils.listener.service.impl.EventAdminServiceListener.DataPublisher;
import com.mmdi.projet.pivo.utils.listener.service.impl.EventAdminServiceListener.Publisher;
import com.mmdi.projet.pivo.utils.module.ModuleUi;
import com.mmdi.projet.pivo.utils.view.ViewUi;
import com.mmdi.projet.pivo.webapp.HostActivator;
import com.mmdi.projet.pivo.webapp.vaadin.navigator.NavigatorEventHandler;
import com.mmdi.projet.pivo.webapp.vaadin.view.LeftSideMenu;
import com.mmdi.projet.pivo.webapp.vaadin.view.Main;
import com.mmdi.projet.pivo.webapp.vaadin.view.Tabs;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.ClientConnector;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

/**
 * The Application's "verticalButtonMain" class
 */
@PreserveOnRefresh
@Title("Portail Santeclair")
@Theme("santeclair")
public class PortalApp extends UI implements PivoEventHandler,
		PortalStartCallback, ViewDisplay, ClientConnector.DetachListener {

	private static final long serialVersionUID = -5547062232353913227L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PortalApp.class);

	// Container global
	private Main main;
	// Container des boutons sur le menu � gauche permettant de naviguer
	private LeftSideMenu leftSideMenu;
	// Container des onglets
	private Tabs tabs;

	private NavigatorEventHandler navigatorEventHandler;

	private DataPublisher<PortalApp, PortalStartCallback> dataPublisherToModuleUiTopic;

	private Publisher<PortalApp> publisherToViewUiTopic;

	private String sessionId;

	/*
	 * D�but du Code UI
	 */
	@Override
	public void init(VaadinRequest request) {
		LOGGER.info("Initialisation de l'UI");
		ApplicationContext applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(VaadinServlet.getCurrent()
						.getServletContext());
		HostActivator hostActivator = applicationContext
				.getBean(HostActivator.class);

		CurrentSessionServiceRegistration currentSessionServiceRegistration = applicationContext
				.getBean(CurrentSessionServiceRegistration.class);
		currentSessionServiceRegistration.unregisterAllServices();

		EventAdminServiceListener eventAdminServiceListener = applicationContext
				.getBean(EventAdminServiceListener.class);

		BundleContext context = hostActivator.getBundleContext();

		// initialisation de l'IHM
		this.setSizeFull();
		this.setErrorHandler();

		Navigator navigator = new Navigator(this, (ViewDisplay) this);
		this.sessionId = this.getSession().getSession().getId();
		navigatorEventHandler = new NavigatorEventHandler(navigator, sessionId);
		currentSessionServiceRegistration
				.addServiceRegistrations(navigatorEventHandler
						.registerEventHandlerItself(context));

		// Cr�ation du composant contenant les tabsheet
		LOGGER.info("Initialisation du container d'onglet");
		tabs = new Tabs(eventAdminServiceListener, sessionId,
				getCurrentUserRoles());
		tabs.init();
		currentSessionServiceRegistration.addServiceRegistrations(tabs
				.registerEventHandlerItself(context));

		navigator.addView("", tabs);
		navigator.addView("container", tabs);

		// Cr�ation du composant contenant le menu � gauche avec les boutons
		LOGGER.info("Initialisation du menu gauche");
		leftSideMenu = new LeftSideMenu(eventAdminServiceListener, sessionId);
		leftSideMenu.init();

		// Cr�ation du container principal
		LOGGER.info("Cr�ation du container principal");
		main = new Main(leftSideMenu, tabs);
		main.init();

		// Enregistrement des listeners d'event dans le portalEventBus
		LOGGER.info("Enregistrement des listeners d'event dans le portalEventBus");

		currentSessionServiceRegistration
				.addServiceRegistrations(registerEventHandlerItself(context));

		dataPublisherToModuleUiTopic = eventAdminServiceListener
				.getDataPublisher(this, PortalStartCallback.class,
						TOPIC_MODULE_UI);
		publisherToViewUiTopic = eventAdminServiceListener.getPublisher(this,
				TOPIC_VIEW_UI);

		this.setContent(main);

		Dictionary<String, Object> props = new Hashtable<>(3);
		props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_PIVO);
		props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STARTED);
		props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
		props.put(PROPERTY_KEY_TAB_CALLBACK, tabs);

		dataPublisherToModuleUiTopic
				.publishEventDataAndDictionnarySynchronously(this, props);
		LOGGER.debug("Fin Initialisation de l'UI");

		addDetachListener(this);
	}

	@Override
	public List<ServiceRegistration<?>> registerEventHandlerItself(
			BundleContext bundleContext) {
		List<ServiceRegistration<?>> srList = new ArrayList<>();
		try {
			srList = AbstractEventHandler.registerEventHandler(bundleContext,
					this);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		return srList;
	}

	@Override
	@Subscriber(topic = TOPIC_PIVO, filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT
			+ "=" + EVENT_CONTEXT_MODULE_UI + ")(" + PROPERTY_KEY_EVENT_NAME
			+ "=" + EVENT_NAME_STARTED + ")(" + PROPERTY_KEY_MODULE_UI + "=*)("
			+ PROPERTY_KEY_MODULE_UI_CODE + "=*))")
	public void addModuleUi(
			@EventProperty(propKey = PROPERTY_KEY_MODULE_UI) final ModuleUi moduleUi,
			@EventProperty(propKey = PROPERTY_KEY_MODULE_UI_CODE) final String moduleCode) {
		String moduleLibelle = moduleUi.getLibelle();
		LOGGER.info("Adding module {} ({}) => Check view roles allowed",
				moduleLibelle, moduleCode);
		Map<String, ViewUi> viewUiMap = moduleUi
				.getViewUis(getCurrentUserRoles());
		if (!viewUiMap.isEmpty()) {
			LOGGER.info(
					"Adding module {} ({}) => At least one view is allowed for current user",
					moduleLibelle, moduleCode);
			leftSideMenu.addModuleUi(moduleUi, viewUiMap);
			LOGGER.info(
					"Module {} ({}) has been added => push notification is fired",
					moduleLibelle, moduleCode);
			PushHelper
					.pushWithNotification(this, moduleLibelle + " charg�",
							"Le module " + moduleLibelle
									+ " est d�sormais disponible.");
		} else {
			LOGGER.info(
					"Removing module {} ({}) during adding process because no views are associated to this module anymore.",
					moduleLibelle, moduleCode);
			boolean moduleHasBeenRemoved = leftSideMenu
					.removeModuleUi(moduleCode);
			if (moduleHasBeenRemoved) {
				LOGGER.info(
						"Module {} ({}) has been removed => push notification is fired",
						moduleLibelle, moduleCode);
				PushHelper.pushWithNotification(this, moduleUi.getLibelle()
						+ " d�charg�", "Le module " + moduleUi.getLibelle()
						+ " est d�sormais indisponible.");
			}
		}
	}

	@Subscriber(topic = TOPIC_PIVO, filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT
			+ "=" + EVENT_CONTEXT_MODULE_UI + ")(" + PROPERTY_KEY_EVENT_NAME
			+ "=" + EVENT_NAME_STOPPED + ")(" + PROPERTY_KEY_MODULE_UI + "=*))")
	public void removeModuleUi(
			@EventProperty(propKey = PROPERTY_KEY_MODULE_UI) final ModuleUi moduleUi) {
		String moduleCode = moduleUi.getCode();
		String moduleLibelle = moduleUi.getLibelle();
		LOGGER.info("Removing module {} ({})", moduleLibelle, moduleCode);
		boolean moduleHasBeenRemoved = leftSideMenu.removeModuleUi(moduleUi
				.getCode());
		if (moduleHasBeenRemoved) {
			LOGGER.info(
					"Module {} ({}) has been removed => push notification is fired",
					moduleLibelle, moduleCode);
			PushHelper.pushWithNotification(this, moduleLibelle + " d�charg�",
					"Le module " + moduleLibelle
							+ " est d�sormais indisponible.");
		}
	}

	@Subscriber(topic = TOPIC_PIVO, filter = "(&(" + PROPERTY_KEY_EVENT_NAME
			+ "=" + EVENT_NAME_EXCEPTION + ")("
			+ PROPERTY_KEY_PORTAL_SESSION_ID + "=*)("
			+ PROPERTY_KEY_EXCEPTION_SUMMARY + "=*)("
			+ PROPERTY_KEY_EXCEPTION_MESSAGE + "=*))")
	public void showError(
			@EventProperty(propKey = PROPERTY_KEY_PORTAL_SESSION_ID, required = true) final String sessionId,
			@EventProperty(propKey = PROPERTY_KEY_EXCEPTION_SUMMARY, required = true) final String summary,
			@EventProperty(propKey = PROPERTY_KEY_EXCEPTION_MESSAGE, required = true) final String message,
			@EventProperty(propKey = PROPERTY_KEY_EXCEPTION_THROWABLE, required = false) final Throwable throwable) {
		if (sessionId.equals(this.sessionId)) {
			Notification.show(summary, message, Type.ERROR_MESSAGE);
			if (throwable != null) {
				LOGGER.error(summary + " : " + message, throwable);
			} else {
				LOGGER.error(summary + " : " + message);
			}
		}
	}

	private List<String> getCurrentUserRoles() {
		return Arrays.asList(new String[] { "ADMIN", "USER" });
	}

	private void setErrorHandler() {
		this.setErrorHandler(new DefaultErrorHandler() {
			private static final long serialVersionUID = -6689459084265117649L;

			@Override
			public void error(com.vaadin.server.ErrorEvent event) {
				String codeErreur = DateFormatUtils.format(new Date(),
						"yyyyMMddHHmmss");
				Throwable t = event.getThrowable();
				if (findAccesDeniedExceptionException(t) != null) {
					Notification
							.show("Vous n'avez pas les droits suffisant pour acc�der � la ressource demand�e.",
									Type.WARNING_MESSAGE);
				} else {
					LOGGER.error("Erreur inattendu dans le portail : "
							+ codeErreur, t);
					Notification
							.show("Erreur inattendue de l'application.\n",
									"Merci de prendre une capture d'�cran et de cr�er une intervention avec cette capture et le code suivant :\n"
											+ codeErreur, Type.ERROR_MESSAGE);
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
	private AccessDeniedException findAccesDeniedExceptionException(
			Throwable throwable) {
		if (throwable != null) {
			if (AccessDeniedException.class.isAssignableFrom(throwable
					.getClass())) {
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

	@Override
	public void detach(DetachEvent event) {
		LOGGER.info("Detachement de l'UI");
		ApplicationContext applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(VaadinServlet.getCurrent()
						.getServletContext());
		CurrentSessionServiceRegistration currentSessionServiceRegistration = applicationContext
				.getBean(CurrentSessionServiceRegistration.class);

		Dictionary<String, Object> props = new Hashtable<>(3);
		props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_PIVO);
		props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STOPPED);
		props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);

		dataPublisherToModuleUiTopic
				.publishEventDataAndDictionnarySynchronously(this, props);
		publisherToViewUiTopic.publishEventSynchronously(props);

		currentSessionServiceRegistration.unregisterAllServices();
	}

}
