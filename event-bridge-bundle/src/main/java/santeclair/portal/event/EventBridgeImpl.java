package santeclair.portal.event;

import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

@Component
@Instantiate
public class EventBridgeImpl {

    @Requires
    private LogService logService;

    @Validate
    public void start() {
        logService.log(LogService.LOG_DEBUG, "EventBridgeImpl started");
    }

    @Subscriber(name = "MODULE_UI_FACTORY_STARTED", topics = "root")
    public void receiveRoot(Event event) {
        logService.log(LogService.LOG_DEBUG, "receiveRoot(" + event + ")");
    }

}
