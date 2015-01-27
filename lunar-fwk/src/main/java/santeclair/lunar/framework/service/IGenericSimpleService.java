package santeclair.lunar.framework.service;

import java.io.Serializable;
import java.util.List;

import santeclair.lunar.framework.dao.IFwkGenericDao;

/**
 * Interface listant les méthodes disponible pour la manipulation des objets du domaine.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE> : Le type d'objet manipulé.
 * @param <DAO> : Le type de DAO permettant de manipuler le domaine dans le système de persistance.
 */
public interface IGenericSimpleService<DOMAINE, DAO extends IFwkGenericDao<DOMAINE>> {

    /**
     * Permet de récupérer l'intégralité des objets connus du domaine.
     * 
     * @return La liste complète des objets connus.
     */
    List<DOMAINE> getAll();

    /**
     * Permet de récupérer une liste d'objet du domaine ayant le champ parmaName valorisé à la valeur paramValue.
     * 
     * @param paramName : Le nom du champ recherché.
     * @param paramValue : La valeur du champ recherché.
     * @return Une liste d'objet du domaine possédant la valeur pour le champ donné.
     */
    List<DOMAINE> getByParam(String paramName, Object paramValue);

    /**
     * Permet de récupérer un unique objet du domaine ayant le champ parmaName valorisé à la valeur paramValue.
     * 
     * @param paramName : Le nom du champ recherché.
     * @param paramValue : La valeur du champ recherché.
     * @return Un objet du domaine possédant la valeur pour le champ donné.
     */
    DOMAINE getByUniqueParam(String paramName, Object paramValue);

    /**
     * Permet de récupérer un unique objet du domaine ayant pour identifiant la valeur id.
     * 
     * @param id : La valeur de l'identifiant recherché.
     * @return Un objet du domaine possédant l'identifiant donné.
     */
    DOMAINE getById(Serializable id);

    /**
     * Ajoute l'objet dans le système de persistance.
     * 
     * @param domaine : Le nouvel objet.
     * @return L'objet sauvegardé.
     */
    DOMAINE addDomaine(DOMAINE domaine);

    /**
     * Ajoute ou met à jour l'objet dans le système de persistance.
     * 
     * @param domaine : L'objet à mettre à jour ou ajouter.
     * @return L'objet après sa sauvegarde.
     */
    DOMAINE addOrUpdateDomaine(DOMAINE domaine);

    /**
     * Met à jour l'objet dans le système de persistance.
     * 
     * @param domaine : L'objet à mettre à jour.
     * @return L'objet mis à jour.
     */
    DOMAINE updateDomaine(DOMAINE domaine);

    /**
     * Supprime l'objet du système de persistance.
     * 
     * @param domaine : L'objet à supprimer
     */
    void deleteDomaine(DOMAINE domaine);

    DOMAINE merge(DOMAINE domaine);
}
