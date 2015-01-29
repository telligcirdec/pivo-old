package santeclair.portal.event;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.event.ModuleUiFactoryBundleStarted;
import santeclair.portal.vaadin.module.ModuleUiFactory;

@Component
@Instantiate
public class EventBridgeImpl {

    @Requires
    private LogService logService;

    @Requires
    private RootEventBusService rootEventBusService;

    @Validate
    public void start() {
        logService.log(LogService.LOG_DEBUG, "EventBridgeImpl started");
    }

    @Subscriber(name = "MODULE_UI_FACTORY_STARTED", topics = "root")
    public void receiveRoot(Event event) {
        logService.log(LogService.LOG_DEBUG, "receiveRoot(" + event + ")");
        rootEventBusService.post(new ModuleUiFactoryBundleStarted() {
            @Override
            public ModuleUiFactory getModuleUiFactory() {
                logService.log(LogService.LOG_DEBUG, "getModuleUiFactory()");
                return null;
            }
        });
    }

}
