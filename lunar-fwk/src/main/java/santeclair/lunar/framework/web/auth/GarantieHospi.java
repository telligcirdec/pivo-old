package santeclair.lunar.framework.web.auth;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

/**
 * Données métier liées à la garantie hospitalière
 * 
 * @author pchaussalet
 */
public class GarantieHospi implements Serializable {
    private static final long serialVersionUID = -717796763684548132L;
    private String nom;
    private String code;
    private Details details;
    private Rac rac;

    public GarantieHospi() {
        this.details = new Details();
        this.rac = new Rac();
    }

    @XmlElement(name = "codegarantie")
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name = "detailgarantie")
    public Details getDetails() {
        return this.details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    @XmlElement(name = "nomgarantie")
    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Rac getRac() {
        return this.rac;
    }

    public void setRac(Rac rac) {
        this.rac = rac;
    }
}
