package santeclair.lunar.framework.enumeration;


/**
 * Cette interface permet de marquer une énumération comme possédant un attribut 
 * code qui permet d'identifier une occurence de cette énumération
 * de manière unique.
 * 
 * @author cgillet
 * 
 * @param <ENUM> Le type de l'énumaration qui implémente cette interface.
 * @param <CODE> Le type du code.
 */
public interface CodeEnum<ENUM extends Enum<ENUM>> {

    /**
     * Renvoie un code qui permet d'identifier de façon unique une occurence de l'énumération.
     * 
     * @return Le code qui identifie de manière unique l'énumération.
     */
    public abstract String getCode();
}
