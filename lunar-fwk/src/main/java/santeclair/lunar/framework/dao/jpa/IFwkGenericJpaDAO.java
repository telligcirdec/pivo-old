package santeclair.lunar.framework.dao.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import santeclair.lunar.framework.dao.IFwkGenericDao;

/**
 * Interface listant les m�thodes disponibles pour les DAOs utilisant JPA.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE> : Le type d'objet manipuler par le DAO.
 */
public interface IFwkGenericJpaDAO<DOMAINE> extends IFwkGenericDao<DOMAINE> {

    /**
     * R�cup�re l'objet du domaine en base dont on passe l'id en param�tre, avec toutes ses associations OneToMany ou ManyToMany initialis�es.
     * ATTENTION, on n'initialise que les associations de l'objet trouv�, pas celles des objets associ�s.
     * 
     * @param id : L'identifiant unique de l'objet recherch�.
     * @param classeDomaine la classe de l'objet recherch�.
     * @return un instance de l'objet du domaine demand�, avec toutes ses Collection initialis�es.
     * @throws NoResultException si aucun PS n'existe en base pour la classe et l'id en param�tre.
     */
    <T extends DOMAINE> T getByIdFetchCollections(Serializable id, Class<T> classeDomaine);

    /**
     * Renvoi la classe du domaine.
     * 
     * @return La classe du domaine.
     */
    Class<DOMAINE> getEntityClass();

    /**
     * Renvoi la classe du domaine.
     * 
     * @return La classe du domaine.
     */
    EntityManager getEntityManager();

}
