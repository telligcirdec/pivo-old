package santeclair.portal.vaadin.deprecated.module;

import java.io.Serializable;
import java.util.Map;

import santeclair.portal.vaadin.deprecated.event.ApplicationEventPublisher;
import santeclair.portal.vaadin.deprecated.event.UIEvent;
import santeclair.portal.vaadin.deprecated.module.uri.ViewUriFragment;
import santeclair.portal.vaadin.deprecated.module.view.CloseViewResult;
import santeclair.portal.vaadin.deprecated.module.view.PresenterName;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontIcon;
import com.vaadin.ui.Component;

@Deprecated
public interface ModuleUi extends Component, Serializable {

	void init(ViewUriFragment viewUriFragment, Navigator navigator, ApplicationEventPublisher applicationEventPublisher, View container,
			Map<String, String> parameters);

	@Override
	String getCaption();

	String getMainViewName();

	FontIcon getIconName();

	ViewUriFragment getViewUriFragment();

	void printView(String viewName);

	ModuleState currentModuleState();

	void changeCurrentModuleState(ModuleState newModuleState);

	Map<String, String> getParameters();

	void setParameters(Map<String, String> parameters);

	String getModuleCode();

	/**
	 * Décharge le module actuellement instancié.
	 * 
	 * @return un objet {@link CloseViewResult} contenant le résultat de la
	 *         fermeture.
	 */
	CloseViewResult closeModule();

	/**
	 * Déclenche un évenement.
	 * 
	 * @param event
	 */
	void publishEvent(UIEvent<?, ?> event);

	/**
	 * Provoque une action de navigation vers la page cible avec les paramètres.
	 * 
	 * @param cible
	 * @param parameters
	 */
	void navigateTo(PresenterName cible, Map<String, String> parameters);

}
