package santeclair.lunar.framework.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import santeclair.lunar.framework.annotation.MethodLogger;
import santeclair.lunar.framework.dao.IFwkGenericDao;

/**
 * Cette classe, qui hérite de {@link santeclair.lunar.framework.dao.hibernate.FwkHibernateDaoSupport}, permet d'ajouter des méthodes de DAO simple
 * (findById, findAll...) de manière générique.
 * 
 * @param DOMAINE Le type du domaine.
 * 
 * @author cgillet
 * 
 */
@SuppressWarnings("unchecked")
public abstract class FwkGenericHibernateDao<DOMAINE> extends FwkHibernateDaoSupport implements IFwkGenericDao<DOMAINE> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /** {@inheritDoc} */
    @MethodLogger
    public List<DOMAINE> findAll() {
        try {
            Query query = this.getSession().createQuery("select s from " + getEntityClass().getSimpleName() + " s");
            List<DOMAINE> domaineList = query.list();
            logger.debug("FIN : findAll. Succes");
            return domaineList;

        } catch (RuntimeException re) {
            logger.warn("FIN : findAll. Echec", re);
            return new ArrayList<DOMAINE>();
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE findById(Serializable id) {
        try {
            return (DOMAINE) this.getSession().get(getEntityClass(), id);
        } catch (RuntimeException re) {
            logger.warn("FIN : findByParameter. Echec", re);
            return null;
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public List<DOMAINE> findByParameter(String paramName, Object paramValue) {
        try {
            Query query = this.getSession().createQuery(
                            "select s from " + getEntityClass().getSimpleName() + " s where s." + paramName + " = :paramValue");
            query.setParameter("paramValue", paramValue);
            return query.list();
        } catch (RuntimeException re) {
            logger.warn("FIN : findByParameter. Echec", re);
            return new ArrayList<DOMAINE>();
        }
    }

    /** {@inheritDoc} */
    public Serializable findMaxValue(String paramName) {
        try {
            Query query = this.getSession().createQuery("select max(s." + paramName + ") from " + getEntityClass().getSimpleName() + " s");
            return (Serializable) query.uniqueResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    /** {@inheritDoc} */
    public Serializable findMinValue(String paramName) {
        try {
            Query query = this.getSession().createQuery("select min(s." + paramName + ") from " + getEntityClass().getSimpleName() + " s");
            return (Serializable) query.uniqueResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public List<DOMAINE> findByParameter(String paramName, Object paramValue, Integer maxResult) {
        try {
            Query query = this.getSession().createQuery(
                            "select s from " + getEntityClass().getSimpleName() + " s where s." + paramName + " = :paramValue");
            query.setParameter("paramValue", paramValue);
            query.setMaxResults(maxResult);
            return query.list();
        } catch (RuntimeException re) {
            return new ArrayList<DOMAINE>();
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE findByUniqueParameter(String paramName, Object paramValue) {
        try {
            Query query = this.getSession().createQuery(
                            "select s from " + getEntityClass().getSimpleName() + " s where s." + paramName + " = :paramValue");
            query.setParameter("paramValue", paramValue);
            DOMAINE domaine = (DOMAINE) query.uniqueResult();
            logger.debug("FIN : findByUniqueParameter. Succes");
            return domaine;
        } catch (RuntimeException re) {
            logger.warn("FIN : findByUniqueParameter. Echec", re);
            return null;
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public void delete(DOMAINE domaine) {
        try {
            this.getSession().delete(domaine);
        } catch (RuntimeException e) {
            logger.warn("FIN : failed to delete " + domaine, e);
            throw e;
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public void flushSession() {
        this.getSession().flush();
    }

    /** {@inheritDoc} */
    @MethodLogger
    public void evict(DOMAINE domaine) {
        this.getSession().evict(domaine);
    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE save(DOMAINE domaine) {
        Serializable id = this.getSession().save(domaine);
        return this.findById(id);
    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE saveOrUpdate(DOMAINE domaine) {
        this.getSession().saveOrUpdate(domaine);
        return domaine;
    }

    /** {@inheritDoc} */
    @MethodLogger
    public List<DOMAINE> saveOrUpdateAll(List<DOMAINE> listeDomaine) {
        for (DOMAINE domaine : listeDomaine) {
            saveOrUpdate(domaine);
        }
        return listeDomaine;
    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE update(DOMAINE domaine) {
        this.getSession().update(domaine);
        return domaine;
    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE merge(DOMAINE domaine) {
        DOMAINE myDomaine = (DOMAINE) this.getSession().merge(domaine);
        this.getSession().flush();
        return myDomaine;
    }

    /** {@inheritDoc} */
    @MethodLogger
    public Long getSizeByParameter(String paramName, Object paramValue) {
        try {
            Query query = this.getSession().createQuery(
                            "select count(*) from " + getEntityClass().getSimpleName() + " s where s." + paramName + " = :paramValue");
            query.setParameter("paramValue", paramValue);
            return (Long) query.uniqueResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public Long getSize() {
        try {
            Query query = this.getSession().createQuery("select count(*) from " + getEntityClass().getSimpleName() + " s");
            return (Long) query.uniqueResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    /**
     * Renvoi la classe du domaine.
     * 
     * @return La classe du domaine.
     */
    protected abstract Class<? extends DOMAINE> getEntityClass();

}
