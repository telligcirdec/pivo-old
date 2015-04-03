package santeclair.portal.bundle.recherche.demande.document.presenter;

import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.osgi.service.event.Event;

import santeclair.portal.bundle.recherche.demande.document.component.callback.RechercheFormComponentCallback;
import santeclair.portal.bundle.recherche.demande.document.form.RechercheForm;
import santeclair.portal.event.EventDictionaryConstant;
import santeclair.reclamation.demande.document.dto.DemandeDocumentCriteresDto;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;
import santeclair.reclamation.demande.document.webservice.DemandeDocumentWebService;

@Component
@Instantiate
public class RechercheDemandeDocumentPresenter {

    @Requires
    private DemandeDocumentWebService demandeDocumentWebService;

    @Subscriber(name = "rechercheDemandeDocument", filter = "(&(" + EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID + "=*)(" + EventDictionaryConstant.PROPERTY_KEY_TAB_HASH + "=*)("
                    + EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME + "=rechercherDemandeDocument)(form=*)(formComponent=*))", topics = "RechercheDemandeDocument")
    public void rechercherDemandeDocumentParCriteres(Event event) {

        RechercheForm form = (RechercheForm) event.getProperty("form");

        RechercheFormComponentCallback rechercheFormComponentCallback = RechercheFormComponentCallback.class.cast(event.getProperty("formComponent"));

        DemandeDocumentCriteresDto criteresDto = new DemandeDocumentCriteresDto();
        criteresDto.setDateDebut(form.getDateDebut());
        criteresDto.setDateFin(form.getDateFin());
        criteresDto.setEtatDossier(form.getEtatDossier());
        criteresDto.setNomBeneficiaire(form.getNomBeneficiaire());
        criteresDto.setNumeroDossier(form.getNumeroDossier());
        criteresDto.setPrenomBeneficiaire(form.getPrenomBeneficiaire());
        criteresDto.setTelephonePS(form.getTelephonePS());
        criteresDto.setTrigrammeDemandeur(form.getTrigrammeDemandeur());
        List<DemandeDocumentDto> listeDemandeDocument = demandeDocumentWebService.rechercherDemandesDocumentParCriteres(criteresDto);

        rechercheFormComponentCallback.rechercheSuccessfull(listeDemandeDocument);

    }

}
