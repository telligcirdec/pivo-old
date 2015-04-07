package santeclair.portal.reclamation.demande.document.recherche.presenter;

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

import santeclair.portal.event.EventDictionaryConstant;
import santeclair.portal.reclamation.demande.document.recherche.form.RechercheForm;
import santeclair.reclamation.demande.document.dto.DemandeDocumentCriteresDto;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;
import santeclair.reclamation.demande.document.webservice.DemandeDocumentWebService;

@Component
@Instantiate
public class RechercheDemandeDocumentPresenter {

    @Requires
    private DemandeDocumentWebService demandeDocumentWebService;
    
    @Publishes(name = "presenterPublisher", topics = "RechercheDemandeDocument")
    private Publisher presenterPublisher;

    @Subscriber(name = "rechercheDemandeDocument", filter = "(&("+ EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME + "=rechercherDemandeDocument)(form=*))", topics = "RechercheDemandeDocument")
    public void rechercherDemandeDocumentParCriteres(Event event) {

        RechercheForm form = (RechercheForm) event.getProperty("form");

       // FormComponentCallback formComponentCallback = (FormComponentCallback) event.getProperty("formComponent");

        DemandeDocumentCriteresDto criteresDto = new DemandeDocumentCriteresDto();
        criteresDto.setDateDebut(form.getDateDebut());
        criteresDto.setDateFin(form.getDateFin());
        criteresDto.setEtatDossier(form.getEtatDossier());
        criteresDto.setNomBeneficiaire(form.getNomBeneficiaire());
        criteresDto.setNumeroDossier(form.getNumeroDossier());
        criteresDto.setPrenomBeneficiaire(form.getPrenomBeneficiaire());
        criteresDto.setTelephonePS(form.getTelephonePS());
        criteresDto.setTrigrammeDemandeur(form.getTrigrammeDemandeur());
        List<DemandeDocumentDto> listeDemandesDocumentDto = demandeDocumentWebService.rechercherDemandesDocumentParCriteres(criteresDto);
        
        Dictionary<String, Object> props = new Hashtable<>();
        props.put(EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME, "rechercheOk");
        props.put(EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID, (String) event.getProperty("sessionId"));
        props.put(EventDictionaryConstant.PROPERTY_KEY_TAB_HASH, (Integer) event.getProperty("tabHash"));
        props.put("listeDemandesDocumentDto", listeDemandesDocumentDto);
        presenterPublisher.send(props);
       // formComponentCallback.rechercheSuccessfull(listeDemandesDocumentDto);

    }

}
