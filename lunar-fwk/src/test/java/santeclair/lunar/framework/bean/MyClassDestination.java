package santeclair.lunar.framework.bean;

import java.util.Date;

/**
 * @author fmokhtari
 * 
 */

public class MyClassDestination {
    private int entier;
    private Date date;
    private Double someDouble;

    /**
     * @return the entier
     */
    public int getEntier() {
        return entier;
    }

    /**
     * @param entier the entier to set
     */
    public void setEntier(int entier) {
        this.entier = entier;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the someDouble
     */
    public Double getSomeDouble() {
        return someDouble;
    }

    /**
     * @param someDouble the someDouble to set
     */
    public void setSomeDouble(Double someDouble) {
        this.someDouble = someDouble;
    }

}
