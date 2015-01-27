/**
 * 
 */
package santeclair.lunar.framework.web.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import santeclair.lunar.framework.web.security.UtilisateurAvecRolesBean;
import santeclair.lunar.framework.web.security.UtilisateurSanteclair;
import santeclair.lunar.framework.web.security.service.SolarUserService;

import com.google.common.annotations.VisibleForTesting;

/**
 * V�rifie les acc�s et les r�les de l'utilisateur pr�alablement authentifi�.
 * Cette v�rification est faite en utilisant la base de donn�es solar.
 * Configur� comme bean Spring dans le fichier applicationContext-security.xml.
 * 
 * @see SolarUserService
 * @author jfourmond
 * 
 */
public class SolarUserServiceImpl implements SolarUserService<Authentication> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SolarUserServiceImpl.class);

    /**
     * La fabrique de clients HTTP pour le web service webext de validation du jeton..
     */
    private JAXRSClientFactoryBean jaxrsClientFactoryBean;

    /*=======================================================*
     *              champs inject�s par Spring
     *=======================================================*/

    /**
     * Le nom de l'application dans la base Solar,
     * table : tr_application, colonne : libelleapplication.
     */
    private String nomApplication;

    /**
     * Le d�but de l'URL (sans le path) du WS de r�cup�ration de l'utilisateur avec ses r�les dans l'application.
     * Ex : http://localhost/solaradmin/rest.
     */
    private String urlWebService;

    /**
     * permet de tester unitairement le comportement de la cache.
     */
    private SolarUserServiceImpl solarUserServiceImplMock;

    /*=======================================================*
     *              		code m�tier
     *=======================================================*/

    /**
     * Initialise le web client. M�thode appel�e par Spring une fois les d�pendences inject�es.
     */
    public void init() {
        jaxrsClientFactoryBean = new JAXRSClientFactoryBean();
        jaxrsClientFactoryBean.setThreadSafe(true);
        jaxrsClientFactoryBean.setAddress(urlWebService);
        LOGGER.info("URL du ws solaradmin : " + urlWebService);
    }

    /**
     * V�rifie que l'utilisateur pr�alablement authentifi� par son login Windows
     * a bien acc�s � l'application dans la bdd Solar. On r�cup�re l'utilisateur
     * avec ses r�les et on renvoie un objet Spring Security correspondant.
     * 
     * @see org.springframework.security.core.userdetails.AuthenticationUserDetailsService#loadUserDetails(org.springframework.security.core.Authentication)
     */
    @Cacheable(value = "userDetails")
    public UserDetails loadUserDetails(Authentication token)
                    throws UsernameNotFoundException {
        if (solarUserServiceImplMock != null) {
            solarUserServiceImplMock.noCache();
        }

        if (token == null)
            throw new IllegalArgumentException("L'objet Authentication en param�tre ne doit pas �tre null.");

        Boolean enabled = null;
        List<GrantedAuthority> authorities = null;
        String principalName = token.getName();
        String loginUtilisateur = getLoginUtilisateur(principalName);

        WebClient webClient = jaxrsClientFactoryBean.createWebClient();
        String path = "/utilisateurs/" + loginUtilisateur + "/roles/application/" + nomApplication;
        webClient.path(path);

        LOGGER.trace("Appel du WS : {}", urlWebService + path);
        UtilisateurAvecRolesBean utilisateurSolar = webClient.get(UtilisateurAvecRolesBean.class);

        String userName = utilisateurSolar.getUserName();
        if (utilisateurSolar.getArchive() == Boolean.FALSE) {
            /* L'utilisateur existe dans Solar et est actif */
            enabled = true;
            authorities = getAuthorities(utilisateurSolar);
        } else {
            enabled = false;
            authorities = new ArrayList<GrantedAuthority>();
            userName = "loginNonConfigure";
        }

        String idenfitiant = utilisateurSolar.getIdentifiant();
        String trigramme = utilisateurSolar.getTrigramme();
        String prenom = utilisateurSolar.getPrenom();
        String nom = utilisateurSolar.getNom();

        UserDetails userDetails = new UtilisateurSanteclair(idenfitiant, userName, trigramme, nom, prenom, enabled, authorities);
        return userDetails;
    }

    /**
     * Renvoie le login court d'un utilisateur (sans le nom de domaine), en minuscules.
     * 
     * @param principalName Le login Windows de l'utilisateur.
     */
    private String getLoginUtilisateur(String principalName) {
        String principalNameLowerCase = principalName.toLowerCase();
        Integer posBackslash = principalNameLowerCase.indexOf("\\");
        String login = null;
        if (posBackslash == -1) {
            login = principalNameLowerCase;
        } else {
            login = principalNameLowerCase.substring(posBackslash + 1);
        }
        if ("".equals(login)) {
            login = "loginVide";
        }
        return login;
    }

    /**
     * Cr�e des r�les Spring Security � partir des r�les de l'utilisateur
     * r�cup�r�s dans la bdd solar (dans la table tr_roleapputilisateur).
     */
    private List<GrantedAuthority> getAuthorities(UtilisateurAvecRolesBean rolesUtilisateurSolar) {
        List<String> listeRoles = rolesUtilisateurSolar.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : listeRoles) {
            SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority(role);
            authorities.add(userAuthority);
        }
        return authorities;
    }

    void noCache() {

    }

    /*=======================================================*
     *                 setters (pour Spring)
     *=======================================================*/

    /**
     * @param urlWebService the urlWebService to set
     */
    public void setUrlWebService(String urlWebService) {
        this.urlWebService = urlWebService;
    }

    /**
     * @param nomApplication the nomApplication to set
     */
    public void setNomApplication(String nomApplication) {
        this.nomApplication = nomApplication;
    }

    void setSolarUserServiceImplMockito(SolarUserServiceImpl solarUserServiceImpl) {
        this.solarUserServiceImplMock = solarUserServiceImpl;
    }

    /**
     * @param jaxrsClientFactoryBean the jaxrsClientFactoryBean to set
     */
    @VisibleForTesting
    void setJaxrsClientFactoryBean(JAXRSClientFactoryBean jaxrsClientFactoryBean) {
        this.jaxrsClientFactoryBean = jaxrsClientFactoryBean;
    }

}
