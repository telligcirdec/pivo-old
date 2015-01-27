package santeclair.lunar.framework.service.impl;

import santeclair.lunar.framework.dao.jpa.IFwkCodeJpaDAO;
import santeclair.lunar.framework.service.AbstractGenericSimpleService;
import santeclair.lunar.framework.service.IGenericCodeService;

/**
 * Classe abstraite regroupant toutes les m�thodes utiles aux services manipulant des objets du domaine poss�dant un code unique.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE> : Le type d'objet du domaine manipul� par le service.
 * @param <DAO> : Le type de DAO utilis� pour manipuler les objet du domaine dans le syst�me de persistance.
 * @param <CODE> : Le type de classe utilis� comme code de l'objet du domaine.
 */
public abstract class AbstractGenericCodeService<DOMAINE, DAO extends IFwkCodeJpaDAO<DOMAINE, CODE>, CODE> extends
                AbstractGenericSimpleService<DOMAINE, DAO> implements IGenericCodeService<DOMAINE, DAO, CODE> {

    /** {@inheritDoc} */
    public DOMAINE getByCode(CODE code) {
        return getDao().findEntityByCode(code);
    }
}
