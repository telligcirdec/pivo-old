package santeclair.reclamation.demande.document.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

import santeclair.lunar.framework.enumeration.AbstractEnumTools;
import santeclair.lunar.framework.enumeration.CodeEnum;
import santeclair.lunar.framework.enumeration.LibelleEnum;

@XmlEnum
public enum BooleanEnum implements
                CodeEnum<BooleanEnum>,
                LibelleEnum<BooleanEnum> {

    @XmlEnumValue("true")
    OUI("true", "Oui"),
    @XmlEnumValue("false")
    NON("false", "Non");

    /**
     * Le code de l'état de la demande de document
     */
    private String code;

    /**
     * Le libellé de l'état de la demande de document
     */
    private String libelle;

    /* ======================================================= *
     *                      constructeur
     * ======================================================= */

    private BooleanEnum(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    /* ======================================================= *
     *                      getters & setters 
     * ======================================================= */

    /**
     * @return the code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return the libelle
     */
    @Override
    public String getLibelle() {
        return libelle;
    }

    /**
     * Retourne l'EtatDemandeEnum correspondant au code passé en paramètre
     * 
     * @param code
     * @return EtatDemandeEnum
     */
    public static BooleanEnum byCode(String code) {
        return AbstractEnumTools.findEnumValuesByCode(
                        BooleanEnum.class, code);
    }
}
