package santeclair.lunar.framework.web.auth;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "codeRetour")
@XmlEnum
public enum CodeRetour {

	@XmlEnumValue("00")
	_00("00"),
	@XmlEnumValue("01")
	_01("01"),
	@XmlEnumValue("02")
	_02("02"),
	@XmlEnumValue("03")
	_03("03");
	
	private final String value;
	
	private CodeRetour(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	
}
