/**
 * 
 */
package santeclair.portal.bundle.view.field;

import java.util.Date;

import com.vaadin.data.Property;
import com.vaadin.ui.DateField;

/**
 * DateField Vaadin avec un paramétrage commun.
 * 
 * @author jfourmond
 * 
 */
@SuppressWarnings("rawtypes")
public class CustomDateField extends DateField {

    private static final long serialVersionUID = 7498614280831995560L;

    private void init() {
        setDateFormat("dd/MM/yyyy");
    }

    public CustomDateField() {
        super();
        init();
    }

    public CustomDateField(Property dataSource) throws IllegalArgumentException {
        super(dataSource);
        init();
    }

    public CustomDateField(String caption, Date value) {
        super(caption, value);
        init();
    }

    public CustomDateField(String caption, Property dataSource) {
        super(caption, dataSource);
        init();
    }

    public CustomDateField(String caption) {
        super(caption);
        init();
    }

}
