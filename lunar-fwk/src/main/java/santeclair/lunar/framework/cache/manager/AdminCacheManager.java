package santeclair.lunar.framework.cache.manager;

import java.util.Collection;

import santeclair.lunar.framework.cache.dto.AdminCache;

/**
 * Interface d'administration des caches.
 * 
 * @author ldelemotte
 * 
 */
public interface AdminCacheManager {
    /**
     * Initialise le manager du cache.
     */
    void init();

    /**
     * Retourne la cache associé au nom passé en paramètre.
     * 
     * @param name identifiant du cache (ne doit pas être null)
     * @return cache associé, ou null si aucune cache n'est trouvée.
     */
    AdminCache getCache(String name);

    /**
     * Retourne une collection de cache connu par ce manager de cache.
     * 
     * @return noms des caches connu par ce manager de cache.
     */
    Collection<String> getCacheNames();

    /**
     * Réinitialise le cache associé au nom passé en paramètre.
     * 
     * @param name identifiant du cache (ne doit pas être null)
     */
    AdminCache clear(String name);

    /**
     * Réinitialisation de tous les caches.
     */
    void clearAll();
}
