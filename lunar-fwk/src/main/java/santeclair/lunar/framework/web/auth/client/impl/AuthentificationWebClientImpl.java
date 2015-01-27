/**
 * 
 */
package santeclair.lunar.framework.web.auth.client.impl;

import javax.annotation.PostConstruct;

import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import santeclair.lunar.framework.web.auth.CodeRetour;
import santeclair.lunar.framework.web.auth.DemandeAuthentificationJeton;
import santeclair.lunar.framework.web.auth.JetonSSO;
import santeclair.lunar.framework.web.auth.ResultatAuthentificationJeton;
import santeclair.lunar.framework.web.auth.client.AuthentificationWebClient;

/**
 * @author jfourmond
 * 
 */
@Service
public class AuthentificationWebClientImpl implements AuthentificationWebClient {

    /**
     * Le début de l'URL (sans le path) de webext-webservices.
     * Ex : http://localhost:8080/webext-ws/internal/rest.
     */
    @Value("#{validationProperties['webext.ws.url']}")
    private String webextWsUrl;

    /**
     * La fabrique de clients HTTP pour le web service webext de validation du jeton.
     */
    private JAXRSClientFactoryBean jaxrsClientFactoryBean;

    /* ======================================================= *
     *                      code métier 
     * ======================================================= */

    /**
     * Initialisation de la fabrique de WebClient.
     */
    @PostConstruct
    public void init() throws Exception {
        jaxrsClientFactoryBean = new JAXRSClientFactoryBean();
        jaxrsClientFactoryBean.setThreadSafe(true);
        jaxrsClientFactoryBean.setAddress(webextWsUrl);
    }

    /** {@inheritDoc} */
    public JetonSSO validerJetonMobile(String idPart, String idJeton, String idAppli) {

        DemandeAuthentificationJeton demande = new DemandeAuthentificationJeton(idJeton, idAppli, idPart, null, null, null);
        WebClient webClient = jaxrsClientFactoryBean.createWebClient();
        webClient.path("/validerJetonMobile");
        ResultatAuthentificationJeton resultatAuthentificationJeton = webClient.post(demande, ResultatAuthentificationJeton.class);
        JetonSSO jetonSSO = resultatAuthentificationJeton.getJetonSSO();
        CodeRetour codeRetour = resultatAuthentificationJeton.getCodeRetour();
        jetonSSO.setCodeRetour(codeRetour);

        return jetonSSO;
    }

}
