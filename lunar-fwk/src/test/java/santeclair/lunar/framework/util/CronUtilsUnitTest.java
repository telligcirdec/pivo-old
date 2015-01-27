package santeclair.lunar.framework.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Classe de tests unitaires pour CronUtils.
 * 
 * @author jfourmond
 * 
 */
public class CronUtilsUnitTest {

    /**
     * Teste la méthode getCronEnFrancais.
     * Les libellés en français sont définis dans le fichier src/test/resources/CronParserI18N_fr.properties.
     */
    @Test
    public void getCronEnFrancais() {
        assertEquals("", CronUtils.getCronEnFrancais(null));
        assertEquals("", CronUtils.getCronEnFrancais("blabla"));
        assertEquals("Toutes les 5 minutes", CronUtils.getCronEnFrancais("0 0/5 * * * ?"));
        assertEquals("À 5:30 AM, seulement le lundi", CronUtils.getCronEnFrancais("0 30 5 ? * 1"));
    }
}
