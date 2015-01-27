package santeclair.lunar.framework.util;

import java.util.Collection;

/**
 * Classe de m�thodes facilitant l'utilisation de JPA.
 * 
 * @author jfourmond
 * 
 */
public class JpaUtils {

    /**
     * R�cup�re en base et charge en m�moire la collection lazy en param�tre.
     * 
     * @param une Collection charg�e en lazy loading par JPA.
     */
    public static void fetchLazyCollection(Collection<?> lazyCollection) {
        lazyCollection.size();
    }

}
