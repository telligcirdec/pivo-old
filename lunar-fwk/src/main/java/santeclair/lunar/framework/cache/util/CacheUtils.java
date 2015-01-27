package santeclair.lunar.framework.cache.util;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Statistics;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;

import santeclair.lunar.framework.cache.dto.AdminCache;

public class CacheUtils {
    /**
     * Initialisation d'un objet AdminCache à partir d'un objet {@link Cache}.
     * 
     * @param ehcache
     */
    public static AdminCache createAdminCache(Cache cache) {
        Ehcache ehcache = ((EhCacheCache) cache).getNativeCache();

        String name = ehcache.getName();
        Statistics cacheStatistics = ehcache.getStatistics();

        int maxElements = ehcache.getCacheConfiguration().getMaxElementsInMemory();
        long memoryStoreSize = ehcache.getMemoryStoreSize();

        int usePercentage = Math.round((float) memoryStoreSize / (float) maxElements * 100);

        // Calcul du hit rate
        long hitCount = cacheStatistics.getCacheHits();
        long totalHit = hitCount + cacheStatistics.getCacheMisses();
        int hitRate = totalHit == 0 ? 0 : Math.round((float) hitCount / (float) totalHit * 100);

        return new AdminCache(name, usePercentage, hitRate);
    }

    /**
     * Initialisation d'un objet AdminCache à partir d'un objet {@link Ehcache}.
     * 
     * @param ehcache
     */
    public static AdminCache createAdminCache(Ehcache ehcache) {
        String name = ehcache.getName();
        Statistics cacheStatistics = ehcache.getStatistics();

        int maxElements = ehcache.getCacheConfiguration().getMaxElementsInMemory();
        long memoryStoreSize = ehcache.getMemoryStoreSize();

        int usePercentage = Math.round((float) memoryStoreSize / (float) maxElements * 100);

        // Calcul du hit rate
        long hitCount = cacheStatistics.getCacheHits();
        long totalHit = hitCount + cacheStatistics.getCacheMisses();
        int hitRate = totalHit == 0 ? 0 : Math.round((float) hitCount / (float) totalHit * 100);

        return new AdminCache(name, usePercentage, hitRate);
    }

    /**
     * Réinitialisation d'une {@link Cache}
     * 
     * @param cache à réinitialiser
     * @return
     */
    public static AdminCache clearCache(Cache cache) {
        cache.clear();
        Ehcache ehcache = ((EhCacheCache) cache).getNativeCache();
        ehcache.getStatistics().clearStatistics();
        return createAdminCache(cache);
    }

    /**
     * Réinitialisation d'une {@link Ehcache}
     * 
     * @param ehcache à réinitialiser
     * @return
     */
    public static AdminCache clearCache(Ehcache ehcache) {
        ehcache.removeAll();
        ehcache.getStatistics().clearStatistics();
        return createAdminCache(ehcache);
    }
}
