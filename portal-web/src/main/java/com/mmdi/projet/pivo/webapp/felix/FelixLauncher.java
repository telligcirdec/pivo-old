package com.mmdi.projet.pivo.webapp.felix;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.felix.framework.Felix;
import org.apache.felix.main.AutoProcessor;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.stereotype.Component;

import com.mmdi.projet.pivo.webapp.HostActivator;
import com.mmdi.projet.pivo.webapp.felix.config.FelixConfigAdminConfig;
import com.mmdi.projet.pivo.webapp.felix.config.FelixEventAdminConfig;
import com.mmdi.projet.pivo.webapp.felix.config.FileInstallConfig;
import com.mmdi.projet.pivo.webapp.felix.config.FrameworkConfig;
import com.mmdi.projet.pivo.webapp.felix.config.InitConfig;
import com.mmdi.projet.pivo.webapp.felix.config.WebEmbeddedConfig;

@Component
public class FelixLauncher implements Serializable {

	private static final long serialVersionUID = -742616636242601752L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FelixLauncher.class);

	@Autowired
	@Qualifier("felixProperties")
	private PropertiesFactoryBean propertiesFactoryBean;

	@Value("${felixlaucher.root.dir}")
	private String rootDir;

	private Felix felix;

	@Autowired
	private HostActivator hostActivator;

	/**
	 * D�marre le contexte OSGi (felix) une fois le reste de l'application
	 * d�marr�
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	public void init() throws IOException {
		LOGGER.debug("HostedFelix.init");
		start();
	}

	/**
	 * Arrete le contexte OSGi � la destruction du contexte
	 */
	@PreDestroy
	public void destroy() {
		LOGGER.debug("HostedFelix.destroy");
		stop();
	}

	private void start() throws IOException {
		LOGGER.debug("FelixLauncherWebappImpl.start()");
		LOGGER.debug("AbstractFelixLauncher.start");
		try {
			// inisitialisation des config n�cessaire au lancement de felix
			Map<String, Object> frameworkInitMap = initConfigMap();

			felix = new Felix(frameworkInitMap);
			felix.init();
			AutoProcessor.process(frameworkInitMap, felix.getBundleContext());
			felix.start();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("\n*********************\n* Installed bundle\n*********************");
				for (Bundle installedBundle : getInstalledBundles()) {
					LOGGER.debug(installedBundle.getBundleId() + " : "
							+ installedBundle.getSymbolicName());
				}
			}

		} catch (BundleException ex) {
			LOGGER.error("Could not create framework: " + ex);
			ex.printStackTrace();
		}

	}

	private void stop() {
		LOGGER.debug("AbstractFelixLauncher.stop");
		try {
			if (felix != null) {
				felix.stop();
				felix.waitForStop(0);
			} else {
				throw new IllegalStateException(
						"Cant stop framework not started.");
			}
		} catch (BundleException | InterruptedException e) {
			e.printStackTrace();
		}

	}

	private Bundle[] getInstalledBundles() {
		LOGGER.debug("FelixLauncherWebappImpl.getInstalledBundles");
		if (hostActivator != null) {
			// Use the system bundle activator to gain external
			// access to the set of installed bundles.
			return hostActivator.getBundles();
		} else {
			throw new IllegalStateException("hostActivator is not initialized.");
		}
	}

	private List<InitConfig> getInitConfigList() throws IOException {
		LOGGER.debug("FelixLauncherWebappImpl.getInitConfigList()");
		List<InitConfig> initConfigList = new ArrayList<>();

		Properties props = propertiesFactoryBean.getObject();

		LOGGER.debug("getInitConfigList=>props : " + props);
		// Config standard du felix
		initConfigList.add(new FrameworkConfig(rootDir, props));
		// Config pour embarquer le contexte osgi dans un war
		initConfigList.add(new WebEmbeddedConfig(hostActivator));
		// Config pour d�tecter l'installation automatique de bundle et de
		// fichier de config
		initConfigList.add(new FileInstallConfig(rootDir));
		// Config pour le service de configadmin
		initConfigList.add(new FelixConfigAdminConfig());
		// Config pour le service event admin
		initConfigList.add(new FelixEventAdminConfig());

		return initConfigList;
	}

	/**
	 * Permet de r�cup�rer une Map compl�te des config de lancement de felix
	 * 
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> initConfigMap() throws IOException {
		LOGGER.debug("AbstractFelixLauncher.initConfigMap");
		Map<String, Object> initConfigMap = new HashMap<>();
		for (InitConfig initConfig : getInitConfigList()) {
			Map<String, Object> initConfigMapFromList = initConfig
					.getInitConfig();
			initConfigMap.putAll(initConfigMapFromList);
		}
		return initConfigMap;
	}
}
