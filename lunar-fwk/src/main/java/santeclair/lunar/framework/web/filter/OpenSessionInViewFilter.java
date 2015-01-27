package santeclair.lunar.framework.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;

public class OpenSessionInViewFilter extends org.springframework.orm.hibernate3.support.OpenSessionInViewFilter {

	private static final Logger logger = LoggerFactory.getLogger(OpenSessionInViewFilter.class);
	
	public OpenSessionInViewFilter() {
		super();
		logger.debug("Création d'une instance.");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		logger.debug("Debut du filtrage de la requete.");
		super.doFilterInternal(request, response, filterChain);
		logger.debug("Fin du filtrage de la requete.");
	}
	
	@Override
	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		logger.debug("Ouverture de la session Hibernate.");
		Session session = super.getSession(sessionFactory);
		session.setFlushMode(FlushMode.AUTO);
		return session;
	}
	
	@Override
	protected void closeSession(Session session, SessionFactory sessionFactory) {
		if (session != null) {
			if (session.isOpen()) {
				logger.debug("Fermeture de la session Hibernate.");
				session.flush();
				super.closeSession(session, sessionFactory);
			} else {
				logger.warn("Tentative de fermeture d'une session Hibernate déjà fermée.");
			}
		} else {
			logger.warn("L'objet session est null.");
		}
	}
}
