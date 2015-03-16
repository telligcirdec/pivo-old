package santeclair.portal.bundle.utils.module;

import org.osgi.service.event.Event;

public interface ModuleUi {

    String getCode();

    void registerViewUi(Event event);

    void unregisterViewUi(Event event);

}
