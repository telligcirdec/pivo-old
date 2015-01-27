package santeclair.lunar.framework.dao.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import santeclair.lunar.framework.dao.IFwkGenericDao;

/**
 * Interface listant les méthodes disponibles pour les DAOs utilisant JPA.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE> : Le type d'objet manipuler par le DAO.
 */
public interface IFwkGenericJpaDAO<DOMAINE> extends IFwkGenericDao<DOMAINE> {

    /**
     * Récupère l'objet du domaine en base dont on passe l'id en paramètre, avec toutes ses associations OneToMany ou ManyToMany initialisées.
     * ATTENTION, on n'initialise que les associations de l'objet trouvé, pas celles des objets associés.
     * 
     * @param id : L'identifiant unique de l'objet recherché.
     * @param classeDomaine la classe de l'objet recherché.
     * @return un instance de l'objet du domaine demandé, avec toutes ses Collection initialisées.
     * @throws NoResultException si aucun PS n'existe en base pour la classe et l'id en paramètre.
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
