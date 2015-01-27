package santeclair.lunar.framework.domaine;

import java.io.Serializable;

/**
 * A ne plus utiliser.
 * 
 * @author cgillet
 * @deprecated Ancienne classe de définition d'un business object Santeclair. Désormais l'heritage est abandonné au profit de l'implémentation. Voir
 *             l'interface ISanteclairBO.
 */
@Deprecated
public abstract class AbstractBusinessObject implements Serializable {

    private static final long serialVersionUID = -4889664745721979904L;

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long identifiant) {
        id = identifiant;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractBusinessObject)) {
            return false;
        }
        final AbstractBusinessObject other = (AbstractBusinessObject) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
