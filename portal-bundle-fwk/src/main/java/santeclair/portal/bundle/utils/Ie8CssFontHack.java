package santeclair.portal.bundle.utils;

import com.vaadin.server.Page;

/**
 * Hack pour l'utilisation de web font avec ie8.
 * A appeler des qu'un composant à afficher utilise un web font.
 * 
 * @author tsensebe
 * 
 */
public class Ie8CssFontHack {

    public static void showFonts() {
        Page.getCurrent().getJavaScript().execute(
                        "var css = ':before,:after{content:none !important'," +
                                        " head = document.getElementsByTagName('head')[0]," +
                                        " style = document.createElement('style');" +
                                        " style.type = 'text/css';" +
                                        " if (style.styleSheet) {" +
                                        "     style.styleSheet.cssText = css;" +
                                        " } else {" +
                                        "     style.appendChild(document.createTextNode(css));" +
                                        " }" +
                                        " head.appendChild(style);" +
                                        " setTimeout(function(){" +
                                        "     head.removeChild(style);" +
                                        " }, 0);");
    }
}
