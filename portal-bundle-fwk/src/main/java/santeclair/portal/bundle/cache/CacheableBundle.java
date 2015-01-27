package santeclair.portal.bundle.cache;

import org.springframework.cache.CacheManager;

public class CacheableBundle {

    private CacheManager cacheManager;
    private String bundleName;

    public CacheableBundle() {
    }

    public CacheableBundle(CacheManager cacheManager, String bundleName) {
        this.cacheManager = cacheManager;
        this.bundleName = bundleName;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

}
