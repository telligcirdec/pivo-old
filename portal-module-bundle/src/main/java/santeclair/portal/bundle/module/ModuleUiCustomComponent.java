package santeclair.portal.bundle.module;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

public class ModuleUiCustomComponent extends CustomComponent {

    private static final long serialVersionUID = 6503344094828946218L;

    private Integer tabHash;
    private final String sessionId;

    public ModuleUiCustomComponent(String sessionId) {
        super();
        this.sessionId = sessionId;
    }

    public ModuleUiCustomComponent(String sessionId, Component compositionRoot) {
        super(compositionRoot);
        this.sessionId = sessionId;
    }

    @Override
    public void setCompositionRoot(Component compositionRoot) {
        super.setCompositionRoot(compositionRoot);
    }

    public Integer getTabHash() {
        return tabHash;
    }

    public void setTabHash(Integer tabHash) {
        this.tabHash = tabHash;
    }

    public String getSessionId() {
        return sessionId;
    }

}
