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
     * Permet de cr�er un dictionnaire de propri�t� pour lever un �v�nement pr�venant d'une exception g�n�rale non fonctionnelle.
     * L'objectif de cet �v�nement est d'afficher un message informatif � l'utilisateur dans le portail.
     * En ce sens, le context n'est pas une donn�e obligatoire et peut avoir une valeur null.
     * 
     * L'event name de cet �v�nement est {@link santeclair.portal.event.EventDictionaryConstant}.EVENT_NAME_EXCEPTION
     * 
     * Afin de d�clencher l'affichage du message d'erreur dans le portial, il convient de firer l'event sur le topic
     * {@link santeclair.portal.event.EventDictionaryConstant}.TOPIC_PORTAL.
     * 
     * L'event peut tout � fait �tre firer sur d'autres event, cependant seul le topic TOPIC_PORTAIL int�gre un comportement par d�faut.
     * 
     * @param context Le context depuis lequel l'evenement a �t� firer. Peut �tre null dans le cas du d�clenchement par d�faut dans le portail.
     * @param sessionId L'id de session pour ne pr�venir que l'utilisateur concern� par cette erreur. Ne peut pas �tre null.
     * @param summary Un r�sum� de l'erreur. Ne peut pas �tre null ou blanc.
     * @param message Un message informatif sur l'erreur. Ne peut pas �tre null ou blanc.
     * @param exception L'exception lev�e. Peut �tre null. Si diff�rent de null, un log error sera alors lev� dans le comportement par d�faut de
     *            l'event dans le topic {@link santeclair.portal.event.EventDictionaryConstant}.TOPIC_PORTAL.
     * @return Un dictionnaire des prpri�t�s qu'il conviendra de firer sur le topic {@link santeclair.portal.event.EventDictionaryConstant}
     *         .TOPIC_PORTAL pour avoir le comportement par d�faut ou sur tout autre topic de votre choix � l'�coute de cet �v�nement.
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
