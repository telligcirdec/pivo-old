package santeclair.portal.bundle.test.module;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.osgi.service.log.LogService;

@Component
public class ModuleUi extends ModuleUiHelper {

    @Override
    @Bind
    public void bindLogService(LogService logService) {
        super.bindLogService(logService);
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
    @Property(name = "isCloseable", value = "true")
    protected void setIsCloseable(Boolean isCloseable) {
        super.setIsCloseable(isCloseable);
    }
    
    @Override
    @Property(name = "severalTabsAllowed", value = "false")
    protected void setSeveralTabsAllowed(Boolean severalTabsAllowed) {
        super.setSeveralTabsAllowed(severalTabsAllowed);
    }
}
