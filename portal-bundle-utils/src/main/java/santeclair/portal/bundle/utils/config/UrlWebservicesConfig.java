package santeclair.portal.bundle.utils.config;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;

@Component(managedservice = "urlWebservices")
@Provides(specifications = UrlWebservicesConfig.class)
@Instantiate
public class UrlWebservicesConfig {

    @Property(name="url.webservice.reclamation")
    private String reclamationUrl;

    public String getReclamationUrl() {
        return reclamationUrl;
    }

}
