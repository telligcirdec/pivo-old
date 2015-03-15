package santeclair.portal.bundle.test.view;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_MODULE_UI;

import java.util.Dictionary;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Updated;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.log.LogService;

import santeclair.portal.bundle.utils.view.AbstractViewUi;
import santeclair.portal.bundle.utils.view.ViewUi;

@Component
public class ViewUiImpl extends AbstractViewUi implements ViewUi {

    @Publishes(name = "testViewUiPublisher", topics = TOPIC_MODULE_UI, dataKey = PROPERTY_KEY_VIEW_UI)
    private Publisher publisher;

    @Validate
    public void start() {
        logService.log(LogService.LOG_INFO, "TestModuleUi Starting");

        logService.log(LogService.LOG_INFO, "TestModuleUi Started");
    }

    @Override
    @Bind
    public void bindLogService(LogService logService) {
        super.bindLogService(logService);
    }

    /*
     * Properties
     */

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
}
