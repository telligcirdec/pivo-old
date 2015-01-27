package santeclair.lunar.framework.dao.jpa.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import santeclair.lunar.framework.annotation.MethodLogger;
import santeclair.lunar.framework.dao.jpa.IFwkGenericJpaDAO;
import santeclair.lunar.framework.dao.jpa.exception.JpaExceptionHelper;
import santeclair.lunar.framework.util.JpaUtils;

/**
 * Classe abstraite regroupant toutes les méthodes communes aux implémentations JPA des DAOs.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE> : Le type d'objet manipulé par le DAO.
 */
public abstract class FwkGenericJpaDAO<DOMAINE> implements IFwkGenericJpaDAO<DOMAINE> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /** {@inheritDoc} */
    @MethodLogger
    public void delete(DOMAINE domaine) {
        getEntityManager().remove(domaine);
        this.flushSession();
    }

    /** {@inheritDoc} */
    @MethodLogger
    public void evict(DOMAINE domaine) {
        getEntityManager().detach(domaine);
    }

    /** {@inheritDoc} */
    @MethodLogger
    public List<DOMAINE> findAll() {
        try {
            TypedQuery<DOMAINE> query = getEntityManager().createQuery("select s from " + getEntityClass().getSimpleName() + " s",
                            getEntityClass());
            return query.getResultList();
        } catch (RuntimeException re) {
            logger.warn("FIN : findAll. Echec", re);
            return new ArrayList<DOMAINE>();
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE findById(Serializable identifiant) {
        return getEntityManager().find(getEntityClass(), identifiant);
    }

    /** {@inheritDoc} */
    @MethodLogger
    public List<DOMAINE> findByParameter(String paramName, Object paramValue) {
        TypedQuery<DOMAINE> query = getEntityManager().createQuery(
                        "select s from " + getEntityClass().getSimpleName() + " s where s." + paramName + " = :paramValue", getEntityClass());
        query.setParameter("paramValue", paramValue);
        return query.getResultList();
    }

    /** {@inheritDoc} */
    @MethodLogger
    public List<DOMAINE> findByParameter(String paramName, Object paramValue, Integer maxResult) {
        return null;
    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE findByUniqueParameter(String paramName, Object paramValue) {

        String query = "select s from " + getEntityClass().getSimpleName() + " s where s." + paramName + " = :paramValue";
        TypedQuery<DOMAINE> typedQuery = getEntityManager().createQuery(query, getEntityClass());
        typedQuery.setParameter("paramValue", paramValue);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw JpaExceptionHelper.addQueryInformation(query, typedQuery, e, NoResultException.class);
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public void flushSession() {
        getEntityManager().flush();
    }

    /** {@inheritDoc} */
    @MethodLogger
    public Long getSizeByParameter(String paramName, Object paramValue) {
        try {
            Query query = getEntityManager().createQuery(
                            "select count(*) from " + getEntityClass().getSimpleName() + " s where s." + paramName + " = :paramValue");
            query.setParameter("paramValue", paramValue);
            return (Long) query.getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public Long getSize() {
        try {
            Query query = getEntityManager().createQuery("select count(*) from " + getEntityClass().getSimpleName() + " s");
            return (Long) query.getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public Serializable findMaxValue(String paramName) {
        try {
            Query query = getEntityManager().createQuery("select max(s." + paramName + ") from " + getEntityClass().getSimpleName() + " s");
            return (Serializable) query.getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public Serializable findMinValue(String paramName) {
        try {
            Query query = getEntityManager().createQuery("select min(s." + paramName + ") from " + getEntityClass().getSimpleName() + " s");
            return (Serializable) query.getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE merge(DOMAINE domaine) {
        return getEntityManager().merge(domaine);
    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE save(DOMAINE domaine) {
        getEntityManager().persist(domaine);
        this.flushSession();
        return domaine;
    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE saveOrUpdate(DOMAINE domaine) {
        DOMAINE result = getEntityManager().merge(domaine);
        this.flushSession();
        return result;
    }

    /** {@inheritDoc} */
    @MethodLogger
    public List<DOMAINE> saveOrUpdateAll(List<DOMAINE> listeDomaine) {

        List<DOMAINE> savedOrUpdatedDomaineList = new ArrayList<DOMAINE>(listeDomaine.size());

        for (DOMAINE domaine : listeDomaine) {
            savedOrUpdatedDomaineList.add(this.saveOrUpdate(domaine));
        }
        return savedOrUpdatedDomaineList;
    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE update(DOMAINE domaine) {
        DOMAINE result = getEntityManager().merge(domaine);
        this.flushSession();
        return result;
    }

    /** {@inheritDoc} */
    @MethodLogger
    @Transactional(propagation = Propagation.MANDATORY)
    public <T extends DOMAINE> T getByIdFetchCollections(Serializable id, Class<T> classeDomaine) {

        String nomClasseDomaine = classeDomaine.getSimpleName();
        T objetDomaine = getEntityManager().find(classeDomaine, id);
        if (objetDomaine == null) {
            throw new NoResultException("Aucun objet de type " + nomClasseDomaine + " trouvé en base pour l'id " + id);
        }

        /* Fetch de toutes les associations OneToMany et ManyToMany de l'objet trouvé. */
        try {

            /* On récupère tous les champs de la classe en paramètre ainsi que de ses classes parentes. */
            ArrayList<Field> allFields = new ArrayList<Field>();
            Class<?> classe = classeDomaine;
            while (Object.class.equals(classe) == false) {
                Field[] fields = classe.getDeclaredFields();
                for (Field field : fields) {
                    allFields.add(field);
                }
                classe = classe.getSuperclass();
            }

            /* On parcourt tous les champs pour vérifier s'ils portent une assocation @OneToMany ou @ManyToMany. */
            for (Field field : allFields) {

                for (Annotation annotation : field.getDeclaredAnnotations()) {
                    Class<?> classeAnnotation = annotation.annotationType();
                    if (OneToMany.class.equals(classeAnnotation) ||
                                    ManyToMany.class.equals(classeAnnotation)) {
                        field.setAccessible(true);
                        Object collection = field.get(objetDomaine);
                        JpaUtils.fetchLazyCollection((Collection<?>) collection);
                        field.setAccessible(false);
                    }
                }

            }
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'initialiser les associations du PS de type " + nomClasseDomaine +
                            " et d'id " + id + " : " + e.getMessage(), e);
        }

        return objetDomaine;
    }

    /** {@inheritDoc} */
    public abstract Class<DOMAINE> getEntityClass();

    /** {@inheritDoc} */
    public abstract EntityManager getEntityManager();

}
