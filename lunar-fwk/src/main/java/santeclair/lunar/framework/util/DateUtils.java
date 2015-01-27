/**
 * 
 */
package santeclair.lunar.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DurationFormatUtils;

/**
 * Classe utilitaire permettant de manipuler les objets Date.
 * 
 * @author jfourmond
 * 
 */
public class DateUtils {
    public static final String DDMMYY = "ddMMyy";
    public static final String DDMMYYYY = "ddMMyyyy";
    public static final String DDMMYYYY_SEP = "dd/MM/yyyy";
    public static final String DATE_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMAT_DATEMYSQL = "yyyy-MM-dd";
    public static final String DATE_TIMESTAMP_2 = "dd-MM-yyyy HH:mm:ss";
    public static final String TIME_HOUR_MIN = "HH:mm";
    public static final String TIME_HOUR_MIN_SEC = "HH:mm:ss";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYMMDD = "yyMMdd";

    /**
     * Instancie un objet date à partir d'une date sous forme textuelle.
     * La chaine peut avoir trois formes : JJMMAA et JJMMAAAA et JJ/MM/AAAAA.
     * 
     * @param date une chaine de caractére contenant la date à parser
     * @return Date
     * @exception IllegalArgumentException si le format ne peut être déduit de la longueur de la chaine
     */
    public static Date newDate(String date) {
        String df = findFormat(date);
        return parse(date, df);
    }

    /**
     * 
     * @param date
     * @param pattern
     * @return Date
     * @exception IllegalArgumentException si le format passé en paramétre n'est pas celui attendu
     */
    public static Date parse(String date, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("La date en paramètre " + date + " n'est pas au format attendu : " + pattern);
        }
    }

    /**
     * Renvoie la date du jour au format "09 août 2012".
     */
    public static String getDateDuJourFormattee() {
        Date dateDuJour = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        String dateFormattee = df.format(dateDuJour);
        return dateFormattee;
    }

    /**
     * Renvoie une date chaine de caractéres formattée
     * 
     * @param date la date à formater
     * @param pattern le format à utiliser
     * @return String
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }

        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * Determination du format de la date à utiliser
     * 
     * @param date
     * @return String
     * @exception IllegalArgumentException si le format ne peut être déduit de la longueur de la chaine
     */
    private static String findFormat(String date) {
        int length = date.length();
        String df = null;
        if (length == 6) {
            df = DDMMYY;
        } else if (length == 8) {
            df = DDMMYYYY;
        } else if (length == 10) {
            df = DDMMYYYY_SEP;
        } else if (length == 19) {
            df = DATE_TIMESTAMP;
        } else {
            throw new IllegalArgumentException("La date en paramètre n'est pas au format JJMMAA ou JJMMAAAA ou JJ/MM/AAAAA: " + date);
        }
        return df;
    }

    /**
     * Calcul l'age en fonction de la date de naissance
     * 
     * @param dateOfBirth
     */
    public static int getAgeFromDateOfBirth(Date dateOfBirth) {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = Calendar.getInstance();
        int age = 0;
        int factor = 0;
        cal1.setTime(dateOfBirth);
        if (cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
            factor = -1;
        }
        age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
        return age;
    }

    /**
     * Formate une date pour Mysql. au format yyyy-MM-dd
     * 
     * @param date
     *            Format de la date dd/MM/yyyy
     * @return
     * @throws Exception
     */
    public static String formateToDateMysql(Date date) throws Exception {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat(FORMAT_DATEMYSQL);
        return f.format(date);
    }

    /**
     * Méthode permettant de mettre une date à 0 heure, 0 minute, 0 seconde et 0 milli-seconde
     * 
     * @param date Date
     * @return Date premier instant du jour de la date
     */
    public static Date getDebutJour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Méthode permettant de mettre une date à 23 heure, 59 minute, 59 seconde et 999 milli-seconde
     * 
     * @param date Date
     * @return Date dernier instant du jour de la date
     */
    public static Date getFinJour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * Méthode permettant de mettre une date à 23 heure, 59 minute, 59 seconde et 999 milli-seconde
     * 
     * @param date Date
     * @return Date dernier instant du jour de la date
     */
    public static String getDurationBetweenTwoDates(Date dateDebut, Date dateFin) {

        if (dateDebut != null && dateFin != null) {
            return DurationFormatUtils.formatPeriod(dateDebut.getTime(), dateFin.getTime(),
                            DateFormatUtils.ISO_TIME_NO_T_FORMAT.getPattern());
        }

        return null;
    }
}
