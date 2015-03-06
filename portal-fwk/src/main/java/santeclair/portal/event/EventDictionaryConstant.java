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
    public static final String TOPIC_PORTAL = TOPIC_ROOT + "/santeclair";
    /**
     * Nom du topic ou sont fir� les event en rapport avec les factories de modules ui
     */
    public static final String TOPIC_MODULE_UI_FACTORY = TOPIC_PORTAL + "/module/ModuleUiFactoryEvent";
    /**
     * Nom du topic ou sont fir� les event en rapport avec les buttons permettant le d�marrage des modules
     */
    public static final String TOPIC_MAIN_BUTTON_MODULE_UI_FACTORY = TOPIC_PORTAL + "/MainButonModuleUiFactory";

    /*
     * Properties keys
     */
    /**
     * Propri�t� permettant de passer des param�tres lors d'un event.
     */
    public static final String PROPERTY_KEY_EVENT_NAME = "property.key.event.name";
    public static final String PROPERTY_KEY_EVENT_HANDLER_ID = "property.key.event.handler.id";
    public static final String PROPERTY_KEY_EVENT_SOURCE = "property.key.event.source";
    public static final String PROPERTY_KEY_EVENT_DATA = "property.key.event.data";
    public static final String PROPERTY_KEY_EVENT_DATA_TYPE = "property.key.event.data.type";

    /**
     * Propri�t� du portail
     */
    // public static final String PROPERTY_KEY_PORTAL_PID = "property.key.portal.pid";

    /**
     * Propri�t� autour des modules ui
     */
    public static final String PROPERTY_KEY_MODULE_UI_FACTORY = "property.key.module.ui.factory";
    public static final String PROPERTY_KEY_MODULE_UI_CODE = "property.key.module.ui.code";
    public static final String PROPERTY_KEY_MODULE_UI_NAME = "property.key.module.ui.name";
    public static final String PROPERTY_KEY_MODULE_UI_ICON = "property.key.module.ui.icon";
    public static final String PROPERTY_KEY_MODULE_UI_DISPLAY_ORDER = "property.key.module.ui.display.order";

    /*
     * Events name
     */
    public static final String EVENT_STARTED = "started";
    public static final String EVENT_STOPPED = "stopped";
    public static final String EVENT_ON_CLICK = "on_click";

}
