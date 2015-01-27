package santeclair.lunar.framework.dao.hibernate;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Cette classe permet d'implémenter des méthodes d'assistance à la création des requetes sql des daos. Celle-ci ne doit plus désormais être étendue.
 * Veuillez à la place hériter de : {@link santeclair.lunar.framework.dao.hibernate.FwkGenericHibernateDao}
 * 
 * @author cgillet
 * @see santeclair.lunar.framework.dao.hibernate.FwkGenericHibernateDao
 * 
 */
public class FwkHibernateDaoSupport extends HibernateDaoSupport {

    protected static final String LEFT_OUTER_JOIN = " LEFT OUTER JOIN ";
    protected static final String INNER_JOIN = " INNER JOIN ";
    protected static final String SPACE = " ";

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Injecte (au besoin) la valeur <code>value</code> dans la 'variable' <code>parameterName</code>
     * 
     * @param query : la query (HQL) qui contient la variable à remplir
     * @param value : valeur de la variable
     * @param parameterName : nom de la variable
     */
    protected void addParameterToQuery(Query query, Object value, String parameterName) {
        if (value instanceof String) {
            // par défaut, on n'autorise pas le remplacement du caractère '*' par '%'
            addParameterToQuery(query, (String) value, parameterName, Boolean.FALSE);
        } else if (null != value) {
            query.setParameter(parameterName, value);
        }
    }

    /**
     * Injecte (au besoin) la chaine de caractère <code>value</code> dans la 'variable' <code>parameterName</code>
     * 
     * @param query : la query (HQL) qui contient la variable à remplir
     * @param value : valeur de la variable
     * @param parameterName : nom de la variable
     * @param allowStar : autorise le remplacement de 'test*' par 'test%'
     */
    protected void addParameterToQuery(Query query, String value, String parameterName, Boolean allowStar) {
        if (StringUtils.isNotBlank(value)) {
            String newVal = value;
            if (allowStar.booleanValue()) {
                newVal = value.replace('*', '%');
            }
            query.setParameter(parameterName, newVal);
        }
    }

    /**
     * Rajoute le mot clé 'AND' en fonction de l'état de contruction de la requête HQL<br>
     * Ex: Si l'utilisateur vient d'ajouter la clause 'WHERE' dans la requête, on ne rajoute pas le mot clé 'AND' sinon il est rajouté
     * 
     * @param queryStr : requête HQL en contruction
     */
    protected void setKeyWordAnd(StringBuilder queryStr) {
        if (!queryStr.toString().trim().endsWith("WHERE")) {
            queryStr.append(" AND ");
        }
    }

    /**
     * 
     * @param queryStr : requête HQL en construction
     * @param value : valeur qui sera injectée dans la query. <br>
     *            Cela permet de savoir si il faut ajouter ou non la variable correspondante
     * @param objectNameDotParameter : ex: "ps.nom" permet de réaliser la clause where si le nom du ps
     * @param parameterQueryName : le nom de la variable correspondant à la donnée à tester
     */
    protected void addToWhere(StringBuilder queryStr, Object value, String objectNameDotParameter, String parameterQueryName) {
        if (value instanceof String) {
            addToWhere(queryStr, (String) value, objectNameDotParameter, parameterQueryName, Boolean.FALSE);
        } else if (null != value) {
            setKeyWordAnd(queryStr);
            queryStr.append(SPACE).append(objectNameDotParameter).append(" = :").append(parameterQueryName).append(SPACE);
        }
    }

    /**
     * 
     * @param queryStr : requête HQL en contruction
     * @param value : chaine de caractère qui sera injectée dans la query. <br>
     *            Cela permet de savoir si il faut ajouter ou non la variable correspondante
     * @param objectNameDotParameter : ex: "ps.nom" permet de réaliser la clause where si le nom du ps
     * @param parameterQueryName : le nom de la variable correspondant à la donnée à tester
     * @param allowStar : autorise le remplacement de 'test*' par 'test%'
     */
    protected void addToWhere(StringBuilder queryStr, String value, String objectNameDotParameter, String parameterQueryName, Boolean allowStar) {
        if (StringUtils.isNotBlank(value)) {
            setKeyWordAnd(queryStr);
            queryStr.append(SPACE).append(objectNameDotParameter);
            if (allowStar.booleanValue() && value.indexOf('*') != -1) {
                queryStr.append(" like ");
            } else {
                queryStr.append(" = ");
            }
            queryStr.append(" :").append(parameterQueryName).append(SPACE);
        }
    }

}
