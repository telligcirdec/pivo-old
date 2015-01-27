package santeclair.portal.bundle;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;

/**
 * Classe abstraite � impl�menter pour la gestion des properties dans les modules
 * @author tsensebe
 *
 */
public abstract class AbstractPropertiesProvider {

	protected Configuration configuration;
	
	/**
	 * Initialise la configuration.
	 */
	public abstract void init();
	
	/**
	 * Recup�re une propri�t�.
	 * @param propertyId identifiant de la propri�t�
	 * @return valeur de la propri�t�
	 */
	public String getProperties(String propertyId) {
		if (configuration != null) {
			Dictionary<String, Object> dictionary = configuration.getProperties();
			if (dictionary != null) {
				return (String) dictionary.get(propertyId);
			}
		}
		return null;
	}

}
