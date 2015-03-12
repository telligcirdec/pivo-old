package santeclair.portal.bundle.test.module;

import org.osgi.service.log.LogService;

public abstract class ModuleUiHelper {

    private LogService logService;

    private String code;

    private String libelle;

    private String icon;

    private Boolean isCloseable;

    private Boolean severalTabsAllowed;

    protected void bindLogService(LogService logService) {
        this.logService = logService;
    }

    protected void setCode(String code) {
        this.code = code;
    }

    protected void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    protected void setIcon(String icon) {
        this.icon = icon;
    }

    protected void setIsCloseable(Boolean isCloseable) {
        this.isCloseable = isCloseable;
    }

    protected void setSeveralTabsAllowed(Boolean severalTabsAllowed) {
        this.severalTabsAllowed = severalTabsAllowed;
    }

}
