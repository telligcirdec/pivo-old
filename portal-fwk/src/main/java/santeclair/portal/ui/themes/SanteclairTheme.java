package santeclair.portal.ui.themes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import com.vaadin.ui.themes.ChameleonTheme;

public class SanteclairTheme extends ChameleonTheme {

    public static String slugify(String input) {
        if (input == null || input.length() == 0)
            return "";
        String toReturn = normalize(input);
        toReturn = toReturn.replace(" ", "-");
        toReturn = toReturn.toLowerCase();
        try {
            toReturn = URLEncoder.encode(toReturn, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    private static String normalize(String input) {
        if (input == null || input.length() == 0)
            return "";
        return Normalizer.normalize(input, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
