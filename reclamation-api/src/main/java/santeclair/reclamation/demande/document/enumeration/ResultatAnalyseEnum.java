package santeclair.reclamation.demande.document.enumeration;

import java.util.Arrays;
import java.util.List;

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
    ABANDON("ABAND", "Abandon",
                    DetailResultatAnalyseEnum.VENTE_NON_FINALISEE,
                    DetailResultatAnalyseEnum.ABANDON_SANTECLAIR,
                    DetailResultatAnalyseEnum.PS_RESILIE,
                    DetailResultatAnalyseEnum.DOCS_NON_ENVOYES);

    /**
     * Le code du résultat d'analyse
     */
    private final String code;

    /**
     * Le libellé du résultat d'analyse
     */
    private final String libelle;

    /**
     * Liste des DetailResultatAnalyseEnum dans le cas d'un résultat d'analyse "Abandon"
     */
    private final List<DetailResultatAnalyseEnum> detailsResultatAnalyseEnum;

    /* ======================================================= *
     *                      constructeur 		   		   	   *
     * ======================================================= */

    private ResultatAnalyseEnum(final String code, final String libelle, final DetailResultatAnalyseEnum... detailsResultatAnalyseEnum) {
        this.code = code;
        this.libelle = libelle;
        this.detailsResultatAnalyseEnum = Arrays.asList(detailsResultatAnalyseEnum);
    }

    /* ======================================================= *
     *                      getters & setters 				   *
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
     * @return the detailsResultatAnalyseEnum
     */
    public List<DetailResultatAnalyseEnum> getDetailsResultatAnalyseEnum() {
        return detailsResultatAnalyseEnum;
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
