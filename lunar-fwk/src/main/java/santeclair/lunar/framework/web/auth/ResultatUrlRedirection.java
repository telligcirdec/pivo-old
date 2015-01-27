/**
 *
 */
package santeclair.lunar.framework.web.auth;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Résultat de la recherche de l'url de redirection.
 * 
 * @author fmokhtari
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultatUrlRedirection {

    private String url;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
