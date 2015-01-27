/**
 *
 */
package santeclair.lunar.framework.web.auth;

import java.util.Date;

/**
 * Cette classe correspond à un assuré présent sur le contrat,
 * dans une réponse à une demande d'identification de code 01.
 * 
 * @author jfourmond
 * 
 */
public class AssureJeton {

    private String nom;
    private String prenom;
    private Date dateNaissance;

    /* ======================================================= *
     *                      constructeurs 
     * ======================================================= */

    /** Constructeur par défaut. */
    public AssureJeton() {
    }

    /**
     * Constructeur avec nom prénom et date de naissance.
     */
    public AssureJeton(String nom, String prenom, Date dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    /*=======================================================*
     *              	getters & setters
     *=======================================================*/

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @param dateNaissance the dateNaissance to set
     */
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @return the dateNaissance
     */
    public Date getDateNaissance() {
        return dateNaissance;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateNaissance == null) ? 0 : dateNaissance.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AssureJeton other = (AssureJeton) obj;
        if (dateNaissance == null) {
            if (other.dateNaissance != null)
                return false;
        } else if (!dateNaissance.equals(other.dateNaissance))
            return false;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        if (prenom == null) {
            if (other.prenom != null)
                return false;
        } else if (!prenom.equals(other.prenom))
            return false;
        return true;
    }

}
