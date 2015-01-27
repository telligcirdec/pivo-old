package santeclair.lunar.framework.personnalisation;

import org.apache.cxf.jaxrs.client.WebClient;

import santeclair.lunar.framework.personnalisation.bean.CharteBean;
import santeclair.lunar.framework.personnalisation.bean.ListePersonnalisationResponseBean;
import santeclair.lunar.framework.personnalisation.bean.RetourPersonnalisationResponseBean;

public class PersonnalisationWebClientMock extends WebClient {

    public enum ModeTest {
        LISTE_CHARTES, CHARTE, DEFAUT
    }

    public ModeTest modeTest;

    public PersonnalisationWebClientMock() {
        super("");
    }

    public <T> T get(Class<T> responseClass) {
        if (modeTest == ModeTest.LISTE_CHARTES) {
            ListePersonnalisationResponseBean listePersonnalisationResponseBean = new ListePersonnalisationResponseBean();
            listePersonnalisationResponseBean.setIdApplication("TESTUNITAIRE");
            return responseClass.cast(listePersonnalisationResponseBean);
        } else if (modeTest == ModeTest.CHARTE) {
            RetourPersonnalisationResponseBean retourPersonnalisationResponseBean = new RetourPersonnalisationResponseBean();
            retourPersonnalisationResponseBean.setIdCharte("TESTUNITAIRE");
            return responseClass.cast(retourPersonnalisationResponseBean);
        } else if (modeTest == ModeTest.DEFAUT) {
            CharteBean charteBean = new CharteBean();
            charteBean.setCode("VERSPIEREN");
            return responseClass.cast(charteBean);
        }
        return null;
    }
}
