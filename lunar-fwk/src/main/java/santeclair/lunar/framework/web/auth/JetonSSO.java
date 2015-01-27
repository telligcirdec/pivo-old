package santeclair.lunar.framework.web.auth;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * <pre>
 * Client : Sant�clair
 * Projet : Espace S�curis�
 * </pre>
 * 
 * Objet m�tier correspondant au dataMetier
 */
@XmlRootElement(name = "datametier")
@XmlAccessorType(XmlAccessType.FIELD)
public class JetonSSO implements Serializable {

    private static final long serialVersionUID = -4333894911791994246L;
    
    @XmlTransient
    private CodeRetour codeRetour;
    
    @XmlElement(name = "codeagence")
    private String codeAgence;

    @XmlElement(name = "numerocontrat")
    private String numeroContrat;

    @XmlElement(name = "nomassure")
    private String nomAssure;

    @XmlElement(name = "prenomassure")
    private String prenomAssure;

    @XmlElement(name = "datenaissanceassure")
    private String dateNaissanceAssure;

    @XmlElement(name = "codeinsee")
    private String codeInsee;

    @XmlElement(name = "codepostal")
    private String codePostal;

    private String commune;

    private Contrat contrat;
    
    @XmlTransient
    private String aliasAssureur;

    private boolean anonyme;

    public JetonSSO() {
        contrat = new Contrat();
    }

    /**
     * @return codeAgence
     */
    public String getCodeAgence() {
        return codeAgence;
    }

    /**
     * @param codeAgence codeAgence � d�finir
     */
    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    /**
     * @return codeInsee
     */
    public String getCodeInsee() {
        return codeInsee;
    }

    /**
     * @param codeInsee codeInsee � d�finir
     */
    public void setCodeInsee(String codeInsee) {
        this.codeInsee = codeInsee;
    }

    /**
     * @return codePostal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * @param codePostal codePostal � d�finir
     */
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * @return codeRetour
     */
    public CodeRetour getCodeRetour() {
        return codeRetour;
    }

    /**
     * @param codeRetour codeRetour � d�finir
     */
    public void setCodeRetour(CodeRetour codeRetour) {
        this.codeRetour = codeRetour;
    }

    /**
     * @return commune
     */
    public String getCommune() {
        return commune;
    }

    /**
     * @param commune commune � d�finir
     */
    public void setCommune(String commune) {
        this.commune = commune;
    }

    /**
     * @return dateNaissanceAssure
     */
    public String getDateNaissanceAssure() {
        return dateNaissanceAssure;
    }

    /**
     * @param dateNaissanceAssure dateNaissanceAssure � d�finir
     */
    public void setDateNaissanceAssure(String dateNaissanceAssure) {
        this.dateNaissanceAssure = dateNaissanceAssure;
    }

    /**
     * @return nomAssure
     */
    public String getNomAssure() {
        return nomAssure;
    }

    /**
     * @param nomAssure nomAssure � d�finir
     */
    public void setNomAssure(String nomAssure) {
        this.nomAssure = nomAssure;
    }

    /**
     * @return numeroContrat
     */
    public String getNumeroContrat() {
        return numeroContrat;
    }

    /**
     * @param numeroContrat numeroContrat � d�finir
     */
    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    /**
     * @return prenomAssure
     */

    public String getPrenomAssure() {
        return prenomAssure;
    }

    /**
     * @param prenomAssure prenomAssure � d�finir
     */
    public void setPrenomAssure(String prenomAssure) {
        this.prenomAssure = prenomAssure;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public String getAliasAssureur() {
        return aliasAssureur;
    }

    public void setAliasAssureur(String aliasAssureur) {
        this.aliasAssureur = aliasAssureur;
    }

    public boolean isAnonyme() {
        return anonyme;
    }

    public void setAnonyme(boolean anonyme) {
        this.anonyme = anonyme;
    }
    
    public boolean isValide(){
    	return CodeRetour._00.equals(this.codeRetour);
    }
    
}
