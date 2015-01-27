package santeclair.lunar.framework.enumeration;


/**
 * Cette interface permet de marquer une �num�ration comme image d'une entit� santeclair. Aisni, l'�num�ration poss�de une m�thode renvoyant une
 * instance de l'entit� � laquelle elle est associ�e.
 * 
 * @author cgillet
 * 
 * @param <ENTITY> Le type de l'entit�
 */
public interface EntityEnum<ENTITY> {

    /**
     * Renvoie une instance de l'entit�.
     * 
     * @return Une instance de l'entit�.
     */
    public ENTITY getEntity();

}
