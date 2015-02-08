package santeclair.portal.event;

public interface EventDictionaryConstant {

    /*
     * Topics name
     */
    /**
     * Nom du topic principal.
     */
    public static final String TOPIC_ROOT = "santeclair/portal";
    public static final String TOPIC_MODULE_UI_FACTORY = TOPIC_ROOT + "/module/ModuleUiFactoryEvent";

    /*
     * Properties keys
     */
    public static final String PROPERTY_KEY_EVENT_NAME = "property.key.event.name";
    public static final String PROPERTY_KEY_MODULE_UI_FACTORY = "property.key.module.ui.factory";

    /*
     * Events name
     */
    public static final String EVENT_STARTED = "started";
    public static final String EVENT_STOPPED = "stopped";

    /*
     * 
     */

}
