package santeclair.lunar.framework.personnalisation.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ListePersonnalisationResponseBean {
	
	private String idApplication;

	@XmlElementWrapper(name = "identifiants-personnalisation")
	@XmlElement(name = "identifiant-personnalisation")
	private List<RetourPersonnalisationResponseBean> listePersonnalisation;

	public ListePersonnalisationResponseBean() {
		this.listePersonnalisation = new ArrayList<RetourPersonnalisationResponseBean>();
	}
	
	public List<RetourPersonnalisationResponseBean> getListePersonnalisation() {
		return listePersonnalisation;
	}

	public String getIdApplication() {
		return idApplication;
	}

	public void setIdApplication(String idApplication) {
		this.idApplication = idApplication;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("ListePersonnalisationResponseBean [idApplication=");
		builder.append(idApplication);
		builder.append(", listePersonnalisation=");
		builder.append(listePersonnalisation != null ? listePersonnalisation
				.subList(0, Math.min(listePersonnalisation.size(), maxLen))
				: null);
		builder.append("]");
		return builder.toString();
	}	
}
