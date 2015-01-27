package santeclair.lunar.framework.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import santeclair.lunar.framework.annotation.HashCode;
import santeclair.lunar.framework.annotation.HashCodeEquals;

/**
 * Cette classe permet de calculer un hashcode en s'appuyant uniquement sur les
 * champs annotés par l'annotation @HashCode et si précisé sur le hashcode de la
 * classe mère.
 * 
 * @author Puppet Master
 * 
 */
public abstract class HashCodeUtils {

    /**
     * Calcul un hashcode en sollicitant la méthode hashCode() de chacun des
     * objets des champs annotés avec @HashCode et en y ajoutant le hashCode de
     * la classe mère si précisé.
     * 
     * @param obj
     *            L'objet dont on doit extraire le hashcode en s'appuyant
     *            exclusivement sur les champs annotés avec @HashCode.
     * @param superHashCode
     *            Permet d'ajouter au hashcode de la classe fille, le hashcode
     *            de la classe mère.
     * @return Le hashcode compilé des hashcodes des différents champs annotés
     *         par @HashCode plus le hash code de la classe mère passé en
     *         paramètre.
     */
    public static <T> int hashCode(T obj, int superHashCode) {
        return hashCodeBuilder(obj).appendSuper(superHashCode).hashCode();
    }

    /**
     * Calcul un hashcode en sollicitant la méthode hashCode() de chacun des
     * objets des champs annotés avec @HashCode.
     * 
     * @param obj
     *            L'objet dont on doit extraire le hashcode en s'appuyant
     *            exclusivement sur les champs annotés avec @HashCode.
     * @return Le hashcode compilé des hashcodes des différents champs annotés
     *         par @HashCode.
     */
    public static <T> int hashCode(T obj) {
        return hashCodeBuilder(obj).hashCode();
    }

    /**
     * Permet de renvoyer un HashCodeBuilder prérempli des hashcode des fields.
     * 
     * @param obj
     *            Objet dont on doit extraire les hashcode des champs annotés
     *            par @HashCode.
     * @return Le HashCodeBuilder prérempli.
     */
    private static <T> HashCodeBuilder hashCodeBuilder(T obj) {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.getAnnotation(HashCode.class) != null || field.getAnnotation(HashCodeEquals.class) != null) {
                    field.setAccessible(true);
                    Object fieldValue = field.get(obj);
                    hashCodeBuilder.append(fieldValue);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return hashCodeBuilder;
    }
}
