package com.mmdi.projet.pivo.webapp.vaadin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.osgi.framework.ServiceRegistration;

public class CurrentSessionServiceRegistration {

	private final List<ServiceRegistration<?>> srList = new ArrayList<>();

	public void addServiceRegistrations(List<ServiceRegistration<?>> srList) {
		this.srList.addAll(srList);
	}

	public void unregisterAllServices() {
		if (CollectionUtils.isNotEmpty(srList)) {
			for (ServiceRegistration<?> serviceRegistration : srList) {
				serviceRegistration.unregister();
				serviceRegistration = null;
			}
			srList.clear();
		}
	}

}
