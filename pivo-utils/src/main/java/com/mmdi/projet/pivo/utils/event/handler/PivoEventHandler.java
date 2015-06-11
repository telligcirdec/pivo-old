package com.mmdi.projet.pivo.utils.event.handler;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public interface PivoEventHandler {

	List<ServiceRegistration<?>> registerEventHandlerItself(
			BundleContext bundleContext);

}
