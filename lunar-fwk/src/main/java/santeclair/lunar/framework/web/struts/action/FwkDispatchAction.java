package santeclair.lunar.framework.web.struts.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * Classe générique, incluse dans le Framework Solar.<br/> Toutes les
 * applications utilisant une action de type DispatchAction, vont devoir héritée
 * de cette classe.<br/>
 * 
 * Factorisation de comportements communs à tous les DispatchAction des projets
 * Santeclair.<br/>
 * 
 * Intégration de Spring grâce à l'héritage,
 * <code>extends DispatchActionSupport</code>.<br/> Ceci permet d'accéder à
 * la fabrique de Spring ou se situe tous les services (ou Bean Spring).<br/>
 * 
 * @author cazoury
 */
public abstract class FwkDispatchAction extends DispatchActionSupport {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public FwkDispatchAction() {
		super();
	}

	public Logger getLogger() {
		return logger;
	}

}
