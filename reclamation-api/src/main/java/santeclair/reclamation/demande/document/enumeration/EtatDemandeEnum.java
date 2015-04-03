package santeclair.reclamation.demande.document.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

import santeclair.lunar.framework.enumeration.AbstractEnumTools;
import santeclair.lunar.framework.enumeration.CodeEnum;
import santeclair.lunar.framework.enumeration.LibelleEnum;

@XmlEnum
public enum EtatDemandeEnum implements
                CodeEnum<EtatDemandeEnum>,
                LibelleEnum<EtatDemandeEnum> {

    @XmlEnumValue("ATTENTE")
    ATTENTE_RECEPTION("ATTENTE", "En attente de réception"),
    @XmlEnumValue("PARTIELLE")
    RECEPTION_PARTIELLE("PARTIELLE", "Réception partielle"),
    @XmlEnumValue("COMPLETE")
    RECEPTION_COMPLETE("COMPLETE", "Réception complète"),
    @XmlEnumValue("ANALYSE")
    DOSSIER_ANALYSE("ANALYSE", "Dossier analysé");

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

    private EtatDemandeEnum(String code, String libelle) {
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
     * Retourne l'EtatDemandeEnum correspondant au code passé en paramètre
     * 
     * @param code
     * @return EtatDemandeEnum
     */
    public static EtatDemandeEnum byCode(String code) {
        return AbstractEnumTools.findEnumValuesByCode(
                        EtatDemandeEnum.class, code);
    }
}
