/**
 * 
 */
package santeclair.lunar.framework.web.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Extension de l'objet User de Spring Security.
 * 
 * @author jfourmond
 * 
 */
public class UtilisateurSanteclair extends User {

    private static final long serialVersionUID = 1L;

    /**
     * L'identifiant technique en provenance de Solar.t_utilisateur.
     */
    private String identifiant;

    /**
     * Le trigrmme de l'utilisateur Santéclair.
     */
    private String trigramme;

    private String nom;
    private String prenom;

    /* ======================================================= *
     *                      constructeurs 
     * ======================================================= */

    /**
     * Constructeur avec tous les arguments.
     */
    public UtilisateurSanteclair(String identifiant, String userName, String trigramme, String nom, String prenom,
                                 Boolean enabled, List<GrantedAuthority> authorities) {
        super(userName, "", enabled, true, true, true, authorities);
        this.identifiant = identifiant;
        this.trigramme = trigramme;
        this.nom = nom;
        this.prenom = prenom;
    }

    /* ======================================================= *
     *                      code métier 
     * ======================================================= */

    /**
     * Renvoie la concaténation du prénom, du nom et du trigramme.
     * 
     * @return "prenom nom (trigramme).
     */
    public String getPrenomNomTrigramme() {
        return prenom + " " + nom + " (" + trigramme + ")";
    }

    /* ======================================================= *
     *                      getters & setters 
     * ======================================================= */

    /**
     * @return the trigramme
     */
    public String getTrigramme() {
        return trigramme;
    }

    /**
     * @return the identifiant
     */
    public String getIdentifiant() {
        return identifiant;
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

}
