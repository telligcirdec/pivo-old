package santeclair.reclamation.demande.document.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

import santeclair.lunar.framework.enumeration.AbstractEnumTools;
import santeclair.lunar.framework.enumeration.CodeEnum;
import santeclair.lunar.framework.enumeration.LibelleEnum;

@XmlEnum
public enum DetailResultatAnalyseEnum implements CodeEnum<DetailResultatAnalyseEnum>,
                LibelleEnum<DetailResultatAnalyseEnum> {

    @XmlEnumValue("DOC_FALS")
    DOCUMENT_FALSIFIE("DOC_FALS", "Document falsifi�"),
    @XmlEnumValue("PUISS_NC")
    PUISSANCE_NON_CONFORME("PUISS_NC", "Puissance non conforme"),
    @XmlEnumValue("DATE_NC")
    DATE_NON_CONFORME("DATE_NC", "Date non conforme"),
    @XmlEnumValue("DOC_NR")
    DOCUMENT_NON_REGLEMENTAIRE("DOC_NR", "Document non r�glementaire"),
    @XmlEnumValue("RAC_NC")
    RAC_NON_CONFORME("RAC_NC", "RAC non conforme"),
    @XmlEnumValue("DOC_AUTRE_PS")
    DOCUMENT_AUTRE_PS("DOC_AUTRE_PS", "Document d'un autre PS"),
    @XmlEnumValue("PROD_NC")
    PRODUIT_NON_CONFORME("PROD_NC", "Produit non conforme"),
    @XmlEnumValue("DATE_ANTPS")
    DATE_ANTERIEUR_TP("DATE_ANTPS", "Date ant�rieure au tiers payant"),
    @XmlEnumValue("VERR_NC")
    VERRIER_NON_CONFORME("VERR_NC", "Verrier non conforme"),
    @XmlEnumValue("QTE_NC")
    QUANTITE_NON_CONFORME("QTE_NC", "Quantit� non conforme"),
    @XmlEnumValue("DATE_PDEM")
    DATE_POST_EGAL_DEMANDE("DATE_PDEM", "Date post�rieure ou �gale � la demande"),
    @XmlEnumValue("ABS_SIGN")
    ABSENCE_SIGNATURE("ABS_SIGN", "Absence de signature"),
    @XmlEnumValue("FAUS_SIGN")
    FAUSSE_SIGNATURE("FAUS_SIGN", "Fausse signature"),
    @XmlEnumValue("VENTE_NF")
    VENTE_NON_FINALISEE("VENTE_NF", "Vente non finalis�e"),
    @XmlEnumValue("ABAND_SC")
    ABANDON_SANTECLAIR("ABAND_SC", "Abandon Sant�clair"),
    @XmlEnumValue("PS_RESIL")
    PS_RESILIE("PS_RESIL", "PS r�sili�"),
    @XmlEnumValue("DOC_NE")
    DOCS_NON_ENVOYES("DOCS_NE", "Documents non envoy�s ");

    /**
     * Le code du r�sultat d'analyse
     */
    private String code;

    /**
     * Le libell� du r�sultat d'analyse
     */
    private String libelle;

    /*
     * ======================================================= * constructeur *
     * =======================================================
     */

    private DetailResultatAnalyseEnum(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    /*
     * ======================================================= * getters &
     * setters * =======================================================
     */

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
     * Retourne la DetailResultatAnalyseEnum correspondant au code pass� en
     * param�tre
     * 
     * @param code
     * @return DetailResultatAnalyseEnum
     */
    public static DetailResultatAnalyseEnum byCode(String code) {
        return AbstractEnumTools.findEnumValuesByCode(
                        DetailResultatAnalyseEnum.class, code);
    }
}
