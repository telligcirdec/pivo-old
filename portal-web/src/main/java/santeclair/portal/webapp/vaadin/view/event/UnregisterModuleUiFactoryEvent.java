package santeclair.portal.webapp.vaadin.view.event;

import santeclair.portal.vaadin.module.ModuleUiFactory;

public class UnregisterModuleUiFactoryEvent {

	private final ModuleUiFactory<?> moduleUiFactory;

	public UnregisterModuleUiFactoryEvent(ModuleUiFactory<?> moduleUiFactory) {
		this.moduleUiFactory = moduleUiFactory;
	}

	public ModuleUiFactory<?> getModuleUiFactory() {
		return moduleUiFactory;
	}

}
