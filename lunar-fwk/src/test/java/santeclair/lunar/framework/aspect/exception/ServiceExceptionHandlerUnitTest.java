package santeclair.lunar.framework.aspect.exception;

import org.junit.Assert;
import org.junit.Test;

public class ServiceExceptionHandlerUnitTest {

    @Test
    public void testInfoException() {
        ServiceExceptionHandler exceptionHandler = new ServiceExceptionHandler();
        try {
            exceptionHandler.myAfterThrowing(null, new TestInfoException());
        } catch (UnifiedWebServiceException unifiedWebServiceException) {
            // On ne fait rien ici
        }
        WrappedExceptionResponse wrappedExceptionResponse = exceptionHandler.getWrappedExceptionResponse();
        Assert.assertEquals("Ceci est un test", wrappedExceptionResponse.getInfo());
    }

    @Test
    public void testNoInfoException() {
        ServiceExceptionHandler exceptionHandler = new ServiceExceptionHandler();
        try {
            exceptionHandler.myAfterThrowing(null, new TestNoInfoException());
        } catch (UnifiedWebServiceException unifiedWebServiceException) {
            // On ne fait rien ici
        }
        WrappedExceptionResponse wrappedExceptionResponse = exceptionHandler.getWrappedExceptionResponse();
        Assert.assertEquals("! Default Value : this exception does not implement santeclair.lunar.framework.aspect.exception.InfoException", wrappedExceptionResponse.getInfo());
    }
}
