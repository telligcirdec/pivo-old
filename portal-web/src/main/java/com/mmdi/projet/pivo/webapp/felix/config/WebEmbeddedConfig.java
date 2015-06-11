package com.mmdi.projet.pivo.webapp.felix.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.felix.framework.util.FelixConstants;
import org.osgi.framework.BundleActivator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebEmbeddedConfig implements InitConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebEmbeddedConfig.class);

    private BundleActivator hostActivator;

    public WebEmbeddedConfig(BundleActivator hostActivator) {
        this.hostActivator = hostActivator;
    }

    @Override
    public Map<String, Object> getInitConfig() {
        LOGGER.debug("init WebEmbeddedConfig");
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP,
                        Arrays.asList(hostActivator));
        return configMap;
    }

}
