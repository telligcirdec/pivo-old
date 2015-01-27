package santeclair.lunar.framework.web.jsf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.SessionScope;

/**
 * Classe mère pour les classes de test d'intégration des backing beans JSF.
 * 
 * @author jfourmond
 * 
 */
public class AbstractFacesContextBeanIntegrationTest {

    @Autowired
    protected ApplicationContext applicationContext;

    /**
     * Méthode d'initialisation de mocks.
     */
    protected void setUp() {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        configurableBeanFactory.registerScope("session", new SessionScope());
        configurableBeanFactory.registerScope("request", new RequestScope());
        configurableBeanFactory.registerScope("view", new ViewScopeMock());

        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }

}
