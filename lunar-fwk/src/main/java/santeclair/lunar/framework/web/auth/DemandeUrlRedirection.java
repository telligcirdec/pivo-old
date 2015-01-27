package santeclair.lunar.framework.web.auth;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de demande d'url de redirection.
 * 
 * @author fmokhtari
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DemandeUrlRedirection {

    private String idApplicationCible;

    private String idApplicationOrigine;

    private JetonSSO jetonSSO;

    private String perso;

    private String[] listeOptions;

    /* ======================================================= *
     *                      getters & setters 
     * ======================================================= */

    /**
     * @return the idApplicationCible
     */
    public String getIdApplicationCible() {
        return idApplicationCible;
    }

    /**
     * @param idApplicationCible the idApplicationCible to set
     */
    public void setIdApplicationCible(String idApplicationCible) {
        this.idApplicationCible = idApplicationCible;
    }

    /**
     * @return the idApplicationOrigine
     */
    public String getIdApplicationOrigine() {
        return idApplicationOrigine;
    }

    /**
     * @param idApplicationOrigine the idApplicationOrigine to set
     */
    public void setIdApplicationOrigine(String idApplicationOrigine) {
        this.idApplicationOrigine = idApplicationOrigine;
    }

    /**
     * @return the jetonSSO
     */
    public JetonSSO getJetonSSO() {
        return jetonSSO;
    }

    /**
     * @param jetonSSO the jetonSSO to set
     */
    public void setJetonSSO(JetonSSO jetonSSO) {
        this.jetonSSO = jetonSSO;
    }

    /**
     * @return the perso
     */
    public String getPerso() {
        return perso;
    }

    /**
     * @param perso the perso to set
     */
    public void setPerso(String perso) {
        this.perso = perso;
    }

    /**
     * @return the listeOptions
     */
    public String[] getListeOptions() {
        return listeOptions;
    }

    /**
     * @param listeOptions the listeOptions to set
     */
    public void setListeOptions(String[] listeOptions) {
        this.listeOptions = listeOptions;
    }

}
