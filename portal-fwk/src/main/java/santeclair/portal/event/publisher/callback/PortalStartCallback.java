package santeclair.portal.event.publisher.callback;

import santeclair.portal.vaadin.module.ModuleUiFactory;

public interface PortalStartCallback {

    public void addNewModuleUiFactory(ModuleUiFactory<?> moduleUiFactory);

}
