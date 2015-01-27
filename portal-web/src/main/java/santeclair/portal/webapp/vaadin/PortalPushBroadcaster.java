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
 * Classe permettant de g�rer la m�canique de broadcast autour du push dans le
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
		 * Permet de r�agir au broadcast une fois enregistr�.
		 * 
		 * @param portalBroadcastetEvent
		 *            Ev�nement qui a d�clench� le broadcast. Ne peut pas �te
		 *            null.
		 * @param data
		 *            Donn�e broadcast�e. Peut �tre null.
		 */
		void receiveBroadcast(final PortalBroadcastetEvent portalBroadcastetEvent, final Object data);
	}

	private static Map<PortalBroadcastetEvent, List<PortalPushBroadcastListener>> listeners = new HashMap<>();

	/**
	 * Permet l'enregistrement d'un listener de broadcast de push du portail aux
	 * �v�nements pass�s en param�tre. Si le tableau dynamique est null, on
	 * consid�re que le listener s'enregistre � tous les �v�nements connus.
	 * 
	 * @param listener
	 *            Listener qui sera appel� sur un broadcast concernant le ou les
	 *            �v�nements pass�s en param�tre.
	 * @param portalBroadcastetEvents
	 *            Ev�nements sur lesquels d�clencher. Si null alors tous les
	 *            �v�nements seront pris en compte.
	 */
	public static synchronized void register(PortalPushBroadcastListener listener, PortalBroadcastetEvent... portalBroadcastetEvents) {
		LOGGER.debug("PortalPushBroadcaster.register({},{})", listener, portalBroadcastetEvents);
		// Enregistrement du listener uniquement si diff�rent de null
		if (listener != null) {
			// Si le tableau dynamique pass� en param�tre est vide on consid�re
			// que l'on enregistre le listener � tous les events qui existent
			// dans l'enum
			if (ArrayUtils.isEmpty(portalBroadcastetEvents)) {
				LOGGER.debug("Enregistrement du listener {} � tous les events connus : {}", listener, portalBroadcastetEvents);
				portalBroadcastetEvents = PortalBroadcastetEvent.values();
			}
			// Parcours de tous les evenement pour lesquels il est n�cessaire
			// d'enregistrer le listener
			for (PortalBroadcastetEvent portalBroadcastetEvent : portalBroadcastetEvents) {
				if (portalBroadcastetEvent != null) {
					// Recuperation de la iste des listeners
					List<PortalPushBroadcastListener> portalPushBroadcastListeners = listeners.get(portalBroadcastetEvent);
					if (portalPushBroadcastListeners == null) {
						// Si la liste r�cup�r�e est null => premier
						// energistrement
						// pour cet event
						portalPushBroadcastListeners = new ArrayList<>();
					}
					// Ajout du listener � la liste
					portalPushBroadcastListeners.add(listener);
					// Ajout de la liste � la map de listener
					listeners.put(portalBroadcastetEvent, portalPushBroadcastListeners);
				}
			}
		} else {
			// Listener null
			LOGGER.warn("Le listener que vous tentez d'enregistrer pour les events suivants : {} est null.\n Enregistrement ignor�.",
					(Object[]) portalBroadcastetEvents);
		}
	}

	/**
	 * Permet le d�senregistrement d'un listener de broadcast de push du portail
	 * selon les �v�nements pass�s en param�tre. Si le tableau dynamique est
	 * null, on consid�re que le listener se desenregistre � tous les �v�nements
	 * connus.
	 * 
	 * @param listener
	 *            Listener qui sera a d�senregistrer.
	 * @param portalBroadcastetEvents
	 *            Ev�nements sur lesquels d�senregistrer le listener.
	 */
	public static synchronized void unregister(PortalPushBroadcastListener listener, PortalBroadcastetEvent... portalBroadcastetEvents) {
		LOGGER.debug("PortalPushBroadcaster.unregister({}, {})", listener, portalBroadcastetEvents);
		// D�senregistrement du listener uniquement si diff�rent de null
		if (listener != null) {
			// Si le tableau dynamique pass� en param�tre est vide on consid�re
			// que l'on d�senregistre le listener � tous les events qui existent
			// dans l'enum
			if (ArrayUtils.isEmpty(portalBroadcastetEvents)) {
				LOGGER.debug("Enregistrement du listener {} � tous les events connus : {}", listener, portalBroadcastetEvents);
				portalBroadcastetEvents = PortalBroadcastetEvent.values();
			}

			// Parcours de tous les evenement pour lesquels il est n�cessaire
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
			LOGGER.warn("Le listener que vous tentez de d�senregistrer pour les events suivants : {} est null.\n D�senregistrement ignor�.",
					(Object[]) portalBroadcastetEvents);
		}

	}

	/**
	 * Permet le broadcast d'un event avec un objet en param�tre. Attention, la
	 * classe de l'objet pass� en param�tre doit �tre assignableFrom la classe
	 * permise par l'�num�ration. L'objet pass� en param�tre peut �galement �tre
	 * null.
	 * 
	 * @param portalBroadcastetEvent
	 *            L'�v�nement lev�. Doit �tre diff�rent de null.
	 * @param data
	 *            La data a transf�rer avec l'�v�nement.
	 * @throws NullPointerException
	 *             Si l'�v�nement pass� en param�tre est null.
	 */
	public static synchronized void broadcast(final PortalBroadcastetEvent portalBroadcastetEvent, final Object data) {
		LOGGER.debug("PortalPushBroadcaster.broadcast({},{})", portalBroadcastetEvent, data);
		Preconditions.checkNotNull(portalBroadcastetEvent, "L'�v�nement lev� doit �tre diff�rent de null.");
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
			LOGGER.warn("Broacast non d�clench�, la classe de la data pass�e en param�tre ({}) ne correspond pas au type attendu pour cet event ({})",
					data.getClass(), portalBroadcastetEvent.getDataClass());
		}

	}
}
