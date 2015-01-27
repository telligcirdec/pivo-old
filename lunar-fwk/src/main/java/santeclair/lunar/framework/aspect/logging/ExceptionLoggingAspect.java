/**
 * 
 */
package santeclair.lunar.framework.aspect.logging;

import javax.faces.validator.ValidatorException;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect permettant de logger les exceptions lancées par les backing beans
 * (package web.view).
 * 
 * @author jfourmond
 * 
 */
@Aspect
@Component
public class ExceptionLoggingAspect {

	private Logger logger = LoggerFactory
			.getLogger(ExceptionLoggingAspect.class);

	@Pointcut("execution(* santeclair..*.web..*.*(..))")
	public void throwingFromPackage() {
	}

	/**
	 * Logge toutes les exceptions non catchées dans une application web et
	 * envoie un mail à la boite surveillant (voir logback.xml).
	 */
	@AfterThrowing(pointcut = "throwingFromPackage()", throwing = "t")
	public void logException(Throwable t) {

		if (!(t instanceof ValidatorException)) {
			/*
			 * Envoie un mail à la boite surveillant
			 */
			logger.error(t.getMessage(), t);
		}
	}

}
