package santeclair.lunar.framework.util;

import java.util.Collection;

/**
 * Classe de méthodes facilitant l'utilisation de JPA.
 * 
 * @author jfourmond
 * 
 */
public class JpaUtils {

    /**
     * Récupère en base et charge en mémoire la collection lazy en paramètre.
     * 
     * @param une Collection chargée en lazy loading par JPA.
     */
    public static void fetchLazyCollection(Collection<?> lazyCollection) {
        lazyCollection.size();
    }

}
