package santeclair.portal.bundle;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;

/**
 * Classe abstraite à implémenter pour la gestion des properties dans les modules
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
	 * Recupère une propriété.
	 * @param propertyId identifiant de la propriété
	 * @return valeur de la propriété
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
