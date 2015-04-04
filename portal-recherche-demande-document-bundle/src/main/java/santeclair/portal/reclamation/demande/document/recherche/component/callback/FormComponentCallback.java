package santeclair.portal.reclamation.demande.document.recherche.component.callback;

import java.util.List;

import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;

public interface FormComponentCallback {

    public void rechercheSuccessfull(List<DemandeDocumentDto> listeDemandesDocument);
    
    public void rechercheFailed(String message);

}
