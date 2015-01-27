/**
 * 
 */
package santeclair.lunar.framework.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de la XMLUtils
 * 
 * @author fmokhtari
 * 
 */
public class XMLUtilsUnitTest {

    @Test
    public void prettyFormat() {
        String unformattedXml =
                        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><demandeIdentification><identifiantCompagnie>" +
                                        "116</identifiantCompagnie><identifiantSupplementaire>0</identifiantSupplementaire><identifiantMetier>SCS20130009492793" +
                                        "</identifiantMetier><application>FFL-IDENTIFICATION</application><numeroContrat>0000789043</numeroContrat>" +
                                        "<typeEchange>I</typeEchange><nom>PI*</nom><nomBeneficiaire>PINTO</nomBeneficiaire><prenom>OLINDA</prenom>" +
                                        "<prenomBeneficiaire>OLINDA</prenomBeneficiaire><dateNaissanceBeneficiaire>1972-09-05T00:00:00+01:00</dateNaissanceBeneficiaire>" +
                                        "</demandeIdentification>";
        Assert.assertNotNull(XMLUtils.prettyFormat(unformattedXml, 5));
    }
}
