package santeclair.portal.webapp.listener.service;

import org.osgi.framework.ServiceListener;

public interface PortalServiceListener<T> extends ServiceListener {

    /**
     * Renvoie un filtre pour limiter la recherche le service sur lequel d�clench�. Si renvoie null, alors la class du service � �couter sera
     * pris par d�faut. Si vous d�finissez un filtre suppl�mentaire (la m�thode renvoie autre chose que null), le filtre construit sera donc
     * &(objectClass=getServiceClass().getName())(votreFiltre).
     * 
     * @return
     */
    String getFilter();

    /**
     * Cette m�thode sera d�clench�e lorsque le service s'enregistre dans le contexte OSGi.
     * 
     * @param serviceEvent ServiceEvent d'enregistrement du service.
     */
    void serviceRegistered(T service);

    /**
     * Cette m�thode sera d�clench�e lorsque le service se d�senregistre du contexte OSGi.
     * 
     * @param serviceEvent ServiceEvent d'enregistrement du service.
     */
    void serviceUnregistering(T service);

    /**
     * Cette m�thode est d�clench�e lorsque les propri�t�s d'un service enregistr� dans le contexte OSGi change (changement du dictionnaire)
     * 
     * @param serviceEvent ServiceEvent d'enregistrement du service.
     */
    void serviceModified(T service);

    /**
     * Cette m�thode est d�clanch� lorsque les propri�t�s d'un service enregistr� dans le contexte OSGi change (changement du dictionnaire) et
     * que les nouvelles priopri�t�s ne corresspondent plus au service.
     * 
     * @param serviceEvent
     */
    void serviceModifiedEndmatch(T service);

}
