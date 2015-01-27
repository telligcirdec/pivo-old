package santeclair.lunar.framework.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import santeclair.lunar.framework.annotation.Equals;
import santeclair.lunar.framework.annotation.HashCodeEquals;

/**
 * Cette classe permet d'industrialiser le comportement du equals en utilisation
 * les annotation @Equals.<br/>
 * <br/>
 * Pour ce faire, il faut positionner l'annotation @Equals sur les champs qui
 * doivent être utilisés pour calculer l'égalité. Puis il faut surchrger la
 * méthode Equals et faire appel à la méthode isEqual(this, obj) de cette
 * classe.<br/>
 * <br/>
 * L'égalité sera alors déterminer à l'aide des champs annotés par @Equals.
 * 
 * @author Puppet Master
 * 
 */
public abstract class EqualsUtils {

    /**
     * Permet de déterminer si un objet est equal à un autre en s'appuyant sur
     * les champs annotés par @Equals<br/>
     * <br/>
     * L'égalité est déterminé en appelant la méthode equals de chacun des
     * champs annotés par @Equals.
     * 
     * @param source
     *            L'objet source. Sera souvent this.
     * @param obj
     *            L'ojet a permettant la comparaison. Sera souvent l'objet passé
     *            en paramètre de la méthode equals(obj).
     * @return true si égalité, false sinon.
     */
    public static <T> boolean isEqual(T source, T obj) {
        if (source == obj)
            return true;
        if (obj == null)
            return false;
        if (source.getClass() != obj.getClass())
            return false;

        List<Field> sourceFieldList = getAnnotedField(source);
        List<Field> objFieldList = getAnnotedField(obj);
        for (int i = 0; i < sourceFieldList.size(); i++) {
            if (!processFieldEqual(sourceFieldList.get(i), source, objFieldList.get(i), obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Permet de déterminer si la valeur d'un champ d'une source est égale à la
     * valeur d'un champ d'un objet cible.
     * 
     * @param fieldToProcessSource
     *            Champ de l'objet source.
     * @param source
     *            L'objet source.
     * @param fieldToProcessObj
     *            Champ de l'objet cible.
     * @param obj
     *            L'objet cible.
     * @return true si égal, false sinon.
     */
    private static <T> boolean processFieldEqual(Field fieldToProcessSource, T source, Field fieldToProcessObj, T obj) {
        try {
            Object objectFieldSource = fieldToProcessSource.get(source);
            Object objectFieldObj = fieldToProcessObj.get(obj);
            if (objectFieldSource == null) {
                if (objectFieldObj != null)
                    return false;
            } else if (!objectFieldSource.equals(objectFieldObj))
                return false;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Permet de récupérer et de rendre accessible les chaps d'un objet passé en
     * paramètre pour ne récupérer que les champs annotés avec @Equals.
     * 
     * @param obj
     *            Objet dont on doit extraire les champs annotés avec @Equals.
     * @return Une liste des champs annotés avec @Equals.
     */
    private static <T> List<Field> getAnnotedField(T obj) {

        Field[] fields = obj.getClass().getDeclaredFields();
        List<Field> sourceFieldList = new ArrayList<>();
        for (Field field : fields) {
            if (field.getAnnotation(Equals.class) != null || field.getAnnotation(HashCodeEquals.class) != null) {
                field.setAccessible(true);
                sourceFieldList.add(field);
            }
        }
        return sourceFieldList;
    }

}
