package santeclair.lunar.framework.personnalisation.client.impl;

import javax.annotation.PostConstruct;

import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import santeclair.lunar.framework.personnalisation.bean.CharteBean;
import santeclair.lunar.framework.personnalisation.bean.ListePersonnalisationResponseBean;
import santeclair.lunar.framework.personnalisation.bean.RetourPersonnalisationResponseBean;
import santeclair.lunar.framework.personnalisation.client.PersonnalisationWebserviceClient;

import com.google.common.annotations.VisibleForTesting;

@Component
public class PersonnalisationWebserviceClientImpl implements PersonnalisationWebserviceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonnalisationWebserviceClientImpl.class);

    @Value("#{persoProperties['owap.ws.url']}")
    private String owapWsUrl;

    @Value("#{persoProperties['owap.ws.methode.liste']}")
    private String owapWsMethodeListe;

    @Value("#{persoProperties['owap.ws.methode.details']}")
    private String owapWsMethodeDetails;

    @Value("#{persoProperties['owap.ws.methode.defaut']}")
    private String owapWsMethodeCourrier;

    /**
     * La fabrique de clients HTTP pour le web service.
     */
    private WebClient webClient;

    /* ======================================================= *
     *                      code métier 
     * ======================================================= */

    @PostConstruct
    public void initFactory() {
        JAXRSClientFactoryBean jaxrsClientFactoryBean = new JAXRSClientFactoryBean();
        jaxrsClientFactoryBean.setThreadSafe(true);
        jaxrsClientFactoryBean.setAddress(owapWsUrl);
        webClient = jaxrsClientFactoryBean.createWebClient();
    }

    /**
     * {@inheritDoc}
     */
    public ListePersonnalisationResponseBean getListePersonnalisation(String idApp) {
        LOGGER.info("Contact de : " + owapWsMethodeListe + idApp + "/");
        try {
            webClient.path(owapWsMethodeListe + idApp + "/");
            return webClient.get(ListePersonnalisationResponseBean.class);
        } finally {
            webClient.reset();
        }
    }

    /**
     * {@inheritDoc}
     */
    public RetourPersonnalisationResponseBean getDetailPersonnalisation(String idApp, String idCharte) {
        LOGGER.info("Contact de : " + owapWsMethodeDetails + idApp + "/" + idCharte + "/");
        try {
            webClient.path(owapWsMethodeDetails + idApp + "/" + idCharte + "/");
            return webClient.get(RetourPersonnalisationResponseBean.class);
        } finally {
            webClient.reset();
        }
    }

    /**
     * {@inheritDoc}
     */
    public CharteBean rechercheCharteCourrierOrganisme(String codeOrganisme) {
        LOGGER.info("Contact de : " + owapWsMethodeCourrier + codeOrganisme);
        try {
            webClient.path(owapWsMethodeCourrier + codeOrganisme);
            return webClient.get(CharteBean.class);
        } finally {
            webClient.reset();
        }
    }

    /* ======================================================= *
     *                      getters & setters 
     * ======================================================= */
    /**
     * Méthode d'écriture de l'attribut : {@link PersonnalisationWebserviceClientImpl#webClient}
     * 
     * @param webClient
     */
    @VisibleForTesting
    void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}
