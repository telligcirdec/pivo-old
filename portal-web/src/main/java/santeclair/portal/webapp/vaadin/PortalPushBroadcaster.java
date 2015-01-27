package santeclair.portal.webapp.vaadin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import santeclair.portal.vaadin.module.ModuleUiFactory;

import com.google.common.base.Preconditions;

/**
 * Classe permettant de gérer la mécanique de broadcast autour du push dans le
 * portail.
 * 
 * @author Puppet master
 *
 */
@Component
public class PortalPushBroadcaster implements Serializable {

	public enum PortalBroadcastetEvent {
		MODULE_FACTORY_REGISTERED(ModuleUiFactory.class), MODULE_FACTORY_UNREGISTERED(ModuleUiFactory.class);

		private final Class<?> dataClass;

		private PortalBroadcastetEvent(Class<?> dataClass) {
			this.dataClass = dataClass;
		}

		public Class<?> getDataClass() {
			return dataClass;
		}

	}

	private static final long serialVersionUID = 4776993772069461888L;

	private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

	private static final Logger LOGGER = LoggerFactory.getLogger(PortalPushBroadcaster.class);

	public interface PortalPushBroadcastListener {
		/**
		 * Permet de réagir au broadcast une fois enregistré.
		 * 
		 * @param portalBroadcastetEvent
		 *            Evénement qui a déclenché le broadcast. Ne peut pas ête
		 *            null.
		 * @param data
		 *            Donnée broadcastée. Peut être null.
		 */
		void receiveBroadcast(final PortalBroadcastetEvent portalBroadcastetEvent, final Object data);
	}

	private static Map<PortalBroadcastetEvent, List<PortalPushBroadcastListener>> listeners = new HashMap<>();

	/**
	 * Permet l'enregistrement d'un listener de broadcast de push du portail aux
	 * événements passés en paramètre. Si le tableau dynamique est null, on
	 * considère que le listener s'enregistre à tous les événements connus.
	 * 
	 * @param listener
	 *            Listener qui sera appelé sur un broadcast concernant le ou les
	 *            événements passés en paramètre.
	 * @param portalBroadcastetEvents
	 *            Evénements sur lesquels déclencher. Si null alors tous les
	 *            événements seront pris en compte.
	 */
	public static synchronized void register(PortalPushBroadcastListener listener, PortalBroadcastetEvent... portalBroadcastetEvents) {
		LOGGER.debug("PortalPushBroadcaster.register({},{})", listener, portalBroadcastetEvents);
		// Enregistrement du listener uniquement si différent de null
		if (listener != null) {
			// Si le tableau dynamique passé en paramètre est vide on considère
			// que l'on enregistre le listener à tous les events qui existent
			// dans l'enum
			if (ArrayUtils.isEmpty(portalBroadcastetEvents)) {
				LOGGER.debug("Enregistrement du listener {} à tous les events connus : {}", listener, portalBroadcastetEvents);
				portalBroadcastetEvents = PortalBroadcastetEvent.values();
			}
			// Parcours de tous les evenement pour lesquels il est nécessaire
			// d'enregistrer le listener
			for (PortalBroadcastetEvent portalBroadcastetEvent : portalBroadcastetEvents) {
				if (portalBroadcastetEvent != null) {
					// Recuperation de la iste des listeners
					List<PortalPushBroadcastListener> portalPushBroadcastListeners = listeners.get(portalBroadcastetEvent);
					if (portalPushBroadcastListeners == null) {
						// Si la liste récupérée est null => premier
						// energistrement
						// pour cet event
						portalPushBroadcastListeners = new ArrayList<>();
					}
					// Ajout du listener à la liste
					portalPushBroadcastListeners.add(listener);
					// Ajout de la liste à la map de listener
					listeners.put(portalBroadcastetEvent, portalPushBroadcastListeners);
				}
			}
		} else {
			// Listener null
			LOGGER.warn("Le listener que vous tentez d'enregistrer pour les events suivants : {} est null.\n Enregistrement ignoré.",
					(Object[]) portalBroadcastetEvents);
		}
	}

	/**
	 * Permet le désenregistrement d'un listener de broadcast de push du portail
	 * selon les événements passés en paramètre. Si le tableau dynamique est
	 * null, on considère que le listener se desenregistre à tous les événements
	 * connus.
	 * 
	 * @param listener
	 *            Listener qui sera a désenregistrer.
	 * @param portalBroadcastetEvents
	 *            Evénements sur lesquels désenregistrer le listener.
	 */
	public static synchronized void unregister(PortalPushBroadcastListener listener, PortalBroadcastetEvent... portalBroadcastetEvents) {
		LOGGER.debug("PortalPushBroadcaster.unregister({}, {})", listener, portalBroadcastetEvents);
		// Désenregistrement du listener uniquement si différent de null
		if (listener != null) {
			// Si le tableau dynamique passé en paramètre est vide on considère
			// que l'on désenregistre le listener à tous les events qui existent
			// dans l'enum
			if (ArrayUtils.isEmpty(portalBroadcastetEvents)) {
				LOGGER.debug("Enregistrement du listener {} à tous les events connus : {}", listener, portalBroadcastetEvents);
				portalBroadcastetEvents = PortalBroadcastetEvent.values();
			}

			// Parcours de tous les evenement pour lesquels il est nécessaire
			// d'enregistrer le listener
			for (PortalBroadcastetEvent portalBroadcastetEvent : portalBroadcastetEvents) {
				if (portalBroadcastetEvent != null) {
					// Recuperation de la iste des listeners
					List<PortalPushBroadcastListener> portalPushBroadcastListeners = listeners.get(portalBroadcastetEvent);
					// Si la liste de listener existe pour cet evenement
					if (portalPushBroadcastListeners != null) {
						// Suppression du listener de la liste
						portalPushBroadcastListeners.remove(listener);
						listeners.put(portalBroadcastetEvent, portalPushBroadcastListeners);
					}
				}
			}
		} else {
			// Listener null
			LOGGER.warn("Le listener que vous tentez de désenregistrer pour les events suivants : {} est null.\n Désenregistrement ignoré.",
					(Object[]) portalBroadcastetEvents);
		}

	}

	/**
	 * Permet le broadcast d'un event avec un objet en paramètre. Attention, la
	 * classe de l'objet passé en paramètre doit être assignableFrom la classe
	 * permise par l'énumération. L'objet passé en paramètre peut également être
	 * null.
	 * 
	 * @param portalBroadcastetEvent
	 *            L'événement levé. Doit être différent de null.
	 * @param data
	 *            La data a transférer avec l'événement.
	 * @throws NullPointerException
	 *             Si l'événement passé en paramètre est null.
	 */
	public static synchronized void broadcast(final PortalBroadcastetEvent portalBroadcastetEvent, final Object data) {
		LOGGER.debug("PortalPushBroadcaster.broadcast({},{})", portalBroadcastetEvent, data);
		Preconditions.checkNotNull(portalBroadcastetEvent, "L'événement levé doit être différent de null.");
		if (data == null || (data != null && data.getClass().isAssignableFrom(portalBroadcastetEvent.getDataClass()))) {
			List<PortalPushBroadcastListener> portalPushBroadcastListeners = listeners.get(portalBroadcastetEvent);
			for (final PortalPushBroadcastListener listener : portalPushBroadcastListeners)
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						listener.receiveBroadcast(portalBroadcastetEvent, data);
					}
				});
		} else {
			LOGGER.warn("Broacast non déclenché, la classe de la data passée en paramètre ({}) ne correspond pas au type attendu pour cet event ({})",
					data.getClass(), portalBroadcastetEvent.getDataClass());
		}

	}
}
