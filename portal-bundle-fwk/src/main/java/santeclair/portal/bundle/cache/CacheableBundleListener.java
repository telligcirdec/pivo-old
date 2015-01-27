package santeclair.portal.bundle.cache;

import java.util.Map;

public interface CacheableBundleListener {

    void setCacheableBundleService(CacheableBundleService cacheableBundleService, Map<?, ?> properties);

    void unsetCacheableBundleService(CacheableBundleService cacheableBundleService, Map<?, ?> properties);

}
