package santeclair.lunar.framework.personnalisation.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CharteBean {
    /** code de la charte */
    private String code;

    /** libelle de la charte */
    private String libelle;

    /**
     * Méthode de lecture de l'attribut : {@link CharteBean#code}
     * 
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Méthode d'écriture de l'attribut : {@link CharteBean#code}
     * 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Méthode de lecture de l'attribut : {@link CharteBean#libelle}
     * 
     * @return libelle
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Méthode d'écriture de l'attribut : {@link CharteBean#libelle}
     * 
     * @param libelle
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
