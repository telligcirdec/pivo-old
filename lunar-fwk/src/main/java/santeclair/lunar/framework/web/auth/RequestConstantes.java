package santeclair.lunar.framework.web.auth;

/**
 * Constantes de nommage des param�tres d'entr�e dans une application
 */
public interface RequestConstantes {

    /** Nom du param�tre de la requ�te contenant l'id du jeton SSO **/
    public static final String PARAM_JETON = "idJeton";

    /** Nom du param�tre de la requ�te contenant la personnalisation particuli�re � appliquer **/
    public static final String PARAM_PERSO = "idCharte";

    /** Nom du param�tre de la requ�te contenant le nom de l'application appelante **/
    public static final String PARAM_PROVENANCE = "provenance";

    /** Nom du param�tre de la requ�te contenant les options **/
    public static final String PARAM_OPTIONS = "options";
}
