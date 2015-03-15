package santeclair.portal.bundle.utils.view;

import java.util.Dictionary;

import org.osgi.service.log.LogService;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;

public abstract class AbstractViewUi implements ViewUi {

    protected LogService logService;

    private String codeModule;

    private String codeOldValue;
    private String code;

    private String libelle;
    private FontIcon icon;
    private Boolean openOnInitialization;

    protected void updated(Dictionary<?, ?> conf) {
        logService.log(LogService.LOG_DEBUG, "ViewUi " + this.getCode() + " has been modified.");
    }

    protected void bindLogService(LogService logService) {
        logService.log(LogService.LOG_DEBUG, "ViewUi " + this.code + " is binding logService.");
        this.logService = logService;
    }

    protected void setCodeModule(String codeModule) {
        logService.log(LogService.LOG_DEBUG, "ViewUi " + this.code + " is binding logService.");
        this.codeModule = codeModule;
    }

    protected void setCode(String code) {
        this.codeOldValue = this.code;
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

    @Override
    public FontIcon getIcon() {
        return icon;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLibelle() {
        return libelle;
    }

    @Override
    public Boolean getOpenOnInitialization() {
        return openOnInitialization;
    }
}
