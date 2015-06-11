package com.mmdi.projet.pivo.utils.listener.service.impl;

import org.osgi.framework.ServiceEvent;
import org.osgi.service.log.LogReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mmdi.projet.pivo.utils.listener.PivoLogListener;
import com.mmdi.projet.pivo.utils.listener.service.AbstractServiceListener;

@Component
public class LogReaderServiceListener extends
		AbstractServiceListener<LogReaderService> {

	/**
     * 
     */
	private static final long serialVersionUID = 5912052764997483805L;

	@Autowired
	private PivoLogListener pivoLogListener;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LogReaderServiceListener.class);

	@Override
	public void serviceRegistered(LogReaderService service,
			ServiceEvent serviceEvent) {
		LOGGER.info("Enregistrement du listener de log : {}", pivoLogListener);
		service.addLogListener(pivoLogListener);
	}

	@Override
	public void serviceUnregistering(LogReaderService service,
			ServiceEvent serviceEvent) {
		LOGGER.info("Suppression du listener de log : {}", pivoLogListener);
		service.removeLogListener(pivoLogListener);
	}

}