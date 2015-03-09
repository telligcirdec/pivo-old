package santeclair.portal.webapp.vaadin.view;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents.ComponentDetachListener;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;

public class Tabs extends TabSheet implements View, SelectedTabChangeListener, CloseHandler,
                ComponentDetachListener {

    private static final long serialVersionUID = 5672663000761618207L;

    private static final Logger LOGGER = LoggerFactory.getLogger(Tabs.class);

    // private static final Map<Integer, Tab> tabContainerNumber = new HashMap<>();

    public Tabs() {
    }

    public void init() {

    }

    @Override
    public void enter(ViewChangeEvent event) {
        LOGGER.debug("enter : " + event);
        StringTokenizer st = new StringTokenizer(event.getParameters(), "/");
        String container = "";
        String moduleCode = "";
        String moduleView = "";

        int count = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (count == 0) {
                container = token;
            }
            if (count == 2) {
                moduleCode = token;
            }
            if (count == 4) {
                moduleView = token;
            }
            count++;
        }
        if ("NEW".equalsIgnoreCase(container)) {

        } else {

        }
        // this.addTab();
    }

    public void callbackModule(Component moduleUiView) {
        Tab moduleUiViewAlreadyAdded = this.getTab(moduleUiView);
        if (moduleUiViewAlreadyAdded != null) {

        } else {

        }
    }

    @Override
    public void componentDetachedFromContainer(ComponentDetachEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabClose(TabSheet tabsheet, Component tabContent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void selectedTabChange(SelectedTabChangeEvent event) {
        // TODO Auto-generated method stub

    }
}
