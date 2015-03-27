package santeclair.portal.listener.service.impl;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_DATA;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_DATA_TYPE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_SOURCE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

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

    /**
     * 
     */
    private static final long serialVersionUID = -335842613390379729L;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventAdminServiceListener.class);

    private final List<Publisher<?>> publishers = new ArrayList<>();

    private EventAdmin service;

    @Override
    public void serviceRegistered(EventAdmin service, ServiceEvent serviceEvent) {
        this.service = service;
    }

    @Override
    public void serviceUnregistering(EventAdmin service, ServiceEvent serviceEvent) {
        this.service = null;
    }

    public <SOURCE, DATA> DataPublisher<SOURCE, DATA> registerDataPublisher(SOURCE source, Class<DATA> dataClazz, String... topics) {
        LOGGER.info("Create a new Publisher");
        DataPublisher<SOURCE, DATA> publisher = new DataPublisherImpl<>(this, source, dataClazz, topics);
        publishers.add(publisher);
        return publisher;
    }

    public <SOURCE, DATA> Publisher<SOURCE> registerPublisher(SOURCE source, String... topics) {
        LOGGER.info("Create a new Publisher");
        Publisher<SOURCE> publisher = new PublisherImpl<>(this, source, topics);
        publishers.add(publisher);
        return publisher;
    }

    public void unregisterPublisher(Publisher<?>... publishers) {
        LOGGER.info("Destroying publishers");
        if (publishers != null && publishers.length > 0) {
            for (Publisher<?> publisher : publishers) {
                if (publisher != null) {
                    this.publishers.remove(publisher);
                }
            }
        }

    }

    public interface Publisher<SOURCE> extends Serializable {

        public void publishEvent(Dictionary<String, Object> dictionary, boolean synchronous);

        public void publishEventSynchronously(Dictionary<String, Object> dictionary);

        public void publishEventAsynchronously(Dictionary<String, Object> dictionary);

    }

    public interface DataPublisher<SOURCE, DATA> extends Serializable, Publisher<SOURCE> {

        public void publishEventData(DATA data, boolean synchronous);

        public void publishEventDataSynchronously(DATA data);

        public void publishEventDataAsynchronously(DATA data);

        public void publishEventDataAndDictionnary(DATA data, Dictionary<String, Object> props, boolean synchronous);

        public void publishEventDataAndDictionnarySynchronously(DATA data, Dictionary<String, Object> props);

        public void publishEventDataAndDictionnaryAsynchronously(DATA data, Dictionary<String, Object> props);

    }

    private class PublisherImpl<SOURCE> implements Publisher<SOURCE> {

        /**
         * 
         */
        private static final long serialVersionUID = -1356857447344703240L;

        protected final EventAdminServiceListener eventAdminServiceListener;
        protected final String[] topics;
        protected final SOURCE source;

        public PublisherImpl(EventAdminServiceListener eventAdminServiceListener, SOURCE source, String... topics) {
            this.eventAdminServiceListener = eventAdminServiceListener;
            this.topics = topics;
            this.source = source;
        }

        @Override
        public void publishEvent(Dictionary<String, Object> dictionary, boolean synchronous) {
            LOGGER.info("Publishing event via send");
            dictionary = dictionary == null ? new Hashtable<String, Object>() : dictionary;
            if (eventAdminServiceListener.isServiceRegistered()) {
                dictionary.put(PROPERTY_KEY_EVENT_SOURCE, source);
                for (String topic : topics) {
                    Event event = new Event(topic, dictionary);
                    if (synchronous) {
                        service.sendEvent(event);
                    } else {
                        service.postEvent(event);
                    }
                }
            } else {
                LOGGER.warn("You tried to send an event on topic {} with dictionary : {} but no {} service is registered in the OSGi context. No event would be publish.",
                                topics, dictionary,
                                EventAdmin.class);
            }
        }

        @Override
        public void publishEventSynchronously(Dictionary<String, Object> dictionary) {
            publishEvent(dictionary, true);
        }

        @Override
        public void publishEventAsynchronously(Dictionary<String, Object> dictionary) {
            publishEvent(dictionary, false);
        }

    }

    private class DataPublisherImpl<SOURCE, DATA> extends PublisherImpl<SOURCE> implements DataPublisher<SOURCE, DATA> {

        /**
         * 
         */
        private static final long serialVersionUID = -1356857447344703240L;

        private final Class<DATA> dataClazz;

        public DataPublisherImpl(EventAdminServiceListener eventAdminServiceListener, SOURCE source, Class<DATA> dataClazz, String... topics) {
            super(eventAdminServiceListener, source, topics);
            this.dataClazz = dataClazz;
        }

        @Override
        public void publishEvent(Dictionary<String, Object> dictionary, boolean synchronous) {
            LOGGER.info("Publishing event via send");
            dictionary = dictionary == null ? new Hashtable<String, Object>() : dictionary;
            if (eventAdminServiceListener.isServiceRegistered()) {
                dictionary.put(PROPERTY_KEY_EVENT_SOURCE, source);
                for (String topic : topics) {
                    Event event = new Event(topic, dictionary);
                    if (synchronous) {
                        service.sendEvent(event);
                    } else {
                        service.postEvent(event);
                    }
                }
            } else {
                LOGGER.warn("You tried to send an event on topic {} with dictionary : {} but no {} service is registered in the OSGi context. No event would be publish.",
                                topics, dictionary,
                                EventAdmin.class);
            }
        }

        @Override
        public void publishEventData(DATA data, boolean synchronous) {
            LOGGER.info("Publishing event via sendData");
            publishEvent(addDataToDictionary(data, null), synchronous);
        }

        @Override
        public void publishEventSynchronously(Dictionary<String, Object> dictionary) {
            publishEvent(dictionary, true);
        }

        @Override
        public void publishEventAsynchronously(Dictionary<String, Object> dictionary) {
            publishEvent(dictionary, false);
        }

        @Override
        public void publishEventDataSynchronously(DATA data) {
            publishEventData(data, true);
        }

        @Override
        public void publishEventDataAsynchronously(DATA data) {
            publishEventData(data, false);
        }

        @Override
        public void publishEventDataAndDictionnary(DATA data, Dictionary<String, Object> props, boolean synchronous) {
            publishEvent(addDataToDictionary(data, props), synchronous);
        }

        @Override
        public void publishEventDataAndDictionnarySynchronously(DATA data, Dictionary<String, Object> props) {
            publishEvent(addDataToDictionary(data, props), true);
        }

        @Override
        public void publishEventDataAndDictionnaryAsynchronously(DATA data, Dictionary<String, Object> props) {
            publishEvent(addDataToDictionary(data, props), false);
        }

        private Dictionary<String, Object> addDataToDictionary(DATA data, Dictionary<String, Object> props) {
            Preconditions.checkArgument(data != null, "To send a data event, data must be set to a value different from null");
            props = props != null ? props : new Hashtable<String, Object>();
            props.put(PROPERTY_KEY_EVENT_DATA, data);
            props.put(PROPERTY_KEY_EVENT_DATA_TYPE, dataClazz.getName());
            return props;
        }

    }

}
