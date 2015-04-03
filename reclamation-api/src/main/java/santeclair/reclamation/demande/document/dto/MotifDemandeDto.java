package santeclair.reclamation.demande.document.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import santeclair.reclamation.demande.document.enumeration.MotifDemandeEnum;

/**
 * DTO de motif de demande de document
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MotifDemandeDto {

    private List<MotifDemandeEnum> motifsDemande = new ArrayList<MotifDemandeEnum>(Arrays.asList(MotifDemandeEnum.values()));

    public List<MotifDemandeEnum> getMotifsDemande() {
        return motifsDemande;
    }

    public void setMotifsDemande(List<MotifDemandeEnum> motifsDemande) {
        this.motifsDemande = motifsDemande;
    }
}
