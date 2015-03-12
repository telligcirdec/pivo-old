package santeclair.portal.bundle.test.view;

import static santeclair.portal.event.EventDictionaryConstant.EVENT_STARTED;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI_FACTORY;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_PORTAL;

import java.util.Dictionary;
import java.util.Enumeration;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Context;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Updated;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.log.LogService;

import santeclair.portal.bundle.test.module.ModuleUi;

@Component(immediate=true)
public class ViewUi extends ViewUiHelper {

    @Context
    private BundleContext context;

    private static final String PUBLISHER_NAME = "TestModuleUiFactoryPublisher";

    @Publishes(name = PUBLISHER_NAME, topics = TOPIC_MODULE_UI_FACTORY)
    private Publisher publisher;

    @Override
    @Bind
    public void bindLogService(LogService logService) {
        super.bindLogService(logService);
    }

    @Bind(filter = "(code=${codeModule})")
    public void bindModuleUi(ModuleUi moduleUi, Dictionary<?, ?> moduleConf) {
        logService.log(LogService.LOG_INFO, "Oh fuck yeah !!! : " + moduleUi.getCode());
        
        Enumeration<?> keysEnumeration = moduleConf.keys();
        while (keysEnumeration.hasMoreElements()) {
            String key = (String)keysEnumeration.nextElement();
            Object value = moduleConf.get(key);
            logService.log(LogService.LOG_DEBUG, key + " => " + value);
            if (value instanceof Object[]) {
                Object[] valueArray = (Object[]) value;
                for (Object object : valueArray) {
                    logService.log(LogService.LOG_DEBUG, "       => " + object);
                }
            }
        }
        
    }

    @Override
    @Validate
    public void start() {
        super.start();
    }

    @Override
    @Invalidate
    public void stop() {
        super.stop();
    }

    @Override
    @Subscriber(name = "portalStarted",
                    topics = TOPIC_PORTAL, filter = "(" + PROPERTY_KEY_EVENT_NAME + "=" + EVENT_STARTED + ")")
    public void portalStarted(Event event) {
        super.portalStarted(event);
    }

    @Override
    @Updated
    public void updated(Dictionary<?, ?> conf) {
        super.updated(conf);
    }

    @Override
    @Property(name = "codeModule", mandatory = true)
    protected void setCodeModule(String codeModule) {
        super.setCodeModule(codeModule);
    }

    @Override
    @Property(name = "code", mandatory = true)
    protected void setCode(String code) {
        super.setCode(code);
    }
    
    @Override
    @Property(name = "libelle", mandatory = true)
    protected void setLibelle(String libelle) {
        super.setLibelle(libelle);
    }
    
    @Override
    @Property(name = "icon", mandatory = true)
    protected void setIcon(String icon) {
        super.setIcon(icon);
    }

    @Override
    @Property(name = "openOnInitialization", value = "false")
    protected void setOpenOnInitialization(Boolean openOnInitialization) {
        super.setOpenOnInitialization(openOnInitialization);
    }

    @Override
    protected BundleContext getContext() {
        return context;
    }

    @Override
    protected Publisher getPublisher() {
        return publisher;
    }

}
