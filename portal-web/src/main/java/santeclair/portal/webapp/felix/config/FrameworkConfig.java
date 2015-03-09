package santeclair.portal.webapp.felix.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrameworkConfig implements InitConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrameworkConfig.class);

    private String rootDir;
    private Properties props;

    public FrameworkConfig(String rootDir, Properties props) {
        this.rootDir = rootDir;
        this.props = props;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Map<String, Object> getInitConfig() {
        LOGGER.debug("init FrameworkConfig");
        // Create a configuration property map.
        Map<String, Object> configMap = new HashMap<>();

        configMap.put("felix.cache.rootdir", rootDir);
        configMap.put("org.osgi.framework.storage.clean", "onFirstInit");
        configMap.put("org.osgi.framework.storage", "bundle-cache");
        configMap.put("felix.auto.deploy.dir", rootDir + "/bundle");
        configMap.put("felix.auto.deploy.action", "install,start,update");
        configMap.put("felix.shutdown.hook", "true");
        configMap.put("felix.service.urlhandlers", "true");

        if (LOGGER.isDebugEnabled() || LOGGER.isTraceEnabled()) {
            configMap.put("felix.log.level", "4");
        } else if (LOGGER.isInfoEnabled()) {
            configMap.put("felix.log.level", "3");
        } else if (LOGGER.isWarnEnabled()) {
            configMap.put("felix.log.level", "2");
        } else if (LOGGER.isErrorEnabled()) {
            configMap.put("felix.log.level", "1");
        } else {
            configMap.put("felix.log.level", "2");
        }

        if (props != null) {
            configMap.putAll((Map) props);
        } else {
            LOGGER.debug("props == null");
        }

        return configMap;
    }

}
