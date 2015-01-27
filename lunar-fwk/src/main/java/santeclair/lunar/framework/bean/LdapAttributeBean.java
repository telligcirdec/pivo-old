/**
 * 
 */
package santeclair.lunar.framework.bean;

/**
 * Cette classe sert à stocker un attribut LDAP et sa valeur.
 * 
 * @author jfourmond
 * 
 */
public class LdapAttributeBean {

    private String nom;
    private Object valeur;

    /**
     * Constructeur avec tous les arguments.
     */
    public LdapAttributeBean(String nom, Object valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    /* ======================================================= *
     *                      getters & setters 
     * ======================================================= */

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the valeur
     */
    public Object getValeur() {
        return valeur;
    }

}
