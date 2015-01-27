package santeclair.lunar.framework.dao.jpa.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import javax.persistence.Parameter;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Permet d'aider à la compréhension des erreurs levées lors d'une requete JPA en
 * formattant automatiquement la trace et en levant une excpetion passer en paramètre.
 * 
 * @author cgillet
 * 
 */
public abstract class JpaExceptionHelper {

    private static final Logger LOG = LoggerFactory.getLogger(JpaExceptionHelper.class);

    /**
     * Permet de lever une exception en interprétant la query passée en paramètre
     * et en formattant la sortie JPA pour que celle-ci soit plus claire.
     * 
     * @param jpqlQuery Query JPA sous la forme de string.
     * @param query Query JPA sur la forme de Query.
     * @param initCause L'exception mère, peut être null.
     * @param exceptionThrowing La classe de l'exception que l'on souhaite lever.
     * @return L'exception à renvoyer avec le message clairement formatté et l'initcause associée si non-null.
     */
    public static <T extends Exception, E extends Exception> E addQueryInformation(String jpqlQuery, Query query, T initCause,
                    Class<E> exceptionThrowing) {
        if (exceptionThrowing != null) {
            StringBuffer sbf = new StringBuffer(initCause != null ? initCause.getMessage() : "Request generated an error");
            sbf.append(" : ").append("\n\t - JPQL : ").append(jpqlQuery);
            if (query != null) {
                sbf.append("\n\nWith parameter(s) : ");
                Set<Parameter<?>> parameters = query.getParameters();
                for (Parameter<?> parameter : parameters) {
                    String parameterValue = null;
                    try {
                        parameterValue = query.getParameterValue(parameter).toString();
                    } catch (IllegalStateException ise) {
                        LOG.warn("Parameter {} has not been bound.", parameter.getName());
                        LOG.warn("", ise);
                    }
                    sbf.append("\n\t - ").append(parameter.getName()).append(" : ").append(parameterValue);
                }
            }

            String message = "Une erreur est survenue :";
            try {
                Constructor<E> constructor = exceptionThrowing.getConstructor(String.class);
                E instance = constructor.newInstance(sbf.toString());
                instance.initCause(initCause);

                return instance;
            } catch (SecurityException e1) {
                LOG.error(message, e1);

            } catch (NoSuchMethodException e1) {
                LOG.error(message, e1);

            } catch (IllegalArgumentException e1) {
                LOG.error(message, e1);

            } catch (InstantiationException e1) {
                LOG.error(message, e1);

            } catch (IllegalAccessException e1) {
                LOG.error(message, e1);

            } catch (InvocationTargetException e1) {
                LOG.error(message, e1);
            }
        }

        return null;
    }

}
