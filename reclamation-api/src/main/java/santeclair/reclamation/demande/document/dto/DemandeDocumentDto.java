package santeclair.reclamation.demande.document.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import santeclair.reclamation.demande.document.enumeration.EtatDemandeEnum;
import santeclair.reclamation.demande.document.enumeration.MotifDemandeEnum;
import santeclair.reclamation.demande.document.enumeration.NiveauIncidentEnum;

/**
 * DTO de demande de document
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DemandeDocumentDto {

	private Integer id;
    private String numeroDossier;
    private MotifDemandeEnum motifDemande;
    private Date dateDemandeDocument;
    private String trigrammeDemandeur;
    private EtatDemandeEnum etat;
    private NiveauIncidentEnum niveauIncident;
    private String organismeBeneficiaire;
    private String numeroContratBeneficiaire;
    private String nomBeneficiaire;
    private String prenomBeneficiaire;
    private Integer identifiantPS;
    private String nomMagasinPS;
    private String enseignePS;
    private String telephonePS;
    private String codePostalPS;
    private String communePS;
    private String commentaire;
    private List<DocumentDto> documentsDto;
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNumeroDossier() {
		return numeroDossier;
	}
	
	public void setNumeroDossier(String numeroDossier) {
		this.numeroDossier = numeroDossier;
	}
	
	public Date getDateDemandeDocument() {
		return dateDemandeDocument;
	}

	public void setDateDemandeDocument(Date dateDemandeDocument) {
		this.dateDemandeDocument = dateDemandeDocument;
	}

	public MotifDemandeEnum getMotifDemande() {
		return motifDemande;
	}
	
	public void setMotifDemande(MotifDemandeEnum motifDemande) {
		this.motifDemande = motifDemande;
	}
	
	public String getTrigrammeDemandeur() {
		return trigrammeDemandeur;
	}
	
	public void setTrigrammeDemandeur(String trigammeDemandeur) {
		this.trigrammeDemandeur = trigammeDemandeur;
	}
	
	public EtatDemandeEnum getEtat() {
		return etat;
	}
	
	public void setEtat(EtatDemandeEnum etat) {
		this.etat = etat;
	}
	
	public NiveauIncidentEnum getNiveauIncident() {
		return niveauIncident;
	}
	
	public void setNiveauIncident(NiveauIncidentEnum niveauIncident) {
		this.niveauIncident = niveauIncident;
	}
	
	public String getOrganismeBeneficiaire() {
		return organismeBeneficiaire;
	}

	public void setOrganismeBeneficiaire(String organismeBeneficiaire) {
		this.organismeBeneficiaire = organismeBeneficiaire;
	}

	public String getNumeroContratBeneficiaire() {
		return numeroContratBeneficiaire;
	}

	public void setNumeroContratBeneficiaire(String numeroContratBeneficiaire) {
		this.numeroContratBeneficiaire = numeroContratBeneficiaire;
	}

	public String getNomBeneficiaire() {
		return nomBeneficiaire;
	}

	public void setNomBeneficiaire(String nomBeneficiaire) {
		this.nomBeneficiaire = nomBeneficiaire;
	}

	public String getPrenomBeneficiaire() {
		return prenomBeneficiaire;
	}

	public void setPrenomBeneficiaire(String prenomBeneficiaire) {
		this.prenomBeneficiaire = prenomBeneficiaire;
	}

	public Integer getIdentifiantPS() {
		return identifiantPS;
	}

	public void setIdentifiantPS(Integer identifiantPS) {
		this.identifiantPS = identifiantPS;
	}

	public String getNomMagasinPS() {
		return nomMagasinPS;
	}

	public void setNomMagasinPS(String nomMagasinPS) {
		this.nomMagasinPS = nomMagasinPS;
	}

	public String getEnseignePS() {
		return enseignePS;
	}

	public void setEnseignePS(String enseignePS) {
		this.enseignePS = enseignePS;
	}

	public String getTelephonePS() {
		return telephonePS;
	}

	public void setTelephonePS(String telephonePS) {
		this.telephonePS = telephonePS;
	}

	public String getCodePostalPS() {
		return codePostalPS;
	}

	public void setCodePostalPS(String codePostalPS) {
		this.codePostalPS = codePostalPS;
	}

	public String getCommunePS() {
		return communePS;
	}

	public void setCommunePS(String communePS) {
		this.communePS = communePS;
	}
	
	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public List<DocumentDto> getDocumentsDto() {
		return documentsDto;
	}
	
	public void setDocumentsDto(List<DocumentDto> documentsDto) {
		this.documentsDto = documentsDto;
	}
}
