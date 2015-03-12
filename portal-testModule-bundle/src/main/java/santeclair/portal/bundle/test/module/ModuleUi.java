package santeclair.portal.bundle.test.module;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.osgi.service.log.LogService;

@Component(immediate = true)
@Provides(specifications = ModuleUi.class)
public class ModuleUi extends ModuleUiHelper {

    @ServiceProperty(name="code", value="${code}")
    private String code;
    
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

    @Override
    @Property(name = "displayOrder", value = "100000")
    protected void setDisplayOrder(Integer displayOrder) {
        super.setDisplayOrder(displayOrder);
    }

    public String getCode() {
        return code;
    }
    
    
}
