package santeclair.lunar.framework.formatter.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import santeclair.lunar.framework.formatter.IFormatter;

/**
 * Cette classe permet de formatter un String contenant des balises EL du type "${test}" avec une propriété du système ayant pour clef le contenu de
 * cette balise.<br>
 * <br>
 * <br>
 * Exemple :<br>
 * <br>
 * String toFormat = "ceci est un ${test}"<br>
 * Propriété sytème ayant pour clef "test" = "bonjour"<br>
 * String formatté = "ceci est un bonjour"
 * 
 * @author cgillet
 * 
 */
public class ElToSystemPropertyFormatter implements IFormatter<String, String> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ElToSystemPropertyFormatter.class);

    private static final String REGEX_EL = "\\$\\{((.*?)?)\\}";

    /** {@inheritDoc} */
    public final String format(String toFormat) {

        String formatted = toFormat;

        Pattern pattern = Pattern.compile(REGEX_EL);
        Matcher matcher = pattern.matcher(toFormat);

        Map<String, String> systemPorpertiesMap = new HashMap<String, String>();

        boolean found = false;
        while (matcher.find()) {
            LOGGER.debug("matcher.groupCount() : " + matcher.groupCount());
            LOGGER.debug("I found the text " + matcher.group() + " starting at " + "index " + matcher.start() + " and ending at index "
                            + matcher.end());

            if (matcher.groupCount() == 2) {
                LOGGER.debug("matcher.group(matcher.groupCount() - 1) : " + matcher.group(matcher.groupCount() - 1));
                String systemPropertyKey = matcher.group(matcher.groupCount() - 1);
                String systemProperty = System.getProperty(systemPropertyKey);
                LOGGER.debug("systemProperty : " + systemProperty);
                systemPorpertiesMap.put(systemPropertyKey, systemProperty);
                found = true;
            }

        }
        if (!found) {
            LOGGER.warn("No match found.");
        }

        if (!systemPorpertiesMap.isEmpty()) {
            Set<String> keySet = systemPorpertiesMap.keySet();
            for (String key : keySet) {
                String elBalise = "\\$\\{" + key + "\\}";
                if (systemPorpertiesMap.get(key) != null) {
                    formatted = formatted.replaceAll(elBalise, systemPorpertiesMap.get(key));
                } else {
                    throw new IllegalArgumentException("Error occurs when formatting string : " + toFormat + " with pattern : " + REGEX_EL
                                    + " => System property value for key : " + key + " is null.");
                }
            }
        }
        LOGGER.debug(formatted);

        return formatted;
    }

}
