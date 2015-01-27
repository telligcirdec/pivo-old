package santeclair.lunar.framework.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import santeclair.lunar.framework.annotation.HashCode;
import santeclair.lunar.framework.annotation.HashCodeEquals;

/**
 * Cette classe permet de calculer un hashcode en s'appuyant uniquement sur les
 * champs annot�s par l'annotation @HashCode et si pr�cis� sur le hashcode de la
 * classe m�re.
 * 
 * @author Puppet Master
 * 
 */
public abstract class HashCodeUtils {

    /**
     * Calcul un hashcode en sollicitant la m�thode hashCode() de chacun des
     * objets des champs annot�s avec @HashCode et en y ajoutant le hashCode de
     * la classe m�re si pr�cis�.
     * 
     * @param obj
     *            L'objet dont on doit extraire le hashcode en s'appuyant
     *            exclusivement sur les champs annot�s avec @HashCode.
     * @param superHashCode
     *            Permet d'ajouter au hashcode de la classe fille, le hashcode
     *            de la classe m�re.
     * @return Le hashcode compil� des hashcodes des diff�rents champs annot�s
     *         par @HashCode plus le hash code de la classe m�re pass� en
     *         param�tre.
     */
    public static <T> int hashCode(T obj, int superHashCode) {
        return hashCodeBuilder(obj).appendSuper(superHashCode).hashCode();
    }

    /**
     * Calcul un hashcode en sollicitant la m�thode hashCode() de chacun des
     * objets des champs annot�s avec @HashCode.
     * 
     * @param obj
     *            L'objet dont on doit extraire le hashcode en s'appuyant
     *            exclusivement sur les champs annot�s avec @HashCode.
     * @return Le hashcode compil� des hashcodes des diff�rents champs annot�s
     *         par @HashCode.
     */
    public static <T> int hashCode(T obj) {
        return hashCodeBuilder(obj).hashCode();
    }

    /**
     * Permet de renvoyer un HashCodeBuilder pr�rempli des hashcode des fields.
     * 
     * @param obj
     *            Objet dont on doit extraire les hashcode des champs annot�s
     *            par @HashCode.
     * @return Le HashCodeBuilder pr�rempli.
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
