package santeclair.portal.reclamation.demande.document.recherche.presenter;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.event.Event;

import santeclair.portal.reclamation.demande.document.recherche.EventConstant;
import santeclair.portal.reclamation.demande.document.recherche.form.RechercheForm;
import santeclair.reclamation.demande.document.dto.DemandeDocumentCriteresDto;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;
import santeclair.reclamation.demande.document.webservice.DemandeDocumentWebService;

@Component
@Instantiate
public class RechercheDemandeDocumentPresenter {

    private static final Integer maxResult = 50;
    
    @Requires
    private DemandeDocumentWebService demandeDocumentWebService;

    @Publishes(name = "rechercheDemandeDocumentPublisher", topics = EventConstant.TOPIC_RECHERCHE_DEMANDE_DOCUMENT, synchronous = true)
    private Publisher rechercheDemandeDocumentPublisher;

    @Subscriber(name = EventConstant.EVENT_RECHERCHER_DEMANDE_DOCUMENT, filter = "(&(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*)("
                    + PROPERTY_KEY_TAB_HASH + "=*)(" + PROPERTY_KEY_EVENT_NAME + "=" + EventConstant.EVENT_RECHERCHER_DEMANDE_DOCUMENT
                    + ")(" + EventConstant.PROPERTY_KEY_FORM + "=*))", topics = EventConstant.TOPIC_RECHERCHE_DEMANDE_DOCUMENT)
    public void rechercherDemandeDocumentParCriteres(Event event) {

        RechercheForm form = (RechercheForm) event.getProperty(EventConstant.PROPERTY_KEY_FORM);

        DemandeDocumentCriteresDto criteresDto = new DemandeDocumentCriteresDto();
        criteresDto.setDateDebut(form.getDateDebut());
        criteresDto.setDateFin(form.getDateFin());
        criteresDto.setEtatDossier(form.getEtatDossier());
        criteresDto.setNomBeneficiaire(form.getNomBeneficiaire());
        criteresDto.setNumeroDossier(form.getNumeroDossier());
        criteresDto.setPrenomBeneficiaire(form.getPrenomBeneficiaire());
        criteresDto.setTelephonePS(form.getTelephonePS());
        criteresDto.setTrigrammeDemandeur(form.getTrigrammeDemandeur());
        criteresDto.setMaxResult(maxResult);
            
        List<DemandeDocumentDto> listeDemandesDocumentDto = demandeDocumentWebService.rechercherDemandesDocumentParCriteres(criteresDto);

        Dictionary<String, Object> props = new Hashtable<>();
        props.put(PROPERTY_KEY_EVENT_NAME, EventConstant.EVENT_RECHERCHE_DEMANDE_DOCUMENT_OK);
        props.put(PROPERTY_KEY_PORTAL_SESSION_ID, event.getProperty(PROPERTY_KEY_PORTAL_SESSION_ID));
        props.put(PROPERTY_KEY_TAB_HASH, event.getProperty(PROPERTY_KEY_TAB_HASH));
        props.put(EventConstant.PROPERTY_KEY_LISTE_DEMANDE_DOCUMENT, listeDemandesDocumentDto);
        props.put(EventConstant.PROPERTY_KEY_MAX_RESULT, maxResult);
        rechercheDemandeDocumentPublisher.send(props);

    }

}
