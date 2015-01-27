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
 * doivent �tre utilis�s pour calculer l'�galit�. Puis il faut surchrger la
 * m�thode Equals et faire appel � la m�thode isEqual(this, obj) de cette
 * classe.<br/>
 * <br/>
 * L'�galit� sera alors d�terminer � l'aide des champs annot�s par @Equals.
 * 
 * @author Puppet Master
 * 
 */
public abstract class EqualsUtils {

    /**
     * Permet de d�terminer si un objet est equal � un autre en s'appuyant sur
     * les champs annot�s par @Equals<br/>
     * <br/>
     * L'�galit� est d�termin� en appelant la m�thode equals de chacun des
     * champs annot�s par @Equals.
     * 
     * @param source
     *            L'objet source. Sera souvent this.
     * @param obj
     *            L'ojet a permettant la comparaison. Sera souvent l'objet pass�
     *            en param�tre de la m�thode equals(obj).
     * @return true si �galit�, false sinon.
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
     * Permet de d�terminer si la valeur d'un champ d'une source est �gale � la
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
     * @return true si �gal, false sinon.
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
     * Permet de r�cup�rer et de rendre accessible les chaps d'un objet pass� en
     * param�tre pour ne r�cup�rer que les champs annot�s avec @Equals.
     * 
     * @param obj
     *            Objet dont on doit extraire les champs annot�s avec @Equals.
     * @return Une liste des champs annot�s avec @Equals.
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
