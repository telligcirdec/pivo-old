package santeclair.portal.bundle.test.view;

import java.util.Dictionary;
import java.util.Enumeration;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Updated;
import org.osgi.service.log.LogService;

import santeclair.portal.bundle.utils.module.ModuleUi;
import santeclair.portal.bundle.utils.view.ViewUi;
import santeclair.portal.bundle.utils.view.ViewUiHelper;

@Component
public class ViewUiImpl extends ViewUiHelper implements ViewUi {

    @Override
    @Bind
    public void bindLogService(LogService logService) {
        super.bindLogService(logService);
    }

    @Bind(filter = "(code=${codeModule})")
    public void bindModuleUi(ModuleUi moduleUi, Dictionary<?, ?> moduleConf) {
        
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
        
        // Enregistrement de la view auprès de son module
        moduleUi.registerView(this);
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
}
