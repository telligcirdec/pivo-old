package santeclair.lunar.framework.web.struts.form;

import org.apache.struts.action.ActionForm;

/**
 * Classe générique, incluse dans le Framework Solar.<br>
 * Toutes les applications vont devoir héritée de cette classe.<br>
 *
 * Factorisation de comportements communs à tous les formulaires des projets
 * Santeclair.<br>
 *
 * @author cazoury
 */
public abstract class FwkForm extends ActionForm {

	private static final long serialVersionUID = 6537222249457623430L;

	public FwkForm() {
		super();
	}

}
