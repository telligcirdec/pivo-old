package santeclair.portal.event.impl;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Unbind;
import org.osgi.service.log.LogService;

@Component(publicFactory = false)
@Instantiate
// @Provides
public class EventBridgeImpl {

	public EventBridgeImpl() {
		System.out.println("Fuck yeah");
	}

	@Bind
	public void bindLogService(LogService log) {
		System.out.println("test bind : " + log.toString());
	}

	@Unbind
	public void unbindLogService(LogService log) {
		System.out.println("test unbind : " + log.toString());
	}

}
