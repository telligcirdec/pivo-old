package santeclair.lunar.framework.web.auth;

/**
 * Constantes de nommage des objets webext en session
 */
public interface SessionConstantes {

    /** Nom de l'objet en session contenant le r�sultat de la demande d'authentification du jeton. */
    public static final String RESULTAT_AUTHENTIFICATION_JETON = "resultatAuthentificationJeton";

    /** Nom de l'objet en session contenant la personnalisation � appliquer */
    public static final String PERSO = "personnalisation";

    /** Nom de l'objet en session contenant les options */
    public static final String OPTIONS = "options";

    /** Nom de l'objet en session contenant l'idPart dans l'url */
    public static final String ID_PART = "idPart";

    /** Identifiant organisme. */
    public static final String ID_ORGANISME = "idOrganisme";
    /** Les Beneficiaires . */
    public static final String BENEFICIAIRES = "beneficiaires";
    /** Le num�ro de contrat. */
    public static final String NUMERO_CONTRAT = "numeroContrat";
    /** L'assur� */
    public static final String ASSURE = "assure";
    /** D�finit les roles utilisateurs */
    public static final String ACCES_PUBLIQUE = "sessionAccesPublique";

}
