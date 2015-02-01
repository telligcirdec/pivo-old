package santeclair.portal.webapp.event;

import org.osgi.service.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import santeclair.portal.event.EventDictionaryConstant;
import santeclair.portal.vaadin.module.ModuleUiFactory;
import santeclair.portal.webapp.event.impl.ModuleUiFactoryEventHandlerImpl;

@Component
public class ModuleUiFactoryEventHandlerHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleUiFactoryEventHandlerImpl.class);

    public void handleEvent(Event event, ModuleUiFactoryEventHandler moduleUiFactoryEventHandler) {
        if (event.containsProperty(EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME)) {
            LOGGER.info("Event on topics {} handles.", moduleUiFactoryEventHandler.getTopics(), null);
            String eventName = event.getProperty(EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME).toString();
            switch (eventName) {
            case (EventDictionaryConstant.EVENT_STARTED):
                LOGGER.info("Topics : {} / Event : {}", moduleUiFactoryEventHandler.getTopics(), EventDictionaryConstant.EVENT_STARTED);
                if (event.containsProperty(EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY)) {
                    Object moduleUiFactory = event.getProperty(EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY);
                    if (ModuleUiFactory.class.isAssignableFrom(moduleUiFactory.getClass())) {
                        moduleUiFactoryEventHandler.addModuleUiFactory(event, (ModuleUiFactory<?>) moduleUiFactory);
                    } else {
                        LOGGER.warn("The event's property {} is not an instance of {} => type is {}", EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY,
                                        ModuleUiFactory.class.getName(), moduleUiFactory.getClass().getName());
                    }
                } else {
                    LOGGER.warn("An event {} has been fired but no property {} containing the module ui factory instance has been found.", EventDictionaryConstant.EVENT_STARTED,
                                    EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY);
                }
                break;
            case (EventDictionaryConstant.EVENT_STOPPED):
                LOGGER.info("Topics : {} / Event : {}", moduleUiFactoryEventHandler.getTopics(), EventDictionaryConstant.EVENT_STOPPED);
                if (event.containsProperty(EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY)) {
                    Object moduleUiFactory = (event.getProperty(EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY));
                    if (ModuleUiFactory.class.isAssignableFrom(moduleUiFactory.getClass())) {
                        moduleUiFactoryEventHandler.removeModuleUiFactory(event, (ModuleUiFactory<?>) moduleUiFactory);
                    } else {
                        LOGGER.warn("The event's property {} is not an instance of {} => type is {}", EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY,
                                        ModuleUiFactory.class.getName(), moduleUiFactory.getClass().getName());
                    }
                } else {
                    LOGGER.warn("An event {} has been fired but no property {} containing the module ui factory instance has been found.", EventDictionaryConstant.EVENT_STOPPED,
                                    EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY);
                }
                break;
            default:
                LOGGER.warn("An event fired on topics {} was not recognized / Event name : {}", moduleUiFactoryEventHandler.getTopics(), eventName);
                break;
            }
        } else {
            LOGGER.error("An event on topics {} has been fired but no property named {} present in the dictionary.", moduleUiFactoryEventHandler.getTopics(),
                            EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME);
        }
    }

    public interface ModuleUiFactoryEventHandler extends EventHandler {
        void addModuleUiFactory(Event event, ModuleUiFactory<?> moduleUiFactory);

        void removeModuleUiFactory(Event event, ModuleUiFactory<?> moduleUiFactory);
    }
}
