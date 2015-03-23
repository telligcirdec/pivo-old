package santeclair.portal.bundle.test.view.component;

import org.apache.felix.ipojo.annotations.Component;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@Component(name="MainComponent")
public class MainComponent extends HorizontalLayout {

    private static final long serialVersionUID = 8369775167208351407L;

    public MainComponent() {
        this.addComponent(new Label("Maouahahahaha => " + Math.random()));
    }

}
