package santeclair.lunar.framework.util;

public abstract class LogUtil {

    public static String formatParamForLog(Object... objects) {
        return formatParamForLog(null, objects);
    }

    public static String formatParamForLog(String[] paramNames, Object... objects) {
        StringBuffer stringBuffer = new StringBuffer("\n");
        boolean withName = false;
        if (objects != null && objects.length > 0) {
            if (paramNames != null && paramNames.length > 0) {
                withName = true;
            }
            if (paramNames != null) {
                for (int i = 0; i < objects.length; i++) {
                    Object object = objects[i];
                    String name = "arg" + i;
                    if (withName && i < paramNames.length) {
                        name = paramNames[i];
                    }
                    Class<?> clazz = object != null ? object.getClass() : null;
                    stringBuffer.append("\t- ").append(clazz != null ? clazz.getSimpleName() : null).append(" ").append(name).append(" : ")
                                    .append(object);
                    if (i < objects.length - 1) {
                        stringBuffer.append("\n");
                    }
                }
            }
        }
        return stringBuffer.toString();
    }

}
