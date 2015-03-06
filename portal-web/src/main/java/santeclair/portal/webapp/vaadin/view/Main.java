package santeclair.portal.webapp.vaadin.view;

import com.vaadin.ui.HorizontalLayout;

public class Main extends HorizontalLayout {

    private static final long serialVersionUID = -7697746614894629369L;

    private final LeftSideMenu sidebarLayout;
    private final Tabs tabs;

    public Main(final LeftSideMenu sidebarLayout, final Tabs tabs) {
        this.sidebarLayout = sidebarLayout;
        this.tabs = tabs;
    }

    public void init() {
        this.setSizeFull();
        this.addComponent(sidebarLayout);
        this.addComponent(tabs);
        this.setExpandRatio(sidebarLayout, 0.14f);
        this.setExpandRatio(tabs, 0.86f);
    }

}
