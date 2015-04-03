package santeclair.reclamation.demande.document.webservice;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import santeclair.reclamation.demande.document.dto.DemandeDocumentRequestDto;
import santeclair.reclamation.demande.document.dto.MotifDemandeDto;

@WebService
public interface DemandeDocumentNotesWebService {

	/**
     * Enregistrement d'une demande de document depuis Notes.
     * 
     * @param request DemandeDocumentRequestDto
     * @return Boolean
     */
    @WebMethod(operationName = "creerDemandeDocument")
    @WebResult
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
    public Boolean creerDemandeDocument(DemandeDocumentRequestDto demandeDocumentRequestDto);
    
    /**
     * Récupération des motifs de demandes depuis Notes.
     * 
     * @return MotifDemandeDto
     */
    @WebMethod(operationName = "recupererMotifsDemande")
    @WebResult    
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
    public MotifDemandeDto recupererMotifsDemande();
    
    
    
}