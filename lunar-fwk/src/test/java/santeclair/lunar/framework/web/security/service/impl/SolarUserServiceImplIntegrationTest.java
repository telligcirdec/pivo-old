/**
 * 
 */
package santeclair.lunar.framework.web.security.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import santeclair.lunar.framework.util.JAXRSClientFactoryBeanMock;
import santeclair.lunar.framework.web.security.service.SolarUserService;
import santeclair.lunar.framework.web.security.service.impl.UtilisateurWebClientMock.ModeTest;

/**
 * Classes de tests d'intégration pour la classe SolarUserServiceImpl.
 * 
 * @see SolarUserServiceImpl
 * @author jfourmond
 * 
 */
@ContextConfiguration(locations = {"/lunarTestApplicationContext.xml"})
@RunWith(value = SpringJUnit4ClassRunner.class)
public class SolarUserServiceImplIntegrationTest {

    @Autowired
    private SolarUserService<Authentication> solarUserService;

    private SolarUserServiceImpl solarUserServiceMock;

    private UtilisateurWebClientMock webClientMock;

    @Before
    public void mock() {
        webClientMock = new UtilisateurWebClientMock();
        JAXRSClientFactoryBeanMock factoryMock = new JAXRSClientFactoryBeanMock();
        factoryMock.setWebClientMock(webClientMock);
        ((SolarUserServiceImpl) solarUserService).setJaxrsClientFactoryBean(factoryMock);

        solarUserServiceMock = Mockito.mock(SolarUserServiceImpl.class);
        ((SolarUserServiceImpl) solarUserService).setSolarUserServiceImplMockito(solarUserServiceMock);
    }

    /**
     * Teste la méthode loadUserDetails pour un login forme longue configuré avec 2 rôles dans l'application.
     * On vérifie qu'on ramène bien les 2 rôles définis en base pour cet utilisateur.
     */
    @Test
    public void loadUserDetailsLoginLong() {
        Authentication token = new PreAuthenticatedAuthenticationToken("SANTECLAIR\\JFOURMOND", null);
        webClientMock.modeTest = ModeTest.LOGIN_AVEC_ROLES;
        UserDetails userDetails = solarUserService.loadUserDetails(token);
        assertNotNull(userDetails);
        assertTrue(userDetails.isEnabled());
        assertEquals("jfourmond", userDetails.getUsername());
        assertNotNull(userDetails.getAuthorities());
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        assertEquals(2, roles.size());
        boolean roleUtilisateurTrouve = false;
        boolean roleDeveloppeurTrouve = false;
        for (GrantedAuthority role : roles) {
            if ("Informatique développement".equals(role.getAuthority())) {
                roleDeveloppeurTrouve = true;
            } else if ("Utilisateur".equals(role.getAuthority())) {
                roleUtilisateurTrouve = true;
            }
        }
        assertTrue(roleUtilisateurTrouve);
        assertTrue(roleDeveloppeurTrouve);

        Mockito.verify(solarUserServiceMock, Mockito.times(1)).noCache();
    }

    /**
     * Teste la méthode loadUserDetails pour un login forme courte configuré avec 2 rôles dans l'application.
     * On vérifie qu'on ramène bien les 2 rôles définis en base pour cet utilisateur.
     */
    @Test
    public void loadUserDetailsLoginCourt() {
        Authentication token = new PreAuthenticatedAuthenticationToken("jfourmond", null);

        webClientMock.modeTest = ModeTest.LOGIN_AVEC_ROLES;
        UserDetails userDetails = solarUserService.loadUserDetails(token);
        assertNotNull(userDetails);
        assertTrue(userDetails.isEnabled());
        assertEquals("jfourmond", userDetails.getUsername());
        assertNotNull(userDetails.getAuthorities());
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        assertEquals(2, roles.size());
        boolean roleUtilisateurTrouve = false;
        boolean roleDeveloppeurTrouve = false;
        for (GrantedAuthority role : roles) {
            if ("Informatique développement".equals(role.getAuthority())) {
                roleDeveloppeurTrouve = true;
            } else if ("Utilisateur".equals(role.getAuthority())) {
                roleUtilisateurTrouve = true;
            }
        }
        assertTrue(roleUtilisateurTrouve);
        assertTrue(roleDeveloppeurTrouve);

        Mockito.verify(solarUserServiceMock, Mockito.times(1)).noCache();
    }

    /**
     * Teste la méthode loadUserDetails avec un login inconnu.
     */
    @Test
    public void loadUserDetailsLoginInconnu() {
        Authentication token = new PreAuthenticatedAuthenticationToken("inconnu", null);

        webClientMock.modeTest = ModeTest.LOGIN_INCONNU;
        UserDetails userDetails = solarUserService.loadUserDetails(token);
        assertNotNull(userDetails);
        assertFalse(userDetails.isEnabled());
        assertEquals("loginNonConfigure", userDetails.getUsername());
        assertNotNull(userDetails.getAuthorities());
        assertTrue(userDetails.getAuthorities().isEmpty());

        Mockito.verify(solarUserServiceMock, Mockito.times(1)).noCache();
    }

    /**
     * Teste la méthode loadUserDetails avec un login vide.
     */
    @Test
    public void loadUserDetailsLoginVide() {
        Authentication token = new PreAuthenticatedAuthenticationToken("", null);
        UserDetails userDetails = solarUserService.loadUserDetails(token);
        assertNotNull(userDetails);
        assertFalse(userDetails.isEnabled());
        assertEquals("loginNonConfigure", userDetails.getUsername());
        assertNotNull(userDetails.getAuthorities());
        assertTrue(userDetails.getAuthorities().isEmpty());

        Mockito.verify(solarUserServiceMock, Mockito.times(1)).noCache();
    }

    /**
     * Teste la méthode loadUserDetails avec un login null.
     */
    @Test
    public void loadUserDetailsLoginNull() {
        Authentication token = new PreAuthenticatedAuthenticationToken(null, null);
        UserDetails userDetails = solarUserService.loadUserDetails(token);
        assertNotNull(userDetails);
        assertFalse(userDetails.isEnabled());
        assertEquals("loginNonConfigure", userDetails.getUsername());
        assertNotNull(userDetails.getAuthorities());
        assertTrue(userDetails.getAuthorities().isEmpty());

        Mockito.verify(solarUserServiceMock, Mockito.times(1)).noCache();
    }

    /**
     * Teste la méthode loadUserDetails avec null en paramètre.
     */
    @Test(expected = IllegalArgumentException.class)
    public void loadUserDetailsNull() {
        Authentication token = null;
        solarUserService.loadUserDetails(token);
        Mockito.verify(solarUserServiceMock, Mockito.times(1)).noCache();
    }

    /**
     * Teste la méthode loadUserDetails pour un login forme longue.
     * On vérifie que l'on ne passe qu'une seule fois.
     */
    @Test
    public void loadUserDetailsLoginLongCheckCacheOneUser() {
        Authentication token = new PreAuthenticatedAuthenticationToken("SANTECLAIR\\ACATELAND", null);
        webClientMock.modeTest = ModeTest.LOGIN_AVEC_ROLES;
        solarUserService.loadUserDetails(token);
        solarUserService.loadUserDetails(token);

        Mockito.verify(solarUserServiceMock, Mockito.times(1)).noCache();
    }

    /**
     * Teste la méthode loadUserDetails pour deux login forme longue.
     * On vérifie que l'on passe deux fois.
     */
    @Test
    public void loadUserDetailsLoginLongCheckCacheTwoUsers() {
        Authentication token = new PreAuthenticatedAuthenticationToken("SANTECLAIR\\FMOKHTARI", null);
        Authentication token2 = new PreAuthenticatedAuthenticationToken("SANTECLAIR\\CGILLET", null);
        webClientMock.modeTest = ModeTest.LOGIN_AVEC_ROLES;
        solarUserService.loadUserDetails(token);
        solarUserService.loadUserDetails(token);
        solarUserService.loadUserDetails(token2);
        Mockito.verify(solarUserServiceMock, Mockito.times(2)).noCache();
    }

}
