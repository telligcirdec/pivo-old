package santeclair.portal.vaadin.deprecated.presenter;

import santeclair.portal.vaadin.deprecated.event.UIEvent;
import santeclair.portal.vaadin.deprecated.event.UIListener;
import santeclair.portal.vaadin.deprecated.module.ModuleUi;
import santeclair.portal.vaadin.deprecated.view.PortalView;

/**
 * Pr�sentateur MVP.<br>
 * Permet l'interaction entre la vue et le mod�le ainsi qu'avec d'autre
 * pr�sentateur.
 * 
 * @author ldelemotte
 * 
 */
@Deprecated
public interface Presenter<T extends ModuleUi, V extends PortalView> extends UIListener<UIEvent<? extends Enum<?>, ?>> {
	/**
	 * @return la vue controll� par le pr�sentateur.
	 */
	V getDisplay();

	/**
	 * Rattache les listeneurs aux composants de la vue.
	 */
	void bind();

	void setModuleUi(T moduleUi);
}
