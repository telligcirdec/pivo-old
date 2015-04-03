package santeclair.reclamation.demande.document.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import santeclair.reclamation.demande.document.dto.DemandeDocumentCriteresDto;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;

@Path("/demandedocument")
public interface DemandeDocumentWebService {

    /**
     * Enregistre la demande de document.
     */
	@PUT
    @Path("/enregistrerDemande")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    DemandeDocumentDto enregistrerDemandeDocument(DemandeDocumentDto demandeDocumentDto);
    
    /**
     * Recherche de demandes de document à partir des critères passés en paramètre.
     */
    @POST
    @Path("/rechercherDemande")
    @Produces(MediaType.APPLICATION_JSON)
    List<DemandeDocumentDto> rechercherDemandesDocumentParCriteres(DemandeDocumentCriteresDto criteresDto);
    
    /**
     * Récupère une demande de document à partir de son identifiant.
     */
    @GET
    @Path("/{identifiant}/identifiant/rechercherDemande")
    @Produces(MediaType.APPLICATION_JSON)
    DemandeDocumentDto rechercherDemandeDocumentParId(@PathParam("identifiant") Integer identifant);
    
}