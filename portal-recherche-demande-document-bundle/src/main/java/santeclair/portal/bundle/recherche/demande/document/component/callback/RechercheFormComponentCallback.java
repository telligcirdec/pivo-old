package santeclair.portal.bundle.recherche.demande.document.component.callback;

import java.util.List;

import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;

public interface RechercheFormComponentCallback {

    public void rechercheSuccessfull(List<DemandeDocumentDto> listeDemandeDocument);
    
    public void rechercheFailed(String message);
    
}
