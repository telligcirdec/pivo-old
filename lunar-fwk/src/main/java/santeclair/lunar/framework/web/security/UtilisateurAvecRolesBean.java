/**
 * 
 */
package santeclair.lunar.framework.web.security;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Cet objet correspond � la r�ponse au web service de solaradmin permettant de r�cup�rer
 * un utilisateur solar avec ses r�les pour une application donn�e.
 * 
 * @author jfourmond
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UtilisateurAvecRolesBean {

    /**
     * L'identifiant de l'utilisateur dans solar (t_utilisateur.idutilisateur).
     * Ex : "17EC4D4BZA8E068FC12A76BA0957A4EB".
     */
    String identifiant;

    /**
     * Le login de l'utilisateur dans solar (t_utilisateur.accountnt).
     */
    String userName;

    /**
     * Les 3 lettres repr�sentant l'utilisateur. (t_utilisateur.trigramme).
     */
    String trigramme;

    String nom;
    String prenom;

    /**
     * Indique si l'utilisateur est archiv�.
     */
    Boolean archive;

    /**
     * La liste des libell�s des r�les pour l'application demand�e, tels que r�cup�r�s dans la bdd solar.
     */
    @XmlElementWrapper
    @XmlElements({
            @XmlElement(name = "role")})
    List<String> roles;

    /*
     * =======================================================*
     * constructeurs
     * =======================================================
     */

    /** Constructeur par d�faut. */
    public UtilisateurAvecRolesBean() {
        this.roles = new ArrayList<String>();
    }

    /*
     * =======================================================*
     * getters & setters
     * =======================================================
     */

    /**
     * @return the roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the trigramme
     */
    public String getTrigramme() {
        return trigramme;
    }

    /**
     * @return the archive
     */
    public Boolean getArchive() {
        return archive;
    }

    /**
     * @return the identifiant
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * @param identifiant the identifiant to set
     */
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @param trigramme the trigramme to set
     */
    public void setTrigramme(String trigramme) {
        this.trigramme = trigramme;
    }

    /**
     * @param archive the archive to set
     */
    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

}
