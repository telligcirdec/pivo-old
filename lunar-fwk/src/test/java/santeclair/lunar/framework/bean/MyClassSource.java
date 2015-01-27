package santeclair.lunar.framework.bean;

import java.util.Date;

public class MyClassSource {
    private int entier;
    private Date date;
    private String chaine;

    public MyClassSource(int entier, Date date, String chaine) {
        this.entier = entier;
        this.date = date;
        this.chaine = chaine;
    }

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
     * @return the chaine
     */
    public String getChaine() {
        return chaine;
    }

    /**
     * @param chaine the chaine to set
     */
    public void setChaine(String chaine) {
        this.chaine = chaine;
    }

}
