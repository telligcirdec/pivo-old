package santeclair.portal.webapp.event.handler.impl;

import org.osgi.service.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import santeclair.portal.event.EventDictionaryConstant;
import santeclair.portal.webapp.event.handler.AbstractEventHandler;
import santeclair.portal.webapp.event.handler.Subscriber;

@Component
public class RootEventHandler extends AbstractEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootEventHandler.class);

    @Subscriber(topic = EventDictionaryConstant.TOPIC_ROOT)
    public void rootEvent(Event event) {
        LOGGER.debug("ROOT Event : " + event.toString());
    }

}
