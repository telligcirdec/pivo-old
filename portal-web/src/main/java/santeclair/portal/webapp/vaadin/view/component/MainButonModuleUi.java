package santeclair.portal.webapp.vaadin.view.component;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import santeclair.portal.event.EventDictionaryConstant;
import santeclair.portal.listener.service.impl.EventAdminServiceListener;
import santeclair.portal.listener.service.impl.EventAdminServiceListener.Publisher;
import santeclair.portal.module.ModuleUi;
import santeclair.portal.view.ViewUi;
import santeclair.portal.webapp.vaadin.navigator.NavigatorEventHandler;

import com.vaadin.server.FontIcon;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class MainButonModuleUi extends Button implements Comparable<MainButonModuleUi>, Button.ClickListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainButonModuleUi.class);

    private static final long serialVersionUID = 2401506522651257986L;

    private static final String MAIN_BUTTON_MODULE_UI_STYLE_NAME = "button-heading";

    private final ModuleUi moduleUi;
    private final Map<String, ViewUi> viewUiMap;
    private final Publisher<MainButonModuleUi> publisher;
    private final String uiId;

    public MainButonModuleUi(final ModuleUi moduleUi,
                             final Map<String, ViewUi> viewUiMap,
                             final EventAdminServiceListener eventAdminServiceListener,
                             final String uiId) {
        this.moduleUi = moduleUi;
        this.viewUiMap = viewUiMap;
        this.publisher = eventAdminServiceListener.registerPublisher(this, EventDictionaryConstant.TOPIC_NAVIGATOR);
        this.uiId = uiId;
        init(moduleUi.getLibelle(), moduleUi.getIcon());
    }

    private void init(final String moduleUiLibelle, final FontIcon moduleUiIcon) {
        this.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        this.addStyleName(MAIN_BUTTON_MODULE_UI_STYLE_NAME);
        this.setCaption(moduleUiLibelle);
        this.setIcon(moduleUiIcon);
        this.setWidth(100, Unit.PERCENTAGE);
        this.addClickListener(this);
    }

    @Override
    public int compareTo(MainButonModuleUi mainButonModuleUiFactoryToCompare) {
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
        } else {

        }
    }

    public ModuleUi getModuleUi() {
        return moduleUi;
    }

}
