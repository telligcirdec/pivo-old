/**
 *
 */
package santeclair.lunar.framework.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import santeclair.lunar.framework.bean.ResultatSelect;

/**
 * Classe de tests d'intégration pour la classe JdbcUtils.
 * 
 * @author jfourmond
 * 
 */
@ContextConfiguration(locations = {"classpath:/lunarTestApplicationContext.xml"})
@RunWith(value = SpringJUnit4ClassRunner.class)
@Ignore
public class JdbcUtilsIntegrationTest {

    @Autowired
    @Qualifier("dataSourceDev")
    private DataSource ds;

    /**
     * Teste la méthode select sur la base Referentiel_Gestionnaire de dév.
     * 
     * @throws Exception
     */
    @Test
    public void testSelect() throws Exception {

        String sql = "select * from referentiel_gestionnaire.tr_cellule_traitement;";
        Connection conn = ds.getConnection();
        ResultatSelect result = JdbcUtils.select(conn, sql);
        assertEquals(sql, result.getSqlQuery());
        assertEquals(7, result.getListLignesResultat().size());
        assertEquals(5, result.getNbColonnes().intValue());
        String html = result.toHtml();
        assertTrue(html.indexOf("code cellule traitement") > 0); /* titre colonne CODE */
        assertTrue(html.indexOf("MAGE") > 0); /* contenu colonne CODE */
        assertTrue(html.indexOf("COLL&CO") > 0); /* contenu colonne LIBELLE */

        conn.close();
    }

    /**
     * Teste la méthode select sur la base Referentiel_Gestionnaire de dév
     * avec une requête qui ne ramène aucune ligne.
     * 
     * @throws Exception
     */
    @Test
    public void testSelectAucunResultat() throws Exception {

        String sql = "select CODE_CELLULE_TRAITEMENT as CODE_CELL from referentiel_gestionnaire.tr_cellule_traitement " +
                        "where code_cellule_traitement='TEST'";
        Connection conn = ds.getConnection();
        ResultatSelect result = JdbcUtils.select(conn, sql);
        assertEquals(sql, result.getSqlQuery());
        assertEquals(0, result.getListLignesResultat().size());
        assertEquals(1, result.getNbColonnes().intValue());
        String html = result.toHtml();
        assertTrue(html.indexOf("CODE_CELL") > 0); /* titre colonne CODE */
        assertTrue(html.indexOf("MAGE") < 0); /* contenu colonne CODE : pas trouvé */

        conn.close();
    }

}
