package santeclair.portal.webapp.vaadin.view.event;

import java.util.List;

import santeclair.portal.vaadin.module.ModuleUiFactory;

public class RegisterModuleUiFactoryEvent {

	private final List<String> roles;
	private final ModuleUiFactory<?> moduleUiFactory;

	public RegisterModuleUiFactoryEvent(ModuleUiFactory<?> moduleUiFactory, List<String> roles) {
		this.moduleUiFactory = moduleUiFactory;
		this.roles = roles;
	}

	public ModuleUiFactory<?> getModuleUiFactory() {
		return moduleUiFactory;
	}

	public List<String> getRoles() {
		return roles;
	}

}
