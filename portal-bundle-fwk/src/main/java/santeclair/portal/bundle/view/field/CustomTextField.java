/**
 * 
 */
package santeclair.portal.bundle.view.field;

import com.vaadin.data.Property;
import com.vaadin.ui.TextField;

/**
 * TextField Vaadin avec un paramétrage commun.
 * 
 * @author jfourmond
 * 
 */
@SuppressWarnings("rawtypes")
public class CustomTextField extends TextField {

    private static final long serialVersionUID = -2646881042636673907L;

    private void init() {
        setNullRepresentation("");
        setNullSettingAllowed(true);
    }

    public CustomTextField() {
        super();
        init();
    }

    public CustomTextField(Property dataSource) {
        super(dataSource);
        init();
    }

    public CustomTextField(String caption, Property dataSource) {
        super(caption, dataSource);
        init();
    }

    public CustomTextField(String caption, String value) {
        super(caption, value);
        init();
    }

    public CustomTextField(String caption) {
        super(caption);
        init();
    }

}
