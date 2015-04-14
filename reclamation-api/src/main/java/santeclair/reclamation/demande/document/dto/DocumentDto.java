package santeclair.reclamation.demande.document.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import santeclair.reclamation.demande.document.enumeration.DetailResultatAnalyseEnum;
import santeclair.reclamation.demande.document.enumeration.ResultatAnalyseEnum;
import santeclair.reclamation.demande.document.enumeration.TypeDocumentEnum;

/**
 * DTO de document
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentDto implements Comparable<DocumentDto> {

	private Integer id;
    private TypeDocumentEnum typeDocument;
    private Boolean documentRecu;
    private Boolean originalDemande;
    private Date dateReception;
    private ResultatAnalyseEnum resultatAnalyse;
    private DetailResultatAnalyseEnum detailResultatAnalyse;
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public TypeDocumentEnum getTypeDocument() {
		return typeDocument;
	}
	
	public void setTypeDocument(TypeDocumentEnum typeDocument) {
		this.typeDocument = typeDocument;
	}
	
	public Boolean getDocumentRecu() {
		return documentRecu;
	}
	
	public void setDocumentRecu(Boolean documentRecu) {
		this.documentRecu = documentRecu;
	}
	
	public Boolean getOriginalDemande() {
		return originalDemande;
	}
	
	public void setOriginalDemande(Boolean originalDemande) {
		this.originalDemande = originalDemande;
	}
	
	public Date getDateReception() {
		return dateReception;
	}
	
	public void setDateReception(Date dateReception) {
		this.dateReception = dateReception;
	}
	
	public ResultatAnalyseEnum getResultatAnalyse() {
		return resultatAnalyse;
	}
	
	public void setResultatAnalyse(ResultatAnalyseEnum resultatAnalyse) {
		this.resultatAnalyse = resultatAnalyse;
	}
	
	public DetailResultatAnalyseEnum getDetailResultatAnalyse() {
		return detailResultatAnalyse;
	}
	
	public void setDetailResultatAnalyse(
			DetailResultatAnalyseEnum detailResultatAnalyse) {
		this.detailResultatAnalyse = detailResultatAnalyse;
	}

    @Override
    public int compareTo(DocumentDto documentDto) {
        return this.getTypeDocument().getOrdre().compareTo(documentDto.getTypeDocument().getOrdre());
    }
}
