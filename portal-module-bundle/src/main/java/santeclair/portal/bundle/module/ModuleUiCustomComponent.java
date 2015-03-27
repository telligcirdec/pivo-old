package santeclair.portal.bundle.module;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

public class ModuleUiCustomComponent extends CustomComponent {

    private static final long serialVersionUID = 6503344094828946218L;

    private int tabHash;
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

    public int getTabHash() {
        return tabHash;
    }

    public void setTabHash(int tabHash) {
        this.tabHash = tabHash;
    }

    public String getSessionId() {
        return sessionId;
    }

}
