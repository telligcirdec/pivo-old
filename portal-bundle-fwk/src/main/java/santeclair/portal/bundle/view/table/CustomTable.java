/**
 * 
 */
package santeclair.portal.bundle.view.table;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;

/**
 * Tableau Vaadin avec un paramétrage commun aux différents panels utilisés par l'identification.
 * 
 * @author jfourmond
 * 
 */
public class CustomTable extends Table {

    private static final long serialVersionUID = -2646881042636673907L;

    /** {@inheritDoc} */
    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property<?> property) {
        /* formatage des dates */
        if (property.getType() == Date.class) {
            Date date = (Date) property.getValue();
            if (date != null) {
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                return df.format(date);
            }
        }
        return super.formatPropertyValue(rowId, colId, property);
    }

}
