package santeclair.portal.bundle.utils;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class ParametersReaderTest {

    @Test
    public void getParameters() {
        String uriTest1 = "param1/X/param2/Y";
        String uriTest2 = "param1/X/param2/Y/param3";

        Map<String, String> result1 = ParametersReader.getParameters(uriTest1);
        assertEquals("X", result1.get("param1"));
        assertEquals("Y", result1.get("param2"));

        Map<String, String> result2 = ParametersReader.getParameters(uriTest2);
        assertEquals("X", result2.get("param1"));
        assertEquals("Y", result2.get("param2"));
        assertEquals(null, result2.get("param3"));
    }
}
