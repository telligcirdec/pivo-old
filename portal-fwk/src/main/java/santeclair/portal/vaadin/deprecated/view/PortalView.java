package santeclair.portal.vaadin.deprecated.view;

import javax.annotation.PostConstruct;

import com.vaadin.ui.Component;

/**
 * Vue MVP.<br>
 * Permet l'affichage des composantes Vaadin.
 * 
 * @author ldelemotte
 * 
 */
@Deprecated
public interface PortalView {
	/**
	 * Récupération de la racine de la vue
	 * 
	 * @return
	 */
	Component getViewRoot();

	/**
	 * Initialisation de la vue.
	 */
	@PostConstruct
	void initView();
}
