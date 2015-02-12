package santeclair.portal.event;

public interface EventDictionaryConstant {

    /*
     * Topics name
     */
    /**
     * Nom du topic principal.
     */
    public static final String TOPIC_ROOT = "santeclair/portal";
    /**
     * Nom du topic ou sont fir� les event en rapport avec les factories de modules ui
     */
    public static final String TOPIC_MODULE_UI_FACTORY = TOPIC_ROOT + "/module/ModuleUiFactoryEvent";

    /*
     * Properties keys
     */
    /**
     * Propri�t� permettant de passer des param�tres lors d'un event.
     */
    public static final String PROPERTY_KEY_EVENT_NAME = "property.key.event.name";
    public static final String PROPERTY_KEY_MODULE_UI_CODE = "property.key.module.ui.code";
    public static final String PROPERTY_KEY_MODULE_UI_DISPLAY_ORDER = "property.key.module.ui.display.order";

    /*
     * Events name
     */
    public static final String EVENT_STARTED = "started";
    public static final String EVENT_STOPPED = "stopped";

    /*
     * 
     */

}
