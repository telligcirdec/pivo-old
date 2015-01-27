package santeclair.lunar.framework.util;

import java.lang.reflect.Field;

public abstract class ToStringUtils {

    /**
     * Attention, ne fonctionne pas avec les types primitifs.
     * 
     * @param obj
     * @return
     */
    public static <T> String toString(T obj) {
        return toStringBuilder(obj, false);
    }

    /**
     * Attention, ne fonctionne pas avec les types primitifs.
     * 
     * @param obj
     * @return
     */
    public static <T> String toString(T obj, boolean appendSuperClass) {
        return toStringBuilder(obj, appendSuperClass);
    }

    private static <T> String toStringBuilder(T obj, boolean appendSuperClass) {
        return toStringBuilder(obj, obj.getClass(), appendSuperClass);
    }

    private static <T> String toStringBuilder(T obj, Class<?> clazz, boolean appendSuperClass) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuffer sb = new StringBuffer();
        boolean firstField = true;
        sb.append(clazz.getSimpleName()).append(" : { ");
        for (Field field : fields) {
            try {
                santeclair.lunar.framework.annotation.ToString toStringAnnotation = field.getAnnotation(santeclair.lunar.framework.annotation.ToString.class);
                if (toStringAnnotation != null) {
                    ToString toStringMethod = (ToString) toStringAnnotation.clazz().newInstance();
                    field.setAccessible(true);
                    Object fieldValue = field.get(obj);
                    if (!firstField) {
                        sb.append(", ");
                    }
                    sb.append(field.getName()).append(" : ");
                    if (fieldValue != null) {
                        sb.append(toStringMethod.getToString(fieldValue));
                    } else {
                        sb.append("null");
                    }
                    firstField = false;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        if (appendSuperClass && !clazz.getSuperclass().isAssignableFrom(Object.class)) {
            sb.append("\n\t extends \n\t\t");
            sb.append(toStringBuilder(obj, clazz.getSuperclass(), appendSuperClass));
        }
        sb.append(" }");
        return sb.toString();
    }

}
