package santeclair.portal.test2.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class TestModule extends CustomComponent {

    /**
     * 
     */
    private static final long serialVersionUID = 8605608392137230695L;

    public TestModule(String message) {

        // Compose from multiple components
        Label label = new Label(message);
        label.setSizeUndefined(); // Shrink

        // Set the size as undefined at all levels
        setSizeUndefined();

        // A layout structure used for composition
        Layout verticalLayout = new VerticalLayout(label, new Button("Ok"));
        verticalLayout.setSizeUndefined();
        Panel panel = new Panel("My Custom Component", verticalLayout);
        panel.setSizeUndefined();

        // The composition root MUST be set
        setCompositionRoot(panel);
    }

}
