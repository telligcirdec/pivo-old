package santeclair.lunar.framework.dao.hibernate;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

/**
 * Redéfinition du filtre
 * 
 * @link org.springframework.orm.hibernate3.support.OpenSessionInViewFilter <br>
 *       afin de gérer les opération CRUD suite à la mise en place du cache Hibernate
 * @author yguenoun
 * 
 */
public class FwkOpenSessionInViewFilter extends OpenSessionInViewFilter {

	private final Logger logger = LoggerFactory.getLogger(FwkOpenSessionInViewFilter.class);
	
    /**
     * Get a Session for the SessionFactory that this filter uses. Note that this just applies in single session mode!
     * <p>
     * The default implementation delegates to SessionFactoryUtils' getSession method and sets the Session's flushMode to NEVER.
     * <p>
     * Can be overridden in subclasses for creating a Session with a custom entity interceptor or JDBC exception translator.
     * 
     * @param sessionFactory the SessionFactory that this filter uses
     * @return the Session to use
     * @throws DataAccessResourceFailureException if the Session could not be created
     * @see org.springframework.orm.hibernate3.SessionFactoryUtils#getSession(SessionFactory, boolean)
     * @see org.hibernate.FlushMode#NEVER
     */
    @Override
    protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
        Session sessionFlush = super.getSession(sessionFactory);
        sessionFlush.setFlushMode(FlushMode.AUTO);
        return sessionFlush;
    }

    /**
     * Close the given Session. Note that this just applies in single session mode!
     * <p>
     * The default implementation delegates to SessionFactoryUtils' releaseSession method.
     * <p>
     * Can be overridden in subclasses, e.g. for flushing the Session before closing it. See class-level javadoc for a discussion of flush handling.
     * Note that you should also override getSession accordingly, to set the flush mode to something else than NEVER.
     * 
     * @param session the Session used for filtering
     * @param sessionFactory the SessionFactory that this filter uses
     */
    @Override
    protected void closeSession(Session session, SessionFactory sessionFactory) {
        logger.debug("début méthode FwkOpenSessionInViewFilter.closeSession");
        session.flush();
        super.closeSession(session, sessionFactory);
        logger.debug("fin méthode FwkOpenSessionInViewFilter.closeSession");
        session.close();
    }
}
