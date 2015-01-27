package santeclair.portal.webapp.felix;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HostActivator implements BundleActivator, ServiceListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(HostActivator.class);

	private BundleContext bundleContext = null;

	@Override
	public void start(BundleContext bundleContext) throws InvalidSyntaxException {
		LOGGER.debug("Starting HostActivator");
		this.bundleContext = bundleContext;

		FrameworkListener f_listener = new FrameworkListener() {

			@Override
			public void frameworkEvent(FrameworkEvent frameworkEvent) {
				System.out.println("frameworkEvent : " + frameworkEvent.getSource().toString() + " (" + frameworkEvent.getType() + ")");

			}
		};

		BundleListener bundleListener = new BundleListener() {

			@Override
			public void bundleChanged(BundleEvent bundleEvent) {
				System.out.println("bundleEvent : " + bundleEvent.getSource().toString() + " (" + bundleEvent.getType() + ")");

			}
		};

		String filter = "(objectclass=" + LogReaderService.class.getName() + ")";

		bundleContext.addServiceListener(this, filter);
		bundleContext.addBundleListener(bundleListener);
		bundleContext.addFrameworkListener(f_listener);
	}

	@Override
	public void stop(BundleContext bundleContext) {
		LOGGER.debug("Stopping HostActivator");
		this.bundleContext = null;
	}

	public Bundle[] getBundles() {
		LOGGER.debug("HostActivator.getBundles");
		if (bundleContext != null) {
			return bundleContext.getBundles();
		}
		return null;
	}

	public BundleContext getBundleContext() {
		LOGGER.debug("HostActivator.getBundleContext");
		return bundleContext;
	}

	@Override
	public void serviceChanged(ServiceEvent serviceEvent) {
		LogReaderService logReaderService = (LogReaderService) bundleContext.getService(serviceEvent.getServiceReference());

		logReaderService.addLogListener(new LogListener() {

			@Override
			public void logged(LogEntry entry) {
				System.out.println(" !!! Listener Maison !!! : " + entry.getMessage());
			}
		});
	}

}
