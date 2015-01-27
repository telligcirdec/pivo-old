package santeclair.lunar.framework.web.struts.action;

import java.util.Map;

import org.springframework.web.struts.LookupDispatchActionSupport;

/**
 * Classe g�n�rique, incluse dans le Framework Solar.<br/> Toutes les
 * applications utilisant une action de type LookupDispatchAction, vont devoir
 * h�rit�e de cette classe.<br/>
 * 
 * Factorisation de comportements communs � tous les LookupDispatchAction des
 * projets Santeclair.<br/>
 * 
 * Int�gration de Spring gr�ce � l'h�ritage, <code>extends ActionSupport</code>.<br/>
 * Ceci permet d'acc�der � la fabrique de Spring ou se situe tous les services
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
