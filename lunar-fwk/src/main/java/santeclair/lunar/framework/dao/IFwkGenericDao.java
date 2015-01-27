package santeclair.lunar.framework.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface listant les méthodes communes à tous les DAOs Santéclair.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE> : Le type de domaine dont dépend le DAO.
 */
public interface IFwkGenericDao<DOMAINE> {

    /**
     * Permet de récuperer la liste complète des objets DOMAINE.
     * 
     * @return L'ensemble des DOMAINE connus du référentiel.
     */
    List<DOMAINE> findAll();

    /**
     * @param paramName : Le nom du champ testé.
     * @param paramValue : La valeur du champ désiré.
     * @return La liste des objets dans le champs contient la valeur donnée.
     */
    List<DOMAINE> findByParameter(String paramName, Object paramValue);

    /**
     * @param paramName : Le nom du champ testé.
     * @return L'intance du domaine possédant la valeur la plus élevée pour le champ donné.
     */
    Serializable findMaxValue(String paramName);

    /**
     * @param paramName : Le nom du champ testé.
     * @return L'intance du domaine possédant la valeur la moins élevée pour le champ donné.
     */
    Serializable findMinValue(String paramName);

    /**
     * @param paramName : Le nom du champ testé.
     * @param paramValue : La valeur du champ désiré.
     * @param maxResult : La taille maximal de la liste devant être retournée.
     * @return La liste de taille maximal donnée contenant des objets dans le champs contient la valeur donnée.
     */
    List<DOMAINE> findByParameter(String paramName, Object paramValue, Integer maxResult);

    /**
     * @param id : L'identifiant unique de l'objet recherché.
     * @return L'instance de l'objet si l'identifiant est connu, <code>null</code> dans tous les autres cas.
     */
    DOMAINE findById(Serializable id);

    /**
     * @param paramName : Le nom du champ testé.
     * @param paramValue : La valeur du champ désiré.
     * @return L'unique objet dont le champs contient la valeur donnée, <code>null</code> dans les autres cas.
     */
    DOMAINE findByUniqueParameter(String paramName, Object paramValue);

    DOMAINE save(DOMAINE domaine);

    DOMAINE update(DOMAINE domaine);

    void delete(DOMAINE domaine);

    void flushSession();

    void evict(DOMAINE domaine);

    DOMAINE saveOrUpdate(DOMAINE domaine);

    /**
     * Sauvegarde ou met à jour, dans le système de persistance, une liste d'objet du domaine.
     * 
     * @param listeDomaine : La liste d'objet à sauvegarder.
     */
    List<DOMAINE> saveOrUpdateAll(List<DOMAINE> listeDomaine);

    DOMAINE merge(DOMAINE domaine);

    /**
     * Permet d'obtenir le nombre d'élémemt correspondant au critère de recherche.
     * 
     * @param paramName : Le nom du champ recherché.
     * @param paramValue : La valeur du champ recherché.
     * @return : Le nombre d'élément dont le champ possède la valeur recherchée.
     */
    Long getSizeByParameter(String paramName, Object paramValue);

    Long getSize();
}
