package com.mmdi.projet.pivo.utils.event;

public interface PivoEventDictionary {

	/*
	 * ========================= = Topics name =========================
	 */
	/**
	 * Nom du topic principal.
	 */
	public static final String TOPIC_ROOT = "santeclair";
	/**
	 * Nom du topic portail
	 */
	public static final String TOPIC_PIVO = TOPIC_ROOT + "/pivo";

	/**
	 * Nom du topic portail
	 */
	public static final String TOPIC_NAVIGATOR = TOPIC_PIVO + "/navigator";

	/**
	 * Nom du topic portail
	 */
	public static final String TOPIC_TABS = TOPIC_PIVO + "/tabs";

	/**
	 * Nom du topic ou sont firé les event en rapport avec les modules ui
	 */
	public static final String TOPIC_MODULE_UI = TOPIC_PIVO
			+ "/module/ModuleUiEvent";
	/**
	 * Nom du topic ou sont firé les event en rapport avec les view ui
	 */
	public static final String TOPIC_VIEW_UI = TOPIC_MODULE_UI + "/ViewUiEvent";
	/**
	 * Nom du topic ou sont firé les event en rapport avec les view ui
	 */
	public static final String TOPIC_COMPONENT_UI = TOPIC_VIEW_UI
			+ "/ComponentUiEvent";
	/**
	 * Nom du topic ou sont firé les event en rapport avec les buttons
	 * permettant le démarrage des modules
	 */
	public static final String TOPIC_MAIN_BUTTON_MODULE_UI = TOPIC_PIVO
			+ "/MainButonModuleUi";

	/*
	 * Properties keys
	 */
	/**
	 * Propriété permettant de passer des paramètres lors d'un event.
	 */
	public static final String PROPERTY_KEY_EVENT_CONTEXT = "property.key.event.context";
	public static final String PROPERTY_KEY_EVENT_NAME = "property.key.event.name";
	public static final String PROPERTY_KEY_EVENT_HANDLER_ID = "property.key.event.handler.id";
	public static final String PROPERTY_KEY_EVENT_SOURCE = "property.key.event.source";
	public static final String PROPERTY_KEY_EVENT_DATA = "property.key.event.data";
	public static final String PROPERTY_KEY_EVENT_DATA_TYPE = "property.key.event.data.type";

	/**
	 * Propriété du portail
	 */
	public static final String PROPERTY_KEY_PORTAL_SESSION_ID = "property.key.event.portal.session.id";
	public static final String PROPERTY_KEY_PORTAL_CURRENT_USER_ROLES = "property.key.event.portal.current.user.roles";

	/**
	 * Propriété de navigation
	 */
	public static final String PROPERTY_KEY_NAVIGATOR_URI = "property.key.event.navigator.uri";

	/**
	 * Propriété des onglets
	 */
	public static final String PROPERTY_KEY_TAB_HASH = "property.key.event.tab.hash";
	public static final String PROPERTY_KEY_TAB_CALLBACK = "property.key.event.tab.callback";

	/**
	 * Propriété autour des modules ui
	 */
	public static final String PROPERTY_KEY_MODULE_UI = "property.key.module.ui";
	public static final String PROPERTY_KEY_MODULE_UI_CODE = "property.key.module.ui.code";

	/**
	 * Propriété autour des view ui
	 */
	public static final String PROPERTY_KEY_VIEW_UI = "property.key.view.ui";
	public static final String PROPERTY_KEY_VIEW_UI_CODE = "property.key.view.ui.code";

	/**
	 * Propriété des paramètres
	 */
	public static final String PROPERTY_KEY_PARAMS = "property.key.params";

	/**
	 * Propriété de gestion des exceptions dans le portail
	 */
	public static final String PROPERTY_KEY_EXCEPTION_SUMMARY = "property.exception.summary";
	public static final String PROPERTY_KEY_EXCEPTION_MESSAGE = "property.exception.message";
	public static final String PROPERTY_KEY_EXCEPTION_THROWABLE = "property.exception.throwable";

	/*
	 * Event context
	 */
	public static final String EVENT_CONTEXT_PIVO = "context-portal";
	public static final String EVENT_CONTEXT_MODULE_UI = "context-moduleUi";
	public static final String EVENT_CONTEXT_VIEW_UI = "context-viewUi";
	public static final String EVENT_CONTEXT_TABS = "context-tabs";

	/*
	 * Events name
	 */
	public static final String EVENT_NAME_STARTED = "started";
	public static final String EVENT_NAME_STOPPED = "stopped";
	public static final String EVENT_NAME_UPDATED = "updated";
	public static final String EVENT_NAME_NEW = "new";
	public static final String EVENT_NAME_ASKING_CLOSED = "asking_closed";
	public static final String EVENT_NAME_CLOSED = "closed";
	public static final String EVENT_NAME_ON_CLICK = "on_click";
	public static final String EVENT_NAME_NAVIGATION = "navigation";
	public static final String EVENT_NAME_EXCEPTION = "exception";

}