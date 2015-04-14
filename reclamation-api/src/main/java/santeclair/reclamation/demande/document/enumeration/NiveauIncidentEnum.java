package santeclair.reclamation.demande.document.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

import santeclair.lunar.framework.enumeration.AbstractEnumTools;
import santeclair.lunar.framework.enumeration.CodeEnum;

@XmlEnum
public enum NiveauIncidentEnum implements
                CodeEnum<NiveauIncidentEnum> {

    @XmlEnumValue("0")
    ZERO("0"),
    @XmlEnumValue("1")
    UN("1"),
    @XmlEnumValue("2")
    DEUX("2"),
    @XmlEnumValue("3")
    TROIS("3"),
    @XmlEnumValue("4")
    QUATRE("4"),
    @XmlEnumValue("5")
    CINQ("5");

    /**
     * Le code du niveau d'incident
     */
    private final String code;

    /* ======================================================= *
     *                      constructeurs 
     * ======================================================= */

    private NiveauIncidentEnum(final String code) {
        this.code = code;
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
     * Retourne le NiveauIncidentEnum correspondant au code passé en paramètre
     * 
     * @param code
     * @return NiveauIncidentEnum
     */
    public static NiveauIncidentEnum byCode(String code) {
        return AbstractEnumTools.findEnumValuesByCode(
                        NiveauIncidentEnum.class, code);
    }
}
