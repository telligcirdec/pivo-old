package santeclair.lunar.framework.cache.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Classe représentant une cache ainsi que ses statistiques.
 * 
 * @author ldelemotte
 * 
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AdminCache {
    /** Nom de la cache. */
    private String name;
    /** Pourcentage d'utilisation de la cache. */
    private int usePercentage;
    /** Pourcentage d'efficacité de la cache. */
    private int hitRate;

    /** Constructeur par defaut. */
    public AdminCache() {

    }

    /** Constructeur avec les valeurs à insérer. */
    public AdminCache(String name, int usePercentage, int hitRate) {
        this.name = name;
        this.usePercentage = usePercentage;
        this.hitRate = hitRate;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    public Integer getUsePercentage() {
        return usePercentage;
    }

    /**
     * {@inheritDoc}
     */
    public Integer getHitRate() {
        return hitRate;
    }

    /**
     * @param usePercentage the usePercentage to set
     */
    public void setUsePercentage(int usePercentage) {
        this.usePercentage = usePercentage;
    }

    /**
     * @param hitRate the hitRate to set
     */
    public void setHitRate(int hitRate) {
        this.hitRate = hitRate;
    }

}
