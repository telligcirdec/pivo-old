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
     * Nom du topic ou sont firé les event en rapport avec les modules ui
     */
    public static final String TOPIC_MODULE_UI = TOPIC_PORTAL + "/module/ModuleUiEvent";
    /**
     * Nom du topic ou sont firé les event en rapport avec les view ui
     */
    public static final String TOPIC_VIEW_UI = TOPIC_PORTAL + "/module/ViewUiEvent";
    /**
     * Nom du topic ou sont firé les event en rapport avec les buttons permettant le démarrage des modules
     */
    public static final String TOPIC_MAIN_BUTTON_MODULE_UI = TOPIC_PORTAL + "/MainButonModuleUi";

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
    public static final String PROPERTY_KEY_PORTAL_USER_ROLES = "property.key.portal.user.roles";

    /**
     * Propriété autour des modules ui
     */
    public static final String PROPERTY_KEY_MODULE_UI_MENU = "property.key.module.ui.menu";
    public static final String PROPERTY_KEY_MODULE_UI_CODE = "property.key.module.ui.code";

    /**
     * Propriété autour des modules ui
     */
    public static final String PROPERTY_KEY_VIEW_UI = "property.key.view.ui";

    /*
     * Event context
     */
    public static final String EVENT_PORTAL = "portal";
    public static final String EVENT_MODULE_UI = "moduleUi";
    public static final String EVENT_VIEW_UI = "viewUi";

    /*
     * Events name
     */
    public static final String EVENT_STARTED = "started";
    public static final String EVENT_STOPPED = "stopped";
    public static final String EVENT_UPDATED = "updated";
    public static final String EVENT_ON_CLICK = "on_click";

}
