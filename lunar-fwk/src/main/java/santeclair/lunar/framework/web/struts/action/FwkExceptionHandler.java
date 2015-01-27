package santeclair.lunar.framework.web.struts.action;

import org.apache.struts.action.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe g�n�rique, incluse dans le Framework Solar.<br/>
 * 
 * @author sajakane
 */
public abstract class FwkExceptionHandler extends ExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public FwkExceptionHandler() {
		super();
	}

	public Logger getLogger() {
		return logger;
	}

}
