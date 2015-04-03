package santeclair.reclamation.osgi.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Modified;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import santeclair.portal.bundle.utils.config.UrlWebservicesConfig;
import santeclair.reclamation.demande.document.dto.DemandeDocumentCriteresDto;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;
import santeclair.reclamation.demande.document.webservice.DemandeDocumentWebService;

/**
 * Implémentation du service OSGi mettant en place un proxy vers le webservice de demande de document.
 */
@Component
@Provides
@Instantiate
public class DemandeDocumentOsgiServiceImpl implements DemandeDocumentWebService {

    @Requires(specification = UrlWebservicesConfig.class)
    private UrlWebservicesConfig urlWebservicesConfig;

    /** Proxy client du webservice de demande de document. */
    private DemandeDocumentWebService demandeDocumentWebService;

    /**
     * Création du proxy client du webservice de gestion des modele
     */
    @Validate
    public void init() {
        demandeDocumentWebService = JAXRSClientFactory.create(urlWebservicesConfig.getReclamationUrl(), DemandeDocumentWebService.class,
                        Collections.singletonList(new JacksonJaxbJsonProvider()), true);
    }

    @Modified
    public void modifiedUrlWebservicesConfig(UrlWebservicesConfig urlWebservicesConfig) {
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DemandeDocumentDto enregistrerDemandeDocument(DemandeDocumentDto demandeDocumentDto) {
        return demandeDocumentWebService.enregistrerDemandeDocument(demandeDocumentDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DemandeDocumentDto> rechercherDemandesDocumentParCriteres(DemandeDocumentCriteresDto criteresDto) {
        return demandeDocumentWebService.rechercherDemandesDocumentParCriteres(criteresDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DemandeDocumentDto rechercherDemandeDocumentParId(Integer identifant) {
        return demandeDocumentWebService.rechercherDemandeDocumentParId(identifant);
    }
}
