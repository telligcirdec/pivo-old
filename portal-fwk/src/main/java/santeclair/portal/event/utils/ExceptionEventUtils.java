package santeclair.portal.event.utils;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_NAME_EXCEPTION;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_CONTEXT;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EXCEPTION_MESSAGE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EXCEPTION_SUMMARY;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EXCEPTION_THROWABLE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

public class ExceptionEventUtils {

    /**
     * Permet de créer un dictionnaire de propriété pour lever un événement prévenant d'une exception générale non fonctionnelle.
     * L'objectif de cet événement est d'afficher un message informatif à l'utilisateur dans le portail.
     * En ce sens, le context n'est pas une donnée obligatoire et peut avoir une valeur null.
     * 
     * L'event name de cet événement est {@link santeclair.portal.event.EventDictionaryConstant}.EVENT_NAME_EXCEPTION
     * 
     * Afin de déclencher l'affichage du message d'erreur dans le portial, il convient de firer l'event sur le topic
     * {@link santeclair.portal.event.EventDictionaryConstant}.TOPIC_PORTAL.
     * 
     * L'event peut tout à fait être firer sur d'autres event, cependant seul le topic TOPIC_PORTAIL intègre un comportement par défaut.
     * 
     * @param context Le context depuis lequel l'evenement a été firer. Peut être null dans le cas du déclenchement par défaut dans le portail.
     * @param sessionId L'id de session pour ne prévenir que l'utilisateur concerné par cette erreur. Ne peut pas être null.
     * @param summary Un résumé de l'erreur. Ne peut pas être null ou blanc.
     * @param message Un message informatif sur l'erreur. Ne peut pas être null ou blanc.
     * @param exception L'exception levée. Peut être null. Si différent de null, un log error sera alors levé dans le comportement par défaut de
     *            l'event dans le topic {@link santeclair.portal.event.EventDictionaryConstant}.TOPIC_PORTAL.
     * @return Un dictionnaire des prpriétés qu'il conviendra de firer sur le topic {@link santeclair.portal.event.EventDictionaryConstant}
     *         .TOPIC_PORTAL pour avoir le comportement par défaut ou sur tout autre topic de votre choix à l'écoute de cet événement.
     */
    public static Dictionary<String, Object> getPortalExceptionProps(String context,
                    String sessionId, String summary, String message, Throwable exception) {
        Preconditions.checkArgument(StringUtils.isNotBlank(sessionId), "sessionId must be set.");
        Preconditions.checkArgument(StringUtils.isNotBlank(summary), "summary must be set.");
        Preconditions.checkArgument(StringUtils.isNotBlank(message), "message must be set.");

        Dictionary<String, Object> props = new Hashtable<>();

        props.put(PROPERTY_KEY_EVENT_CONTEXT, context);
        props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_EXCEPTION);
        props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
        props.put(PROPERTY_KEY_EXCEPTION_SUMMARY, summary);
        props.put(PROPERTY_KEY_EXCEPTION_MESSAGE, message);
        props.put(PROPERTY_KEY_EXCEPTION_THROWABLE, exception);

        return props;
    }

}
