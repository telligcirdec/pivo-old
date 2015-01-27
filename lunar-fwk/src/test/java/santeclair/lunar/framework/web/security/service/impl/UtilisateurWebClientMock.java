/**
 * 
 */
package santeclair.lunar.framework.web.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;

import santeclair.lunar.framework.web.security.UtilisateurAvecRolesBean;

/**
 * @author jfourmond
 * 
 */
public class UtilisateurWebClientMock extends WebClient {

    enum ModeTest {
        LOGIN_AVEC_ROLES,
        LOGIN_INCONNU,
        LOGIN_VIDE;
    }

    /**
     * Définit ce que le mock va renvoyer.
     */
    public ModeTest modeTest;

    /**
     * Constructeur par défaut.
     */
    public UtilisateurWebClientMock() {
        super("");
    }

    /* (non-Javadoc)
     * @see org.apache.cxf.jaxrs.client.WebClient#get(java.lang.Class)
     */
    @Override
    public <T> T get(Class<T> responseClass) {

        UtilisateurAvecRolesBean utilisateurAvecRolesBean = new UtilisateurAvecRolesBean();
        if (modeTest == ModeTest.LOGIN_AVEC_ROLES) {
            utilisateurAvecRolesBean.setUserName("jfourmond");
            String role1 = "Informatique développement";
            String role2 = "Utilisateur";
            List<String> roles = new ArrayList<String>();
            roles.add(role1);
            roles.add(role2);
            utilisateurAvecRolesBean.setRoles(roles);
            utilisateurAvecRolesBean.setArchive(false);
        }
        return responseClass.cast(utilisateurAvecRolesBean);
    }

}
