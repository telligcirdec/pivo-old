package santeclair.lunar.framework.web.auth;

/**
 * Constantes de nommage des paramètres d'entrée dans une application
 */
public interface RequestConstantes {

    /** Nom du paramètre de la requête contenant l'id du jeton SSO **/
    public static final String PARAM_JETON = "idJeton";

    /** Nom du paramètre de la requête contenant la personnalisation particulière à appliquer **/
    public static final String PARAM_PERSO = "idCharte";

    /** Nom du paramètre de la requête contenant le nom de l'application appelante **/
    public static final String PARAM_PROVENANCE = "provenance";

    /** Nom du paramètre de la requête contenant les options **/
    public static final String PARAM_OPTIONS = "options";
}
