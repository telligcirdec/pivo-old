/**
 * 
 */
package santeclair.lunar.framework.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;

/**
 * Classe de tests unitaires pour la classe {@link ResultatSelect}.
 * 
 * @author jfourmond
 * 
 */
public class ResultatSelectUnitTest {

    /**
     * Teste le constructeur.
     */
    @Test
    public void testNew() {
        ResultatSelect result = new ResultatSelect("select * from t_job", 2, new String[]{"id", "code"}, new String[]{"int", "varchar"});
        assertEquals(0, result.getListLignesResultat().size());
        assertEquals(0, result.getMapLignesResultat().size());
        assertEquals("select * from t_job", result.getSqlQuery());
        assertEquals(2, result.getNbColonnes().intValue());
        assertEquals("id", result.getNomsColonnes()[0]);
        assertEquals("code", result.getNomsColonnes()[1]);
        assertEquals("int", result.getTypesColonnes()[0]);
        assertEquals("varchar", result.getTypesColonnes()[1]);
    }

    /**
     * Teste le constructeur avec un nombre de colonnes ne correspondant pas au noms de colonnes.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNewKO() {
        new ResultatSelect("select * from t_job", 1, new String[]{"id", "code"}, new String[]{"int", "varchar"});
    }

    /**
     * Teste les méthodes addLigne et getLigne pour une ligne avec le bon nombre de colonnes,
     * et une ligne avec un mauvais nombre de colonnes.
     */
    @Test
    public void addLigneGetLigne() {
        ResultatSelect result = new ResultatSelect("select * from t_job", 2, new String[]{"id", "code"}, new String[]{"int", "varchar"});
        assertNull(result.getLigne("id1"));

        result.addLigne(new String[]{"id1", "code1"});
        assertEquals(1, result.getListLignesResultat().size());
        assertEquals(1, result.getMapLignesResultat().size());
        assertNotNull(result.getLigne("id1"));

        try {
            result.addLigne(new String[]{"id2"});
        } catch (IllegalArgumentException iae) {
            assertEquals(1, result.getListLignesResultat().size());
        }
    }

    /**
     * Teste la méthode toHtml.
     */
    @Test
    public void toHtml() {
        ResultatSelect result = new ResultatSelect("select * from t_job", 2, new String[]{"id", "code"}, new String[]{"int", "varchar"});
        String html = result.toHtml();
        assertNotNull(html);
        assertTrue(html.startsWith("<html>"));
        assertTrue(html.indexOf("select * from t_job") > 0);
        assertTrue(html.indexOf("id") > 0);
        assertTrue(html.indexOf("code") > 0);
    }

    /**
     * Teste la méthode getIdentifiants.
     */
    @Test
    public void getIdentifiants() {
        ResultatSelect result = new ResultatSelect(null, 2, new String[]{"id", "code"}, new String[]{"int", "varchar"});
        Set<String> identifiants = result.getIdentifiants();
        assertEquals(0, identifiants.size());

        result.addLigne(new String[]{"id1", "code1"});
        result.addLigne(new String[]{"id2", "code2"});

        assertEquals(2, identifiants.size());
        assertTrue(identifiants.contains("id1"));
        assertTrue(identifiants.contains("id2"));
    }

    /**
     * Teste la méthode getNumeroColonne.
     */
    @Test
    public void getNumeroColonne() {
        ResultatSelect result = new ResultatSelect(null, 2, new String[]{"id", "code"}, new String[]{"int", "varchar"});
        int numeroColonneId = result.getNumeroColonne("id");
        assertEquals(0, numeroColonneId);
        int numeroColonneCode = result.getNumeroColonne("code");
        assertEquals(1, numeroColonneCode);

        try {
            result.getNumeroColonne("libelle");
            fail();
        } catch (IllegalArgumentException e) {
            /* normal */
        }
    }

    /**
     * Teste la méthode escapeApostrophesEtNulls.
     */
    @Test
    public void escapeApostrophesEtNulls() {
        ResultatSelect resultatSelect = new ResultatSelect(null, 2, new String[]{"id", "code"}, new String[]{"int", "varchar"});
        String[] resultatSelectEscaped = resultatSelect.escapeApostrophesEtNulls(new String[]{"L'équipe", "L'équipe"});
        assertEquals("L'équipe", resultatSelectEscaped[0]); /* L'apostrophe n'a pas été dédoublé parce que le type de la colonne est int. */
        assertEquals("'L''équipe'", resultatSelectEscaped[1]); /* L'apostrophe a bien été dédoublé parce que le type de la colonne est varchar. */

        String[] resultatSelectEscaped2 = resultatSelect.escapeApostrophesEtNulls(new String[]{null, null});
        assertEquals("NULL", resultatSelectEscaped2[0]);
        assertEquals("NULL", resultatSelectEscaped2[1]);

    }
}
