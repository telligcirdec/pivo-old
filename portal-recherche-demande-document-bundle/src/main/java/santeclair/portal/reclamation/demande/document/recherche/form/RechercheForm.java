package santeclair.portal.reclamation.demande.document.recherche.form;

import java.util.Date;

import santeclair.reclamation.demande.document.enumeration.EtatDemandeEnum;

/**
 * Formulaire de recherche de demande de document.
 */
public class RechercheForm {
    /** Nom du b�n�ficiaire. */
    private String nomBeneficiaire;
    
    /** Pr�nom du b�n�ficiaire. */
    private String prenomBeneficiaire;
    
    /** T�l�phone du PS. */
    private String telephonePS;
    
    /** Num�ro du dossier. */
    private String numeroDossier;
    
    /** Trigramme du demandeur. */
    private String trigrammeDemandeur;
    
    /** Date de d�but. */
    private Date dateDebut;
    
    /** Date de fin. */
    private Date dateFin;
    
    /** Etat du dossier. */
    private EtatDemandeEnum etatDossier;

    /**
     * Constructeur par d�faut.
     */
    public RechercheForm() {
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

}
