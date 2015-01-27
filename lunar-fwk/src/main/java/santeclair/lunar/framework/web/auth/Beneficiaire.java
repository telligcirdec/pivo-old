package santeclair.lunar.framework.web.auth;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Données métier liées au bénéficiaire du contrat
 * 
 * @author pchaussalet
 */
public class Beneficiaire implements Serializable, Comparable<Beneficiaire> {

    private static final Logger LOG = LoggerFactory.getLogger(Beneficiaire.class);
    private static final long serialVersionUID = 3022580330163273959L;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private GarantieHospi garantieHospi;

    public Beneficiaire() {
    }

    public Beneficiaire(String nom, String prenom, String dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        garantieHospi = new GarantieHospi();
    }

    @XmlElement(name = "datenaissancebeneficiaire")
    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @XmlElement(name = "garantiehospi")
    public GarantieHospi getGarantieHospi() {
        return garantieHospi;
    }

    public void setGarantieHospi(GarantieHospi garantieHospi) {
        this.garantieHospi = garantieHospi;
    }

    @XmlElement(name = "nombeneficiaire")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @XmlElement(name = "prenombeneficiaire")
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public int hashCode() {
        StringBuilder chaineHashCode = new StringBuilder();
        chaineHashCode.append(Beneficiaire.class.getName());
        chaineHashCode.append(nom);
        chaineHashCode.append(prenom);
        chaineHashCode.append(dateNaissance);
        return chaineHashCode.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Beneficiaire) {
            Beneficiaire beneficiaire = (Beneficiaire) obj;
            return compareTo(beneficiaire) == 0;
        }
        return false;
    }

    public int compareTo(Beneficiaire o) {

        if (dateNaissance != null && o.dateNaissance != null) {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date1 = formatter.parse(dateNaissance);
                Date date2 = formatter.parse(o.dateNaissance);
                // attention le datetime ne marche pas car les date inférieurs au 01/01/1970 donne des valeurs négatives ce qui fausse la
                // comparaison.
                if (date1.before(date2)) {
                    return -1;
                } else if (date1.after(date2)) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (ParseException e) {
                LOG.error("L'une des date " + dateNaissance + " ou " + o.dateNaissance + " n'est pas valide!", e.getMessage());
            }
        }

        if (nom != null && o.nom != null) {
            int resultatNom = nom.compareToIgnoreCase(o.nom);
            if (resultatNom != 0) {
                return resultatNom;
            }
        }

        if (prenom != null && o.prenom != null) {
            int resultatPrenom = prenom.compareToIgnoreCase(o.prenom);
            if (resultatPrenom != 0) {
                return resultatPrenom;
            }
        }

        return 0;
    }
}
