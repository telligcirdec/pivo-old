package santeclair.portal.webapp.vaadin.view.component;

import com.vaadin.ui.Window;

public class PopInInformation extends Window {

    // private Window creerPopInInformation(final TabSheet tabsheet,
    // final ModuleUi module, CloseViewResult closeViewResult) {
    // final Window popInInformation = new Window("Abandon création devis");
    //
    // Label label = new Label(closeViewResult.getMessage(), ContentMode.HTML);
    //
    // HorizontalLayout horizontalLayout = null;
    // if (CloseMessageType.CONFIRMATION_NEEDED.equals(closeViewResult
    // .getType())) {
    // Button boutonOui = new Button("Oui", new ClickListener() {
    // /** Serial Version UID */
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // public void buttonClick(ClickEvent event) {
    // popInInformation.close();
    // //unloadModuleUi(tabsheet, module);
    // tabsheet.removeComponent(module);
    // }
    // });
    // boutonOui.setClickShortcut(KeyCode.ENTER);
    // boutonOui.addStyleName(ValoTheme.BUTTON_PRIMARY);
    //
    // Button boutonNon = new Button("Non", new ClickListener() {
    // /** Serial Version UID */
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // public void buttonClick(ClickEvent event) {
    // popInInformation.close();
    // }
    // });
    // boutonNon.setClickShortcut(KeyCode.ESCAPE);
    //
    // horizontalLayout = new HorizontalLayout(boutonOui, boutonNon);
    // } else {
    // Button boutonOk = new Button("Ok", new ClickListener() {
    // /** Serial Version UID */
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // public void buttonClick(ClickEvent event) {
    // popInInformation.close();
    // }
    // });
    // boutonOk.setClickShortcut(KeyCode.ENTER);
    //
    // horizontalLayout = new HorizontalLayout(boutonOk);
    // }
    //
    // horizontalLayout.setSpacing(true);
    //
    // VerticalLayout verticalLayout = new VerticalLayout(label,
    // horizontalLayout);
    // verticalLayout.setSpacing(true);
    // verticalLayout.setMargin(true);
    // verticalLayout.setStyleName("popup");
    // verticalLayout.setComponentAlignment(horizontalLayout,
    // Alignment.MIDDLE_CENTER);
    //
    // popInInformation.setContent(verticalLayout);
    // popInInformation.setModal(true);
    // popInInformation.center();
    // popInInformation.setResizable(false);
    //
    // return popInInformation;
    // }

}
