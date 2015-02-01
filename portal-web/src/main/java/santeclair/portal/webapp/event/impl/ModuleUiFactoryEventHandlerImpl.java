package santeclair.portal.webapp.event.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import santeclair.portal.event.EventDictionaryConstant;
import santeclair.portal.vaadin.module.ModuleUiFactory;
import santeclair.portal.webapp.event.AbstractEventHandler;
import santeclair.portal.webapp.event.ModuleUiFactoryEventHandlerHelper;
import santeclair.portal.webapp.event.ModuleUiFactoryEventHandlerHelper.ModuleUiFactoryEventHandler;

/**
 * Cet event hendler permet d'�tre � l'�coute du d�marrage et de la suppression des modules ui factory (muf). D�s qu'un event du type
 * {@link EventDictionaryConstant}{@code .TOPIC_MODULE_UI_FACTORY} est lanc� par un bundle, on recherche dans le dictionaire de l'event la
 * propri�t� {@link EventDictionaryConstant}{@code .PROPERTY_KEY_EVENT_NAME} qui permet de distinguer un d�marrage
 * ({@link EventDictionaryConstant}{@code .EVENT_STARTED} d'un arret ({@link EventDictionaryConstant}{@code .EVENT_STARTED}). Dans le cas d'un
 * d�marrage, la propri�t�
 * 
 * @author Puppet master
 *
 */
@Component
public class ModuleUiFactoryEventHandlerImpl extends AbstractEventHandler implements ModuleUiFactoryEventHandler {

    // private static final Logger LOGGER = LoggerFactory.getLogger(ModuleUiFactoryEventHandlerImpl.class);
    private List<ModuleUiFactory<?>> listModuleUiFactory = Collections.synchronizedList(new ArrayList<ModuleUiFactory<?>>());

    @Autowired
    private ModuleUiFactoryEventHandlerHelper moduleUiFactoryEventHandlerHelper;

    @Override
    public void handleEvent(Event event) {
        moduleUiFactoryEventHandlerHelper.handleEvent(event, this);
    }

    public synchronized List<ModuleUiFactory<?>> getModuleUiFactories() {
        return listModuleUiFactory;
    }

    @Override
    public String[] getTopics() {
        return new String[]{EventDictionaryConstant.TOPIC_MODULE_UI_FACTORY};
    }

    @Override
    public synchronized void addModuleUiFactory(Event event, ModuleUiFactory<?> moduleUiFactory) {
        listModuleUiFactory.add(moduleUiFactory);
    }

    @Override
    public synchronized void removeModuleUiFactory(Event event, ModuleUiFactory<?> moduleUiFactory) {
        listModuleUiFactory.remove(moduleUiFactory);
    }

}
