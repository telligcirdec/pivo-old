package santeclair.portal.bundle.test.module;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

public class ModuleUiCustomComponent extends CustomComponent {

    private static final long serialVersionUID = 6503344094828946218L;

    public ModuleUiCustomComponent() {
        super();
    }

    public ModuleUiCustomComponent(Component compositionRoot) {
        super(compositionRoot);
    }

    @Override
    public void setCompositionRoot(Component compositionRoot) {
        super.setCompositionRoot(compositionRoot);
    }

}
