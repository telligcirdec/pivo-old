package santeclair.portal.webapp.vaadin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import santeclair.portal.vaadin.module.ModuleUiFactory;

@Component
public class ModuleUiFactoryRepository {

    private List<ModuleUiFactory<?>> listModuleUiFactory = Collections.synchronizedList(new ArrayList<ModuleUiFactory<?>>());

    public synchronized void addModuleUiFactory(ModuleUiFactory<?> moduleUiFactory) {
        listModuleUiFactory.add(moduleUiFactory);
    }

    public synchronized void removeModuleUiFactory(ModuleUiFactory<?> moduleUiFactory) {
        listModuleUiFactory.remove(moduleUiFactory);
    }

    public synchronized List<ModuleUiFactory<?>> getModuleUiFactories() {
        return listModuleUiFactory;
    }

}
