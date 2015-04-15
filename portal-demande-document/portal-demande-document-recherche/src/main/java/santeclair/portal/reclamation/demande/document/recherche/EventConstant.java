package santeclair.portal.reclamation.demande.document.recherche;

public interface EventConstant {

    public static final String EVENT_RECHERCHER_DEMANDE_DOCUMENT = "rechercherDemandeDocument";
    public static final String EVENT_RECHERCHE_DEMANDE_DOCUMENT_OK = "rechercheOK";
    public static final String EVENT_UPDATE_RESULTAT_RECHERCHE_DEMANDE_DOCUMENT = "majResultatsRechercheDemandeDocument";
    
    public static final String PROPERTY_KEY_FORM = "form";
    public static final String PROPERTY_KEY_LISTE_DEMANDE_DOCUMENT = "listeDemandesDocumentDto";
    public static final String PROPERTY_KEY_ID_DEMANDE_DOCUMENT = "idDemandeDocument";
    public static final String PROPERTY_KEY_ETAT_DEMANDE_DOCUMENT = "etatDemandeDocument";
    public static final String PROPERTY_KEY_MAX_RESULT = "maxResult";
    
    public static final String TOPIC_RECHERCHE_DEMANDE_DOCUMENT = "RechercheDemandeDocument";
}
