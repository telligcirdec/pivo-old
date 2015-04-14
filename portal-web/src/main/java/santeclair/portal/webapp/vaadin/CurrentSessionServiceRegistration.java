package santeclair.portal.webapp.vaadin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.osgi.framework.ServiceRegistration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(org.springframework.web.context.WebApplicationContext.SCOPE_SESSION)
public class CurrentSessionServiceRegistration {

    private final List<ServiceRegistration<?>> srList = new ArrayList<>();

    public void addServiceRegistrations(List<ServiceRegistration<?>> srList) {
        this.srList.addAll(srList);
    }

    public void unregisterAllServices() {
        if (CollectionUtils.isNotEmpty(srList)) {
            for (ServiceRegistration<?> serviceRegistration : srList) {
                serviceRegistration.unregister();
                serviceRegistration = null;
            }
            srList.clear();
        }
    }

}
