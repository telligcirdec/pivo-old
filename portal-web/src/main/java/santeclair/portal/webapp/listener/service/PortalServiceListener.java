package santeclair.portal.webapp.listener.service;

import org.osgi.framework.ServiceListener;

public interface PortalServiceListener<T> extends ServiceListener {

    /**
     * Renvoie un filtre pour limiter la recherche le service sur lequel déclenché. Si renvoie null, alors la class du service à écouter sera
     * pris par défaut. Si vous définissez un filtre supplémentaire (la méthode renvoie autre chose que null), le filtre construit sera donc
     * &(objectClass=getServiceClass().getName())(votreFiltre).
     * 
     * @return
     */
    String getFilter();

    /**
     * Cette méthode sera déclenchée lorsque le service s'enregistre dans le contexte OSGi.
     * 
     * @param serviceEvent ServiceEvent d'enregistrement du service.
     */
    void serviceRegistered(T service);

    /**
     * Cette méthode sera déclenchée lorsque le service se désenregistre du contexte OSGi.
     * 
     * @param serviceEvent ServiceEvent d'enregistrement du service.
     */
    void serviceUnregistering(T service);

    /**
     * Cette méthode est déclenchée lorsque les propriétés d'un service enregistré dans le contexte OSGi change (changement du dictionnaire)
     * 
     * @param serviceEvent ServiceEvent d'enregistrement du service.
     */
    void serviceModified(T service);

    /**
     * Cette méthode est déclanché lorsque les propriétés d'un service enregistré dans le contexte OSGi change (changement du dictionnaire) et
     * que les nouvelles priopriétés ne corresspondent plus au service.
     * 
     * @param serviceEvent
     */
    void serviceModifiedEndmatch(T service);

}
