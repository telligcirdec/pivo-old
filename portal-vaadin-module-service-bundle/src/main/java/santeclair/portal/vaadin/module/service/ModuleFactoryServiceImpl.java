package santeclair.portal.vaadin.module.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import santeclair.portal.vaadin.deprecated.module.api.ModuleFactory;
import santeclair.portal.vaadin.deprecated.module.api.ModuleFactoryService;
import santeclair.portal.vaadin.deprecated.module.api.ModuleFactoryServiceListener;
import santeclair.portal.vaadin.module.ModuleUi;

@Component
public class ModuleFactoryServiceImpl implements ModuleFactoryService, Serializable
{

    private static final long serialVersionUID = 4820663093650475779L;

    private List<ModuleFactory<? extends ModuleUi>> moduleFactories = new ArrayList<>();

    private List<ModuleFactoryServiceListener> listeners = new ArrayList<>();

    public synchronized void registerModuleFactory(ModuleFactory<? extends ModuleUi> moduleFactory) {
        moduleFactories.add(moduleFactory);
        List<ModuleFactoryServiceListener> listenersCloned = new ArrayList<>();
        listenersCloned.addAll(listeners);
        for (ModuleFactoryServiceListener listener : listenersCloned) {
            listener.moduleFactoryRegistered(moduleFactory);
        }
    }

    public synchronized void unregisterModuleFactory(ModuleFactory<? extends ModuleUi> moduleFactory) {
        moduleFactories.remove(moduleFactory);
        List<ModuleFactoryServiceListener> listenersCloned = new ArrayList<>();
        listenersCloned.addAll(listeners);
        for (ModuleFactoryServiceListener listener : listenersCloned) {
            listener.moduleFactoryUnregistered(moduleFactory);
        }
    }

    public List<ModuleFactory<? extends ModuleUi>> getListModuleFactory() {
        return Collections.unmodifiableList(moduleFactories);
    }

    public synchronized void addListener(ModuleFactoryServiceListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeListener(ModuleFactoryServiceListener listener) {
        listeners.remove(listener);
    }

}
