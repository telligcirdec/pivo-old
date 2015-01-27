package santeclair.lunar.framework.service.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.transport.http.HTTPTransport;

/**
 * Classe utilitaire de génération d'objets d'appel à un webservice
 * 
 * @author pchaussalet
 */
public final class SoapCallFactory {
    /**
     * Crée un objet d'appel à un webService SOAP renvoyant un bean Java
     * 
     * @param urlService l'URL du webservice distant
     * @param uriService l'URI du webservice distant
     * @param methodeService la méthode du webservice distant à appeler
     * @param classeRetour la classe renvoyée par le webservice distant
     * @return Un objet org.apache.axis.client.Call permettant d'appeler le web service distant
     * @throws ServiceException
     */
    public static Call creerAppelSoap(URL urlService, String uriService, String methodeService, Style styleService, Class<?> classeRetour, String soapAction) throws ServiceException {
        Call appelSoap = initAppelSoap(urlService, uriService, methodeService, styleService, soapAction);

        // Gestion du bean renvoyé par le webservice
        appelSoap.setReturnType(new javax.xml.namespace.QName(uriService, classeRetour.getName()), classeRetour);
        appelSoap.registerTypeMapping(classeRetour, new javax.xml.namespace.QName(uriService, classeRetour.getName()), org.apache.axis.encoding.ser.BeanSerializerFactory.class,
                        org.apache.axis.encoding.ser.BeanDeserializerFactory.class, false);
        return appelSoap;
    }

    /**
     * Crée un objet d'appel à un webService SOAP renvoyant un type natif SOAP
     * 
     * @param urlService l'URL du webservice distant
     * @param uriService l'URI du webservice distant
     * @param methodeService la méthode du webservice distant à appeler
     * @return Un objet org.apache.axis.client.Call permettant d'appeler le web service distant
     * @throws ServiceException
     */
    public static Call creerAppelSoap(URL urlService, String targetNameSpace, String methodeService, Style styleService, QName typeRetour, String soapAction)
                    throws ServiceException {
        Call appelSoap = initAppelSoap(urlService, targetNameSpace, methodeService, styleService, soapAction);

        // Gestion du type SOAP renvoyé par le webservice
        appelSoap.setReturnType(typeRetour);
        return appelSoap;
    }

    private static Call initAppelSoap(URL urlService, String targetNameSpace, String methodeService, Style styleService, String soapAction) throws ServiceException {
        Call appelSoap = (Call) new Service().createCall();

        // Définition des paramètres génériques d'appel
        appelSoap.setTransport(new HTTPTransport());
        appelSoap.setEncodingStyle(Constants.URI_SOAP11_ENC);

        // Définition des paramètres d'appel techniques
        appelSoap.setTargetEndpointAddress(urlService);
        appelSoap.setOperationName(new QName(targetNameSpace, methodeService));
        appelSoap.setUseSOAPAction(true);
        appelSoap.setSOAPActionURI(soapAction);
        appelSoap.setOperationStyle(styleService);
        appelSoap.setOperationUse(Use.ENCODED);
        appelSoap.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        appelSoap.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        appelSoap.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        return appelSoap;
    }
}
