package com.mmdi.projet.pivo.webapp.felix.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FelixConfigAdminConfig implements InitConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FelixConfigAdminConfig.class);

    @Override
    public Map<String, Object> getInitConfig() {
        LOGGER.debug("init FelixConfigAdminConfig");

        Map<String, Object> configAdminConfig = new HashMap<>();

        if (LOGGER.isDebugEnabled() || LOGGER.isTraceEnabled()) {
            configAdminConfig.put("felix.cm.loglevell", "4");
        } else if (LOGGER.isInfoEnabled()) {
            configAdminConfig.put("felix.cm.loglevel", "3");
        } else if (LOGGER.isWarnEnabled()) {
            configAdminConfig.put("felix.cm.loglevel", "2");
        } else if (LOGGER.isErrorEnabled()) {
            configAdminConfig.put("felix.cm.loglevel", "1");
        } else {
            configAdminConfig.put("felix.cm.loglevel", "2");
        }

        return configAdminConfig;
    }

}
