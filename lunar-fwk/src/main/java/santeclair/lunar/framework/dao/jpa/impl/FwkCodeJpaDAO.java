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
 * Implémentation de l'interface {@link IFwkCodeJpaDAO}.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE>
 *            : Le type du domaine manipulé par ce DAO.
 * @param <CODE>
 *            : Le type de classe servant de code.
 */
@SuppressWarnings("unchecked")
public abstract class FwkCodeJpaDAO<DOMAINE, CODE> extends
                FwkGenericJpaDAO<DOMAINE> implements IFwkCodeJpaDAO<DOMAINE, CODE> {

    private static final String CODE_HQL_VAR_NAME = "code";

    /**
     * Permet de récupérer la méthode d'une liste donnée possédant les
     * annotations {@link Code} et {@link Column}.
     * 
     * @param methodToScans
     *            : La liste des méthodes à vérifier.
     * @return La {@link Method} possédant les 2 annotations si elle existe.
     * @throws IllegalArgumentException
     *             Si la colonne affecté n'est pas unique ou si aucune méthode
     *             ne possède les 2 annotations.
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
     * Permet de récupérer la méthode d'une liste donnée possédant l'annotation {@link Id}.
     * 
     * @param methodToScans
     *            : La liste des méthodes à vérifier.
     * @return La {@link Method} possédant l'annotation si elle existe.
     * @throws IllegalArgumentException
     *             Si la colonne affecté n'est pas unique ou si aucune méthode
     *             ne possède l'annotation.
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
     * get en retirant les trois premier caractère (get) et en transformant en
     * minuscule la première lettre (POJO style).
     * 
     * @param method
     *            La méthode get permettant d'accéder au parametre recherché.
     * @return Le nom du paramètre.
     */
    private String getParameterName(Method method) {

        String methodCodeWithoutGet = method.getName().substring(3);
        String columnCode = StringUtils.uncapitalize(methodCodeWithoutGet);

        return columnCode;
    }

    /**
     * Cette méthode permet de trouver la méthode annotée avec toutes les
     * annotations passées en paramètre.
     * 
     * @param methodToScans
     *            Tableau des méthodes à controller.
     * @param annotations
     *            Tableau des annotations attendues sur la méthode.
     * @return La première méthode possédant toutes les annotations passées en
     *         paramètre. Si aucune méthode alors renvoie null.
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

        // On recupère le nom du champ code à partir du nom de son getter.
        String columnCodeName = getParameterName(methodCode);

        // On recupère le nom du champ id à partir du nom de son getter.
        String columnIdName = getParameterName(methodId);

        // COnstruction de la requête SQL
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
