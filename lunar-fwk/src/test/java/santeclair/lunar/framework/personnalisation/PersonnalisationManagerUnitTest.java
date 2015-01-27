package santeclair.lunar.framework.personnalisation;

import javax.faces.context.FacesContext;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import santeclair.lunar.framework.personnalisation.bean.ElementPersonnalisationResponseBean;
import santeclair.lunar.framework.personnalisation.bean.ListePersonnalisationResponseBean;
import santeclair.lunar.framework.personnalisation.bean.PartenaireResponseBean;
import santeclair.lunar.framework.personnalisation.bean.RetourPersonnalisationResponseBean;
import santeclair.lunar.framework.personnalisation.client.impl.PersonnalisationWebserviceClientImpl;
import santeclair.lunar.framework.personnalisation.impl.PersonnalisationManagerBaseImpl;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FacesContext.class})
public class PersonnalisationManagerUnitTest {

    private ListePersonnalisationResponseBean fakedRetour;

    private PersonnalisationWebserviceClientImpl mockPersonnalisationWebserviceImpl;

    @Before
    public void setup() {

        this.mockPersonnalisationWebserviceImpl = EasyMock.createMock(PersonnalisationWebserviceClientImpl.class);

        /*
         * Initialisation d'une liste de personnalisation a manipuler par le PersoManager
         */
        this.fakedRetour = new ListePersonnalisationResponseBean();
        this.fakedRetour.setIdApplication("TESTUNITAIRE");
        /* Initialisation de la charte TESTUNITAIRE de test */
        RetourPersonnalisationResponseBean charte = new RetourPersonnalisationResponseBean();
        charte.setIdCharte("TESTUNITAIRE");
        /* Ajout d'éléments de personnalisation */
        ElementPersonnalisationResponseBean elementPerso1 = new ElementPersonnalisationResponseBean("PRIMCOLOR", "ffff00", "COLOR_HEX",
                        "couleur principale");
        ElementPersonnalisationResponseBean elementPerso2 = new ElementPersonnalisationResponseBean("SECCOLOR", "000000", "COLOR_HEX",
                        "couleur secondaire");
        ElementPersonnalisationResponseBean elementPerso3 = new ElementPersonnalisationResponseBean("MAINADVERT",
                        "<span style=\"font-size: 10pt; \">Element</span>", "RICH_TEXT", "TESTUNITAIRE");
        ElementPersonnalisationResponseBean elementPerso4 = new ElementPersonnalisationResponseBean("MAINLOGO",
                        "data:image/png;base64,TESTUNITAIRE", "UPLOAD", "Logo partenaire");

        charte.getElementPersonnalisationResponseBeans().add(elementPerso1);
        charte.getElementPersonnalisationResponseBeans().add(elementPerso2);
        charte.getElementPersonnalisationResponseBeans().add(elementPerso3);
        charte.getElementPersonnalisationResponseBeans().add(elementPerso4);

        PartenaireResponseBean partenaire1 = new PartenaireResponseBean("SANTECLAIR", "Santeclair");
        PartenaireResponseBean partenaire2 = new PartenaireResponseBean("TESTUNITAIRE", "TESTUNITAIRE");
        PartenaireResponseBean partenaire3 = new PartenaireResponseBean("PARTSANSCHARTE", "PARTSANSCHARTE");
        charte.getPartenaires().add(partenaire1);
        charte.getPartenaires().add(partenaire2);
        charte.getPartenaires().add(partenaire3);

        this.fakedRetour.getListePersonnalisation().add(charte);

        /* Initialisation de la charte SANTECLAIR de test */
        RetourPersonnalisationResponseBean charteSanteclair = new RetourPersonnalisationResponseBean();
        charteSanteclair.setIdCharte("SANTECLAIR");
        /* Ajout d'éléments de personnalisation */
        ElementPersonnalisationResponseBean elementPersoSanteclair1 = new ElementPersonnalisationResponseBean("PRIMCOLOR", "0e6571",
                        "COLOR_HEX", "couleur principale");
        ElementPersonnalisationResponseBean elementPersoSanteclair2 = new ElementPersonnalisationResponseBean("SECCOLOR", "000000", "COLOR_HEX",
                        "couleur secondaire");
        ElementPersonnalisationResponseBean elementPersoSanteclair3 = new ElementPersonnalisationResponseBean("MAINADVERT",
                        "<span style=\"font-size: 10pt; \">Element</span>", "RICH_TEXT", "TESTUNITAIRE");
        ElementPersonnalisationResponseBean elementPersoSanteclair4 = new ElementPersonnalisationResponseBean("MAINLOGO",
                        "data:image/png;base64,SANTECLAIR", "UPLOAD", "Logo partenaire");

        charteSanteclair.getElementPersonnalisationResponseBeans().add(elementPersoSanteclair1);
        charteSanteclair.getElementPersonnalisationResponseBeans().add(elementPersoSanteclair2);
        charteSanteclair.getElementPersonnalisationResponseBeans().add(elementPersoSanteclair3);
        charteSanteclair.getElementPersonnalisationResponseBeans().add(elementPersoSanteclair4);

        PartenaireResponseBean santeclair = new PartenaireResponseBean("SANTECLAIR", "Santeclair");
        charteSanteclair.getPartenaires().add(santeclair);

        this.fakedRetour.getListePersonnalisation().add(charteSanteclair);
    }

    /**
     * Teste la récupération dans une même charte de de deux valeurs pour une même charte
     * 
     * @throws Exception
     */
    @Test
    public void testGetCodeValue() throws Exception {
        EasyMock.expect(mockPersonnalisationWebserviceImpl.getListePersonnalisation(null)).andReturn(fakedRetour);
        EasyMock.expect(mockPersonnalisationWebserviceImpl.getListePersonnalisation("commun")).andReturn(fakedRetour);

        EasyMock.replay(mockPersonnalisationWebserviceImpl);

        PersonnalisationManagerBase personnalisationManagerTest = new PersonnalisationManagerBaseImpl(mockPersonnalisationWebserviceImpl);

        String value1 = personnalisationManagerTest.getCodeValue("PRIMCOLOR", "TESTUNITAIRE");
        String value2 = personnalisationManagerTest.getCodeValue("MAINLOGO", "TESTUNITAIRE");

        EasyMock.verify(mockPersonnalisationWebserviceImpl);

        Assert.assertEquals("#ffff00", value1);
        Assert.assertEquals("data:image/png;base64,TESTUNITAIRE", value2);
    }

    /**
     * Teste la récupération des éléments de charte pour un partenaire sans charte nominative
     * 
     * @throws Exception
     */
    @Test
    public void testGetCodeValuePartenaireSansCharteNominative() throws Exception {
        EasyMock.expect(mockPersonnalisationWebserviceImpl.getListePersonnalisation("commun")).andReturn(fakedRetour);
        EasyMock.expect(mockPersonnalisationWebserviceImpl.getListePersonnalisation(null)).andReturn(fakedRetour);

        EasyMock.replay(mockPersonnalisationWebserviceImpl);

        PersonnalisationManagerBase personnalisationManagerTest = new PersonnalisationManagerBaseImpl(mockPersonnalisationWebserviceImpl);

        String value1 = personnalisationManagerTest.getCodeValue("PRIMCOLOR", "PARTSANSCHARTE");
        String value2 = personnalisationManagerTest.getCodeValue("MAINLOGO", "PARTSANSCHARTE");

        EasyMock.verify(mockPersonnalisationWebserviceImpl);

        Assert.assertEquals("#ffff00", value1);
        Assert.assertEquals("data:image/png;base64,TESTUNITAIRE", value2);
    }

    /**
     * Teste la récupération des éléments de charte pour un partenaire ou une charte inconnue
     * 
     * @throws Exception
     */
    @Test
    public void testGetCodeValuePartenaireCharteInconnu() throws Exception {
        EasyMock.expect(mockPersonnalisationWebserviceImpl.getListePersonnalisation("commun")).andReturn(fakedRetour);
        EasyMock.expect(mockPersonnalisationWebserviceImpl.getListePersonnalisation(null)).andReturn(fakedRetour);

        EasyMock.replay(mockPersonnalisationWebserviceImpl);

        PersonnalisationManagerBase personnalisationManagerTest = new PersonnalisationManagerBaseImpl(mockPersonnalisationWebserviceImpl);

        String value1 = personnalisationManagerTest.getCodeValue("PRIMCOLOR");
        String value2 = personnalisationManagerTest.getCodeValue("MAINLOGO");

        EasyMock.verify(mockPersonnalisationWebserviceImpl);

        Assert.assertEquals("#0e6571", value1);
        Assert.assertEquals("data:image/png;base64,SANTECLAIR", value2);
    }

}
