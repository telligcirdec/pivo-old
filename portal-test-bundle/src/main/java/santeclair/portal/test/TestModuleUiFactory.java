package santeclair.portal.test;

import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.log.LogService;

import santeclair.portal.vaadin.module.ModuleUiFactory;

import com.vaadin.server.FontIcon;

@Component(publicFactory=false)
@Instantiate
public class TestModuleUiFactory implements ModuleUiFactory<TestModuleUi> {

    private static final long serialVersionUID = 3710994197607738603L;

    @Requires
    private LogService logService;

    @Publishes(name = "TEST_MODULE_UI_FACTORY_PUBLISHER", topics = "root")
    private Publisher publisher;

    @Validate
    public void start() {
        logService.log(LogService.LOG_DEBUG, "TestModuleUiFactory start");
        publisher.sendData(this);
    }

    @Override
    public Boolean isSeveralModuleAllowed() {
        return false;
    }

    @Override
    public String getCode() {
        return "TEST";
    }

    @Override
    public String getName() {
        return "Module de Test";
    }

    @Override
    public FontIcon getIcon() {
        return null;
    }

    @Override
    public Integer displayOrder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean isCloseable(List<String> roles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean openOnInitialization(List<String> roles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean securityCheck(List<String> roles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TestModuleUi buid(List<String> roles) {
        // TODO Auto-generated method stub
        return null;
    }

}
