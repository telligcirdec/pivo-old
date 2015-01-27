package santeclair.lunar.framework.personnalisation.client.impl;

import java.io.InputStream;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.context.ContextConfiguration;

import santeclair.lunar.framework.personnalisation.PersonnalisationWebClientMock;
import santeclair.lunar.framework.personnalisation.PersonnalisationWebClientMock.ModeTest;
import santeclair.lunar.framework.personnalisation.bean.CharteBean;
import santeclair.lunar.framework.personnalisation.bean.ListePersonnalisationResponseBean;
import santeclair.lunar.framework.personnalisation.bean.RetourPersonnalisationResponseBean;
import santeclair.lunar.framework.personnalisation.client.PersonnalisationWebserviceClient;
import santeclair.lunar.framework.util.JAXRSClientFactoryBeanMock;

@ContextConfiguration(locations = {"/lunarTestApplicationContext.xml"})
@RunWith(value = PowerMockRunner.class)
@PrepareForTest({FacesContext.class})
public class PersonnalisationManagerWebserviceClientTest {

    private FacesContext mockFacesContext;

    private ExternalContext mockExternalContext;

    private InputStream mockInputStream1;

    private InputStream mockInputStream2;

    private JAXRSClientFactoryBeanMock factoryMock;

    private PersonnalisationWebClientMock webClientMock;

    @Before
    public void mock() {
        webClientMock = new PersonnalisationWebClientMock();
        factoryMock = new JAXRSClientFactoryBeanMock();
        factoryMock.setWebClientMock(webClientMock);

        this.mockFacesContext = EasyMock.createMock(FacesContext.class);
        PowerMock.mockStatic(FacesContext.class);
        this.mockExternalContext = EasyMock.createMock(ExternalContext.class);
        this.mockInputStream1 = EasyMock.createMock(InputStream.class);
        this.mockInputStream2 = EasyMock.createMock(InputStream.class);

    }

    @Test
    public void loadChartesApplication() {

        EasyMock.expect(FacesContext.getCurrentInstance()).andReturn(this.mockFacesContext);
        EasyMock.expect(mockFacesContext.getExternalContext()).andReturn(this.mockExternalContext);
        EasyMock.expect(mockFacesContext.getExternalContext()).andReturn(this.mockExternalContext);
        EasyMock.expect(mockExternalContext.getResourceAsStream("/WEB-INF/conf/validation.properties")).andReturn(this.mockInputStream1);
        EasyMock.expect(mockExternalContext.getResourceAsStream("/WEB-INF/conf/perso.properties")).andReturn(this.mockInputStream2);

        PowerMock.replay(FacesContext.class);
        EasyMock.replay(mockFacesContext);
        EasyMock.replay(mockExternalContext);

        webClientMock.modeTest = ModeTest.LISTE_CHARTES;
        PersonnalisationWebserviceClient personnalisationWebserviceClient = new PersonnalisationWebserviceClientImpl();
        ((PersonnalisationWebserviceClientImpl) personnalisationWebserviceClient).setWebClient(webClientMock);

        ListePersonnalisationResponseBean listePersonnalisationResponseBean = personnalisationWebserviceClient
                        .getListePersonnalisation("TESTUNITAIRE");
        Assert.assertEquals("TESTUNITAIRE", listePersonnalisationResponseBean.getIdApplication());
    }

    @Test
    public void loadCharte() {

        EasyMock.expect(FacesContext.getCurrentInstance()).andReturn(this.mockFacesContext);
        EasyMock.expect(mockFacesContext.getExternalContext()).andReturn(this.mockExternalContext);
        EasyMock.expect(mockFacesContext.getExternalContext()).andReturn(this.mockExternalContext);
        EasyMock.expect(mockExternalContext.getResourceAsStream("/WEB-INF/conf/validation.properties")).andReturn(this.mockInputStream1);
        EasyMock.expect(mockExternalContext.getResourceAsStream("/WEB-INF/conf/perso.properties")).andReturn(this.mockInputStream2);

        PowerMock.replay(FacesContext.class);
        EasyMock.replay(mockFacesContext);
        EasyMock.replay(mockExternalContext);

        webClientMock.modeTest = ModeTest.CHARTE;
        PersonnalisationWebserviceClient personnalisationWebserviceClient = new PersonnalisationWebserviceClientImpl();
        ((PersonnalisationWebserviceClientImpl) personnalisationWebserviceClient).setWebClient(webClientMock);

        RetourPersonnalisationResponseBean retourPersonnalisationResponseBean = personnalisationWebserviceClient.getDetailPersonnalisation(
                        "TESTUNITAIRE", "TESTUNITAIRE");
        Assert.assertEquals("TESTUNITAIRE", retourPersonnalisationResponseBean.getIdCharte());
    }

    @Test
    public void testGetDefautCharte() {
        EasyMock.expect(FacesContext.getCurrentInstance()).andReturn(this.mockFacesContext);
        EasyMock.expect(mockFacesContext.getExternalContext()).andReturn(this.mockExternalContext);
        EasyMock.expect(mockFacesContext.getExternalContext()).andReturn(this.mockExternalContext);
        EasyMock.expect(mockExternalContext.getResourceAsStream("/WEB-INF/conf/validation.properties")).andReturn(this.mockInputStream1);
        EasyMock.expect(mockExternalContext.getResourceAsStream("/WEB-INF/conf/perso.properties")).andReturn(this.mockInputStream2);

        PowerMock.replay(FacesContext.class);
        EasyMock.replay(mockFacesContext);
        EasyMock.replay(mockExternalContext);

        webClientMock.modeTest = ModeTest.DEFAUT;
        PersonnalisationWebserviceClient personnalisationWebserviceClient = new PersonnalisationWebserviceClientImpl();
        ((PersonnalisationWebserviceClientImpl) personnalisationWebserviceClient).setWebClient(webClientMock);

        CharteBean retourCharteBean = personnalisationWebserviceClient.rechercheCharteCourrierOrganisme("A");
        Assert.assertEquals("VERSPIEREN", retourCharteBean.getCode());
    }
}
