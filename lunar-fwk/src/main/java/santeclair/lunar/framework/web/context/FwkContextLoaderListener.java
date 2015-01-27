package santeclair.lunar.framework.web.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

public class FwkContextLoaderListener implements ServletContextListener {

    private static final String WEB_APP_CTXT = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;

    private static final String SERVICE_LOCATOR_CLASS = "serviceLocatorClass";

    private static final Logger LOGGER = LoggerFactory.getLogger(FwkContextLoaderListener.class);

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {

        // Récupération de la ServletContext
        ServletContext servletContext = event.getServletContext();

        // On récupère le nom de la classe qui va servir de ServiceLocator dans l'application.
        String locatorClassName = servletContext.getInitParameter(SERVICE_LOCATOR_CLASS);

        try {
            // Lecture de la Classe, suivant son nom.
            Class<?> locatorClass = Class.forName(locatorClassName);

            // Création des arguments pour identifier le constructeur qui va être utilisé pour créer une instance du ServiceLocator.
            // Exemple : MyAppServiceLocatorImpl(WebApplicationContext aWebAppCtxt)
            Class<?>[] args = new Class[1];
            args[0] = WebApplicationContext.class;

            // Récupération du constrcuteur identifié par ses arguments.
            Constructor<?> constructor = locatorClass.getConstructor(args);

            // Création des paramètres utilisés pour instancier un ServiceLocator
            Object[] objects = new Object[1];
            objects[0] = servletContext.getAttribute(WEB_APP_CTXT);

            // Instanciation du ServiceLocator par appel du constructeur.
            Object aServiceLocator = constructor.newInstance(objects);

            // Stockage du ServiceLocator dans le contexte applicatif.
            servletContext.setAttribute("serviceLocator", aServiceLocator);

        } catch (ClassNotFoundException e) {
            LOGGER.error("Erreur au moment de la création du ServiceLocator spécifique à l'application", e);

        } catch (SecurityException e) {
            LOGGER.error("Erreur au moment de la création du ServiceLocator spécifique à l'application", e);

        } catch (NoSuchMethodException e) {
            LOGGER.error("Erreur au moment de la création du ServiceLocator spécifique à l'application", e);

        } catch (IllegalArgumentException e) {
            LOGGER.error("Erreur au moment de la création du ServiceLocator spécifique à l'application", e);

        } catch (InstantiationException e) {
            LOGGER.error("Erreur au moment de la création du ServiceLocator spécifique à l'application", e);

        } catch (IllegalAccessException e) {
            LOGGER.error("Erreur au moment de la création du ServiceLocator spécifique à l'application", e);

        } catch (InvocationTargetException e) {
            LOGGER.error("Erreur au moment de la création du ServiceLocator spécifique à l'application", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {

        // Récupération de la ServletContext
        event.getServletContext().setAttribute("serviceLocator", null);
    }
}
