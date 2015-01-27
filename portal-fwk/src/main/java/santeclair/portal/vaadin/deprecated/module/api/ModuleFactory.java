package santeclair.portal.vaadin.deprecated.module.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import santeclair.portal.vaadin.deprecated.event.ApplicationEventPublisher;
import santeclair.portal.vaadin.deprecated.module.ModuleMapKey;
import santeclair.portal.vaadin.deprecated.module.ModuleUi;
import santeclair.portal.vaadin.deprecated.module.uri.ViewUriFragment;
import santeclair.portal.vaadin.deprecated.module.view.PresenterName;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontIcon;

@Deprecated
public interface ModuleFactory<T extends ModuleUi> extends Serializable {

	String getCode();

	String getName();

	FontIcon getIconName();

	boolean isSeveralInstanceAllowed();

	T createModule(ViewUriFragment viewUriFragment, Navigator navigator, ApplicationEventPublisher applicationEventPublisher, View container,
			Map<String, String> parameters);

	void setModuleFactoryService(ModuleFactoryService service, Map<?, ?> properties);

	void unsetModuleFactoryService(ModuleFactoryService service, Map<?, ?> properties);

	boolean securityCheck(List<String> roles);

	int displayOrder();

	boolean isCloseable();

	boolean openOnInitialisation();

	ModuleMapKey getModuleMapKey();

	List<PresenterName> getMenuView(List<String> roles);
}
