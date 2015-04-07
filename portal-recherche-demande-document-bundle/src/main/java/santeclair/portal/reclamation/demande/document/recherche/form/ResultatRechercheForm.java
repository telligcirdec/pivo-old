package santeclair.portal.reclamation.demande.document.recherche.form;

import java.io.Serializable;
import java.util.Date;

import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;

import com.vaadin.ui.HorizontalLayout;

public class ResultatRechercheForm implements Serializable {
    
    /** Serial Version UID */
    private static final long serialVersionUID = 1L;

    /** Boutons d'action */
    private HorizontalLayout buttonLayout;

    private Integer id;
    
    private String numeroDossier;
    
    private Date dateDemandeDocument;
    
    private String trigrammeDemandeur;
    
    private String etat;
    
    private String nomBeneficiaire;
    
    private String prenomBeneficiaire;
    
    private String telephonePS;

    /**
     * Constructeur par défaut.
     */
    public ResultatRechercheForm() {
    }

    public ResultatRechercheForm(DemandeDocumentDto dto) {
        this.id = dto.getId();
        this.numeroDossier = dto.getNumeroDossier();
        this.dateDemandeDocument = dto.getDateDemandeDocument();
        this.trigrammeDemandeur = dto.getTrigrammeDemandeur();
        this.etat = dto.getEtat().getLibelle();
        this.nomBeneficiaire = dto.getNomBeneficiaire();
        this.prenomBeneficiaire = dto.getPrenomBeneficiaire();
        this.telephonePS = dto.getTelephonePS();
    }

    public HorizontalLayout getButtonLayout() {
        return buttonLayout;
    }

    public void setButtonLayout(HorizontalLayout buttonLayout) {
        this.buttonLayout = buttonLayout;
    }

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

    public String getTrigrammeDemandeur() {
        return trigrammeDemandeur;
    }

    public void setTrigrammeDemandeur(String trigrammeDemandeur) {
        this.trigrammeDemandeur = trigrammeDemandeur;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
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
}
