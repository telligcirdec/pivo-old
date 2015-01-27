package santeclair.lunar.framework.web.auth;

import javax.xml.bind.annotation.XmlRootElement;

/***
 * Permet de savoir si un idPart � acces � un idCharte et � une idAppli.
 * 
 * @author cgillet
 */
@XmlRootElement
public class ResultatAuthorizationAccess {

    /**
     * D�termine si l'acces est autoris� ou non.<br>
     * True => acces autoris�<br>
     * False => acces interdit.
     */
    private Boolean accessGranted;

    /**
     * accesGranted to get<br>
     * <br>
     * True => acces autoris�<br>
     * False => acces interdit.
     * 
     * @return accesGranted
     */
    public Boolean getAccessGranted() {
        return accessGranted;
    }

    /**
     * accesGranted to set<br>
     * <br>
     * 
     * True => acces autoris�<br>
     * False => acces interdit.
     * 
     * @param accesGranted
     */
    public void setAccessGranted(Boolean accessGranted) {
        this.accessGranted = accessGranted;
    }

}
