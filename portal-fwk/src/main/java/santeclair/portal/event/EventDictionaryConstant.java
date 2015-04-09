package santeclair.portal.event;

public interface EventDictionaryConstant {

    /*
     *  =========================
     *  = Topics name
     *  =========================
     */
    /**
     * Nom du topic principal.
     */
    public static final String TOPIC_ROOT = "santeclair";
    /**
     * Nom du topic portail
     */
    public static final String TOPIC_PORTAL = TOPIC_ROOT + "/portal";

    /**
     * Nom du topic portail
     */
    public static final String TOPIC_NAVIGATOR = TOPIC_PORTAL + "/navigator";

    /**
     * Nom du topic ou sont fir� les event en rapport avec les modules ui
     */
    public static final String TOPIC_MODULE_UI = TOPIC_PORTAL + "/module/ModuleUiEvent";
    /**
     * Nom du topic ou sont fir� les event en rapport avec les view ui
     */
    public static final String TOPIC_VIEW_UI = TOPIC_MODULE_UI + "/ViewUiEvent";
    /**
     * Nom du topic ou sont fir� les event en rapport avec les view ui
     */
    public static final String TOPIC_COMPONENT_UI = TOPIC_VIEW_UI + "/ComponentUiEvent";
    /**
     * Nom du topic ou sont fir� les event en rapport avec les buttons permettant le d�marrage des modules
     */
    public static final String TOPIC_MAIN_BUTTON_MODULE_UI = TOPIC_PORTAL + "/MainButonModuleUi";

    /*
     * Properties keys
     */
    /**
     * Propri�t� permettant de passer des param�tres lors d'un event.
     */
    public static final String PROPERTY_KEY_EVENT_CONTEXT = "property.key.event.context";
    public static final String PROPERTY_KEY_EVENT_NAME = "property.key.event.name";
    public static final String PROPERTY_KEY_EVENT_HANDLER_ID = "property.key.event.handler.id";
    public static final String PROPERTY_KEY_EVENT_SOURCE = "property.key.event.source";
    public static final String PROPERTY_KEY_EVENT_DATA = "property.key.event.data";
    public static final String PROPERTY_KEY_EVENT_DATA_TYPE = "property.key.event.data.type";

    /**
     * Propri�t� du portail
     */
    public static final String PROPERTY_KEY_PORTAL_SESSION_ID = "property.key.event.portal.session.id";
    public static final String PROPERTY_KEY_PORTAL_CURRENT_USER_ROLES = "property.key.event.portal.current.user.roles";

    /**
     * Propri�t� de navigation
     */
    public static final String PROPERTY_KEY_NAVIGATOR_URI = "property.key.event.navigator.uri";

    /**
     * Propri�t� des onglets
     */
    public static final String PROPERTY_KEY_TAB_HASH = "property.key.event.tab.hash";

    /**
     * Propri�t� autour des modules ui
     */
    public static final String PROPERTY_KEY_MODULE_UI = "property.key.module.ui";
    public static final String PROPERTY_KEY_MODULE_UI_CODE = "property.key.module.ui.code";

    /**
     * Propri�t� autour des view ui
     */
    public static final String PROPERTY_KEY_VIEW_UI = "property.key.view.ui";
    public static final String PROPERTY_KEY_VIEW_UI_CODE = "property.key.view.ui.code";

    /**
     * Propri�t� des param�tres
     */
    public static final String PROPERTY_KEY_PARAMS = "property.key.params";
    
    /*
     * Event context
     */
    public static final String EVENT_CONTEXT_PORTAL = "context-portal";
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

}
