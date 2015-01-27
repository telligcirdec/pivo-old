package santeclair.lunar.framework.web.auth;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.StringUtils;

/**
 * Données utilisées pour le calcul du RAC des garanties hospitalières
 * 
 * @author pchaussalet
 */
public class Rac implements Serializable {
    private static final long serialVersionUID = 2261010574536572809L;
    private String honoraires;
    private String forfait;
    private String roInclus;
    private String dateEffet;
    private String racmaternite;
    private String garantieCpParJour;
    private String garantieCpFraisReels;
    private String bonusCp;
    private String bonusSoin;

    @XmlElement(name = "datepriseeffet")
    public String getDateEffet() {
        return this.dateEffet;
    }

    public void setDateEffet(String dateEffet) {
        this.dateEffet = dateEffet;
    }

    public String getHonoraires() {
        return this.honoraires;
    }

    public void setHonoraires(String honoraires) {
        this.honoraires = honoraires;
    }

    public String getForfait() {
        return forfait;
    }

    public void setForfait(String forfait) {
        this.forfait = forfait;
    }

    @XmlElement(name = "roinclus")
    public String getRoInclus() {
        return this.roInclus;
    }

    public void setRoInclus(String roInclus) {
        this.roInclus = roInclus;
    }

    /**
     * @return the racmaternite
     */
    @XmlElement(name = "racmaternite")
    @XmlJavaTypeAdapter(TinyintStringConverter.class)
    public String getRacmaternite() {
        return racmaternite;
    }

    /**
     * @param racmaternite the racmaternite to set
     */
    public void setRacmaternite(String racmaternite) {
        this.racmaternite = racmaternite;
    }

    /**
     * Retourne la valeur de la garantie
     * 
     * @return une chaine de caratère ou null
     */
    @XmlElement(name = "garantiecpparjour")
    @XmlJavaTypeAdapter(TinyintStringConverter.class)
    public String getGarantieCpParJour() {
        return this.garantieCpParJour;
    }

    /**
     * Fixe la valeur de la garantie des chambre particulière journalière
     * 
     * @param garantieCpParJour garantie journalière
     */
    public void setGarantieCpParJour(String garantieCpParJour) {
        this.garantieCpParJour = garantieCpParJour;
    }

    /**
     * Indique si le remboursement de la chambre particulière est au frais réels
     * 
     * @return 1 si remboursement au frais réels ou 0 (par défaut)
     */
    @XmlElement(name = "garantiecpfraisreels")
    public String getGarantieCpFraisReels() {
        if (StringUtils.isBlank(this.garantieCpFraisReels)) {
            return "0";
        } else {
            return this.garantieCpFraisReels;
        }

    }

    /**
     * Fixe la valeur de la garantie frais reels
     * 
     * @param garantieCpFraisReels
     */
    public void setGarantieCpFraisReels(String garantieCpFraisReels) {
        this.garantieCpFraisReels = garantieCpFraisReels;
    }

    /**
     * Méthode de lecture de l'attribut : {@link Rac#bonusCp}
     * 
     * @return bonusCp
     */
    @XmlElement(name = "bonuscp", required = false)
    public String getBonusCp() {
        return bonusCp;
    }

    /**
     * Méthode d'écriture de l'attribut : {@link Rac#bonusCp}
     * 
     * @param bonusCp
     */
    public void setBonusCp(String bonusCp) {
        this.bonusCp = bonusCp;
    }

    /**
     * Méthode de lecture de l'attribut : {@link Rac#bonusSoin}
     * 
     * @return bonusSoin
     */
    @XmlElement(name = "bonussoin", required = false)
    public String getBonusSoin() {
        return bonusSoin;
    }

    /**
     * Méthode d'écriture de l'attribut : {@link Rac#bonusSoin}
     * 
     * @param bonusSoin
     */
    public void setBonusSoin(String bonusSoin) {
        this.bonusSoin = bonusSoin;
    }

}
