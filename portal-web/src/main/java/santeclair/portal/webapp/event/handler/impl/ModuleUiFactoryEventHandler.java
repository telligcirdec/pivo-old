package santeclair.portal.webapp.event.handler.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import santeclair.portal.event.EventDictionaryConstant;
import santeclair.portal.vaadin.module.ModuleUiFactory;
import santeclair.portal.webapp.event.handler.AbstractEventHandler;
import santeclair.portal.webapp.event.handler.EventArg;
import santeclair.portal.webapp.event.handler.Subscriber;

/**
 * Cet event hendler permet d'être à l'écoute du démarrage et de la suppression des modules ui factory (muf). Dès qu'un event du type
 * {@link EventDictionaryConstant}{@code .TOPIC_MODULE_UI_FACTORY} est lancé par un bundle, on recherche dans le dictionaire de l'event la
 * propriété {@link EventDictionaryConstant}{@code .PROPERTY_KEY_EVENT_NAME} qui permet de distinguer un démarrage
 * ({@link EventDictionaryConstant}{@code .EVENT_STARTED} d'un arret ({@link EventDictionaryConstant}{@code .EVENT_STARTED}). Dans le cas d'un
 * démarrage, la propriété
 * 
 * @author Puppet master
 *
 */
@Component
public class ModuleUiFactoryEventHandler extends AbstractEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleUiFactoryEventHandler.class);
    private List<ModuleUiFactory<?>> moduleUiFactories = Collections.synchronizedList(new ArrayList<ModuleUiFactory<?>>());

    public synchronized List<ModuleUiFactory<?>> getModuleUiFactories() {
        return moduleUiFactories;
    }

    @Subscriber(topic = EventDictionaryConstant.TOPIC_MODULE_UI_FACTORY, filter = "(" + EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME + "="
                    + EventDictionaryConstant.EVENT_STARTED + ")")
    public synchronized void addModuleUiFactory(Event event, @EventArg(name = EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY) final ModuleUiFactory<?> moduleUiFactory) {
        LOGGER.debug("ModuleUiFactoryEventHandler.addModuleUiFactory({},{})", event, moduleUiFactory);
        moduleUiFactories.add(moduleUiFactory);
    }

    @Subscriber(topic = EventDictionaryConstant.TOPIC_MODULE_UI_FACTORY, filter = "(" + EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME + "="
                    + EventDictionaryConstant.EVENT_STOPPED + ")")
    public synchronized void removeModuleUiFactory(Event event, @EventArg(name = EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_FACTORY) final ModuleUiFactory<?> moduleUiFactory) {
        LOGGER.debug("ModuleUiFactoryEventHandler.removeModuleUiFactory({},{})", event, moduleUiFactory);
        moduleUiFactories.remove(moduleUiFactory);
    }

}
