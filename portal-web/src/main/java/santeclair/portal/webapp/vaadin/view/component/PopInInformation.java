package santeclair.portal.webapp.vaadin.view.component;

import santeclair.portal.vaadin.deprecated.module.ModuleUi;
import santeclair.portal.vaadin.deprecated.module.view.CloseViewResult;
import santeclair.portal.vaadin.deprecated.module.view.CloseViewResult.CloseMessageType;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PopInInformation extends Window{

    private Window creerPopInInformation(final TabSheet tabsheet,
                    final ModuleUi module, CloseViewResult closeViewResult) {
        final Window popInInformation = new Window("Abandon création devis");

        Label label = new Label(closeViewResult.getMessage(), ContentMode.HTML);

        HorizontalLayout horizontalLayout = null;
        if (CloseMessageType.CONFIRMATION_NEEDED.equals(closeViewResult
                        .getType())) {
            Button boutonOui = new Button("Oui", new ClickListener() {
                /** Serial Version UID */
                private static final long serialVersionUID = 1L;

                @Override
                public void buttonClick(ClickEvent event) {
                    popInInformation.close();
                    //unloadModuleUi(tabsheet, module);
                    tabsheet.removeComponent(module);
                }
            });
            boutonOui.setClickShortcut(KeyCode.ENTER);
            boutonOui.addStyleName(ValoTheme.BUTTON_PRIMARY);

            Button boutonNon = new Button("Non", new ClickListener() {
                /** Serial Version UID */
                private static final long serialVersionUID = 1L;

                @Override
                public void buttonClick(ClickEvent event) {
                    popInInformation.close();
                }
            });
            boutonNon.setClickShortcut(KeyCode.ESCAPE);

            horizontalLayout = new HorizontalLayout(boutonOui, boutonNon);
        } else {
            Button boutonOk = new Button("Ok", new ClickListener() {
                /** Serial Version UID */
                private static final long serialVersionUID = 1L;

                @Override
                public void buttonClick(ClickEvent event) {
                    popInInformation.close();
                }
            });
            boutonOk.setClickShortcut(KeyCode.ENTER);

            horizontalLayout = new HorizontalLayout(boutonOk);
        }

        horizontalLayout.setSpacing(true);

        VerticalLayout verticalLayout = new VerticalLayout(label,
                        horizontalLayout);
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(true);
        verticalLayout.setStyleName("popup");
        verticalLayout.setComponentAlignment(horizontalLayout,
                        Alignment.MIDDLE_CENTER);

        popInInformation.setContent(verticalLayout);
        popInInformation.setModal(true);
        popInInformation.center();
        popInInformation.setResizable(false);

        return popInInformation;
    }
    
}
