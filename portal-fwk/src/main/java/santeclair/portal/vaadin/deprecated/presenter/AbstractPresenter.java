package santeclair.portal.vaadin.deprecated.presenter;

import javax.annotation.PostConstruct;

import santeclair.portal.vaadin.deprecated.event.UIEvent;
import santeclair.portal.vaadin.deprecated.module.ModuleUi;
import santeclair.portal.vaadin.deprecated.view.PortalView;

@Deprecated
public abstract class AbstractPresenter<T extends ModuleUi, V extends PortalView> implements Presenter<T, V> {
	protected T moduleUi;

	@Override
	public void onApplicationEvent(UIEvent<? extends Enum<?>, ?> event) {
		// do nothing
	}

	@Override
	public void callbackEvent(UIEvent<? extends Enum<?>, ?> event) {
		// do nothing
	}

	@PostConstruct
	public void postConstruct() {
		bind();
	}

	@Override
	public abstract V getDisplay();

	@Override
	public abstract void bind();

	@Override
	public void setModuleUi(T moduleUi) {
		this.moduleUi = moduleUi;
	}

	@SuppressWarnings("unchecked")
	public static <X> X getEventType(UIEvent<? extends Enum<?>, ?> event, Class<X> eventTypeClass) {
		if (eventTypeClass.isAssignableFrom(event.getEventType().getClass())) {
			return (X) event.getEventType();
		}
		return null;
	}

	@Override
	public Integer order() {
		return 100000;
	}
}
