package santeclair.portal.webapp.event.impl;

import org.osgi.service.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import santeclair.portal.event.EventDictionaryConstant;
import santeclair.portal.vaadin.module.ModuleUiFactory;
import santeclair.portal.webapp.event.AbstractEventHandler;
import santeclair.portal.webapp.vaadin.ModuleUiFactoryRepository;

@Component
public class ModuleUiFactoryEventHandler extends AbstractEventHandler {

    private Logger LOGGER = LoggerFactory.getLogger(ModuleUiFactoryEventHandler.class);

    @Autowired
    private ModuleUiFactoryRepository moduleUiFactoryRepository;

    @Override
    public void handleEvent(Event event) {
        if (event.containsProperty(EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME)) {
            LOGGER.info("Event on topics {} handles.", getTopics(), null);
            String eventName = event.getProperty(EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME).toString();
            switch (eventName) {
            case (EventDictionaryConstant.EVENT_STARTED):
                LOGGER.info("Topics : {} / Event : {}", getTopics(), EventDictionaryConstant.EVENT_STARTED);
                if (event.containsProperty(EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY)) {
                    Object moduleUiFactory = event.getProperty(EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY);
                    if (ModuleUiFactory.class.isAssignableFrom(moduleUiFactory.getClass())) {
                        moduleUiFactoryRepository.addModuleUiFactory((ModuleUiFactory<?>) moduleUiFactory);
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
                LOGGER.info("Topics : {} / Event : {}", getTopics(), EventDictionaryConstant.EVENT_STOPPED);
                if (event.containsProperty(EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY)) {
                    Object moduleUiFactory = event.getProperty(EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY);
                    if (ModuleUiFactory.class.isAssignableFrom(moduleUiFactory.getClass())) {
                        moduleUiFactoryRepository.removeModuleUiFactory((ModuleUiFactory<?>) moduleUiFactory);
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
                LOGGER.warn("An event fired on topics {} was not recognized / Event name : {}", getTopics(), eventName);
                break;
            }
        } else {
            LOGGER.error("An event on topics {} has been fired but no property named {} present in the dictionary.", getTopics(),
                            EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME);
        }
    }

    @Override
    public String[] getTopics() {
        return new String[]{EventDictionaryConstant.TOPIC_MODULE_UI_FACTORY};
    }

}
