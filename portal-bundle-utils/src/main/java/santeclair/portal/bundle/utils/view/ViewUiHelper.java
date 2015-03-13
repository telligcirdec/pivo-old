package santeclair.portal.bundle.utils.view;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import org.osgi.service.log.LogService;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;

public abstract class ViewUiHelper {

    private static final String ROLE_ANY = "ANY";

    protected LogService logService;
    
    private String codeModule;
    private String code;
    private String libelle;
    private FontIcon icon;
    private Boolean openOnInitialization;
    private List<ViewUi> viewsUi;

    protected void updated(Dictionary<?, ?> conf) {
        logService.log(LogService.LOG_DEBUG, "The instance was reconfigured");
        Enumeration<?> keysEnumeration = conf.keys();
        while (keysEnumeration.hasMoreElements()) {
            String key = (String)keysEnumeration.nextElement();
            Object value = conf.get(key);
            logService.log(LogService.LOG_DEBUG, key + " => " + value);
            if (value instanceof Object[]) {
                Object[] valueArray = (Object[]) value;
                for (Object object : valueArray) {
                    logService.log(LogService.LOG_DEBUG, "       => " + object);
                }
            }
        }
    }
    
    protected void bindLogService(LogService logService) {
        this.logService = logService;
    }

    protected void setCodeModule(String codeModule) {
        this.codeModule = codeModule;
    }

    protected void setCode(String code) {
        this.code = code;
    }
    
    protected void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    protected void setIcon(String icon) {
        if (icon != null && !icon.isEmpty()) {
            this.icon = FontAwesome.valueOf(icon);
        }
    }
    
    protected void setOpenOnInitialization(Boolean openOnInitialization) {
        this.openOnInitialization = openOnInitialization;
    }
    
    public void setViewsUi(List<ViewUi> viewsUi) {
        this.viewsUi = viewsUi;
    }

    public FontIcon getIcon() {
        return icon;
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Boolean getOpenOnInitialization() {
        return openOnInitialization;
    }
    
}
