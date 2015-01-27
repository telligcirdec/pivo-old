package santeclair.lunar.framework.service;

import santeclair.lunar.framework.dao.jpa.IFwkCodeJpaDAO;

/**
 * Interface listant les services disponibles pour la manipulation d'objet du domaine possédant des codes uniques.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE> : Le type d'objet du domaine manipulé par le service.
 * @param <DAO> : Le type de DAO utilisé pour manipuler les objet du domaine dans le système de persistance.
 * @param <CODE> : Le type de classe utilisé comme code de l'objet du domaine.
 */
public interface IGenericCodeService<DOMAINE, DAO extends IFwkCodeJpaDAO<DOMAINE, CODE>, CODE> {

    /**
     * Permet de récupérer l'instance unique de l'objet de type DOMAINE possédant le code donné.
     * 
     * @param code : Le code recherché.
     * @return L'objet du domaine possédant le code recherché s'il existe.
     */
    DOMAINE getByCode(CODE code);

}
