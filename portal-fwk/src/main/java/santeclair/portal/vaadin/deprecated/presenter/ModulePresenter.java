package santeclair.portal.vaadin.deprecated.presenter;

import santeclair.portal.vaadin.deprecated.module.ModuleUi;
import santeclair.portal.vaadin.deprecated.module.uri.ModuleUriFragment;
import santeclair.portal.vaadin.deprecated.module.view.PresenterName;
import santeclair.portal.vaadin.deprecated.view.PortalView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;

@Deprecated
public interface ModulePresenter<T extends ModuleUi, V extends PortalView> extends View, Presenter<T, V> {

	PresenterName getPresenterName();

	String getPresenterTitle();

	void setNavigator(Navigator navigator);

	void setModuleUriFragment(ModuleUriFragment moduleUriFragment);

}
