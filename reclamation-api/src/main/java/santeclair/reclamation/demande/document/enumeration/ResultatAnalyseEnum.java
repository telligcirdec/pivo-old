package santeclair.reclamation.demande.document.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

import santeclair.lunar.framework.enumeration.AbstractEnumTools;
import santeclair.lunar.framework.enumeration.CodeEnum;
import santeclair.lunar.framework.enumeration.LibelleEnum;

@XmlEnum
public enum ResultatAnalyseEnum implements
                CodeEnum<ResultatAnalyseEnum>,
                LibelleEnum<ResultatAnalyseEnum> {

    @XmlEnumValue("CONF")
    CONFORME("CONF", "Conforme"),
    @XmlEnumValue("NO_CONF")
    NON_CONFORME("NO_CONF", "Non conforme"),
    @XmlEnumValue("ABAND")
    ABANDON("ABAND", "Abandon");

    /**
     * Le code du résultat d'analyse
     */
    private String code;

    /**
     * Le libellé du résultat d'analyse
     */
    private String libelle;

    /* ======================================================= *
     *                      constructeur 		   		   	   *
     * ======================================================= */

    private ResultatAnalyseEnum(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    /* ======================================================= *
     *                      getters & setters 				   *
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
     * Retourne le ResultatAnalyseEnum correspondant au code passé en paramètre
     * 
     * @param code
     * @return ResultatAnalyseEnum
     */
    public static ResultatAnalyseEnum byCode(String code) {
        return AbstractEnumTools.findEnumValuesByCode(
                        ResultatAnalyseEnum.class, code);
    }
}
