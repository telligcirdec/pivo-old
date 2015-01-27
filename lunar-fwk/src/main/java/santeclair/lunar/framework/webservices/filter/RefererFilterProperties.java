package santeclair.lunar.framework.webservices.filter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefererFilterProperties {

    private static final Logger LOGGER = LoggerFactory
                    .getLogger(RefererFilterProperties.class);

    public static Map<String, List<String>> propsToMap(Properties props,
                    String pattern) {

        LOGGER.info("call propsToMap : {}, {}", props, pattern);

        Map<String, List<String>> map = new HashMap<String, List<String>>();
        Pattern httpMethodPattern = Pattern.compile(pattern);
        for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements();) {
            String key = (String) en.nextElement();
            String value = props.getProperty(key);
            if (value != null) {
                Matcher idMatcher = httpMethodPattern.matcher(key);
                if (idMatcher.matches()) {
                    String mapKey = idMatcher.group(1);
                    List<String> valueList = new ArrayList<>();
                    StringTokenizer stringTokenizer = new StringTokenizer(
                                    value, ",");
                    while (stringTokenizer.hasMoreTokens()) {
                        valueList.add(stringTokenizer.nextToken());
                    }
                    map.put(mapKey, valueList);
                }
            }
        }
        return map;
    }
}
