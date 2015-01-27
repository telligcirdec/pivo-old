package santeclair.lunar.framework.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface listant les m�thodes communes � tous les DAOs Sant�clair.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE> : Le type de domaine dont d�pend le DAO.
 */
public interface IFwkGenericDao<DOMAINE> {

    /**
     * Permet de r�cuperer la liste compl�te des objets DOMAINE.
     * 
     * @return L'ensemble des DOMAINE connus du r�f�rentiel.
     */
    List<DOMAINE> findAll();

    /**
     * @param paramName : Le nom du champ test�.
     * @param paramValue : La valeur du champ d�sir�.
     * @return La liste des objets dans le champs contient la valeur donn�e.
     */
    List<DOMAINE> findByParameter(String paramName, Object paramValue);

    /**
     * @param paramName : Le nom du champ test�.
     * @return L'intance du domaine poss�dant la valeur la plus �lev�e pour le champ donn�.
     */
    Serializable findMaxValue(String paramName);

    /**
     * @param paramName : Le nom du champ test�.
     * @return L'intance du domaine poss�dant la valeur la moins �lev�e pour le champ donn�.
     */
    Serializable findMinValue(String paramName);

    /**
     * @param paramName : Le nom du champ test�.
     * @param paramValue : La valeur du champ d�sir�.
     * @param maxResult : La taille maximal de la liste devant �tre retourn�e.
     * @return La liste de taille maximal donn�e contenant des objets dans le champs contient la valeur donn�e.
     */
    List<DOMAINE> findByParameter(String paramName, Object paramValue, Integer maxResult);

    /**
     * @param id : L'identifiant unique de l'objet recherch�.
     * @return L'instance de l'objet si l'identifiant est connu, <code>null</code> dans tous les autres cas.
     */
    DOMAINE findById(Serializable id);

    /**
     * @param paramName : Le nom du champ test�.
     * @param paramValue : La valeur du champ d�sir�.
     * @return L'unique objet dont le champs contient la valeur donn�e, <code>null</code> dans les autres cas.
     */
    DOMAINE findByUniqueParameter(String paramName, Object paramValue);

    DOMAINE save(DOMAINE domaine);

    DOMAINE update(DOMAINE domaine);

    void delete(DOMAINE domaine);

    void flushSession();

    void evict(DOMAINE domaine);

    DOMAINE saveOrUpdate(DOMAINE domaine);

    /**
     * Sauvegarde ou met � jour, dans le syst�me de persistance, une liste d'objet du domaine.
     * 
     * @param listeDomaine : La liste d'objet � sauvegarder.
     */
    List<DOMAINE> saveOrUpdateAll(List<DOMAINE> listeDomaine);

    DOMAINE merge(DOMAINE domaine);

    /**
     * Permet d'obtenir le nombre d'�l�memt correspondant au crit�re de recherche.
     * 
     * @param paramName : Le nom du champ recherch�.
     * @param paramValue : La valeur du champ recherch�.
     * @return : Le nombre d'�l�ment dont le champ poss�de la valeur recherch�e.
     */
    Long getSizeByParameter(String paramName, Object paramValue);

    Long getSize();
}
