package santeclair.lunar.framework.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultToString implements ToString {

    @SuppressWarnings("unchecked")
    @Override
    public String getToString(Object object) {
        StringBuffer sbf = new StringBuffer();
        if (object != null) {
            if (object.getClass().isArray()) {
                sbf.append(" [");
                boolean first = true;
                for (Object objectList : (Object[])object) {
                    if (!first) {
                        sbf.append(", ");
                    }
                    sbf.append("'").append(objectList.toString()).append("'");
                    first = false;
                }
                sbf.append("] ");
            } else if (List.class.isAssignableFrom(object.getClass())) {
                List<Object> list = List.class.cast(object);
                sbf.append(" [");
                boolean first = true;
                for (Object objectList : list) {
                    if (!first) {
                        sbf.append(", ");
                    }
                    sbf.append("'").append(objectList.toString()).append("'");
                    first = false;
                }
                sbf.append("] ");
            } else if (Set.class.isAssignableFrom(object.getClass())) {
                Set<Object> set = Set.class.cast(object);
                sbf.append(" [");
                boolean first = true;
                for (Object objectSet : set) {
                    if (!first) {
                        sbf.append(", ");
                    }
                    sbf.append("'").append(objectSet.toString()).append("'");
                    first = false;
                }
                sbf.append("] ");
            } else if (Map.class.isAssignableFrom(object.getClass())) {
                Map<Object, Object> map = Map.class.cast(object);
                sbf.append(" [");
                boolean first = true;
                for (Object objectKeyMap : map.keySet()) {
                    if (!first) {
                        sbf.append(", ");
                    }
                    sbf.append(objectKeyMap.toString()).append(" : ");
                    sbf.append("'").append(map.get(objectKeyMap).toString()).append("'");
                    first = false;
                }
                sbf.append("] ");
            } else {
                sbf.append("'").append(object.toString()).append("'");
            }
            return sbf.toString();
        }
        return "null";
    }

}
