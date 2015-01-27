package santeclair.portal.bundle.cache;

public interface CacheableBundleServiceListener {

    void cacheableBundleRegistered(CacheableBundle cacheableBundle);

    void cacheableBundleUnregistred(CacheableBundle cacheableBundle);

    CacheableBundleService getCacheableBundleService();

}
