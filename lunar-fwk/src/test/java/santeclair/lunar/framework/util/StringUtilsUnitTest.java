package santeclair.lunar.framework.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de tests unitaires pour JourUtils.
 * 
 * @author jfourmond
 * 
 */
public class StringUtilsUnitTest {

    /*************************************************
     * Tests la méthode replacerAccentsEtEnleverCedille
     *************************************************/
    @Test
    public void replacerAccentsEtEnleverCedille() {

        String chaineTest = "éeçÁç ñ-¾énakuf deiAA";
        String resultat = StringUtils.remplacerAccentsEtEnleverCedille(chaineTest);
        Assert.assertEquals(resultat, "eecac n-yenakuf deiAA");
    }

    @Test
    public void replacerAccentsEtEnleverCedilleNull() {

        String chaineTest = null;
        String resultat = StringUtils.remplacerAccentsEtEnleverCedille(chaineTest);
        Assert.assertEquals(resultat, null);

    }

    @Test
    public void replacerAccentsEtEnleverCedilleEmpty() {

        String chaineTest = "";
        String resultat = StringUtils.remplacerAccentsEtEnleverCedille(chaineTest);
        Assert.assertEquals(resultat, "");

    }

    @Test
    public void replacerAccentsEtEnleverCedilleCaracteresSpeciaux() {

        String chaineTest = "eap  01%ee$gt&";
        String resultat = StringUtils.remplacerAccentsEtEnleverCedille(chaineTest);
        Assert.assertEquals(resultat, "eap  01%ee$gt&");

    }

    @Test
    public void replacerAccentsEtEnleverCedilleUnCaractere() {

        String chaineTest = "é";
        String resultat = StringUtils.remplacerAccentsEtEnleverCedille(chaineTest);
        Assert.assertEquals(resultat, "e");

    }

    /*************************************************
     * Tests la méthode formaterEtMettreEnMajuscule
     *************************************************/
    @Test
    public void formaterEtMettreEnMajuscule() {

        String chaineTest = "éeçÁç ñ-¾énakuf deiAA";
        String resultat = StringUtils.formaterEtMettreEnMajuscule(chaineTest);
        Assert.assertEquals(resultat, "EECAC N-YENAKUF DEIAA");
    }

    @Test
    public void formaterEtMettreEnMajusculeNull() {

        String chaineTest = null;
        String resultat = StringUtils.formaterEtMettreEnMajuscule(chaineTest);
        Assert.assertEquals(resultat, null);
    }

    /*************************************************
     * Tests la méthode comparerAvecLocal
     *************************************************/

    @Test
    public void comparerAvecLocal() {
        Assert.assertTrue(StringUtils.comparerAvecLocal("eECAC N-yENAkUf DEIaa", "EECAC N-YENAKUF DEIAA"));
    }

    @Test
    public void comparerAvecLocalNull() {
        Assert.assertTrue(StringUtils.comparerAvecLocal(null, null));
    }

    @Test
    public void comparerAvecLocalPremierNull() {
        Assert.assertFalse(StringUtils.comparerAvecLocal("", null));
    }

    @Test
    public void comparerAvecLocalDeuxiemeNull() {
        Assert.assertFalse(StringUtils.comparerAvecLocal(null, ""));
    }

    @Test
    public void comparerAvecLocalTailleNonEgal() {
        Assert.assertFalse(StringUtils.comparerAvecLocal("eAkUf DEIaa", "EECAC N-YENAKUF DEIAA"));
    }

    @Test
    public void comparerAvecLocalNonEgal() {
        Assert.assertFalse(StringUtils.comparerAvecLocal("eAkUf zEIaa", "EECAC N-YENAKUF DEIAA"));
    }
}
