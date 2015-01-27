package santeclair.lunar.framework.aspect.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;

@Aspect
@Component
public class ServiceExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    private WrappedExceptionResponse responseException;

    /**
     * Cette méthode permet de pallier au fait que, après migration vers tc7, les webservices retournent un objet
     * de type InternalServerErrorException dans lequel on perd la cause mère.
     * Elle Intercèpte l'exception levée par un webservice et créé une réponse exploitable
     * par le client et contenant le détail du message associé à l'exception.
     * le WebApplicationException (qui gère les exceptions REST) est encapsulé dans un UnifiedWebServiceException
     * (qui gère les exceptions SOAP).
     */
    @AfterThrowing(pointcut = "execution(* santeclair..*.webservice..*.*(..))", throwing = "e")
    public void myAfterThrowing(JoinPoint joinPoint, Throwable e) {

        LOGGER.debug("Exception interceptée par l'aspect ServiceExceptionHandler lors de l'appel du web service");

        ResponseBuilderImpl builder = new ResponseBuilderImpl();
        builder.status(Response.Status.INTERNAL_SERVER_ERROR);
        String detailMessageException = ExceptionUtils.getFullStackTrace(e);
        responseException = new WrappedExceptionResponse();
        if (IllegalArgumentException.class.isAssignableFrom(e.getClass()) || IllegalStateException.class.isAssignableFrom(e.getClass())) {
            responseException.setException(e);
        }
        else {
            InfoException infoException = findInfoException(e);
            if (infoException != null) {
                responseException.setException(new RuntimeException(e.getMessage(), e));
                String info = infoException.getInfo();
                info = StringUtils.isBlank(info) ? "! No info" : info;
                responseException.setInfo(info);
            }
            else {
                responseException.setException(new RuntimeException(e.getMessage(), e));
            }
        }

        builder.entity(responseException);
        Response response = builder.build();

        LOGGER.debug("Fin d'exécution de l'aspect");
        LOGGER.error(detailMessageException);
        throw new UnifiedWebServiceException(detailMessageException, response);
    }

    /**
     * Permet de récupérer la cause mère au format String en fonction de la classe
     * */
    public static Throwable getRootException(WebApplicationException webApplicationException) {
        Response response = webApplicationException.getResponse();
        WrappedExceptionResponse messagErreur = response.readEntity(WrappedExceptionResponse.class);

        return messagErreur.getException();
    }

    @VisibleForTesting
    WrappedExceptionResponse getWrappedExceptionResponse() {
        return responseException;
    }

    /**
     * Fonction recursive pour rechercher une cause dans les exceptions filles
     * de type InfoException.
     * 
     * @param throwable Exception a controler
     * @return L'exception de type InfoException ou null si aucune.
     */
    private InfoException findInfoException(Throwable throwable) {
        if (throwable != null) {
            if (InfoException.class.isAssignableFrom(throwable.getClass())) {
                return (InfoException) throwable;
            } else {
                return findInfoException(throwable.getCause());
            }
        }
        return null;
    }

}
