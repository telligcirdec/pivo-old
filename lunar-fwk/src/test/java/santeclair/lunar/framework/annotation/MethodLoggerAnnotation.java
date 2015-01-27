package santeclair.lunar.framework.annotation;

import santeclair.lunar.framework.annotation.MethodLogger.Detail;
import santeclair.lunar.framework.annotation.MethodLogger.Level;
import santeclair.lunar.framework.annotation.MethodLogger.OutputType;

public class MethodLoggerAnnotation {

    public static final String MESSAGE = "test";
    public static final String ERROR_MESSAGE = "error";
    
    @MethodLogger
    public void methodLoggerMethodForUnitTest(){    
    }
    
    @MethodLogger(detail=Detail.LONG)
    public void methodLoggerMethodForUnitTestWithDetailLong(){
    }
    
    @MethodLogger(outputType=OutputType.RETURN)
    public void methodLoggerMethodForUnitTestWithOutputTypeReturn(){
    }
    
    @MethodLogger(message=MESSAGE)
    public void methodLoggerMethodForUnitTestWithMessageTest(){
    }
    
    @MethodLogger(errorMessage=ERROR_MESSAGE)
    public void methodLoggerMethodForUnitTestWithErrorMessageError(){
    }
    
    @MethodLogger(messageLevel=Level.ERROR)
    public void methodLoggerMethodForUnitTestWithMessageLevelError(){
    }
    
}
