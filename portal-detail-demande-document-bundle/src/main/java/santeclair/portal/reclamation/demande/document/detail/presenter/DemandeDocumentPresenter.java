package santeclair.portal.reclamation.demande.document.detail.presenter;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_TABS;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.osgi.service.event.Event;

import santeclair.portal.event.utils.TabsEventUtil;
import santeclair.portal.reclamation.demande.document.detail.EventConstant;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;
import santeclair.reclamation.demande.document.webservice.DemandeDocumentWebService;

@Component
@Instantiate
public class DemandeDocumentPresenter {

    @Requires
    private DemandeDocumentWebService demandeDocumentWebService;

    @Publishes(name = "presenterPublisher", topics = EventConstant.TOPIC_DEMANDE_DOCUMENT, synchronous = true)
    private Publisher presenterPublisher;
    
    @Publishes(name = "tabsComponentPublisher", topics = TOPIC_TABS, synchronous = true)
    private Publisher tabsComponentPublisher;
    
    @Subscriber(name = EventConstant.EVENT_RECUPERER_DEMANDE_DOCUMENT, filter = "(&(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*)("
                    + PROPERTY_KEY_TAB_HASH + "=*)(" + PROPERTY_KEY_EVENT_NAME + "=" + EventConstant.EVENT_RECUPERER_DEMANDE_DOCUMENT
                    + ")(" + EventConstant.PROPERTY_KEY_ID_DEMANDE_DOCUMENT + "=*))", topics = EventConstant.TOPIC_DEMANDE_DOCUMENT)
    public void recupererDemandeDocumentParId(Event event) {

        Integer idDemandeDocument = (Integer) event.getProperty(EventConstant.PROPERTY_KEY_ID_DEMANDE_DOCUMENT);

        DemandeDocumentDto demandeDocumentDto = demandeDocumentWebService.rechercherDemandeDocumentParId(idDemandeDocument);

        Dictionary<String, Object> props = new Hashtable<>();
        props.put(PROPERTY_KEY_EVENT_NAME, EventConstant.EVENT_RECUPERER_DEMANDE_DOCUMENT_OK);
        props.put(PROPERTY_KEY_PORTAL_SESSION_ID, event.getProperty(PROPERTY_KEY_PORTAL_SESSION_ID));
        props.put(PROPERTY_KEY_TAB_HASH, event.getProperty(PROPERTY_KEY_TAB_HASH));
        props.put(EventConstant.PROPERTY_KEY_DEMANDE_DOCUMENT, demandeDocumentDto);
        presenterPublisher.send(props);
    }
    
    @Subscriber(name = EventConstant.EVENT_ENREGISTRER_DEMANDE_DOCUMENT, filter = "(&(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*)("
                    + PROPERTY_KEY_TAB_HASH + "=*)(" + PROPERTY_KEY_EVENT_NAME + "=" + EventConstant.EVENT_ENREGISTRER_DEMANDE_DOCUMENT
                    + ")(" + EventConstant.PROPERTY_KEY_DEMANDE_DOCUMENT + "=*))", topics = EventConstant.TOPIC_DEMANDE_DOCUMENT)
    public void enregistrerDemandeDocument(Event event) {

        DemandeDocumentDto demandeDocumentDto = (DemandeDocumentDto) event.getProperty(EventConstant.PROPERTY_KEY_DEMANDE_DOCUMENT);
        demandeDocumentWebService.enregistrerDemandeDocument(demandeDocumentDto);
        
        tabsComponentPublisher.send(TabsEventUtil.getCloseTabsProps((String) event.getProperty(PROPERTY_KEY_PORTAL_SESSION_ID), (Integer) event.getProperty(PROPERTY_KEY_TAB_HASH)));
    }

}
