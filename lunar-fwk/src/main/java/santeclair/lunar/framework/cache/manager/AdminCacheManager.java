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
     * Retourne la cache associ� au nom pass� en param�tre.
     * 
     * @param name identifiant du cache (ne doit pas �tre null)
     * @return cache associ�, ou null si aucune cache n'est trouv�e.
     */
    AdminCache getCache(String name);

    /**
     * Retourne une collection de cache connu par ce manager de cache.
     * 
     * @return noms des caches connu par ce manager de cache.
     */
    Collection<String> getCacheNames();

    /**
     * R�initialise le cache associ� au nom pass� en param�tre.
     * 
     * @param name identifiant du cache (ne doit pas �tre null)
     */
    AdminCache clear(String name);

    /**
     * R�initialisation de tous les caches.
     */
    void clearAll();
}
