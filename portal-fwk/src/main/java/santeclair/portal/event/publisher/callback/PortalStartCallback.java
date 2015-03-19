package santeclair.portal.event.publisher.callback;

import santeclair.portal.module.ModuleUi;

public interface PortalStartCallback {

    public void addModuleUi(ModuleUi moduleUi, final String moduleCode);

    // public void removeMenuModule(MenuModule menuModule);

}
