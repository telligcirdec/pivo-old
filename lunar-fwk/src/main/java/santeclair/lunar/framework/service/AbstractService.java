package santeclair.lunar.framework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Permet l'implémentation d'un logger.Si votre service possède une structure simple avec un seul DAO sollicité, veuillez hériter de
 * {@link santeclair.lunar.framework.service.AbstractGenericSimpleService} qui hérite de AbstractService.
 * 
 * @author cgillet
 * 
 */
public abstract class AbstractService implements Service {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

}
