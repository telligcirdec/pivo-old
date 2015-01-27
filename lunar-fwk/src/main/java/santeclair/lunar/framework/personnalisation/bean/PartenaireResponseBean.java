package santeclair.lunar.framework.personnalisation.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PartenaireResponseBean {
	private String identifiant;
	
	private String libelle;

	public PartenaireResponseBean() {
		// Ne fait rien
	}
	
	public PartenaireResponseBean(String identifiant, String libelle) {
		this.identifiant = identifiant;
		this.libelle = libelle;
	}
	
	public String getIdentifiant() {
		return identifiant;
	}

	public String getLibelle() {
		return libelle;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PartenaireResponseBean [identifiant=");
		builder.append(identifiant);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append("]");
		return builder.toString();
	}
	
	
}
