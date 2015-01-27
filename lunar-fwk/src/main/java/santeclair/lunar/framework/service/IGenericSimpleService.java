package santeclair.lunar.framework.service;

import java.io.Serializable;
import java.util.List;

import santeclair.lunar.framework.dao.IFwkGenericDao;

/**
 * Interface listant les m�thodes disponible pour la manipulation des objets du domaine.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE> : Le type d'objet manipul�.
 * @param <DAO> : Le type de DAO permettant de manipuler le domaine dans le syst�me de persistance.
 */
public interface IGenericSimpleService<DOMAINE, DAO extends IFwkGenericDao<DOMAINE>> {

    /**
     * Permet de r�cup�rer l'int�gralit� des objets connus du domaine.
     * 
     * @return La liste compl�te des objets connus.
     */
    List<DOMAINE> getAll();

    /**
     * Permet de r�cup�rer une liste d'objet du domaine ayant le champ parmaName valoris� � la valeur paramValue.
     * 
     * @param paramName : Le nom du champ recherch�.
     * @param paramValue : La valeur du champ recherch�.
     * @return Une liste d'objet du domaine poss�dant la valeur pour le champ donn�.
     */
    List<DOMAINE> getByParam(String paramName, Object paramValue);

    /**
     * Permet de r�cup�rer un unique objet du domaine ayant le champ parmaName valoris� � la valeur paramValue.
     * 
     * @param paramName : Le nom du champ recherch�.
     * @param paramValue : La valeur du champ recherch�.
     * @return Un objet du domaine poss�dant la valeur pour le champ donn�.
     */
    DOMAINE getByUniqueParam(String paramName, Object paramValue);

    /**
     * Permet de r�cup�rer un unique objet du domaine ayant pour identifiant la valeur id.
     * 
     * @param id : La valeur de l'identifiant recherch�.
     * @return Un objet du domaine poss�dant l'identifiant donn�.
     */
    DOMAINE getById(Serializable id);

    /**
     * Ajoute l'objet dans le syst�me de persistance.
     * 
     * @param domaine : Le nouvel objet.
     * @return L'objet sauvegard�.
     */
    DOMAINE addDomaine(DOMAINE domaine);

    /**
     * Ajoute ou met � jour l'objet dans le syst�me de persistance.
     * 
     * @param domaine : L'objet � mettre � jour ou ajouter.
     * @return L'objet apr�s sa sauvegarde.
     */
    DOMAINE addOrUpdateDomaine(DOMAINE domaine);

    /**
     * Met � jour l'objet dans le syst�me de persistance.
     * 
     * @param domaine : L'objet � mettre � jour.
     * @return L'objet mis � jour.
     */
    DOMAINE updateDomaine(DOMAINE domaine);

    /**
     * Supprime l'objet du syst�me de persistance.
     * 
     * @param domaine : L'objet � supprimer
     */
    void deleteDomaine(DOMAINE domaine);

    DOMAINE merge(DOMAINE domaine);
}
