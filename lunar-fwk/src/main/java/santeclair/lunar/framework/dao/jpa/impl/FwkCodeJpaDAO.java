package santeclair.lunar.framework.dao.jpa.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;

import santeclair.lunar.framework.annotation.Code;
import santeclair.lunar.framework.annotation.MethodLogger;
import santeclair.lunar.framework.dao.jpa.IFwkCodeJpaDAO;
import santeclair.lunar.framework.dao.jpa.exception.JpaExceptionHelper;

/**
 * Impl�mentation de l'interface {@link IFwkCodeJpaDAO}.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE>
 *            : Le type du domaine manipul� par ce DAO.
 * @param <CODE>
 *            : Le type de classe servant de code.
 */
@SuppressWarnings("unchecked")
public abstract class FwkCodeJpaDAO<DOMAINE, CODE> extends
                FwkGenericJpaDAO<DOMAINE> implements IFwkCodeJpaDAO<DOMAINE, CODE> {

    private static final String CODE_HQL_VAR_NAME = "code";

    /**
     * Permet de r�cup�rer la m�thode d'une liste donn�e poss�dant les
     * annotations {@link Code} et {@link Column}.
     * 
     * @param methodToScans
     *            : La liste des m�thodes � v�rifier.
     * @return La {@link Method} poss�dant les 2 annotations si elle existe.
     * @throws IllegalArgumentException
     *             Si la colonne affect� n'est pas unique ou si aucune m�thode
     *             ne poss�de les 2 annotations.
     */
    private Method findCodeMethod() {
        Method methodGoodOne = findOneMethodByAnnotations(getEntityClass().getMethods(),
                        Code.class, Column.class);
        if (methodGoodOne != null) {
            Column columnAnnotation = methodGoodOne.getAnnotation(Column.class);
            if (columnAnnotation.unique()) {
                return methodGoodOne;
            }
            throw new IllegalArgumentException("Your column "
                            + columnAnnotation.name() + " is not declared unique.");
        }
        else {
            Field[] fields = getAllDeclaredFields(getEntityClass()).toArray(new Field[]{});
            Field field = findOneFieldByAnnotations(fields, Code.class, Column.class);
            if (field != null) {
                methodGoodOne = findMethodFieldWithColumnAnnotationsAndUniqueParam(field);
                if (methodGoodOne != null) {
                    return methodGoodOne;
                }
            }
        }
        throw new IllegalArgumentException(
                        "No getter method found for annotations : "
                                        + Code.class.getSimpleName() + " and "
                                        + Column.class.getSimpleName());
    }

    /**
     * Permet de r�cup�rer la m�thode d'une liste donn�e poss�dant l'annotation {@link Id}.
     * 
     * @param methodToScans
     *            : La liste des m�thodes � v�rifier.
     * @return La {@link Method} poss�dant l'annotation si elle existe.
     * @throws IllegalArgumentException
     *             Si la colonne affect� n'est pas unique ou si aucune m�thode
     *             ne poss�de l'annotation.
     */
    private Method findIdMethod() {
        Method methodGoodOne = findOneMethodByAnnotations(getEntityClass().getMethods(),
                        Id.class, Column.class);
        if (methodGoodOne != null) {
            Column columnAnnotation = methodGoodOne.getAnnotation(Column.class);
            if (columnAnnotation.unique()) {
                return methodGoodOne;
            }
            throw new IllegalArgumentException("Your column "
                            + columnAnnotation.name() + " is not declared unique.");
        }
        else {
            Field[] fields = getAllDeclaredFields(getEntityClass()).toArray(new Field[]{});
            Field field = findOneFieldByAnnotations(fields, Id.class, Column.class);
            if (field != null) {
                methodGoodOne = findMethodFieldWithColumnAnnotationsAndUniqueParam(field);
                if (methodGoodOne != null) {
                    return methodGoodOne;
                }
            }
        }

        throw new IllegalArgumentException(
                        "No getter method found with annotations : "
                                        + Id.class.getSimpleName());
    }

    /**
     * Permet de renvoyer le parametre pour les requetes JPQL depuis la methode
     * get en retirant les trois premier caract�re (get) et en transformant en
     * minuscule la premi�re lettre (POJO style).
     * 
     * @param method
     *            La m�thode get permettant d'acc�der au parametre recherch�.
     * @return Le nom du param�tre.
     */
    private String getParameterName(Method method) {

        String methodCodeWithoutGet = method.getName().substring(3);
        String columnCode = StringUtils.uncapitalize(methodCodeWithoutGet);

        return columnCode;
    }

    /**
     * Cette m�thode permet de trouver la m�thode annot�e avec toutes les
     * annotations pass�es en param�tre.
     * 
     * @param methodToScans
     *            Tableau des m�thodes � controller.
     * @param annotations
     *            Tableau des annotations attendues sur la m�thode.
     * @return La premi�re m�thode poss�dant toutes les annotations pass�es en
     *         param�tre. Si aucune m�thode alors renvoie null.
     */
    private Method findOneMethodByAnnotations(Method[] methodToScans,
                    Class<? extends Annotation>... annotations) {
        boolean found = false;
        for (Method method : methodToScans) {
            for (Class<? extends Annotation> annotation : annotations) {
                if (method.isAnnotationPresent(annotation)) {
                    found = true;
                } else {
                    found = false;
                    break;
                }
            }
            if (found) {
                return method;
            }
        }
        return null;
    }

    private Field findOneFieldByAnnotations(Field[] fields,
                    Class<? extends Annotation>... annotations) {
        boolean annotedFieldFound = false;
        for (Field field : fields) {
            for (Class<? extends Annotation> annotation : annotations) {
                if (field.isAnnotationPresent(annotation)) {
                    annotedFieldFound = true;
                } else {
                    annotedFieldFound = false;
                    break;
                }
            }
            if (annotedFieldFound) {
                return field;
            }
        }
        return null;
    }

    private Method findMethodFieldWithColumnAnnotationsAndUniqueParam(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column.unique()) {
            String getterMethod = "get" + StringUtils.capitalize(field.getName());
            Method method = null;
            try {
                method = getEntityClass().getMethod(getterMethod);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return method;
        }
        throw new IllegalArgumentException("Your column "
                        + column.name() + " is not declared unique.");
    }

    private List<Field> getAllDeclaredFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        if (clazz != null && clazz != Object.class) {
            fields.addAll(getAllDeclaredFields(clazz.getSuperclass()));
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                fields.add(field);
            }
        }
        return fields;
    }

    /** {@inheritDoc} */
    @MethodLogger
    public Integer findIdByCode(CODE code) {

        Method methodCode = findCodeMethod();

        Method methodId = findIdMethod();

        // On recup�re le nom du champ code � partir du nom de son getter.
        String columnCodeName = getParameterName(methodCode);

        // On recup�re le nom du champ id � partir du nom de son getter.
        String columnIdName = getParameterName(methodId);

        // COnstruction de la requ�te SQL
        StringBuffer queryBuffered = new StringBuffer("select s.")
                        .append(columnIdName).append(" from ")
                        .append(getEntityClass().getSimpleName()).append(" s where s.")
                        .append(columnCodeName).append(" = :")
                        .append(CODE_HQL_VAR_NAME);

        logger.debug("findIdByCode query : " + queryBuffered.toString());

        TypedQuery<Integer> query = getEntityManager().createQuery(
                        queryBuffered.toString(), Integer.class);
        query.setParameter(CODE_HQL_VAR_NAME, code);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            NoResultException nre = JpaExceptionHelper
                            .addQueryInformation(queryBuffered.toString(), query, e,
                                            NoResultException.class);
            if (nre == null) {
                throw e;
            }
            throw nre;
        }

    }

    /** {@inheritDoc} */
    @MethodLogger
    public DOMAINE findEntityByCode(CODE code) throws NoResultException {

        Method methodCode = findCodeMethod();

        String columnCode = getParameterName(methodCode);

        StringBuffer queryBuffered = new StringBuffer("select s from ")
                        .append(getEntityClass().getSimpleName()).append(" s where s.")
                        .append(columnCode).append(" = :").append(CODE_HQL_VAR_NAME);

        logger.debug("findIdByCode query : " + queryBuffered.toString());

        TypedQuery<DOMAINE> query = getEntityManager().createQuery(
                        queryBuffered.toString(), getEntityClass());
        query.setParameter(CODE_HQL_VAR_NAME, code);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            NoResultException nre = JpaExceptionHelper
                            .addQueryInformation(queryBuffered.toString(), query, e,
                                            NoResultException.class);
            if (nre == null) {
                throw e;
            }
            throw nre;
        }
    }

    /** {@inheritDoc} */
    @MethodLogger
    public List<CODE> findAllCode() {
        Method methodCode = findCodeMethod();
        if (methodCode != null) {
            String columnCode = getParameterName(methodCode);

            StringBuffer queryBuffered = new StringBuffer("select s.")
                            .append(columnCode).append(" from ")
                            .append(getEntityClass().getSimpleName()).append(" s ");

            logger.debug("findIdByCode query : " + queryBuffered.toString());
            return getEntityManager().createQuery(queryBuffered.toString())
                            .getResultList();
        }
        return null;
    }

    /** {@inheritDoc} */
    public Long getSizeByCode(CODE code) {

        Method methodCode = findCodeMethod();
        String columnCode = getParameterName(methodCode);

        return getSizeByParameter(columnCode, code);
    }

}
