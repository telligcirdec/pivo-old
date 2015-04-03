package santeclair.reclamation.demande.document.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import santeclair.lunar.framework.enumeration.AbstractEnumTools;
import santeclair.lunar.framework.enumeration.CodeEnum;
import santeclair.lunar.framework.enumeration.LibelleEnum;

@XmlEnum
@XmlType(name = "motifDemandeEnum")
public enum MotifDemandeEnum implements
                CodeEnum<MotifDemandeEnum>,
                LibelleEnum<MotifDemandeEnum> {

    @XmlEnumValue("CPO1")
    CPO_CONTROLE("CPO1", "CPO - 1er contrôle"),
    @XmlEnumValue("CPOI")
    CPO_INDIC("CPOI", "CPO - indicateurs"),
    @XmlEnumValue("CPOOC")
    CPO_OCAM("CPOOC", "CPO - OCAM"),
    @XmlEnumValue("CPOOS")
    CPO_OSS("CPOOS", "CPO - OSS"),
    @XmlEnumValue("RECL")
    RECLAMATION("RECL", "Réclamation"),
    @XmlEnumValue("TP")
    TIERS_PAYANT("TP", "Tiers payant");

    /**
     * Le code du motif de la demande de document
     */
    private String code;

    /**
     * Le libellé du motif de la demande de document
     */
    private String libelle;

    /* ======================================================= *
     *                      constructeur
     * ======================================================= */

    private MotifDemandeEnum(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    /* ======================================================= *
     *                      getters & setters 
     * ======================================================= */

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the libelle
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Retourne le MotifDemandeEnum correspondant au code passé en paramètre
     * 
     * @param code
     * @return MotifDemandeEnum
     */
    public static MotifDemandeEnum byCode(String code) {
        return AbstractEnumTools.findEnumValuesByCode(
                        MotifDemandeEnum.class, code);
    }
}
