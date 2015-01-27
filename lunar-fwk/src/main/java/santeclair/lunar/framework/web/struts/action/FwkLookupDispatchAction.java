package santeclair.lunar.framework.web.struts.action;

import java.util.Map;

import org.springframework.web.struts.LookupDispatchActionSupport;

/**
 * Classe générique, incluse dans le Framework Solar.<br/> Toutes les
 * applications utilisant une action de type LookupDispatchAction, vont devoir
 * héritée de cette classe.<br/>
 * 
 * Factorisation de comportements communs à tous les LookupDispatchAction des
 * projets Santeclair.<br/>
 * 
 * Intégration de Spring grâce à l'héritage, <code>extends ActionSupport</code>.<br/>
 * Ceci permet d'accéder à la fabrique de Spring ou se situe tous les services
 * (ou Bean Spring).<br/>
 * 
 * @author cazoury
 */
public abstract class FwkLookupDispatchAction extends
		LookupDispatchActionSupport {

	public FwkLookupDispatchAction() {
		super();
	}

	protected Map<?, ?> getKeyMethodMap() {
		return null;
	}

}
