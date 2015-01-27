/**
 * 
 */
package santeclair.lunar.framework.util;

import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;

/**
 * Mock pour les fabriques de clients HTTP.
 * 
 * @author jfourmond
 * 
 */
public class JAXRSClientFactoryBeanMock extends JAXRSClientFactoryBean {

    private WebClient webClientMock;

    /** {@inheritDoc} */
    @Override
    public WebClient createWebClient() {
        WebClient.getConfig(webClientMock).setBus(BusFactory.getThreadDefaultBus());
        return webClientMock;
    }

    /**
     * @param webClientMock the webClientMock to set
     */
    public void setWebClientMock(WebClient webClientMock) {
        WebClient.getConfig(webClientMock).setBus(BusFactory.getThreadDefaultBus());
        this.webClientMock = webClientMock;
    }

}
