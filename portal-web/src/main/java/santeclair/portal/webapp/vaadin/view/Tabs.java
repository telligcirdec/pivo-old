package santeclair.portal.webapp.vaadin.view;

import santeclair.portal.webapp.vaadin.view.event.RegisterModuleUiFactoryEvent;
import santeclair.portal.webapp.vaadin.view.event.UnregisterModuleUiFactoryEvent;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.TabSheet;

public class Tabs extends TabSheet {

	private static final long serialVersionUID = 5672663000761618207L;

	public void init() {

	}

	@Subscribe
	public void addApplicationButton(RegisterModuleUiFactoryEvent registerModuleUiFactoryEvent) {

	}

	@Subscribe
	public void removeApplicationButton(UnregisterModuleUiFactoryEvent unregisterModuleUiFactoryEvent) {

	}
}
