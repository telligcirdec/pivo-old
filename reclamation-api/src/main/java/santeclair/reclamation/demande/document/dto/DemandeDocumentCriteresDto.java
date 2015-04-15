package santeclair.reclamation.demande.document.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import santeclair.reclamation.demande.document.enumeration.EtatDemandeEnum;

/**
 * DTO des critères de recherche de demande document
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DemandeDocumentCriteresDto {

	private String nomBeneficiaire;
    private String prenomBeneficiaire;
    private String telephonePS;
    private String numeroDossier;
    private String trigrammeDemandeur;
    private Date dateDebut;
    private Date dateFin;
    private EtatDemandeEnum etatDossier;
    private Integer maxResult;
    
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
	
	public String getTelephonePS() {
		return telephonePS;
	}
	
	public void setTelephonePS(String telephonePS) {
		this.telephonePS = telephonePS;
	}
	
	public String getNumeroDossier() {
		return numeroDossier;
	}
	
	public void setNumeroDossier(String numeroDossier) {
		this.numeroDossier = numeroDossier;
	}
	
	public String getTrigrammeDemandeur() {
		return trigrammeDemandeur;
	}
	
	public void setTrigrammeDemandeur(String trigrammeDemandeur) {
		this.trigrammeDemandeur = trigrammeDemandeur;
	}
	
	public Date getDateDebut() {
		return dateDebut;
	}
	
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	
	public Date getDateFin() {
		return dateFin;
	}
	
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
	public EtatDemandeEnum getEtatDossier() {
		return etatDossier;
	}
	
	public void setEtatDossier(EtatDemandeEnum etatDossier) {
		this.etatDossier = etatDossier;
	}

    public Integer getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(Integer maxResult) {
        this.maxResult = maxResult;
    }
}
