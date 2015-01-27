package santeclair.lunar.framework.enumeration;

public interface LibelleEnum<ENUM extends Enum<ENUM>> {

    /**
     * Renvoie un libelle qui permet d'identifier de façon unique une occurence de l'énumération.
     * 
     * @return Le libelle qui identifie de manière unique l'énumération.
     */
    public abstract String getLibelle();

}
