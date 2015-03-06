package santeclair.portal.webapp.vaadin.view;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class Tabs extends TabSheet implements View {

    private static final long serialVersionUID = 5672663000761618207L;

    private static final Logger LOGGER = LoggerFactory.getLogger(Tabs.class);

    public Tabs() {
    }

    public void init() {

    }

    @Override
    public void enter(ViewChangeEvent event) {
        LOGGER.debug("enter : " + event);
        StringTokenizer st = new StringTokenizer(event.getParameters(), "/");
        String container;
        String moduleCode;

        int count = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (count == 0) {
                container = token;
            }
            if (count == 2) {
                moduleCode = token;
            }
            count++;
        }

        this.addTab(new VerticalLayout());
    }

    public void callbackModule(Component moduleUiView) {

    }
}
