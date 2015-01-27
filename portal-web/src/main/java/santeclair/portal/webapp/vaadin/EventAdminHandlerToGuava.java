package santeclair.portal.webapp.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventAdminHandlerToGuava {

	@Autowired
	private PortalEventBus portalEventBus;

}
