package santeclair.lunar.framework.web.auth;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlElement;


/**
 * Données métier liées au contrat
 * 
 * @author pchaussalet
 */
public class Contrat implements Serializable {

    private static final long serialVersionUID = 2907786185046243334L;

    /** Liste des bénéficiaires du contrat */
    private Set<Beneficiaire> beneficiaires;

    @XmlElement(name = "beneficiaire")
    public Set<Beneficiaire> getBeneficiaires() {
        return beneficiaires;
    }

    public void setBeneficiaires(Set<Beneficiaire> beneficiaires) {
        this.beneficiaires = beneficiaires;
    }

    public Beneficiaire addBeneficiaire(String nom, String prenom, String dateNaissance) {
        if (beneficiaires == null) {
            beneficiaires = new TreeSet<Beneficiaire>();
        }
        Beneficiaire beneficiaire = new Beneficiaire(nom, prenom, dateNaissance);
        if (!beneficiaires.add(beneficiaire)) {
            return null;
        }
        return beneficiaire;
    }

}
