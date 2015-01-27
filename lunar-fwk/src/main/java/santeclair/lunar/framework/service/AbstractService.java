package santeclair.lunar.framework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Permet l'impl�mentation d'un logger.Si votre service poss�de une structure simple avec un seul DAO sollicit�, veuillez h�riter de
 * {@link santeclair.lunar.framework.service.AbstractGenericSimpleService} qui h�rite de AbstractService.
 * 
 * @author cgillet
 * 
 */
public abstract class AbstractService implements Service {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

}
