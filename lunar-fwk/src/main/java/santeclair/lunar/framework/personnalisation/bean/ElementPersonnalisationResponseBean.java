package santeclair.lunar.framework.personnalisation.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ElementPersonnalisationResponseBean {

	private String code;
	private String value;
	private String type;
	private String description;
	
	public ElementPersonnalisationResponseBean() {
		// Ne fait rien
	}
 
	public ElementPersonnalisationResponseBean(String code, String value, String type, String description) {
		this.code = code;
		this.value = value;
		this.type = type;
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
	
	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ElementPersonnalisationResponseBean [code=");
		builder.append(code);
		builder.append(", value=");
		builder.append(value);
		builder.append(", type=");
		builder.append(type);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

}
