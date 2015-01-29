package santeclair.portal.webapp.vaadin.view;

import org.vaadin.jouni.animator.AnimatorProxy;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.VerticalLayout;

public class LeftSideMenu extends CustomLayout {

	private static final long serialVersionUID = -4748523363216844520L;

	private final VerticalLayout buttonContainer;

	public LeftSideMenu() {
		super("sidebarLayout");
		buttonContainer = new VerticalLayout();
	}

	public void init() {
		this.setSizeFull();
		buttonContainer.setSizeFull();
		final AnimatorProxy proxy = new AnimatorProxy();
        buttonContainer.addComponent(proxy);
	}

	public void addButtonModuleUiFactory(){
	    
	    
	    
	}
	
}
