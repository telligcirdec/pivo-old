package santeclair.lunar.framework.dao.hibernate;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Cette classe permet d'impl�menter des m�thodes d'assistance � la cr�ation des requetes sql des daos. Celle-ci ne doit plus d�sormais �tre �tendue.
 * Veuillez � la place h�riter de : {@link santeclair.lunar.framework.dao.hibernate.FwkGenericHibernateDao}
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
     * @param query : la query (HQL) qui contient la variable � remplir
     * @param value : valeur de la variable
     * @param parameterName : nom de la variable
     */
    protected void addParameterToQuery(Query query, Object value, String parameterName) {
        if (value instanceof String) {
            // par d�faut, on n'autorise pas le remplacement du caract�re '*' par '%'
            addParameterToQuery(query, (String) value, parameterName, Boolean.FALSE);
        } else if (null != value) {
            query.setParameter(parameterName, value);
        }
    }

    /**
     * Injecte (au besoin) la chaine de caract�re <code>value</code> dans la 'variable' <code>parameterName</code>
     * 
     * @param query : la query (HQL) qui contient la variable � remplir
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
     * Rajoute le mot cl� 'AND' en fonction de l'�tat de contruction de la requ�te HQL<br>
     * Ex: Si l'utilisateur vient d'ajouter la clause 'WHERE' dans la requ�te, on ne rajoute pas le mot cl� 'AND' sinon il est rajout�
     * 
     * @param queryStr : requ�te HQL en contruction
     */
    protected void setKeyWordAnd(StringBuilder queryStr) {
        if (!queryStr.toString().trim().endsWith("WHERE")) {
            queryStr.append(" AND ");
        }
    }

    /**
     * 
     * @param queryStr : requ�te HQL en construction
     * @param value : valeur qui sera inject�e dans la query. <br>
     *            Cela permet de savoir si il faut ajouter ou non la variable correspondante
     * @param objectNameDotParameter : ex: "ps.nom" permet de r�aliser la clause where si le nom du ps
     * @param parameterQueryName : le nom de la variable correspondant � la donn�e � tester
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
     * @param queryStr : requ�te HQL en contruction
     * @param value : chaine de caract�re qui sera inject�e dans la query. <br>
     *            Cela permet de savoir si il faut ajouter ou non la variable correspondante
     * @param objectNameDotParameter : ex: "ps.nom" permet de r�aliser la clause where si le nom du ps
     * @param parameterQueryName : le nom de la variable correspondant � la donn�e � tester
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
