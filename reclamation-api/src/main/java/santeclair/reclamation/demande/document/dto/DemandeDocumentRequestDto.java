package santeclair.reclamation.demande.document.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import santeclair.reclamation.demande.document.enumeration.MotifDemandeEnum;
import santeclair.reclamation.demande.document.enumeration.TypeDocumentEnum;

/**
 * DTO de demande de document
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DemandeDocumentRequestDto {

    private String numeroDossier;
    private MotifDemandeEnum motifDemande;
    private String trigrammeDemandeur;
    private String organismeBeneficiaire;
    private String numeroContratBeneficiaire;
    private String nomBeneficiaire;
    private String prenomBeneficiaire;
    private Integer identifiantPS;
    private Set<TypeDocumentEnum> typesDocuments;
	
	public String getNumeroDossier() {
		return numeroDossier;
	}
	
	public void setNumeroDossier(String numeroDossier) {
		this.numeroDossier = numeroDossier;
	}
	
	public String getTrigrammeDemandeur() {
		return trigrammeDemandeur;
	}
	
	public void setTrigrammeDemandeur(String trigammeDemandeur) {
		this.trigrammeDemandeur = trigammeDemandeur;
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

	public MotifDemandeEnum getMotifDemande() {
		return motifDemande;
	}

	public void setMotifDemande(MotifDemandeEnum motifDemande) {
		this.motifDemande = motifDemande;
	}

	public Set<TypeDocumentEnum> getTypesDocuments() {
		return typesDocuments;
	}

	public void setTypesDocuments(Set<TypeDocumentEnum> typesDocuments) {
		this.typesDocuments = typesDocuments;
	}
}
