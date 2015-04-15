package santeclair.portal.reclamation.demande.document.detail;

public interface EventConstant {

    public static final String EVENT_RECUPERER_DEMANDE_DOCUMENT = "recupererDemandeDocument";
    public static final String EVENT_RECUPERER_DEMANDE_DOCUMENT_OK = "recuperationOK";
    public static final String EVENT_RECUPERER_DEMANDE_DOCUMENT_KO = "recuperationKO";
    public static final String EVENT_ENREGISTRER_DEMANDE_DOCUMENT = "enregistrerDemandeDocument";
    public static final String EVENT_UPDATE_RESULTAT_RECHERCHE_DEMANDE_DOCUMENT = "majResultatsRechercheDemandeDocument";
    
    public static final String PROPERTY_KEY_FORM = "form";
    public static final String PROPERTY_KEY_ID_DEMANDE_DOCUMENT = "idDemandeDocument";
    public static final String PROPERTY_KEY_DEMANDE_DOCUMENT = "demandeDocumentDto";
    public static final String PROPERTY_KEY_ETAT_DEMANDE_DOCUMENT = "etatDemandeDocument";
    
    public static final String TOPIC_DEMANDE_DOCUMENT = "DemandeDocument";
    public static final String TOPIC_RECHERCHE_DEMANDE_DOCUMENT = "RechercheDemandeDocument";
}
