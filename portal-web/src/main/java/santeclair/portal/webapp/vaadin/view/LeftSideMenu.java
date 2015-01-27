package santeclair.portal.webapp.vaadin.view;

import santeclair.portal.webapp.vaadin.view.event.UnregisterModuleUiFactoryEvent;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.VerticalLayout;

public class LeftSideMenu extends CustomLayout {

	private static final long serialVersionUID = -4748523363216844520L;

	private final VerticalLayout buttonContainer;

	public LeftSideMenu() {
		super("sidebarLayout");
		buttonContainer = new VerticalLayout();
	}

	public void init() {
		this.setSizeFull();
		buttonContainer.setSizeFull();
	}

	// @Subscribe
	// public void addApplicationButton(RegisterModuleUiFactoryEvent
	// registerModuleUiFactoryEvent) {
	//
	// ModuleUiFactory<?> moduleUiFactory =
	// registerModuleUiFactoryEvent.getModuleFactory();
	// List<String> roles = registerModuleUiFactoryEvent.getRoles();
	//
	// String moduleCode = moduleUiFactory.getCode();
	// final Button menuComponent;
	// final VerticalLayout secoundaryButonVerticalLayout = new
	// VerticalLayout();
	// secoundaryButonVerticalLayout.setStyleName("menu-button-layout");
	// final List<PresenterName> menuView = moduleUiFactory.getMenuView(roles);
	// if (menuView != null && menuView.size() > 1) {
	//
	// }
	// }

	@Subscribe
	public void removeApplicationButton(UnregisterModuleUiFactoryEvent unregisterModuleUiFactoryEvent) {

	}

}
