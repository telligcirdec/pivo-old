/**
 * 
 */
package santeclair.lunar.framework.web.auth;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;

/**
 * Contrôleur appelant le web service webext pour vérifier si le jeton est bien valide.
 * 
 * @author jfourmond
 * 
 */
public class AuthentificationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(AuthentificationServlet.class);

    /** Le préfixe des propriétés correspondant aux pages de redirection. */
    public static final String PREFIXE_PROPRIETES_PAGES_REDIRECTION = "webext.ws.redirection.retour_";

    /** Le nom de la propriété correspondant à la page de redirection en cas de code retour 01 dans la réponse du WS webext. */
    public static final String PAGE_REDIRECTION_RETOUR_01 = "webext.ws.redirection.retour_01";

    /** Objet contenant les propriétés du fichier validation.properties. */
    private Properties validationProperties;

    /**
     * Extrait des properties.
     */
    private String methodeWsWebExt;

    /**
     * La fabrique de clients HTTP pour le web service webext de validation du jeton..
     */
    private JAXRSClientFactoryBean jaxrsClientFactoryBean;

    /** {@inheritDoc} */
    @Override
    public void init() throws ServletException {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("validation.properties");

        try {
            validationProperties = new Properties();
            validationProperties.load(is);
            this.methodeWsWebExt = validationProperties.getProperty("webext.ws.methode");
            String urlWsWebExt = validationProperties.getProperty("webext.ws.url");
            logger.info("URL du web service webext : {}{}", urlWsWebExt, methodeWsWebExt);
            jaxrsClientFactoryBean = new JAXRSClientFactoryBean();
            jaxrsClientFactoryBean.setThreadSafe(true);
            jaxrsClientFactoryBean.setAddress(urlWsWebExt);

        } catch (IOException e) {
            logger.error("Erreur lors de la lecture du fichier validation.properties", e);
            throw new ServletException(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("debut de doPost");
        String idAppliCible = validationProperties.getProperty("idApplication");
        // Récupération des paramètres de l'URL
        String idPart = request.getParameter(SessionConstantes.ID_PART);
        String idJeton = request.getParameter(RequestConstantes.PARAM_JETON);
        String idCharte = request.getParameter(RequestConstantes.PARAM_PERSO);
        String options = request.getParameter(SessionConstantes.OPTIONS);
        String provenance = request.getParameter(RequestConstantes.PARAM_PROVENANCE);
        // Nettoyage de la session si nécessaire
        HttpSession session = request.getSession();
        if (session != null) {
            logger.debug("Nettoyage de la session");
            session.removeAttribute(SessionConstantes.RESULTAT_AUTHENTIFICATION_JETON);
            session.removeAttribute(SessionConstantes.PERSO);
            session.removeAttribute(SessionConstantes.OPTIONS);
            session.removeAttribute(SessionConstantes.BENEFICIAIRES);
            session.removeAttribute(SessionConstantes.ID_ORGANISME);
            session.removeAttribute(SessionConstantes.NUMERO_CONTRAT);
            session.removeAttribute(SessionConstantes.ASSURE);
            session.removeAttribute(SessionConstantes.ACCES_PUBLIQUE);
        }

        DemandeAuthentificationJeton demande = new DemandeAuthentificationJeton(idJeton, idAppliCible, idPart, idCharte, provenance, options);
        WebClient webClient = jaxrsClientFactoryBean.createWebClient();
        webClient.path(methodeWsWebExt);
        ResultatAuthentificationJeton resultatAuthentificationJeton = webClient.post(demande, ResultatAuthentificationJeton.class);

        if (resultatAuthentificationJeton.isJetonValide()) {
            mettreInfosEnSession(request, resultatAuthentificationJeton, idCharte, idPart, options);
        }

        String contextPath = request.getContextPath();
        String redirection = getPathRedirection(contextPath, resultatAuthentificationJeton);

        session.setAttribute(SessionConstantes.ASSURE, resultatAuthentificationJeton.getAssure());
        session.setAttribute(SessionConstantes.ID_ORGANISME, resultatAuthentificationJeton.getIdOrganisme());
        session.setAttribute(SessionConstantes.NUMERO_CONTRAT, resultatAuthentificationJeton.getNumeroContrat());

        session.setAttribute(SessionConstantes.BENEFICIAIRES, resultatAuthentificationJeton.getBeneficiaires());
        session.setAttribute(SessionConstantes.ID_PART, idPart);

        logger.info("send redirect : " + redirection);

        response.sendRedirect(redirection);
    }

    /**
     * Sauvegarde en session les informations passées en paramètre de requête,
     * ainsi que le résultat de l'authentification du jeton, si celui-ci a été validé.
     */
    private void mettreInfosEnSession(HttpServletRequest request, ResultatAuthentificationJeton resultatAuthentificationJeton, String idCharte, String idPart, String options) {
        logger.debug("Le jeton a été validé.");

        // Récupération ou création de la session
        HttpSession session = request.getSession();

        // On sauvegarde en session le nom de la perso à appliquer
        if (StringUtils.isNotBlank(idCharte)) {
            session.setAttribute(SessionConstantes.PERSO, idCharte.toLowerCase(Locale.FRENCH));
        } else {
            session.setAttribute(SessionConstantes.PERSO, idPart.toLowerCase(Locale.FRENCH));
        }

        // On sauvegarde en session le jeton pour prouver l'authentification
        session.setAttribute(SessionConstantes.RESULTAT_AUTHENTIFICATION_JETON, resultatAuthentificationJeton);

        // On sauvegarde en session le tableau des options (si présent)
        if (StringUtils.isNotBlank(options)) {
            String[] tabOptions = options.split("-");
            List<String> listeOptions = new ArrayList<String>();
            String option;

            for (String tabOption : tabOptions) {
                option = tabOption.toLowerCase();
                listeOptions.add(option);
            }

            // On ne met en session que si nécessaire
            if (!listeOptions.isEmpty()) {
                logger.debug("Liste des options utilisées : '{}'.", listeOptions.toString());
                session.setAttribute(SessionConstantes.OPTIONS, listeOptions);
            }
        }
    }

    /**
     * Renvoie le path de la page vers laquelle l'utilisateur est redirigé
     * suite à l'authentification de son jeton.
     */
    @VisibleForTesting
    String getPathRedirection(String contextPath, ResultatAuthentificationJeton resultatAuthentificationJeton) {

        CodeRetour codeRetour = resultatAuthentificationJeton.getCodeRetour();
        logger.info("codeRetour : " + codeRetour);
        String proprietePageRedirection = null;
        if (codeRetour != null) {
            proprietePageRedirection = PREFIXE_PROPRIETES_PAGES_REDIRECTION + codeRetour.getValue();
        } else {
            proprietePageRedirection = PAGE_REDIRECTION_RETOUR_01;
        }
        String pageRedirection = validationProperties.getProperty(proprietePageRedirection);
        /* Au cas où on aurait des codes retour non prévus, on redirige vers la page du code 01. */
        if (pageRedirection == null) {
            pageRedirection = validationProperties.getProperty(PAGE_REDIRECTION_RETOUR_01);
        }
        String redirection = contextPath + pageRedirection;
        return redirection;
    }

    /** {@inheritDoc} */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * Méthode utilisée pour les tests uniquement.
     */
    void setValidationProperties(Properties validationProperties) {
        this.validationProperties = validationProperties;
    }

}
