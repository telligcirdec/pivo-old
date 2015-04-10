package santeclair.reclamation.demande.document.enumeration;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import santeclair.lunar.framework.enumeration.AbstractEnumTools;
import santeclair.lunar.framework.enumeration.CodeEnum;
import santeclair.lunar.framework.enumeration.LibelleEnum;

@XmlEnum
@XmlType(name = "typeDocumentEnum")
public enum TypeDocumentEnum implements
                CodeEnum<TypeDocumentEnum>,
                LibelleEnum<TypeDocumentEnum> {

    @XmlEnumValue("ORDO")
    ORDONNANCE("ORDO", "Ordonnance",
                    DetailResultatAnalyseEnum.DOCUMENT_FALSIFIE,
                    DetailResultatAnalyseEnum.PUISSANCE_NON_CONFORME,
                    DetailResultatAnalyseEnum.DATE_NON_CONFORME,
                    DetailResultatAnalyseEnum.DOCUMENT_NON_REGLEMENTAIRE),
    @XmlEnumValue("BON_LIVR")
    BON_LIVRAISON("BON_LIVR", "Bon de livraison",
                    DetailResultatAnalyseEnum.DOCUMENT_AUTRE_PS,
                    DetailResultatAnalyseEnum.DOCUMENT_FALSIFIE,
                    DetailResultatAnalyseEnum.DATE_POST_EGAL_DEMANDE,
                    DetailResultatAnalyseEnum.PRODUIT_NON_CONFORME,
                    DetailResultatAnalyseEnum.PUISSANCE_NON_CONFORME,
                    DetailResultatAnalyseEnum.QUANTITE_NON_CONFORME,
                    DetailResultatAnalyseEnum.VERRIER_NON_CONFORME),
    @XmlEnumValue("MAGASIN")
    FACTURE_MAGASIN("MAGASIN", "Facture magasin",
                    DetailResultatAnalyseEnum.RAC_NON_CONFORME,
                    DetailResultatAnalyseEnum.DOCUMENT_AUTRE_PS,
                    DetailResultatAnalyseEnum.PRODUIT_NON_CONFORME,
                    DetailResultatAnalyseEnum.DOCUMENT_NON_REGLEMENTAIRE,
                    DetailResultatAnalyseEnum.DOCUMENT_FALSIFIE,
                    DetailResultatAnalyseEnum.DATE_ANTERIEUR_TP,
                    DetailResultatAnalyseEnum.VERRIER_NON_CONFORME,
                    DetailResultatAnalyseEnum.QUANTITE_NON_CONFORME),
    @XmlEnumValue("TIERS_P")
    DEMANDE_TIERS_PAYANT("TIERS_P", "Demande de tiers payant",
                    DetailResultatAnalyseEnum.ABSENCE_SIGNATURE,
                    DetailResultatAnalyseEnum.FAUSSE_SIGNATURE),
    @XmlEnumValue("SUBRO")
    FACTURE_SUBROGATOIRE("SUBRO", "Facture subrogatoire",
                    DetailResultatAnalyseEnum.ABSENCE_SIGNATURE,
                    DetailResultatAnalyseEnum.FAUSSE_SIGNATURE);

    /**
     * Le code du type de document
     */
    private String code;

    /**
     * Le libellé du type de document
     */
    private String libelle;

    /**
     * Liste des DetailResultatAnalyseEnum dans le cas d'un résultat d'analyse "Non Conforme"
     */
    private List<DetailResultatAnalyseEnum> detailsResultatAnalyseEnum;

    /* ======================================================= *
     *                      constructeurs  		   		   	   *
     * ======================================================= */

    private TypeDocumentEnum(String code, String libelle, DetailResultatAnalyseEnum... detailsResultatAnalyseEnum) {
        this.code = code;
        this.libelle = libelle;
        this.detailsResultatAnalyseEnum = Arrays.asList(detailsResultatAnalyseEnum);
    }

    /* ======================================================= *
     *                      getters & setters  		   		   *
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
     * @return the detailsResultatAnalyseEnum
     */
    public List<DetailResultatAnalyseEnum> getDetailsResultatAnalyseEnum() {
        return detailsResultatAnalyseEnum;
    }

    /**
     * Retourne le TypeDocumentEnum correspondant au code passé en paramètre
     * 
     * @param code
     * @return TypeDocumentEnum
     */
    public static TypeDocumentEnum byCode(String code) {
        return AbstractEnumTools.findEnumValuesByCode(
                        TypeDocumentEnum.class, code);
    }
}
