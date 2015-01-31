package santeclair.portal.event.impl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.log.LogService;

import santeclair.portal.event.EventDictionaryConstant;

@Component(publicFactory = false)
@Instantiate
public class EventBridgeImpl {

    @Requires(optional = true, exception = IllegalStateException.class)
    private LogService logService;

    @Publishes(name = "rootPublisher", topics = EventDictionaryConstant.TOPIC_ROOT)
    private Publisher rootPublisher;

    @Validate
    public void start() {
        logService.log(LogService.LOG_DEBUG, "EventBridgeImpl started");
        Dictionary<String, String> eventProps = new Hashtable<>();
        eventProps.put("test", "oh yeah !");
        rootPublisher.send(eventProps);
        logService.log(LogService.LOG_DEBUG, "event publish");
    }

}
