package santeclair.lunar.framework.annotation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import santeclair.lunar.framework.annotation.MethodLogger.Detail;
import santeclair.lunar.framework.annotation.MethodLogger.Level;
import santeclair.lunar.framework.annotation.MethodLogger.OutputType;

public class UnitMethodLoggerTest {

    private MethodLoggerAnnotation methodLoggerAnnotation;

    @Before
    public void init() {
        methodLoggerAnnotation = new MethodLoggerAnnotation();
    }

    @Test
    public void testMethodLogger() throws SecurityException, NoSuchMethodException {

        MethodLogger methodLogger = methodLoggerAnnotation.getClass().getMethod(MethodLoggerAnnotation_.methodLoggerMethodForUnitTest,
                        (Class[]) null).getAnnotation(MethodLogger.class);

        assertThat(methodLogger.detail(), is(Detail.SHORT));
        assertThat(methodLogger.errorLevel(), is(Level.ERROR));
        assertThat(methodLogger.errorMessage(), is(""));
        assertThat(methodLogger.message(), is(""));
        assertThat(methodLogger.messageLevel(), is(Level.DEBUG));
        assertThat(methodLogger.outputType(), is(OutputType.ARGS_AND_RETURN));
    }

    @Test
    public void testMethodLoggerWithMessageLevelError() throws SecurityException, NoSuchMethodException {

        MethodLogger methodLogger = methodLoggerAnnotation.getClass().getMethod(
                        MethodLoggerAnnotation_.methodLoggerMethodForUnitTestWithMessageLevelError, (Class[]) null).getAnnotation(
                        MethodLogger.class);

        assertThat(methodLogger.detail(), is(Detail.SHORT));
        assertThat(methodLogger.errorLevel(), is(Level.ERROR));
        assertThat(methodLogger.errorMessage(), is(""));
        assertThat(methodLogger.message(), is(""));
        assertThat(methodLogger.messageLevel(), is(Level.ERROR));
        assertThat(methodLogger.outputType(), is(OutputType.ARGS_AND_RETURN));
    }

    @Test
    public void testMethodLoggerWithDetailLong() throws SecurityException, NoSuchMethodException {

        MethodLogger methodLogger = methodLoggerAnnotation.getClass().getMethod(
                        MethodLoggerAnnotation_.methodLoggerMethodForUnitTestWithDetailLong, (Class[]) null).getAnnotation(MethodLogger.class);

        assertThat(methodLogger.detail(), is(Detail.LONG));
        assertThat(methodLogger.errorLevel(), is(Level.ERROR));
        assertThat(methodLogger.errorMessage(), is(""));
        assertThat(methodLogger.message(), is(""));
        assertThat(methodLogger.messageLevel(), is(Level.DEBUG));
        assertThat(methodLogger.outputType(), is(OutputType.ARGS_AND_RETURN));
    }

    @Test
    public void testMethodLoggerWithErrorMessageError() throws SecurityException, NoSuchMethodException {

        MethodLogger methodLogger = methodLoggerAnnotation.getClass().getMethod(
                        MethodLoggerAnnotation_.methodLoggerMethodForUnitTestWithErrorMessageError, (Class[]) null).getAnnotation(
                        MethodLogger.class);

        assertThat(methodLogger.detail(), is(Detail.SHORT));
        assertThat(methodLogger.errorLevel(), is(Level.ERROR));
        assertThat(methodLogger.errorMessage(), is(MethodLoggerAnnotation.ERROR_MESSAGE));
        assertThat(methodLogger.message(), is(""));
        assertThat(methodLogger.messageLevel(), is(Level.DEBUG));
        assertThat(methodLogger.outputType(), is(OutputType.ARGS_AND_RETURN));
    }

    @Test
    public void testMethodLoggerWithMessageTest() throws SecurityException, NoSuchMethodException {

        MethodLogger methodLogger = methodLoggerAnnotation.getClass().getMethod(
                        MethodLoggerAnnotation_.methodLoggerMethodForUnitTestWithMessageTest, (Class[]) null).getAnnotation(MethodLogger.class);

        assertThat(methodLogger.detail(), is(Detail.SHORT));
        assertThat(methodLogger.errorLevel(), is(Level.ERROR));
        assertThat(methodLogger.errorMessage(), is(""));
        assertThat(methodLogger.message(), is(MethodLoggerAnnotation.MESSAGE));
        assertThat(methodLogger.messageLevel(), is(Level.DEBUG));
        assertThat(methodLogger.outputType(), is(OutputType.ARGS_AND_RETURN));
    }

    @Test
    public void testMethodLoggerWithOutputTypeReturn() throws SecurityException, NoSuchMethodException {

        MethodLogger methodLogger = methodLoggerAnnotation.getClass().getMethod(
                        MethodLoggerAnnotation_.methodLoggerMethodForUnitTestWithOutputTypeReturn, (Class[]) null).getAnnotation(
                        MethodLogger.class);

        assertThat(methodLogger.detail(), is(Detail.SHORT));
        assertThat(methodLogger.errorLevel(), is(Level.ERROR));
        assertThat(methodLogger.errorMessage(), is(""));
        assertThat(methodLogger.message(), is(""));
        assertThat(methodLogger.messageLevel(), is(Level.DEBUG));
        assertThat(methodLogger.outputType(), is(OutputType.RETURN));
    }

}
