package com.mmdi.projet.pivo.utils.listener.service;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

public interface PivoServiceListener<T> extends ServiceListener {

	/**
	 * Renvoie un filtre pour limiter la recherche le service sur lequel
	 * déclenché. Si renvoie null, alors la class du service à écouter sera pris
	 * par défaut. Si vous définissez un filtre supplémentaire (la méthode
	 * renvoie autre chose que null), le filtre construit sera donc
	 * &(objectClass=getServiceClass().getName())(votreFiltre).
	 * 
	 * @return
	 */
	String getFilter();

	/**
	 * Cette méthode sera déclenchée lorsque le service s'enregistre dans le
	 * contexte OSGi.
	 * 
	 * @param serviceEvent
	 *            ServiceEvent d'enregistrement du service.
	 */
	void serviceRegistered(T service, ServiceEvent serviceEvent);

	/**
	 * Cette méthode sera déclenchée lorsque le service se désenregistre du
	 * contexte OSGi.
	 * 
	 * @param serviceEvent
	 *            ServiceEvent d'enregistrement du service.
	 */
	void serviceUnregistering(T service, ServiceEvent serviceEvent);

	/**
	 * Cette méthode est déclenchée lorsque les propriétés d'un service
	 * enregistré dans le contexte OSGi change (changement du dictionnaire)
	 * 
	 * @param serviceEvent
	 *            ServiceEvent d'enregistrement du service.
	 */
	void serviceModified(T service, ServiceEvent serviceEvent);

	/**
	 * Cette méthode est déclanché lorsque les propriétés d'un service
	 * enregistré dans le contexte OSGi change (changement du dictionnaire) et
	 * que les nouvelles priopriétés ne corresspondent plus au service.
	 * 
	 * @param serviceEvent
	 */
	void serviceModifiedEndmatch(T service, ServiceEvent serviceEvent);

	void registerItself(BundleContext bundleContext)
			throws InvalidSyntaxException;

}