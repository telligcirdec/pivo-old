package santeclair.lunar.framework.enumeration;


/**
 * Cette interface permet de marquer une énumération comme image d'une entité santeclair. Aisni, l'énumération possède une méthode renvoyant une
 * instance de l'entité à laquelle elle est associée.
 * 
 * @author cgillet
 * 
 * @param <ENTITY> Le type de l'entité
 */
public interface EntityEnum<ENTITY> {

    /**
     * Renvoie une instance de l'entité.
     * 
     * @return Une instance de l'entité.
     */
    public ENTITY getEntity();

}
