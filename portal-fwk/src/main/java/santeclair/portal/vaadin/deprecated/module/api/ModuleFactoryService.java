package santeclair.portal.vaadin.deprecated.module.api;

import java.io.Serializable;
import java.util.List;

import santeclair.portal.vaadin.deprecated.module.ModuleUi;

@Deprecated
public interface ModuleFactoryService extends Serializable {

	public void registerModuleFactory(ModuleFactory<? extends ModuleUi> moduleFactory);

	public void unregisterModuleFactory(ModuleFactory<? extends ModuleUi> moduleFactory);

	public List<ModuleFactory<? extends ModuleUi>> getListModuleFactory();

	public void addListener(ModuleFactoryServiceListener listener);

	public void removeListener(ModuleFactoryServiceListener listener);

}
