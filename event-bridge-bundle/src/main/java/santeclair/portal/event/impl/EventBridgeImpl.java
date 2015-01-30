package santeclair.portal.event.impl;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.event.RootEventBusService;

@Component(publicFactory=false)
@Instantiate
public class EventBridgeImpl {

    @Requires
    private LogService logService;

    @Requires(optional=true)
    private RootEventBusService rootEventBusService;

    @Validate
    public void start() {
        System.out.println("Mouahahaaaaaaaaaaaaaaaaaaaaahhhhhhhahaa   !!!!!!!!!");
        logService.log(LogService.LOG_DEBUG, "EventBridgeImpl started");
    }

    @Subscriber(name = "EVENT_BRIDGE", topics = "root")
    public void receiveRoot(Event event) {
        System.out.println("Mouahahaaaaaaaaaaaaaaaaaaaaahhhhhhhahaa   !!!!!!!!! 222222222222222222");
        logService.log(LogService.LOG_DEBUG, "receiveRoot(" + event + ")");
        // rootEventBusService.post();
    }

}
