package santeclair.lunar.framework.web.struts.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.struts.MappingDispatchActionSupport;

/**
 * Classe g�n�rique, incluse dans le Framework Solar.<br/> Toutes les
 * applications utilisant une action de type MappingDispatchAction, vont devoir
 * h�rit�e de cette classe.<br/>
 *
 * Factorisation de comportements communs � tous les MappingDispatchAction des
 * projets Santeclair.<br/>
 *
 * Int�gration de Spring gr�ce � l'h�ritage,
 * <code>extends MappingDispatchActionSupport</code>.<br/> Ceci permet
 * d'acc�der � la fabrique de Spring ou se situe tous les services (ou Bean
 * Spring).<br/>
 *
 * @author cazoury
 */
public abstract class FwkMappingDispatchAction extends MappingDispatchActionSupport {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public FwkMappingDispatchAction() {
		super();
	}

	public Logger getLogger() {
		return logger;
	}
}
