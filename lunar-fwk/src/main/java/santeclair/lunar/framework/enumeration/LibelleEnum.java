package santeclair.lunar.framework.enumeration;

public interface LibelleEnum<ENUM extends Enum<ENUM>> {

    /**
     * Renvoie un libelle qui permet d'identifier de fa�on unique une occurence de l'�num�ration.
     * 
     * @return Le libelle qui identifie de mani�re unique l'�num�ration.
     */
    public abstract String getLibelle();

}
