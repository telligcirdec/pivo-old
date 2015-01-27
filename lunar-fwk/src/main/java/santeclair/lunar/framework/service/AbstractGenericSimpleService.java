package santeclair.lunar.framework.service;

import java.io.Serializable;
import java.util.List;

import santeclair.lunar.framework.dao.IFwkGenericDao;

/**
 * Cette classe abstraite d�finie un certain nombre de m�thode de service simple (getAll, getById...). Elle a vocation � aider dans la mise en place
 * de service simple ne faisant appelle qu'� un seul DAO.
 * 
 * @author cgillet
 * 
 * @param <DOMAINE> Le type de l'objet manipul�.
 * @param <DAO> Le type du Dao.
 */
public abstract class AbstractGenericSimpleService<DOMAINE, DAO extends IFwkGenericDao<DOMAINE>> extends AbstractService implements
                IGenericSimpleService<DOMAINE, DAO> {

    /** {@inheritDoc} */
    public final List<DOMAINE> getAll() {
        logger.debug("DEBUT : getAll");
        return getDao().findAll();
    }

    /** {@inheritDoc} */
    public final List<DOMAINE> getByParam(String paramName, Object paramValue) {
        if (logger.isDebugEnabled()) {
            logger.debug("DEBUT : getByParam(" + paramName + ", " + paramValue + ")");
        }
        return getDao().findByParameter(paramName, paramValue);
    }

    /** {@inheritDoc} */
    public final DOMAINE getById(Serializable id) {
        if (logger.isDebugEnabled()) {
            logger.debug("DEBUT : getById(" + id + ")");
        }
        return getDao().findById(id);
    }

    /** {@inheritDoc} */
    public final DOMAINE addDomaine(DOMAINE domaine) {
        if (logger.isDebugEnabled()) {
            logger.debug("DEBUT : addDomaine(" + domaine + ")");
        }
        return getDao().save(domaine);
    }

    /** {@inheritDoc} */
    public final DOMAINE updateDomaine(DOMAINE domaine) {
        if (logger.isDebugEnabled()) {
            logger.debug("DEBUT : updateDomaine(" + domaine + ")");
        }
        return getDao().update(domaine);
    }

    /** {@inheritDoc} */
    public final void deleteDomaine(DOMAINE domaine) {
        if (logger.isDebugEnabled()) {
            logger.debug("DEBUT : deleteDomaine(" + domaine + ")");
        }
        getDao().delete(domaine);
    }

    /** {@inheritDoc} */
    public final DOMAINE addOrUpdateDomaine(DOMAINE domaine) {
        if (logger.isDebugEnabled()) {
            logger.debug("DEBUT : addOrUpdateDomaine(" + domaine + ")");
        }
        return getDao().saveOrUpdate(domaine);
    }

    /** {@inheritDoc} */
    public final DOMAINE merge(DOMAINE domaine) {
        if (logger.isDebugEnabled()) {
            logger.debug("DEBUT : merge(" + domaine + ")");
        }
        return getDao().merge(domaine);
    }

    /** {@inheritDoc} */
    public DOMAINE getByUniqueParam(String paramName, Object paramValue) {
        if (logger.isDebugEnabled()) {
            logger.debug("DEBUT : getByUniqueParam(" + paramName + ", " + paramValue + ")");
        }
        return getDao().findByUniqueParameter(paramName, paramValue);
    }

    /**
     * Permet d'obtenir le DAO utilis� pour manipuler les objets DOMAINE dans le syst�me de persistance.
     * 
     * @return Le DAO utilis� par le service.
     */
    protected abstract DAO getDao();

}
