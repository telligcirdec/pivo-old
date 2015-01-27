package santeclair.lunar.framework.web.auth;

import javax.xml.bind.annotation.XmlRootElement;

/***
 * Permet de savoir si un idPart à acces à un idCharte et à une idAppli.
 * 
 * @author cgillet
 */
@XmlRootElement
public class ResultatAuthorizationAccess {

    /**
     * Détermine si l'acces est autorisé ou non.<br>
     * True => acces autorisé<br>
     * False => acces interdit.
     */
    private Boolean accessGranted;

    /**
     * accesGranted to get<br>
     * <br>
     * True => acces autorisé<br>
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
     * True => acces autorisé<br>
     * False => acces interdit.
     * 
     * @param accesGranted
     */
    public void setAccessGranted(Boolean accessGranted) {
        this.accessGranted = accessGranted;
    }

}
