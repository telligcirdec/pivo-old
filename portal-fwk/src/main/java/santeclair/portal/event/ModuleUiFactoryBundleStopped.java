package santeclair.portal.event;

import santeclair.portal.vaadin.module.ModuleUi;
import santeclair.portal.vaadin.module.ModuleUiFactory;

public interface ModuleUiFactoryBundleStopped<T extends ModuleUi> {

    ModuleUiFactory<T> getModuleUiFactory();

}
