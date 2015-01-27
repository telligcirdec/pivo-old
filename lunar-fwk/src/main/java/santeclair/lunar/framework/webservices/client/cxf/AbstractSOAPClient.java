package santeclair.lunar.framework.webservices.client.cxf;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.google.common.annotations.VisibleForTesting;

/**
 * Classe abstraite outil de client à un web service SOAP
 * 
 * @author ldelemotte
 * 
 */
public abstract class AbstractSOAPClient<WEBSERVICE> {

    /** Proxy de connexion au webservice. */
    protected JaxWsProxyFactoryBean jaxWsProxyFactoryBean;

    /** Initialisation du client. */
    public void initFactory() {
        jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress(getUrlWebService());
        jaxWsProxyFactoryBean.setServiceClass(getGenericTypeClass());
    }

    /**
     * Permet de récupérer une instance du webservice afin d'en réaliser un appel.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public WEBSERVICE getInstance() {
        return (WEBSERVICE) jaxWsProxyFactoryBean.create();
    }

    /**
     * @param jaxWsProxyFactoryBean the jaxWsProxyFactoryBean to set
     */
    @VisibleForTesting
    protected void setJaxWsProxyFactoryBean(JaxWsProxyFactoryBean jaxWsProxyFactoryBean) {
        this.jaxWsProxyFactoryBean = jaxWsProxyFactoryBean;
    }

    @SuppressWarnings("unchecked")
    private Class<WEBSERVICE> getGenericTypeClass() {
        Type type = this.getClass().getGenericSuperclass();
        if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] parameterizedTypes = parameterizedType.getActualTypeArguments();
            if (parameterizedTypes != null) {
                for (Type typeFromSuperClass : parameterizedTypes) {
                    if (typeFromSuperClass instanceof Class<?>) {
                        return (Class<WEBSERVICE>) typeFromSuperClass;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Permet de définir l'URL d'accès au webservice.
     * 
     * @return URL du webservice
     */
    protected abstract String getUrlWebService();
}
