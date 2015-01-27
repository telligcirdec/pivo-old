package santeclair.lunar.framework.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Classe de tests unitaires pour JourUtils.
 * 
 * @author jfourmond
 * 
 */
public class JourUtilsUnitTest {

    /**
     * Teste la méthode isJourOuvre avec différentes dates.
     */
    @Test
    public void isJourOuvre() {

        /* Lundi de Pâques */
        DateTime lundiPaques2012 = new DateTime(2012, 4, 9, 0, 0, 0, 0);
        assertFalse(JourUtils.isJourOuvre(lundiPaques2012.toGregorianCalendar()));

        /* un samedi */
        DateTime unSamedi = new DateTime(2012, 8, 25, 0, 0, 0, 0);
        assertFalse(JourUtils.isJourOuvre(unSamedi.toGregorianCalendar()));

        /* un dimanche */
        DateTime unDimanche = new DateTime(2012, 8, 26, 0, 0, 0, 0);
        assertFalse(JourUtils.isJourOuvre(unDimanche.toGregorianCalendar()));

        /* un lundi */
        DateTime unLundi = new DateTime(2012, 8, 27, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourOuvre(unLundi.toGregorianCalendar()));

        /* un mardi */
        DateTime unMardi = new DateTime(2012, 8, 28, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourOuvre(unMardi.toGregorianCalendar()));

        /* un mercredi */
        DateTime unMercredi = new DateTime(2012, 8, 28, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourOuvre(unMercredi.toGregorianCalendar()));

        /* un jeudi */
        DateTime unJeudi = new DateTime(2012, 8, 29, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourOuvre(unJeudi.toGregorianCalendar()));

        /* un vendredi */
        DateTime unVendredi = new DateTime(2012, 8, 30, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourOuvre(unVendredi.toGregorianCalendar()));
    }

    /**
     * Teste la méthode isJourOuvre avec différentes dates.
     */
    @Test
    public void isJourOuvreAndSaturday() {

        /* Lundi de Pâques */
        DateTime lundiPaques2012 = new DateTime(2012, 4, 9, 0, 0, 0, 0);
        assertFalse(JourUtils.isJourOuvreAndSaturday(lundiPaques2012.toGregorianCalendar()));

        /* un samedi */
        DateTime unSamedi = new DateTime(2012, 8, 25, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourOuvreAndSaturday(unSamedi.toGregorianCalendar()));

        /* un dimanche */
        DateTime unDimanche = new DateTime(2012, 8, 26, 0, 0, 0, 0);
        assertFalse(JourUtils.isJourOuvreAndSaturday(unDimanche.toGregorianCalendar()));

        /* un lundi */
        DateTime unLundi = new DateTime(2012, 8, 27, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourOuvreAndSaturday(unLundi.toGregorianCalendar()));

        /* un mardi */
        DateTime unMardi = new DateTime(2012, 8, 28, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourOuvreAndSaturday(unMardi.toGregorianCalendar()));

        /* un mercredi */
        DateTime unMercredi = new DateTime(2012, 8, 28, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourOuvreAndSaturday(unMercredi.toGregorianCalendar()));

        /* un jeudi */
        DateTime unJeudi = new DateTime(2012, 8, 29, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourOuvreAndSaturday(unJeudi.toGregorianCalendar()));

        /* un vendredi */
        DateTime unVendredi = new DateTime(2012, 8, 30, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourOuvreAndSaturday(unVendredi.toGregorianCalendar()));
    }

    /**
     * Teste la méthode isJourFerie avec différentes dates.
     */
    @Test
    public void isJourFerie() {

        /* Lundi de Pâques */
        DateTime lundiPaques2012 = new DateTime(2012, 4, 9, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerie(lundiPaques2012.toGregorianCalendar()));

        /* 1er Novembre */
        DateTime premierNovembre2024 = new DateTime(2024, 11, 1, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieFixe(premierNovembre2024.toGregorianCalendar()));

        /* jour ordinaire */
        DateTime quinzeSeptembre2013 = new DateTime(2013, 9, 15, 0, 0, 0, 0);
        assertFalse(JourUtils.isJourFerieFixe(quinzeSeptembre2013.toGregorianCalendar()));
    }

    /**
     * Teste la méthode isJourFerieFixe avec différentes dates autour de jours fériés fixes.
     */
    @Test
    public void isJourFerieFixe() {

        /* Jour de l'an */

        DateTime jourDeLAn1900 = new DateTime(1900, 1, 1, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieFixe(jourDeLAn1900.toGregorianCalendar()));

        DateTime deuxJanvier1900 = new DateTime(1900, 1, 2, 0, 0, 0, 0);
        assertFalse(JourUtils.isJourFerieFixe(deuxJanvier1900.toGregorianCalendar()));

        /* 1er Mai */

        DateTime premierMai1999Debut = new DateTime(1999, 5, 1, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieFixe(premierMai1999Debut.toGregorianCalendar()));

        DateTime premierMai1999Fin = new DateTime(1999, 5, 1, 23, 59, 59, 999);
        assertTrue(JourUtils.isJourFerieFixe(premierMai1999Fin.toGregorianCalendar()));

        DateTime deuxMai1999 = new DateTime(1999, 5, 2, 0, 0, 0, 0);
        assertFalse(JourUtils.isJourFerieFixe(deuxMai1999.toGregorianCalendar()));

        /* 8 Mai */
        DateTime huitMai2000 = new DateTime(2000, 5, 8, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieFixe(huitMai2000.toGregorianCalendar()));

        /* 14 Juillet */
        DateTime quartorzeJuillet2057 = new DateTime(2057, 7, 14, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieFixe(quartorzeJuillet2057.toGregorianCalendar()));

        /* 15 Août */
        DateTime quinzeAout2013 = new DateTime(2013, 8, 15, 16, 32, 57, 451);
        assertTrue(JourUtils.isJourFerieFixe(quinzeAout2013.toGregorianCalendar()));

        /* 1er Novembre */
        DateTime premierNovembre2024 = new DateTime(2024, 11, 1, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieFixe(premierNovembre2024.toGregorianCalendar()));

        /* 11 Novembre */

        DateTime onzeNovembre2024 = new DateTime(2024, 11, 11, 23, 59, 59, 999);
        assertTrue(JourUtils.isJourFerieFixe(onzeNovembre2024.toGregorianCalendar()));

        /* Noël */

        DateTime veille2012Noel = new DateTime(2012, 12, 24, 0, 0, 0, 0);
        assertFalse(JourUtils.isJourFerieFixe(veille2012Noel.toGregorianCalendar()));

        DateTime noel2012Moins1Ms = new DateTime(2012, 12, 24, 23, 59, 59, 999);
        assertFalse(JourUtils.isJourFerieFixe(noel2012Moins1Ms.toGregorianCalendar()));

        DateTime noel2012 = new DateTime(2012, 12, 25, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieFixe(noel2012.toGregorianCalendar()));

        DateTime noel2046 = new DateTime(2046, 12, 25, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieFixe(noel2046.toGregorianCalendar()));
    }

    /**
     * Teste la méthode getDimanchePaques.
     * Voir http://fr.wikipedia.org/wiki/Calcul_de_la_date_de_P%C3%A2ques.
     */
    @Test
    public void getDimanchePaques() {

        Calendar dimanchePaques2012 = JourUtils.getDimanchePaques(2012);
        assertEquals(2012, dimanchePaques2012.get(Calendar.YEAR));
        assertEquals(4, dimanchePaques2012.get(Calendar.MONTH) + 1); /* Janvier = 0, Février = 1... */
        assertEquals(8, dimanchePaques2012.get(Calendar.DAY_OF_MONTH));

        Calendar dimanchePaques2013 = JourUtils.getDimanchePaques(2013);
        assertEquals(2013, dimanchePaques2013.get(Calendar.YEAR));
        assertEquals(3, dimanchePaques2013.get(Calendar.MONTH) + 1);
        assertEquals(31, dimanchePaques2013.get(Calendar.DAY_OF_MONTH));

        Calendar dimanchePaques2014 = JourUtils.getDimanchePaques(2014);
        assertEquals(2014, dimanchePaques2014.get(Calendar.YEAR));
        assertEquals(4, dimanchePaques2014.get(Calendar.MONTH) + 1);
        assertEquals(20, dimanchePaques2014.get(Calendar.DAY_OF_MONTH));

        Calendar dimanchePaques2015 = JourUtils.getDimanchePaques(2015);
        assertEquals(2015, dimanchePaques2015.get(Calendar.YEAR));
        assertEquals(4, dimanchePaques2015.get(Calendar.MONTH) + 1);
        assertEquals(5, dimanchePaques2015.get(Calendar.DAY_OF_MONTH));

        Calendar dimanchePaques2016 = JourUtils.getDimanchePaques(2016);
        assertEquals(2016, dimanchePaques2016.get(Calendar.YEAR));
        assertEquals(3, dimanchePaques2016.get(Calendar.MONTH) + 1);
        assertEquals(27, dimanchePaques2016.get(Calendar.DAY_OF_MONTH));

        Calendar dimanchePaques2017 = JourUtils.getDimanchePaques(2017);
        assertEquals(2017, dimanchePaques2017.get(Calendar.YEAR));
        assertEquals(4, dimanchePaques2017.get(Calendar.MONTH) + 1);
        assertEquals(16, dimanchePaques2017.get(Calendar.DAY_OF_MONTH));

        Calendar dimanchePaques2018 = JourUtils.getDimanchePaques(2018);
        assertEquals(2018, dimanchePaques2018.get(Calendar.YEAR));
        assertEquals(4, dimanchePaques2018.get(Calendar.MONTH) + 1);
        assertEquals(1, dimanchePaques2018.get(Calendar.DAY_OF_MONTH));

        Calendar dimanchePaques2019 = JourUtils.getDimanchePaques(2019);
        assertEquals(2019, dimanchePaques2019.get(Calendar.YEAR));
        assertEquals(4, dimanchePaques2019.get(Calendar.MONTH) + 1);
        assertEquals(21, dimanchePaques2019.get(Calendar.DAY_OF_MONTH));

        Calendar dimanchePaques2020 = JourUtils.getDimanchePaques(2020);
        assertEquals(2020, dimanchePaques2020.get(Calendar.YEAR));
        assertEquals(4, dimanchePaques2020.get(Calendar.MONTH) + 1);
        assertEquals(12, dimanchePaques2020.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Teste la méthode isJourFerieVariable avec différentes dates autour de jours fériés variables.
     */
    @Test
    public void isJourFerieVariable() {

        /* Lundi de Pâques */
        DateTime lundiPaques2012 = new DateTime(2012, 4, 9, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieVariable(lundiPaques2012.toGregorianCalendar()));

        /* L'année suivante, ça ne tombe pas à la même date. */
        DateTime fauxLundiPaques2013 = new DateTime(2013, 4, 9, 0, 0, 0, 0);
        assertFalse(JourUtils.isJourFerieVariable(fauxLundiPaques2013.toGregorianCalendar()));

        DateTime lundiPaques2013 = new DateTime(2013, 4, 1, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieVariable(lundiPaques2013.toGregorianCalendar()));

        DateTime lundiPaques2014 = new DateTime(2014, 4, 21, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieVariable(lundiPaques2014.toGregorianCalendar()));

        DateTime lundiPaques2015 = new DateTime(2015, 4, 6, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieVariable(lundiPaques2015.toGregorianCalendar()));

        DateTime lundiPaques2016 = new DateTime(2016, 3, 28, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieVariable(lundiPaques2016.toGregorianCalendar()));

        /* Jeudi de l'Ascension */
        DateTime jeudiAscension2012 = new DateTime(2012, 5, 17, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieVariable(jeudiAscension2012.toGregorianCalendar()));

        /* L'année suivante, ça ne tombe pas à la même date. */
        DateTime fauxJeudiAscension2013 = new DateTime(2013, 5, 17, 0, 0, 0, 0);
        assertFalse(JourUtils.isJourFerieVariable(fauxJeudiAscension2013.toGregorianCalendar()));

        DateTime jeudiAscension2013 = new DateTime(2013, 5, 9, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieVariable(jeudiAscension2013.toGregorianCalendar()));

        DateTime jeudiAscension2014 = new DateTime(2014, 5, 29, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieVariable(jeudiAscension2014.toGregorianCalendar()));

        DateTime jeudiAscension2099 = new DateTime(2099, 5, 21, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieVariable(jeudiAscension2099.toGregorianCalendar()));

        /* Lundi de Pentecôte */
        DateTime lundiPentecote2012 = new DateTime(2012, 5, 28, 0, 0, 0, 0);
        assertTrue(JourUtils.isJourFerieVariable(lundiPentecote2012.toGregorianCalendar()));
    }

    /**
     * Teste la méthode isWeekEnd.
     */
    @Test
    public void isWeekEnd() {
        DateTime vendredi24Aout2012 = new DateTime(2012, 8, 24, 23, 59, 59, 999);
        assertFalse(JourUtils.isWeekEnd(vendredi24Aout2012.toGregorianCalendar()));

        DateTime samedi25Aout2012 = new DateTime(2012, 8, 25, 0, 0, 0, 0);
        assertTrue(JourUtils.isWeekEnd(samedi25Aout2012.toGregorianCalendar()));

        DateTime dimanche26Aout2012 = new DateTime(2012, 8, 26, 23, 59, 59, 999);
        assertTrue(JourUtils.isWeekEnd(dimanche26Aout2012.toGregorianCalendar()));

        DateTime lundi27Aout2012 = new DateTime(2012, 8, 27, 0, 0, 0, 0);
        assertFalse(JourUtils.isWeekEnd(lundi27Aout2012.toGregorianCalendar()));
    }

    /**
     * Teste la méthode isWeekEnd.
     */
    @Test
    public void isDay() {
        DateTime vendredi24Aout2012 = new DateTime(2012, 8, 24, 23, 59, 59, 999);
        assertTrue(JourUtils.isDay(vendredi24Aout2012.toGregorianCalendar(), Calendar.FRIDAY));
        assertFalse(JourUtils.isDay(vendredi24Aout2012.toGregorianCalendar(), Calendar.SUNDAY));

        DateTime samedi25Aout2012 = new DateTime(2012, 8, 25, 0, 0, 0, 0);
        assertTrue(JourUtils.isDay(samedi25Aout2012.toGregorianCalendar(), Calendar.SATURDAY));
        assertFalse(JourUtils.isDay(samedi25Aout2012.toGregorianCalendar(), Calendar.MONDAY));

        DateTime dimanche26Aout2012 = new DateTime(2012, 8, 26, 23, 59, 59, 999);
        assertTrue(JourUtils.isDay(dimanche26Aout2012.toGregorianCalendar(), Calendar.SUNDAY));
        assertFalse(JourUtils.isDay(dimanche26Aout2012.toGregorianCalendar(), Calendar.THURSDAY));

        DateTime lundi27Aout2012 = new DateTime(2012, 8, 27, 0, 0, 0, 0);
        assertTrue(JourUtils.isDay(lundi27Aout2012.toGregorianCalendar(), Calendar.MONDAY));
        assertFalse(JourUtils.isDay(lundi27Aout2012.toGregorianCalendar(), Calendar.WEDNESDAY));
    }

    /**
     * Teste la méthode decalerDeNJoursOuvres avec des objets Calendar en ajoutant 0 jour.
     */
    @Test
    public void decalerDe0JourOuvres() {

        DateTime vendredi24Aout2012 = new DateTime(2012, 8, 24, 23, 59, 59, 999);
        Calendar jourPlusNJoursOuvres = JourUtils.decalerDeNJoursOuvres(vendredi24Aout2012.toGregorianCalendar(), 0);
        assertEquals(2012, jourPlusNJoursOuvres.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres.get(Calendar.MONTH) + 1);
        assertEquals(24, jourPlusNJoursOuvres.get(Calendar.DAY_OF_MONTH));
        assertEquals(23, jourPlusNJoursOuvres.get(Calendar.HOUR_OF_DAY));
        assertEquals(59, jourPlusNJoursOuvres.get(Calendar.MINUTE));
        assertEquals(59, jourPlusNJoursOuvres.get(Calendar.SECOND));
        assertEquals(999, jourPlusNJoursOuvres.get(Calendar.MILLISECOND));

        DateTime dimanche26Aout2012 = new DateTime(2012, 8, 26, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres2 = JourUtils.decalerDeNJoursOuvres(dimanche26Aout2012.toGregorianCalendar(), 0);
        assertEquals(2012, jourPlusNJoursOuvres2.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres2.get(Calendar.MONTH) + 1);
        assertEquals(26, jourPlusNJoursOuvres2.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Teste la méthode decalerDeNJoursOuvresWithSaturday avec des objets Calendar en ajoutant 0 jour.
     */
    @Test
    public void decalerDe0JourOuvresWithSaturday() {

        DateTime vendredi24Aout2012 = new DateTime(2012, 8, 24, 23, 59, 59, 999);
        Calendar jourPlusNJoursOuvres = JourUtils.decalerDeNJoursOuvresWithSaturday(vendredi24Aout2012.toGregorianCalendar(), 0);
        assertEquals(2012, jourPlusNJoursOuvres.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres.get(Calendar.MONTH) + 1);
        assertEquals(24, jourPlusNJoursOuvres.get(Calendar.DAY_OF_MONTH));
        assertEquals(23, jourPlusNJoursOuvres.get(Calendar.HOUR_OF_DAY));
        assertEquals(59, jourPlusNJoursOuvres.get(Calendar.MINUTE));
        assertEquals(59, jourPlusNJoursOuvres.get(Calendar.SECOND));
        assertEquals(999, jourPlusNJoursOuvres.get(Calendar.MILLISECOND));

        DateTime dimanche26Aout2012 = new DateTime(2012, 8, 26, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres2 = JourUtils.decalerDeNJoursOuvresWithSaturday(dimanche26Aout2012.toGregorianCalendar(), 0);
        assertEquals(2012, jourPlusNJoursOuvres2.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres2.get(Calendar.MONTH) + 1);
        assertEquals(26, jourPlusNJoursOuvres2.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Teste la méthode decalerDeNJoursOuvres avec des objets Calendar en ajoutant 1 jour.
     */
    @Test
    public void decalerDe1JourOuvre() {

        DateTime jeudi23Aout2012 = new DateTime(2012, 8, 23, 23, 59, 59, 999);
        Calendar jourPlusNJoursOuvres = JourUtils.decalerDeNJoursOuvres(jeudi23Aout2012.toGregorianCalendar(), 1);
        assertEquals(2012, jourPlusNJoursOuvres.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres.get(Calendar.MONTH) + 1);
        assertEquals(24, jourPlusNJoursOuvres.get(Calendar.DAY_OF_MONTH));
        assertEquals(23, jourPlusNJoursOuvres.get(Calendar.HOUR_OF_DAY));
        assertEquals(59, jourPlusNJoursOuvres.get(Calendar.MINUTE));
        assertEquals(59, jourPlusNJoursOuvres.get(Calendar.SECOND));
        assertEquals(999, jourPlusNJoursOuvres.get(Calendar.MILLISECOND));

        DateTime vendredi24Aout2012 = new DateTime(2012, 8, 24, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres2 = JourUtils.decalerDeNJoursOuvres(vendredi24Aout2012.toGregorianCalendar(), 1);
        assertEquals(2012, jourPlusNJoursOuvres2.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres2.get(Calendar.MONTH) + 1);
        assertEquals(27, jourPlusNJoursOuvres2.get(Calendar.DAY_OF_MONTH));

        DateTime samedi25Aout2012 = new DateTime(2012, 8, 25, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres3 = JourUtils.decalerDeNJoursOuvres(samedi25Aout2012.toGregorianCalendar(), 1);
        assertEquals(2012, jourPlusNJoursOuvres3.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres3.get(Calendar.MONTH) + 1);
        assertEquals(27, jourPlusNJoursOuvres3.get(Calendar.DAY_OF_MONTH));

        DateTime dimanche26Aout2012 = new DateTime(2012, 8, 25, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres4 = JourUtils.decalerDeNJoursOuvres(dimanche26Aout2012.toGregorianCalendar(), 1);
        assertEquals(2012, jourPlusNJoursOuvres4.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres4.get(Calendar.MONTH) + 1);
        assertEquals(27, jourPlusNJoursOuvres4.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Teste la méthode decalerDeNJoursOuvresWithSaturday avec des objets Calendar en ajoutant 1 jour.
     */
    @Test
    public void decalerDe1JourOuvreWithSaturday() {

        DateTime jeudi23Aout2012 = new DateTime(2012, 8, 23, 23, 59, 59, 999);
        Calendar jourPlusNJoursOuvres = JourUtils.decalerDeNJoursOuvresWithSaturday(jeudi23Aout2012.toGregorianCalendar(), 1);
        assertEquals(2012, jourPlusNJoursOuvres.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres.get(Calendar.MONTH) + 1);
        assertEquals(24, jourPlusNJoursOuvres.get(Calendar.DAY_OF_MONTH));
        assertEquals(23, jourPlusNJoursOuvres.get(Calendar.HOUR_OF_DAY));
        assertEquals(59, jourPlusNJoursOuvres.get(Calendar.MINUTE));
        assertEquals(59, jourPlusNJoursOuvres.get(Calendar.SECOND));
        assertEquals(999, jourPlusNJoursOuvres.get(Calendar.MILLISECOND));

        DateTime vendredi24Aout2012 = new DateTime(2012, 8, 24, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres2 = JourUtils.decalerDeNJoursOuvresWithSaturday(vendredi24Aout2012.toGregorianCalendar(), 1);
        assertEquals(2012, jourPlusNJoursOuvres2.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres2.get(Calendar.MONTH) + 1);
        assertEquals(25, jourPlusNJoursOuvres2.get(Calendar.DAY_OF_MONTH));

        DateTime samedi25Aout2012 = new DateTime(2012, 8, 25, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres3 = JourUtils.decalerDeNJoursOuvresWithSaturday(samedi25Aout2012.toGregorianCalendar(), 1);
        assertEquals(2012, jourPlusNJoursOuvres3.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres3.get(Calendar.MONTH) + 1);
        assertEquals(27, jourPlusNJoursOuvres3.get(Calendar.DAY_OF_MONTH));

        DateTime dimanche26Aout2012 = new DateTime(2012, 8, 25, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres4 = JourUtils.decalerDeNJoursOuvresWithSaturday(dimanche26Aout2012.toGregorianCalendar(), 1);
        assertEquals(2012, jourPlusNJoursOuvres4.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres4.get(Calendar.MONTH) + 1);
        assertEquals(27, jourPlusNJoursOuvres4.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Teste la méthode decalerDeNJoursOuvres en vérifiant que le Calendar de départ n'est pas modifié.
     */
    @Test
    public void decalerDe1JourOuvreVerifPasDeModif() {
        DateTime vendredi24Aout2012 = new DateTime(2012, 8, 24, 0, 0, 0, 0);
        Calendar calendar = vendredi24Aout2012.toGregorianCalendar();

        JourUtils.decalerDeNJoursOuvres(calendar, 1);

        assertEquals(2012, calendar.get(Calendar.YEAR));
        assertEquals(8, calendar.get(Calendar.MONTH) + 1);
        assertEquals(24, calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Teste la méthode decalerDeNJoursOuvresWithSaturday en vérifiant que le Calendar de départ n'est pas modifié.
     */
    @Test
    public void decalerDe1JourOuvreVerifPasDeModifWithSaturday() {
        DateTime vendredi24Aout2012 = new DateTime(2012, 8, 24, 0, 0, 0, 0);
        Calendar calendar = vendredi24Aout2012.toGregorianCalendar();

        JourUtils.decalerDeNJoursOuvresWithSaturday(calendar, 1);

        assertEquals(2012, calendar.get(Calendar.YEAR));
        assertEquals(8, calendar.get(Calendar.MONTH) + 1);
        assertEquals(24, calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Teste la méthode decalerDeNJoursOuvres avec des objets Calendar en ajoutant 3 jours.
     */
    @Test
    public void decalerDe3JoursOuvres() {

        DateTime mercredi22Aout2012 = new DateTime(2012, 8, 22, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres = JourUtils.decalerDeNJoursOuvres(mercredi22Aout2012.toGregorianCalendar(), 3);
        assertEquals(2012, jourPlusNJoursOuvres.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres.get(Calendar.MONTH) + 1);
        assertEquals(27, jourPlusNJoursOuvres.get(Calendar.DAY_OF_MONTH));

        DateTime lundi27Aout2012 = new DateTime(2012, 8, 27, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres2 = JourUtils.decalerDeNJoursOuvres(lundi27Aout2012.toGregorianCalendar(), 3);
        assertEquals(2012, jourPlusNJoursOuvres2.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres2.get(Calendar.MONTH) + 1);
        assertEquals(30, jourPlusNJoursOuvres2.get(Calendar.DAY_OF_MONTH));

        DateTime vendredi31Aout2012 = new DateTime(2012, 8, 31, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres3 = JourUtils.decalerDeNJoursOuvres(vendredi31Aout2012.toGregorianCalendar(), 3);
        assertEquals(2012, jourPlusNJoursOuvres3.get(Calendar.YEAR));
        assertEquals(9, jourPlusNJoursOuvres3.get(Calendar.MONTH) + 1);
        assertEquals(5, jourPlusNJoursOuvres3.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Teste la méthode decalerDeNJoursOuvresWithSaturday avec des objets Calendar en ajoutant 3 jours.
     */
    @Test
    public void decalerDe3JoursOuvresWithSaturday() {

        DateTime mercredi22Aout2012 = new DateTime(2012, 8, 22, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres = JourUtils.decalerDeNJoursOuvresWithSaturday(mercredi22Aout2012.toGregorianCalendar(), 3);
        assertEquals(2012, jourPlusNJoursOuvres.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres.get(Calendar.MONTH) + 1);
        assertEquals(25, jourPlusNJoursOuvres.get(Calendar.DAY_OF_MONTH));

        DateTime lundi27Aout2012 = new DateTime(2012, 8, 27, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres2 = JourUtils.decalerDeNJoursOuvresWithSaturday(lundi27Aout2012.toGregorianCalendar(), 3);
        assertEquals(2012, jourPlusNJoursOuvres2.get(Calendar.YEAR));
        assertEquals(8, jourPlusNJoursOuvres2.get(Calendar.MONTH) + 1);
        assertEquals(30, jourPlusNJoursOuvres2.get(Calendar.DAY_OF_MONTH));

        DateTime vendredi31Aout2012 = new DateTime(2012, 8, 31, 0, 0, 0, 0);
        Calendar jourPlusNJoursOuvres3 = JourUtils.decalerDeNJoursOuvresWithSaturday(vendredi31Aout2012.toGregorianCalendar(), 3);
        assertEquals(2012, jourPlusNJoursOuvres3.get(Calendar.YEAR));
        assertEquals(9, jourPlusNJoursOuvres3.get(Calendar.MONTH) + 1);
        assertEquals(4, jourPlusNJoursOuvres3.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Teste la méthode decalerDeNJoursOuvres avec un nombre de jours négatif.
     */
    @Test(expected = IllegalArgumentException.class)
    public void decalerDeMoins1JourOuvres() {

        DateTime vendredi24Aout2012 = new DateTime(2012, 8, 24, 23, 59, 59, 999);
        JourUtils.decalerDeNJoursOuvres(vendredi24Aout2012.toGregorianCalendar(), -1);
    }

    /**
     * Teste la méthode decalerDeNJoursOuvresWithSaturday avec un nombre de jours négatif.
     */
    @Test(expected = IllegalArgumentException.class)
    public void decalerDeMoins1JourOuvresWithSaturday() {

        DateTime vendredi24Aout2012 = new DateTime(2012, 8, 24, 23, 59, 59, 999);
        JourUtils.decalerDeNJoursOuvresWithSaturday(vendredi24Aout2012.toGregorianCalendar(), -1);
    }

    /**
     * Teste la méthode decalerDeNJoursOuvres avec des objets Date en ajoutant 1 jour.
     */
    @Test
    public void decalerDe1JourOuvreDate() {

        DateTime mercredi22Aout2012 = new DateTime(2012, 8, 22, 0, 0, 0, 0);
        Date jourPlusNJoursOuvres = JourUtils.decalerDeNJoursOuvres(mercredi22Aout2012.toDate(), 1);
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.FRANCE);
        String dateString = df.format(jourPlusNJoursOuvres);
        assertEquals("jeudi 23 août 2012 00:00:00", dateString);

        DateTime mardi28Fevrier2012 = new DateTime(2012, 2, 28, 13, 45, 2, 100);
        Date jourPlusNJoursOuvres2 = JourUtils.decalerDeNJoursOuvres(mardi28Fevrier2012.toDate(), 1);
        DateFormat df2 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.FRANCE);
        String dateString2 = df2.format(jourPlusNJoursOuvres2);
        assertEquals("mercredi 29 février 2012 13:45:02", dateString2);

        DateTime jeudi28Fevrier2013 = new DateTime(2013, 2, 28, 13, 45, 2, 100);
        Date jourPlusNJoursOuvres3 = JourUtils.decalerDeNJoursOuvres(jeudi28Fevrier2013.toDate(), 1);
        DateFormat df3 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.FRANCE);
        String dateString3 = df3.format(jourPlusNJoursOuvres3);
        assertEquals("vendredi 1 mars 2013 13:45:02", dateString3);
    }

    /**
     * Teste la méthode decalerDeNJoursOuvres avec des objets Date en ajoutant 1 jour.
     */
    @Test
    public void decalerDe1JourOuvreDateWithSaturday() {

        DateTime mercredi22Aout2012 = new DateTime(2012, 8, 22, 0, 0, 0, 0);
        Date jourPlusNJoursOuvres = JourUtils.decalerDeNJoursOuvresWithSaturday(mercredi22Aout2012.toDate(), 1);
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.FRANCE);
        String dateString = df.format(jourPlusNJoursOuvres);
        assertEquals("jeudi 23 août 2012 00:00:00", dateString);

        DateTime mardi28Fevrier2012 = new DateTime(2012, 2, 28, 13, 45, 2, 100);
        Date jourPlusNJoursOuvres2 = JourUtils.decalerDeNJoursOuvresWithSaturday(mardi28Fevrier2012.toDate(), 1);
        DateFormat df2 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.FRANCE);
        String dateString2 = df2.format(jourPlusNJoursOuvres2);
        assertEquals("mercredi 29 février 2012 13:45:02", dateString2);

        DateTime jeudi28Fevrier2013 = new DateTime(2013, 2, 28, 13, 45, 2, 100);
        Date jourPlusNJoursOuvres3 = JourUtils.decalerDeNJoursOuvresWithSaturday(jeudi28Fevrier2013.toDate(), 1);
        DateFormat df3 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.FRANCE);
        String dateString3 = df3.format(jourPlusNJoursOuvres3);
        assertEquals("vendredi 1 mars 2013 13:45:02", dateString3);

        DateTime vendredi1Mars2013 = new DateTime(2013, 3, 1, 13, 45, 2, 100);
        Date jourPlusNJoursOuvres4 = JourUtils.decalerDeNJoursOuvresWithSaturday(vendredi1Mars2013.toDate(), 1);
        DateFormat df4 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.FRANCE);
        String dateString4 = df4.format(jourPlusNJoursOuvres4);
        assertEquals("samedi 2 mars 2013 13:45:02", dateString4);
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec la même date.
     */
    @Test
    public void getNbJoursOuvresEntre0() {
        Calendar mercredi22Aout2012 = new DateTime(2012, 8, 22, 0, 0, 0, 0).toGregorianCalendar();
        assertEquals(0, JourUtils.getNbJoursOuvresEntre(mercredi22Aout2012, mercredi22Aout2012));

        Calendar mercredi22Aout2012AutreHeure = new DateTime(2012, 8, 22, 23, 59, 0, 0).toGregorianCalendar();
        assertEquals(0, JourUtils.getNbJoursOuvresEntre(mercredi22Aout2012, mercredi22Aout2012AutreHeure));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntreWithSaturday avec la même date.
     */
    @Test
    public void getNbJoursOuvresEntre0WithSaturday() {
        Calendar mercredi22Aout2012 = new DateTime(2012, 8, 22, 0, 0, 0, 0).toGregorianCalendar();
        assertEquals(0, JourUtils.getNbJoursOuvresEntreWithSaturday(mercredi22Aout2012, mercredi22Aout2012));

        Calendar mercredi22Aout2012AutreHeure = new DateTime(2012, 8, 22, 23, 59, 0, 0).toGregorianCalendar();
        assertEquals(0, JourUtils.getNbJoursOuvresEntreWithSaturday(mercredi22Aout2012, mercredi22Aout2012AutreHeure));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec deux jours consécutifs en semaine.
     */
    @Test
    public void getNbJoursOuvresEntreJoursConsecutifsSemaine() {
        /* avec 2 jours séparés de moins de 24h */
        Calendar mercredi22Aout2012 = new DateTime(2012, 8, 22, 23, 59, 59, 999).toGregorianCalendar();
        Calendar jeudi23Aout2012 = new DateTime(2012, 8, 23, 17, 45, 00, 000).toGregorianCalendar();
        assertEquals(1, JourUtils.getNbJoursOuvresEntre(mercredi22Aout2012, jeudi23Aout2012));

        /* avec 2 jours séparés de plus de 24h */
        Calendar mercredi22Aout2012Bis = new DateTime(2012, 8, 22, 0, 0, 0, 0).toGregorianCalendar();
        Calendar jeudi23Aout2012Bis = new DateTime(2012, 8, 23, 23, 59, 59, 999).toGregorianCalendar();
        assertEquals(1, JourUtils.getNbJoursOuvresEntre(mercredi22Aout2012Bis, jeudi23Aout2012Bis));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntreWithSaturday avec deux jours consécutifs en semaine.
     */
    @Test
    public void getNbJoursOuvresEntreJoursConsecutifsSemaineWithSaturday() {
        /* avec 2 jours séparés de moins de 24h */
        Calendar mercredi22Aout2012 = new DateTime(2012, 8, 22, 23, 59, 59, 999).toGregorianCalendar();
        Calendar jeudi23Aout2012 = new DateTime(2012, 8, 23, 17, 45, 00, 000).toGregorianCalendar();
        assertEquals(1, JourUtils.getNbJoursOuvresEntreWithSaturday(mercredi22Aout2012, jeudi23Aout2012));

        /* avec 2 jours séparés de plus de 24h */
        Calendar mercredi22Aout2012Bis = new DateTime(2012, 8, 22, 0, 0, 0, 0).toGregorianCalendar();
        Calendar jeudi23Aout2012Bis = new DateTime(2012, 8, 23, 23, 59, 59, 999).toGregorianCalendar();
        assertEquals(1, JourUtils.getNbJoursOuvresEntreWithSaturday(mercredi22Aout2012Bis, jeudi23Aout2012Bis));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec deux jours et un week-end entre.
     */
    @Test
    public void getNbJoursOuvresEntreJoursAutourWeekEnd() {

        Calendar vendredi24Aout2012 = new DateTime(2012, 8, 24, 0, 0, 0, 0).toGregorianCalendar();
        Calendar lundi27Aout2012 = new DateTime(2012, 8, 27, 0, 0, 0, 0).toGregorianCalendar();
        assertEquals(1, JourUtils.getNbJoursOuvresEntre(vendredi24Aout2012, lundi27Aout2012));

        Calendar samedi25Aout2012 = new DateTime(2012, 8, 25, 0, 0, 0, 0).toGregorianCalendar();
        Calendar dimanche2Septembre = new DateTime(2012, 9, 1, 0, 0, 0, 0).toGregorianCalendar();
        assertEquals(5, JourUtils.getNbJoursOuvresEntre(samedi25Aout2012, dimanche2Septembre));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntreWithSaturday avec deux jours et un week-end entre.
     */
    @Test
    public void getNbJoursOuvresEntreJoursAutourWeekEndWithSaturday() {

        Calendar vendredi24Aout2012 = new DateTime(2012, 8, 24, 0, 0, 0, 0).toGregorianCalendar();
        Calendar lundi27Aout2012 = new DateTime(2012, 8, 27, 0, 0, 0, 0).toGregorianCalendar();
        assertEquals(2, JourUtils.getNbJoursOuvresEntreWithSaturday(vendredi24Aout2012, lundi27Aout2012));

        Calendar samedi25Aout2012 = new DateTime(2012, 8, 25, 0, 0, 0, 0).toGregorianCalendar();
        Calendar dimanche2Septembre = new DateTime(2012, 9, 1, 0, 0, 0, 0).toGregorianCalendar();
        assertEquals(6, JourUtils.getNbJoursOuvresEntreWithSaturday(samedi25Aout2012, dimanche2Septembre));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec deux jours consécutifs en semaine.
     */
    @Test
    public void getNbJoursOuvresEntre1() {
        /* avec 2 jours séparés de moins de 24h */
        Calendar mercredi22Aout2012 = new DateTime(2012, 8, 22, 23, 59, 59, 999).toGregorianCalendar();
        Calendar jeudi23Aout2012 = new DateTime(2012, 8, 23, 17, 45, 00, 000).toGregorianCalendar();
        assertEquals(1, JourUtils.getNbJoursOuvresEntre(mercredi22Aout2012, jeudi23Aout2012));

        /* avec 2 jours séparés de plus de 24h */
        Calendar mercredi22Aout2012Bis = new DateTime(2012, 8, 22, 0, 0, 0, 0).toGregorianCalendar();
        Calendar jeudi23Aout2012Bis = new DateTime(2012, 8, 23, 23, 59, 59, 999).toGregorianCalendar();
        assertEquals(1, JourUtils.getNbJoursOuvresEntre(mercredi22Aout2012Bis, jeudi23Aout2012Bis));

        /* avec 2 jours séparés de pile 24h */
        Calendar mercredi22Aout2012Ter = new DateTime(2012, 8, 22, 0, 0, 0, 0).toGregorianCalendar();
        Calendar jeudi23Aout2012Ter = new DateTime(2012, 8, 23, 0, 0, 0, 0).toGregorianCalendar();
        assertEquals(1, JourUtils.getNbJoursOuvresEntre(mercredi22Aout2012Ter, jeudi23Aout2012Ter));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec deux jours consécutifs en semaine.
     */
    @Test
    public void getNbJoursOuvresEntre1WithSaturday() {
        /* avec 2 jours séparés de moins de 24h */
        Calendar mercredi22Aout2012 = new DateTime(2012, 8, 22, 23, 59, 59, 999).toGregorianCalendar();
        Calendar jeudi23Aout2012 = new DateTime(2012, 8, 23, 17, 45, 00, 000).toGregorianCalendar();
        assertEquals(1, JourUtils.getNbJoursOuvresEntreWithSaturday(mercredi22Aout2012, jeudi23Aout2012));

        /* avec 2 jours séparés de plus de 24h */
        Calendar mercredi22Aout2012Bis = new DateTime(2012, 8, 22, 0, 0, 0, 0).toGregorianCalendar();
        Calendar jeudi23Aout2012Bis = new DateTime(2012, 8, 23, 23, 59, 59, 999).toGregorianCalendar();
        assertEquals(1, JourUtils.getNbJoursOuvresEntreWithSaturday(mercredi22Aout2012Bis, jeudi23Aout2012Bis));

        /* avec 2 jours séparés de pile 24h */
        Calendar mercredi22Aout2012Ter = new DateTime(2012, 8, 22, 0, 0, 0, 0).toGregorianCalendar();
        Calendar jeudi23Aout2012Ter = new DateTime(2012, 8, 23, 0, 0, 0, 0).toGregorianCalendar();
        assertEquals(1, JourUtils.getNbJoursOuvresEntreWithSaturday(mercredi22Aout2012Ter, jeudi23Aout2012Ter));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre autour du jour de l'an.
     */
    @Test
    public void getNbJoursOuvresEntreJoursAutourJourDeLan() {

        Calendar lundi31Decembre2012 = new DateTime(2012, 12, 31, 18, 0, 0, 0).toGregorianCalendar();
        Calendar vendredi4Janvier2013 = new DateTime(2013, 1, 4, 12, 0, 0, 0).toGregorianCalendar();
        assertEquals(3, JourUtils.getNbJoursOuvresEntre(lundi31Decembre2012, vendredi4Janvier2013));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntreWithSaturday autour du jour de l'an.
     */
    @Test
    public void getNbJoursOuvresEntreJoursAutourJourDeLanWithSaturday() {

        Calendar lundi31Decembre2012 = new DateTime(2012, 12, 31, 18, 0, 0, 0).toGregorianCalendar();
        Calendar samedi5Janvier2013 = new DateTime(2013, 1, 5, 12, 0, 0, 0).toGregorianCalendar();
        assertEquals(4, JourUtils.getNbJoursOuvresEntreWithSaturday(lundi31Decembre2012, samedi5Janvier2013));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre autour du 28/29 février.
     */
    @Test
    public void getNbJoursOuvresEntreJoursAutour28Fevrier() {

        Calendar mardi28Fevrier2012 = new DateTime(2012, 2, 28, 18, 0, 0, 0).toGregorianCalendar();
        Calendar jeudi1erMars2012 = new DateTime(2012, 3, 1, 12, 0, 0, 0).toGregorianCalendar();
        assertEquals(2, JourUtils.getNbJoursOuvresEntre(mardi28Fevrier2012, jeudi1erMars2012));

        Calendar jeudi28Fevrier2013 = new DateTime(2013, 2, 28, 18, 0, 0, 0).toGregorianCalendar();
        Calendar vendredi1erMars2013 = new DateTime(2013, 3, 1, 12, 0, 0, 0).toGregorianCalendar();
        assertEquals(1, JourUtils.getNbJoursOuvresEntre(jeudi28Fevrier2013, vendredi1erMars2013));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntreWithSaturday autour du 28/29 février.
     */
    @Test
    public void getNbJoursOuvresEntreJoursAutour28FevrierWithSaturday() {

        Calendar mardi28Fevrier2012 = new DateTime(2012, 2, 28, 18, 0, 0, 0).toGregorianCalendar();
        Calendar jeudi1erMars2012 = new DateTime(2012, 3, 1, 12, 0, 0, 0).toGregorianCalendar();
        assertEquals(2, JourUtils.getNbJoursOuvresEntreWithSaturday(mardi28Fevrier2012, jeudi1erMars2012));

        Calendar jeudi28Fevrier2013 = new DateTime(2013, 2, 28, 18, 0, 0, 0).toGregorianCalendar();
        Calendar samedi2Mars2013 = new DateTime(2013, 3, 2, 12, 0, 0, 0).toGregorianCalendar();
        assertEquals(2, JourUtils.getNbJoursOuvresEntreWithSaturday(jeudi28Fevrier2013, samedi2Mars2013));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec des objets Date.
     */
    @Test
    public void getNbJoursOuvresEntreAvecDate() {

        Date mardi28Fevrier2012 = new DateTime(2012, 2, 28, 18, 0, 0, 0).toDate();
        Date jeudi1erMars2012 = new DateTime(2012, 3, 1, 12, 0, 0, 0).toDate();
        assertEquals(2, JourUtils.getNbJoursOuvresEntre(mardi28Fevrier2012, jeudi1erMars2012));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec des objets Date.
     */
    @Test
    public void getNbJoursOuvresEntreAvecDateWithSaturday() {

        Date mardi28Fevrier2012 = new DateTime(2012, 2, 28, 18, 0, 0, 0).toDate();
        Date samedi3Mars2012 = new DateTime(2012, 3, 3, 12, 0, 0, 0).toDate();
        assertEquals(4, JourUtils.getNbJoursOuvresEntreWithSaturday(mardi28Fevrier2012, samedi3Mars2012));
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec la 1ère Date à null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getNbJoursOuvresEntreAvecDate1Null() {

        Date mardi28Fevrier2012 = new DateTime(2012, 2, 28, 18, 0, 0, 0).toDate();
        JourUtils.getNbJoursOuvresEntre(null, mardi28Fevrier2012);
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec la 1ère Date à null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getNbJoursOuvresEntreAvecDate1NullWithSaturday() {

        Date mardi28Fevrier2012 = new DateTime(2012, 2, 28, 18, 0, 0, 0).toDate();
        JourUtils.getNbJoursOuvresEntreWithSaturday(null, mardi28Fevrier2012);
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec la 2ème Date à null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getNbJoursOuvresEntreAvecDate2Null() {

        Date mardi28Fevrier2012 = new DateTime(2012, 2, 28, 18, 0, 0, 0).toDate();
        JourUtils.getNbJoursOuvresEntre(mardi28Fevrier2012, null);
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec la 2ème Date à null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getNbJoursOuvresEntreAvecDate2NullWithSaturday() {

        Date mardi28Fevrier2012 = new DateTime(2012, 2, 28, 18, 0, 0, 0).toDate();
        JourUtils.getNbJoursOuvresEntreWithSaturday(mardi28Fevrier2012, null);
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec la 1ère date plus récente que la 2ème.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getNbJoursOuvresEntreAvecDatesInverses() {

        Date mardi28Fevrier2012 = new DateTime(2012, 2, 28, 18, 0, 0, 0).toDate();
        Date jeudi1erMars2012 = new DateTime(2012, 3, 1, 12, 0, 0, 0).toDate();
        JourUtils.getNbJoursOuvresEntre(jeudi1erMars2012, mardi28Fevrier2012);
    }

    /**
     * Teste la méthode getNbJoursOuvresEntre avec la 1ère date plus récente que la 2ème.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getNbJoursOuvresEntreAvecDatesInversesWithSaturday() {

        Date mardi28Fevrier2012 = new DateTime(2012, 2, 28, 18, 0, 0, 0).toDate();
        Date jeudi1erMars2012 = new DateTime(2012, 3, 1, 12, 0, 0, 0).toDate();
        JourUtils.getNbJoursOuvresEntreWithSaturday(jeudi1erMars2012, mardi28Fevrier2012);
    }
}
