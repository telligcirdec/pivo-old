package santeclair.portal.webapp.vaadin.view.component;

import static santeclair.portal.event.EventDictionaryConstant.TOPIC_NAVIGATOR;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.jouni.animator.AnimatorProxy;
import org.vaadin.jouni.animator.shared.AnimType;

import santeclair.portal.listener.service.impl.EventAdminServiceListener;
import santeclair.portal.listener.service.impl.EventAdminServiceListener.Publisher;
import santeclair.portal.module.ModuleUi;
import santeclair.portal.view.ViewUi;
import santeclair.portal.webapp.vaadin.navigator.NavigatorEventHandler;

import com.vaadin.server.FontIcon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ModuleUiButon extends Button implements Comparable<ModuleUiButon>, Button.ClickListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleUiButon.class);

    private static final long serialVersionUID = 2401506522651257986L;

    private static final String MAIN_BUTTON_MODULE_UI_STYLE_NAME = "button-heading";

    private final ModuleUi moduleUi;
    private final Map<String, ViewUi> viewUiMap;
    private final EventAdminServiceListener eventAdminServiceListener;
    private final Publisher<ModuleUiButon> publisher;
    private final String uiId;
    private VerticalLayout viewUiButtonVl;
    private boolean secoundaryMenuOpen = false;
    final AnimatorProxy proxy;

    public ModuleUiButon(final ModuleUi moduleUi,
                         final Map<String, ViewUi> viewUiMap,
                         final EventAdminServiceListener eventAdminServiceListener,
                         final String uiId, final AnimatorProxy proxy) {
        this.moduleUi = moduleUi;
        this.viewUiMap = viewUiMap;
        this.publisher = eventAdminServiceListener.registerPublisher(this, TOPIC_NAVIGATOR);
        this.uiId = uiId;
        this.eventAdminServiceListener = eventAdminServiceListener;
        this.proxy = proxy;
        init(moduleUi.getLibelle(), moduleUi.getIcon());
    }

    private void init(final String moduleUiLibelle, final FontIcon moduleUiIcon) {
        this.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        this.addStyleName(MAIN_BUTTON_MODULE_UI_STYLE_NAME);
        this.setCaption(moduleUiLibelle);
        this.setIcon(moduleUiIcon);
        this.setWidth(100, Unit.PERCENTAGE);
        this.addClickListener(this);
        if (severalViewUi()) {
            this.addStyleName("btn-menu-close");
        }
    }

    @Override
    public void detach() {
        super.detach();
        eventAdminServiceListener.unregisterPublisher(publisher);
    }

    @Override
    public int compareTo(ModuleUiButon mainButonModuleUiFactoryToCompare) {
        String moduleUiLibelleToCompare = mainButonModuleUiFactoryToCompare.getModuleUi().getLibelle();
        String moduleUiLibelleSource = this.getModuleUi().getLibelle();
        Integer displayOrderToCompare = mainButonModuleUiFactoryToCompare.getModuleUi().getDisplayOrder();
        Integer displayOrderSource = this.getModuleUi().getDisplayOrder();
        if (displayOrderToCompare == null || Integer.signum(displayOrderToCompare) < 1) {
            displayOrderToCompare = Integer.MAX_VALUE;
        }
        if (displayOrderSource == null || Integer.signum(displayOrderSource) < 1) {
            displayOrderSource = Integer.MAX_VALUE;
        }
        if (displayOrderSource.equals(displayOrderToCompare)) {
            return moduleUiLibelleSource.compareTo(moduleUiLibelleToCompare);
        }
        return displayOrderSource.compareTo(displayOrderToCompare);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        LOGGER.debug("Click event : {}", event);
        if (!viewUiMap.isEmpty() && viewUiMap.size() == 1) {
            Set<String> keySet = viewUiMap.keySet();
            String viewCode = null;
            for (String key : keySet) {
                viewCode = key;
            }
            publisher.publishEventSynchronously(NavigatorEventHandler.getNavigateToProps("container/new/modules/" + this.moduleUi.getCode() + "/views/" + viewCode, uiId));
        } else if (severalViewUi()) {
            VerticalLayout parent = getVerticalLayoutParent();
            createSecoundaryButtonVerticalLayoutAndAddIt(parent);
            animateSecoundaryButtonVerticalLayout(proxy);
        }
    }

    public ModuleUi getModuleUi() {
        return moduleUi;
    }

    private void createSecoundaryButtonVerticalLayoutAndAddIt(VerticalLayout parent) {
        if (viewUiButtonVl == null) {
            Set<String> keySet = viewUiMap.keySet();
            viewUiButtonVl = new VerticalLayout();
            viewUiButtonVl.setStyleName("menu-button-layout");
            for (String key : keySet) {
                ViewUi viewUi = viewUiMap.get(key);
                Button viewUiButton = new Button(viewUi.getLibelle(), viewUi.getIcon());
                viewUiButton.setSizeFull();
                viewUiButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
                viewUiButtonVl.addComponent(viewUiButton);
                viewUiButtonVl.setComponentAlignment(
                                viewUiButton, Alignment.MIDDLE_RIGHT);
            }
        }
        int mainButtonIndex = parent.getComponentIndex(this);
        parent.addComponent(viewUiButtonVl, ++mainButtonIndex);
    }

    private void animateSecoundaryButtonVerticalLayout(AnimatorProxy proxy) {
        if (!secoundaryMenuOpen) {
            this.removeStyleName("btn-menu-close");
            this.addStyleName("btn-menu-open");
            proxy.animate(viewUiButtonVl, AnimType.ROLL_DOWN_OPEN_POP);
            secoundaryMenuOpen = true;
        } else {
            this.removeStyleName("btn-menu-open");
            this.addStyleName("btn-menu-close");
            proxy.animate(viewUiButtonVl, AnimType.ROLL_UP_CLOSE_REMOVE);
            secoundaryMenuOpen = false;
        }
    }

    public VerticalLayout getVerticalLayoutParent() {
        if (parentIsVerticalLayout()) {
            return (VerticalLayout) this.getParent();
        }
        throw new IllegalStateException("Parent of ModuleUiButon must be a vertical layout");
    }

    private boolean parentIsVerticalLayout() {
        return VerticalLayout.class.isAssignableFrom(this.getParent().getClass());
    }

    private boolean severalViewUi() {
        return !viewUiMap.isEmpty() && viewUiMap.size() > 1;
    }

}