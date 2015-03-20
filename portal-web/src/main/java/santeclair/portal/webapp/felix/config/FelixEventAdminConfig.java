package santeclair.portal.webapp.felix.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FelixEventAdminConfig implements InitConfig{

    private static final Logger LOGGER = LoggerFactory.getLogger(FelixEventAdminConfig.class);
    
    @Override
    public Map<String, Object> getInitConfig() {
        LOGGER.debug("init FelixConfigAdminConfig");

        Map<String, Object> configAdminConfig = new HashMap<>();

        if (LOGGER.isDebugEnabled() || LOGGER.isTraceEnabled()) {
            configAdminConfig.put("org.apache.felix.eventadmin.LogLevel", "4");
            configAdminConfig.put("org.apache.felix.eventadmin.Timeout", "0");
        } else if (LOGGER.isInfoEnabled()) {
            configAdminConfig.put("org.apache.felix.eventadmin.LogLevel", "3");
        } else if (LOGGER.isWarnEnabled()) {
            configAdminConfig.put("org.apache.felix.eventadmin.LogLevel", "2");
        } else if (LOGGER.isErrorEnabled()) {
            configAdminConfig.put("org.apache.felix.eventadmin.LogLevel", "1");
        } else {
            configAdminConfig.put("felix.cm.loglevel", "2");
        }

        return configAdminConfig;
    }

}
