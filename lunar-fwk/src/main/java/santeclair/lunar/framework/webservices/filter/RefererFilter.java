package santeclair.lunar.framework.webservices.filter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.annotations.VisibleForTesting;

/**
 * Filtre les appels web service en fonction du referer. A rajouter dans la
 * partie provider de la config spring/cxf.
 * 
 * @author tsensebe
 * 
 */
@Provider
public class RefererFilter implements RequestHandler {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RefererFilter.class);

	/** Liste de domaine authorisé */
	@Value("#{refererProperties['trustHosts'].split(',')}")
	private List<String> trustHosts;

	@Value("#{T(santeclair.lunar.framework.webservices.filter.RefererFilterProperties).propsToMap(refererProperties, 'exclusionUrlType\\.(\\w*)')}")
	private Map<String, List<String>> mapExclusionUrl;

	/**
	 * Si la requete n'as pas le bon referer on renvoi UNAUTHORIZED.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Response handleRequest(Message m, ClassResourceInfo resourceClass) {

		LOGGER.info(
				"handle raquest for referer filter : trustHosts {} / mapExclusionUrl {}",
				trustHosts, mapExclusionUrl);

		String httpMethod = m.get(Message.HTTP_REQUEST_METHOD) != null ? m.get(
				Message.HTTP_REQUEST_METHOD).toString() : null;
		LOGGER.debug("httpMethod : {}", httpMethod);
		String path = m.get(Message.PATH_INFO).toString() != null ? m.get(
				Message.PATH_INFO).toString() : null;
		LOGGER.debug("path : {}", path);

		// Vérification des exclusions d'url en fonction de la methode HHTP
		// définie dans la clef de la propriété commençant par exclusionUrlType
		if (checkExclusionHttpMethod(httpMethod, path)) {
			return null;
		}

		Map<String, List> headers = (Map<String, List>) m
				.get(Message.PROTOCOL_HEADERS);
		String referer = null;
		try {
			referer = (String) headers.get("referer").get(0);
			LOGGER.debug("referer found : {}", referer);
		} catch (Exception e1) {
			LOGGER.debug("exception while getting referer", e1);
		}

		if (referer == null) {
			LOGGER.error(
					"Impossible de recupérer le referer - Webservice : '{}' - Client : '{}'",
					path, getRemoteAddr(m));
			return Response.status(Status.UNAUTHORIZED).build();
		}

		String domaine = "";
		try {
			URL url = new URL(referer);
			domaine = url.getHost();
			LOGGER.debug("domaine found : {}", domaine);
		} catch (MalformedURLException e) {
			LOGGER.debug("Error while getting domaine", e);
		}

		for (String host : trustHosts) {
			// Retourner null veut dire à cxf de continuer la requete
			// normalement.
			LOGGER.debug("Looking for trusted host : {}", host);
			if (domaine.toLowerCase().endsWith(host.toLowerCase())) {
				LOGGER.info("Found authorized referer => domaine : {} / trutedHost : {}", domaine, host);
				return null;
			}

		}

		// On renvoi une reponse avec UNAUTHORIZED si on ne connait pas le
		// referer
		LOGGER.error(
				"Referer non authorisé - Webservice : '{}' - Referer '{}' - Client : '{}'",
				m.get(Message.PATH_INFO), referer, getRemoteAddr(m));
		return Response.status(Status.UNAUTHORIZED).build();
	}

	/**
	 * @param trustHosts
	 *            the trustHosts to set
	 */
	@VisibleForTesting
	void setTrustHosts(List<String> trustHosts) {
		this.trustHosts = trustHosts;
	}

	/**
	 * @param trustHosts
	 *            the trustHosts to set
	 */
	@VisibleForTesting
	void setMapExclusionUrl(Map<String, List<String>> mapExclusionUrl) {
		this.mapExclusionUrl = mapExclusionUrl;
	}

	/**
	 * Recupère l'adresse ip de l'appelant
	 * 
	 * @param m
	 * @return l'adresse ip de l'appelant
	 */
	private String getRemoteAddr(Message m) {
		try {
			HttpServletRequest request = (HttpServletRequest) m
					.get(AbstractHTTPDestination.HTTP_REQUEST);
			return request.getRemoteAddr();
		} catch (Exception e) {
			LOGGER.error("Impossible de recupérer l'adresse de l'appelant");
			return null;
		}
	}

	private boolean checkExclusionHttpMethod(String httpMethod, String path) {
		if (httpMethod != null && path != null) {
			for (String httpMethodKey : mapExclusionUrl.keySet()) {
				if (httpMethod.equalsIgnoreCase(httpMethodKey)) {
					List<String> listUrlExclusion = mapExclusionUrl
							.get(httpMethodKey);
					for (String urlExclusion : listUrlExclusion) {
						if (path.toLowerCase().endsWith(
								urlExclusion.toLowerCase()))
							LOGGER.info(
									"URL exclusion found : urlExclusion {}",
									urlExclusion);
						return true;
					}
				}
			}
		}
		LOGGER.info(
				"No url exclusion found => HttpMethod : {}  /  path : {}  /  exclusionUrl : {}",
				httpMethod, path, mapExclusionUrl);
		return false;
	}

}
