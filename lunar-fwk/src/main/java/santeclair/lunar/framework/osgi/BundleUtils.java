package santeclair.lunar.framework.osgi;

import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

public abstract class BundleUtils {

    public static String bundleNameWithVersion(Bundle bundle) {
        StringBuffer sbf = new StringBuffer();
        if (bundle != null) {
            sbf.append("[BUNDLE : ").append(bundle.getSymbolicName());
            Version version = bundle.getVersion();
            if (version != null) {
                sbf.append(" | ").append(version.toString()).append("");
            }
            sbf.append(" (").append(bundle.getBundleId()).append(")")
                            .append("]");
        }
        return sbf.toString();
    }
}
