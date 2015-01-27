package santeclair.lunar.framework.enumeration;


/**
 * Cette interface permet de marquer une �num�ration comme poss�dant un attribut 
 * code qui permet d'identifier une occurence de cette �num�ration
 * de mani�re unique.
 * 
 * @author cgillet
 * 
 * @param <ENUM> Le type de l'�numaration qui impl�mente cette interface.
 * @param <CODE> Le type du code.
 */
public interface CodeEnum<ENUM extends Enum<ENUM>> {

    /**
     * Renvoie un code qui permet d'identifier de fa�on unique une occurence de l'�num�ration.
     * 
     * @return Le code qui identifie de mani�re unique l'�num�ration.
     */
    public abstract String getCode();
}
