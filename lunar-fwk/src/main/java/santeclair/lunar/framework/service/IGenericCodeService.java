package santeclair.lunar.framework.service;

import santeclair.lunar.framework.dao.jpa.IFwkCodeJpaDAO;

/**
 * Interface listant les services disponibles pour la manipulation d'objet du domaine poss�dant des codes uniques.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE> : Le type d'objet du domaine manipul� par le service.
 * @param <DAO> : Le type de DAO utilis� pour manipuler les objet du domaine dans le syst�me de persistance.
 * @param <CODE> : Le type de classe utilis� comme code de l'objet du domaine.
 */
public interface IGenericCodeService<DOMAINE, DAO extends IFwkCodeJpaDAO<DOMAINE, CODE>, CODE> {

    /**
     * Permet de r�cup�rer l'instance unique de l'objet de type DOMAINE poss�dant le code donn�.
     * 
     * @param code : Le code recherch�.
     * @return L'objet du domaine poss�dant le code recherch� s'il existe.
     */
    DOMAINE getByCode(CODE code);

}
