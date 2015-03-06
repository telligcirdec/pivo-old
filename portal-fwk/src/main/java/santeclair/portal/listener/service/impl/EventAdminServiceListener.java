package santeclair.portal.listener.service.impl;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_DATA;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_DATA_TYPE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_SOURCE;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.ServiceEvent;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import santeclair.portal.listener.service.AbstractPortalServiceListener;

import com.google.common.base.Preconditions;

@Component
public class EventAdminServiceListener extends AbstractPortalServiceListener<EventAdmin> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventAdminServiceListener.class);

    private final List<Publisher<?, ?>> publishers = new ArrayList<>();

    private EventAdmin service;

    @Override
    public void serviceRegistered(EventAdmin service, ServiceEvent serviceEvent) {
        this.service = service;
    }

    @Override
    public void serviceUnregistering(EventAdmin service, ServiceEvent serviceEvent) {
        this.service = null;
    }

    public <SOURCE, DATA> Publisher<SOURCE, DATA> registerPublisher(String topic, SOURCE source) {
        LOGGER.info("Create a new Publisher");
        Publisher<SOURCE, DATA> publisher = new PublisherImpl<>(topic, this, source);
        publishers.add(publisher);
        return publisher;
    }

    public void unregisterPublisher(Publisher<?, ?>... publishers) {
        LOGGER.info("Destroying publishers");
        for (Publisher<?, ?> publisher : publishers) {
            this.publishers.remove(publisher);
        }
    }

    public interface Publisher<SOURCE, DATA> {

        public void publishEvent(String eventName, Dictionary<String, Object> dictionary, boolean synchronous);

        public void publishEventSynchronously(String eventName, Dictionary<String, Object> dictionary);

        public void publishEventAsynchronously(String eventName, Dictionary<String, Object> dictionary);

        public void publishEventData(String eventName, DATA data, boolean synchronous);

        public void publishEventDataSynchronously(String eventName, DATA data);

        public void publishEventDataAsynchronously(String eventName, DATA data);

    }

    private class PublisherImpl<SOURCE, DATA> implements Publisher<SOURCE, DATA> {

        private final EventAdminServiceListener eventAdminServiceListener;
        private final String topic;
        private final SOURCE source;

        public PublisherImpl(String topic, EventAdminServiceListener eventAdminServiceListener, SOURCE source) {
            this.eventAdminServiceListener = eventAdminServiceListener;
            this.topic = topic;
            this.source = source;
        }

        @Override
        public void publishEvent(final String eventName, Dictionary<String, Object> dictionary, boolean synchronous) {
            Preconditions.checkArgument(StringUtils.isNotBlank(eventName), "You must set an eventName to send an event");
            LOGGER.info("Publishing event via send");
            dictionary = dictionary == null ? new Hashtable<String, Object>() : dictionary;
            if (eventAdminServiceListener.isServiceRegistered()) {
                dictionary.put(PROPERTY_KEY_EVENT_NAME, eventName);
                dictionary.put(PROPERTY_KEY_EVENT_SOURCE, source);
                Event event = new Event(topic, dictionary);
                if (synchronous) {
                    service.sendEvent(event);
                } else {
                    service.postEvent(event);
                }
            } else {
                LOGGER.warn("You tried to send an event {} on topic {} with dictionary : {} but no {} service is registered in the OSGi context. No event would be publish.",
                                eventName, topic, dictionary,
                                EventAdmin.class);
            }
        }

        @Override
        public void publishEventData(String eventName, DATA data, boolean synchronous) {
            Preconditions.checkArgument(data != null, "To send a data event, data must be set to a value different from null");
            LOGGER.info("Publishing event via sendData");
            Dictionary<String, Object> dictionary = new Hashtable<>();
            dictionary.put(PROPERTY_KEY_EVENT_DATA, data);
            dictionary.put(PROPERTY_KEY_EVENT_DATA_TYPE, data.getClass().getName());
            publishEvent(eventName, dictionary, synchronous);
        }

        @Override
        public void publishEventSynchronously(String eventName, Dictionary<String, Object> dictionary) {
            publishEvent(eventName, dictionary, true);
        }

        @Override
        public void publishEventAsynchronously(String eventName, Dictionary<String, Object> dictionary) {
            publishEvent(eventName, dictionary, false);
        }

        @Override
        public void publishEventDataSynchronously(String eventName, DATA data) {
            publishEventData(eventName, data, true);
        }

        @Override
        public void publishEventDataAsynchronously(String eventName, DATA data) {
            publishEventData(eventName, data, false);
        }

    }

}
