package santeclair.portal.bundle.cache;

import java.util.Map;

import santeclair.lunar.framework.cache.manager.AdminCacheManager;

public interface CacheableBundleService {

    void registerCacheManager(CacheableBundle cacheableBundle);

    void unregisterCacheManager(CacheableBundle cacheableBundle);

    void addListener(CacheableBundleServiceListener listener);

    void removeListener(CacheableBundleServiceListener listener);

    Map<String, AdminCacheManager> getCacheManagers();

}
