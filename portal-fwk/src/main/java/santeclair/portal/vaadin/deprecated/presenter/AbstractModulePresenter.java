package santeclair.portal.vaadin.deprecated.presenter;

import santeclair.portal.vaadin.deprecated.module.ModuleUi;
import santeclair.portal.vaadin.deprecated.module.uri.ModuleUriFragment;
import santeclair.portal.vaadin.deprecated.module.view.PresenterName;
import santeclair.portal.vaadin.deprecated.view.PortalView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@Deprecated
@SuppressWarnings("serial")
public abstract class AbstractModulePresenter<T extends ModuleUi, V extends PortalView> extends AbstractPresenter<T, V> implements ModulePresenter<T, V> {
	/** Navigateur entre les {@link com.vaadin.navigator.View}. */
	protected Navigator navigator;
	/** version fragmenté de l'URI. */
	protected ModuleUriFragment moduleUriFragment;

	@Override
	public abstract void enter(ViewChangeEvent event);

	@Override
	public abstract PresenterName getPresenterName();

	@Override
	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

	@Override
	public void setModuleUriFragment(ModuleUriFragment moduleUriFragment) {
		this.moduleUriFragment = moduleUriFragment;
	}

	@Override
	public String getPresenterTitle() {
		return getPresenterName().getLibelle();
	}
}
