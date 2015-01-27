package santeclair.lunar.framework.web.redirect.impl;

import javax.annotation.PostConstruct;

import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import santeclair.lunar.framework.web.auth.DemandeUrlRedirection;
import santeclair.lunar.framework.web.auth.JetonSSO;
import santeclair.lunar.framework.web.auth.ResultatUrlRedirection;
import santeclair.lunar.framework.web.redirect.RedirectionWebClient;

import com.google.common.annotations.VisibleForTesting;

@Component
public class RedirectionWebClientImpl implements RedirectionWebClient {

    private Logger LOGGER = LoggerFactory.getLogger(RedirectionWebClientImpl.class);

    /**
     * La fabrique de clients HTTP pour le web service de récupération de l'url de redirection
     */
    private JAXRSClientFactoryBean jaxrsClientFactoryBeanRedirection;

    @Value("#{generalProperties['webext.redirection.ws.url']}")
    private String redirectionWsUrl;

    @PostConstruct
    public void init() throws Exception {
        if (jaxrsClientFactoryBeanRedirection == null) {
            jaxrsClientFactoryBeanRedirection = new JAXRSClientFactoryBean();
            jaxrsClientFactoryBeanRedirection.setThreadSafe(true);
            jaxrsClientFactoryBeanRedirection.setAddress(redirectionWsUrl);
        }
    }

    /** {@inheritDoc} */
    public ResultatUrlRedirection recupererUrlRedirection(String idAppliCible, String idAppliOrigine, JetonSSO jetonSSO,
                    String idPart, String perso, String[] listeOptions) {

        DemandeUrlRedirection demandeUrlRedirection = new DemandeUrlRedirection();
        demandeUrlRedirection.setIdApplicationCible(idAppliCible);
        demandeUrlRedirection.setIdApplicationOrigine(idAppliOrigine);
        demandeUrlRedirection.setJetonSSO(jetonSSO);
        demandeUrlRedirection.setPerso(perso);
        demandeUrlRedirection.setListeOptions(listeOptions);

        LOGGER.debug("Début appel web service webext.RedirectionWebService.recupererUrlRedirection({})", demandeUrlRedirection.toString());

        WebClient webClient = jaxrsClientFactoryBeanRedirection.createWebClient();
        webClient.path("/organismes/" + idPart + "/urlRedirection");
        ResultatUrlRedirection resultatUrlRedirection = webClient.post(demandeUrlRedirection, ResultatUrlRedirection.class);

        LOGGER.debug("Fin appel web service webext.RedirectionWebService.recupererUrlRedirection({})", resultatUrlRedirection.toString());
        return resultatUrlRedirection;
    }

    /**
     * @param jaxrsClientFactoryBeanRedirection
     */
    @VisibleForTesting
    public void setJaxrsClientFactoryBeanRedirection(JAXRSClientFactoryBean jaxrsClientFactoryBeanRedirection) {
        this.jaxrsClientFactoryBeanRedirection = jaxrsClientFactoryBeanRedirection;
    }

}
