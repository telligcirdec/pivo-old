package santeclair.portal.webapp.event.impl;

import org.osgi.service.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import santeclair.portal.event.EventDictionaryConstant;
import santeclair.portal.webapp.event.AbstractEventHandler;

@Component
public class RootEventHandlerImpl extends AbstractEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootEventHandlerImpl.class);

    @Override
    public void handleEvent(Event event) {
        LOGGER.debug("handleEvent({})", event);
    }

    @Override
    public String[] getTopics() {
        return new String[]{EventDictionaryConstant.TOPIC_ROOT};
    }

}
