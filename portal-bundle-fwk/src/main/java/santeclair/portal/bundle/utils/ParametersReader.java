package santeclair.portal.bundle.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Parameters readers from vaadin uri "param1/X/param2/Y...."
 * @author tsensebe
 *
 */
public class ParametersReader {

	/**
	 * Extrait les parametres d'une URI "param1/X/param2/Y...."
	 * @param uri l'uri
	 * @return une map des parametres
	 */
	public static Map<String,String> getParameters(String uri) {
		Map<String, String> parameters = new HashMap<String,String>();
		String[] strings = uri.split("/");
		Integer i = 0;
		while(i<strings.length) {
			String key = strings[i];
			String value = (i+1<strings.length ? strings[i+1] : null);
			parameters.put(key, value);
			i+=2;
		}
		return parameters;
	}
	
	/** Constructeur par defaut. */
	private ParametersReader() {
	}

}
