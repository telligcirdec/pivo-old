package santeclair.portal.event.publisher.callback;

import santeclair.portal.menu.MenuModule;

public interface PortalStartCallback {

    public void addMenuModule(MenuModule menuModule);
    
    public void removeMenuModule(MenuModule menuModule);

}
