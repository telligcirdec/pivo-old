package santeclair.lunar.framework.util;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

/**
 * Classe utilitaire permettant de savoir si un jour tombe un week-end ou un jour férié.
 * 
 * @author jfourmond
 * 
 */
public class JourUtils {

    /**
     * Renvoie true si le Calendar en paramètre correspond à un jour ouvré, false sinon.
     * 
     * @return true si le jour n'est ni férié, ni un samedi ou dimanche.
     */
    public static boolean isJourOuvre(Calendar jour) {

        boolean isJourFerie = isJourFerie(jour);
        boolean isWeekEnd = isWeekEnd(jour);

        if (!isJourFerie && !isWeekEnd) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Renvoie true si le Calendar en paramètre correspond à un jour ouvré, false sinon.
     * 
     * @return true si le jour n'est ni férié, ni un samedi ou dimanche.
     */
    public static boolean isJourOuvreAndSaturday(Calendar jour) {

        boolean isJourFerie = isJourFerie(jour);
        boolean isDimanche = isDay(jour, Calendar.SUNDAY);

        if (!isJourFerie && !isDimanche) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Renvoie true si le Calendar en paramètre correspond à un jour férié, false sinon.
     */
    public static boolean isJourFerie(Calendar jour) {

        boolean isJourFerieFixe = isJourFerieFixe(jour);
        boolean isJourFerieVariable = isJourFerieVariable(jour);
        return isJourFerieFixe || isJourFerieVariable;
    }

    /**
     * Renvoie true si le Calendar en paramètre correspond à un jour férié à date fixe, false sinon.
     */
    @VisibleForTesting
    static boolean isJourFerieFixe(Calendar jour) {
        if (jour.get(Calendar.DAY_OF_MONTH) == 1 && jour.get(Calendar.MONTH) == Calendar.JANUARY) {
            return true;
        }
        if (jour.get(Calendar.DAY_OF_MONTH) == 1 && jour.get(Calendar.MONTH) == Calendar.MAY) {
            return true;
        }
        if (jour.get(Calendar.DAY_OF_MONTH) == 8 && jour.get(Calendar.MONTH) == Calendar.MAY) {
            return true;
        }
        if (jour.get(Calendar.DAY_OF_MONTH) == 14 && jour.get(Calendar.MONTH) == Calendar.JULY) {
            return true;
        }
        if (jour.get(Calendar.DAY_OF_MONTH) == 15 && jour.get(Calendar.MONTH) == Calendar.AUGUST) {
            return true;
        }
        if (jour.get(Calendar.DAY_OF_MONTH) == 1 && jour.get(Calendar.MONTH) == Calendar.NOVEMBER) {
            return true;
        }
        if (jour.get(Calendar.DAY_OF_MONTH) == 11 && jour.get(Calendar.MONTH) == Calendar.NOVEMBER) {
            return true;
        }
        if (jour.get(Calendar.DAY_OF_MONTH) == 25 && jour.get(Calendar.MONTH) == Calendar.DECEMBER) {
            return true;
        }
        return false;
    }

    /**
     * Renvoie true si le Calendar en paramètre correspond à un jour férié à date variable, false sinon.
     */
    @VisibleForTesting
    static boolean isJourFerieVariable(Calendar jour) {

        /* Récupération du dimanche de Pâques, qui permet de retrouver le lundi de Pâques, l'Ascension (et aussi la Pentecôte). */
        int year = jour.get(Calendar.YEAR);
        Calendar dimanchePaques = getDimanchePaques(year);

        /* Lundi de Pâques */
        Calendar lundiPaques = dimanchePaques;
        lundiPaques.add(Calendar.DAY_OF_YEAR, 1);
        if (jour.get(Calendar.DAY_OF_YEAR) == lundiPaques.get(Calendar.DAY_OF_YEAR)) {
            return true;
        }

        /* Ascension */
        Calendar ascension = lundiPaques;
        ascension.add(Calendar.DAY_OF_YEAR, 38); /* 1 + 38 = 39 jours après le dimanche de Pâques. */
        if (jour.get(Calendar.DAY_OF_YEAR) == ascension.get(Calendar.DAY_OF_YEAR)) {
            return true;
        }

        /* Pentecôte */
        Calendar pentecote = ascension;
        pentecote.add(Calendar.DAY_OF_YEAR, 11); /* 1 + 38 + 11 = 50 jours après le dimanche de Pâques. */
        if (jour.get(Calendar.DAY_OF_YEAR) == pentecote.get(Calendar.DAY_OF_YEAR)) {
            return true;
        }
        return false;
    }

    /**
     * Renvoie la date en paramètre plus N jours ouvrés.
     * Exemples :
     * lundi + 0 = lundi.
     * lundi + 1 = mardi.
     * vendredi + 1 = lundi.
     * vendredi + 3 = mercredi.
     * samedi + 0 = samedi.
     * samedi + 1 = lundi.
     * dimanche + 0 = dimanche.
     * dimanche + 1 = lundi.
     * 
     * @param n le nombre de jours ouvrés à ajouter. Ne peut pas être négatif.
     */
    public static Calendar decalerDeNJoursOuvres(Calendar jour, int n) {

        Preconditions.checkArgument(n >= 0);

        Calendar jourPlusNJoursOuvres = (Calendar) jour.clone();
        int nbJoursAjoutes = 0;
        while (nbJoursAjoutes < n) {
            jourPlusNJoursOuvres.add(Calendar.DAY_OF_YEAR, 1);
            if (isJourOuvre(jourPlusNJoursOuvres)) {
                nbJoursAjoutes++;
            }
        }
        return jourPlusNJoursOuvres;
    }

    /**
     * Renvoie la date en paramètre plus N jours ouvrés samedi compris.
     * Exemples :
     * lundi + 0 = lundi.
     * lundi + 1 = mardi.
     * vendredi + 1 = lundi.
     * vendredi + 3 = mardi.
     * samedi + 0 = samedi.
     * samedi + 1 = lundi.
     * dimanche + 0 = dimanche.
     * dimanche + 1 = lundi.
     * 
     * @param n le nombre de jours ouvrés à ajouter. Ne peut pas être négatif.
     */
    public static Calendar decalerDeNJoursOuvresWithSaturday(Calendar jour, int n) {

        Preconditions.checkArgument(n >= 0);

        Calendar jourPlusNJoursOuvres = (Calendar) jour.clone();
        int nbJoursAjoutes = 0;
        while (nbJoursAjoutes < n) {
            jourPlusNJoursOuvres.add(Calendar.DAY_OF_YEAR, 1);
            if (isJourOuvreAndSaturday(jourPlusNJoursOuvres)) {
                nbJoursAjoutes++;
            }
        }
        return jourPlusNJoursOuvres;
    }

    /**
     * Renvoie le nombre de jours ouvrés avec le samedi entre les 2 Calendar en paramètre.
     * Si les 2 représentent le même jour, on renvoie 0.
     * Les objets en paramètre ne sont pas modifiés.
     * 
     * @param jour1 un Calendar != null
     * @param jour2 un Calendar != null (égal ou moins ancien que jour1)
     */
    public static int getNbJoursOuvresEntreWithSaturday(Calendar jour1, Calendar jour2) {

        Preconditions.checkArgument(jour1.compareTo(jour2) <= 0);
        int nbJours = 0;

        Calendar jour1Minuit = getCopieCalendarAvecHeureAMinuit(jour1);
        Calendar jour2Minuit = getCopieCalendarAvecHeureAMinuit(jour2);
        Calendar jour = jour1Minuit;

        while (jour.before(jour2Minuit)) {
            boolean isJourOuvre = isJourOuvreAndSaturday(jour);
            if (isJourOuvre) {
                nbJours++;
            }
            jour.add(Calendar.DAY_OF_YEAR, 1);
        }

        return nbJours;
    }

    /**
     * Renvoie le nombre de jours ouvrés entre les 2 Calendar en paramètre.
     * Si les 2 représentent le même jour, on renvoie 0.
     * Les objets en paramètre ne sont pas modifiés.
     * 
     * @param jour1 un Calendar != null
     * @param jour2 un Calendar != null (égal ou moins ancien que jour1)
     */
    public static int getNbJoursOuvresEntre(Calendar jour1, Calendar jour2) {

        Preconditions.checkArgument(jour1.compareTo(jour2) <= 0);
        int nbJours = 0;

        Calendar jour1Minuit = getCopieCalendarAvecHeureAMinuit(jour1);
        Calendar jour2Minuit = getCopieCalendarAvecHeureAMinuit(jour2);
        Calendar jour = jour1Minuit;

        while (jour.before(jour2Minuit)) {
            boolean isJourOuvre = isJourOuvre(jour);
            if (isJourOuvre) {
                nbJours++;
            }
            jour.add(Calendar.DAY_OF_YEAR, 1);
        }

        return nbJours;
    }

    /**
     * Renvoie le nombre de jours ouvrés entre les 2 Date en paramètre.
     * Si les 2 représentent le même jour, on renvoie 0.
     * Les objets en paramètre ne sont pas modifiés.
     * 
     * @param jour1 un Date != null
     * @param jour2 un Date != null (égal ou moins ancien que jour1)
     */
    public static int getNbJoursOuvresEntre(Date jour1, Date jour2) {

        Preconditions.checkArgument(jour1 != null);
        Preconditions.checkArgument(jour2 != null);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(jour1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(jour2);

        int nbJoursOuvres = getNbJoursOuvresEntre(calendar1, calendar2);
        return nbJoursOuvres;
    }

    /**
     * Renvoie le nombre de jours ouvrés avec le samedi entre les 2 Date en paramètre.
     * Si les 2 représentent le même jour, on renvoie 0.
     * Les objets en paramètre ne sont pas modifiés.
     * 
     * @param jour1 un Date != null
     * @param jour2 un Date != null (égal ou moins ancien que jour1)
     */
    public static int getNbJoursOuvresEntreWithSaturday(Date jour1, Date jour2) {

        Preconditions.checkArgument(jour1 != null);
        Preconditions.checkArgument(jour2 != null);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(jour1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(jour2);

        int nbJoursOuvres = getNbJoursOuvresEntreWithSaturday(calendar1, calendar2);
        return nbJoursOuvres;
    }

    /**
     * Renvoie une copie du Calendar en paramètre avec l'heure à 00:00:00.
     */
    private static Calendar getCopieCalendarAvecHeureAMinuit(Calendar jour) {
        Calendar jourMinuit = (Calendar) jour.clone();
        jourMinuit.set(Calendar.HOUR_OF_DAY, 0);
        jourMinuit.set(Calendar.MINUTE, 0);
        jourMinuit.set(Calendar.SECOND, 0);
        jourMinuit.set(Calendar.MILLISECOND, 0);
        return jourMinuit;
    }

    /**
     * Renvoie la date en paramètre plus N jours ouvrés.
     * Exemples :
     * lundi + 0 = lundi.
     * lundi + 1 = mardi.
     * vendredi + 1 = lundi.
     * vendredi + 3 = mercredi.
     * samedi + 0 = samedi.
     * samedi + 1 = lundi.
     * dimanche + 0 = dimanche.
     * dimanche + 1 = lundi.
     * 
     * @param n le nombre de jours ouvrés à ajouter. Ne peut pas être négatif.
     * @return un nouvel objet Date
     */
    public static Date decalerDeNJoursOuvres(Date jour, int n) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jour);

        Calendar jourPlusNJoursOuvres = decalerDeNJoursOuvres(calendar, n);
        long ms = jourPlusNJoursOuvres.getTimeInMillis();

        Date date = new Date(ms);
        return date;
    }

    /**
     * Renvoie la date en paramètre plus N jours ouvrés.
     * Exemples :
     * lundi + 0 = lundi.
     * lundi + 1 = mardi.
     * vendredi + 1 = lundi.
     * vendredi + 3 = mardi.
     * samedi + 0 = samedi.
     * samedi + 1 = lundi.
     * dimanche + 0 = dimanche.
     * dimanche + 1 = lundi.
     * 
     * @param n le nombre de jours ouvrés à ajouter. Ne peut pas être négatif.
     * @return un nouvel objet Date
     */
    public static Date decalerDeNJoursOuvresWithSaturday(Date jour, int n) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jour);

        Calendar jourPlusNJoursOuvresWithSaturday = decalerDeNJoursOuvresWithSaturday(calendar, n);
        long ms = jourPlusNJoursOuvresWithSaturday.getTimeInMillis();

        Date date = new Date(ms);
        return date;
    }

    /**
     * Renvoie true si le jour en paramètre est un samedi ou un dimanche, false si c'est un jour de la semaine.
     */
    public static boolean isWeekEnd(Calendar jour) {
        if (isDay(jour, Calendar.SATURDAY) || isDay(jour, Calendar.SUNDAY)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Renvoie true si le jour en paramètre est égal au jour passé en parametre (voir Calendar). false dans le cas
     * contraire.
     */
    public static boolean isDay(Calendar jour, int day) {
        int jourDeLaSemaine = jour.get(Calendar.DAY_OF_WEEK);
        if (jourDeLaSemaine == day) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Renvoie un Calendar correspondant au dimanche de Pâques de l'année en paramètre.
     * Voir http://fr.wikipedia.org/wiki/Calcul_de_la_date_de_P%C3%A2ques
     */
    @VisibleForTesting
    static Calendar getDimanchePaques(int year) {

        int g, c, c_4, e, h, k, p, q, i, b, j1, j2, r;
        g = year % 19;
        c = year / 100;
        c_4 = c / 4;
        e = (8 * c + 13) / 25;
        h = (19 * g + c - c_4 - e + 15) % 30;
        k = h / 28;
        p = 29 / (h + 1);
        q = (21 - g) / 11;
        i = (k * p * q - 1) * k + h;
        b = year / 4 + year;
        j1 = (b + i + 2 + c_4) - c;
        j2 = j1 % 7;
        r = 28 + i - j2;
        DateTime dimanchePaques = null;
        if (r > 31) {
            dimanchePaques = new DateTime(year, 4, r - 31, 0, 0, 0, 0);
        } else {
            dimanchePaques = new DateTime(year, 3, r, 0, 0, 0, 0);
        }
        return dimanchePaques.toGregorianCalendar();
    }

}
