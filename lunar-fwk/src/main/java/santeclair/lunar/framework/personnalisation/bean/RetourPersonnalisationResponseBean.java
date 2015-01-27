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
public class RetourPersonnalisationResponseBean {
	
	private String idCharte;
	
	@XmlElementWrapper(name = "partenaires")
	@XmlElement(name = "partenaire")
	private List<PartenaireResponseBean> partenairesResponseBean;

	@XmlElementWrapper(name = "element-personnalisations")
    @XmlElement(name = "element-personnalisation")
	private List<ElementPersonnalisationResponseBean> elementPersonnalisationResponseBeans;
	
	public RetourPersonnalisationResponseBean() {
		this.elementPersonnalisationResponseBeans = new ArrayList<ElementPersonnalisationResponseBean>();
		this.partenairesResponseBean = new ArrayList<PartenaireResponseBean>();
	}
	
	public List<ElementPersonnalisationResponseBean> getElementPersonnalisationResponseBeans() {
		return elementPersonnalisationResponseBeans;
	}
	
	public List<PartenaireResponseBean> getPartenaires() {
		return partenairesResponseBean;
	}

	public void setPartenaires(List<PartenaireResponseBean> partenairesResponseBean) {
		this.partenairesResponseBean = partenairesResponseBean;
	}

	public String getIdCharte() {
		return idCharte;
	}

	public void setIdCharte(String idCharte) {
		this.idCharte = idCharte;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("RetourPersonnalisationResponseBean [idCharte=");
		builder.append(idCharte);
		builder.append(", partenaires=");
		builder.append(partenairesResponseBean != null ? partenairesResponseBean.subList(0,
				Math.min(partenairesResponseBean.size(), maxLen)) : null);
		builder.append(", elementPersonnalisationResponseBeans=");
		builder.append(elementPersonnalisationResponseBeans != null ? elementPersonnalisationResponseBeans
				.subList(0, Math.min(
						elementPersonnalisationResponseBeans.size(), maxLen))
				: null);
		builder.append("]");
		return builder.toString();
	}

	
}
