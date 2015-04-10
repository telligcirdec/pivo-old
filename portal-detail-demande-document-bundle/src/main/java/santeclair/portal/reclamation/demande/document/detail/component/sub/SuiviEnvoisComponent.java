package santeclair.portal.reclamation.demande.document.detail.component.sub;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;

import java.util.Date;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.vaadin.viritin.layouts.MVerticalLayout;

import santeclair.portal.utils.component.SubComponent;
import santeclair.portal.utils.component.SubComponentInit;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

@SubComponent
@Component
@Provides(specifications = {SuiviEnvoisComponent.class})
public class SuiviEnvoisComponent extends Panel {

    private static final long serialVersionUID = 8369775167208351407L;

    @Property(name = PROPERTY_KEY_PORTAL_SESSION_ID)
    private String sessionId;
    @Property(name = PROPERTY_KEY_TAB_HASH)
    private Integer tabHash;

    private Table tableauSuiviEnvois;

    MVerticalLayout suiviEnvoiLayout;

    @SubComponentInit
    public void init() {
        initTableauSuiviEnvois();

        initLayout();
    }

    /** Initialise le tableau de suivi des envois. */
    private void initTableauSuiviEnvois() {
        tableauSuiviEnvois = new Table();
        tableauSuiviEnvois.setPageLength(0);
        tableauSuiviEnvois.setSizeFull();
        tableauSuiviEnvois.addContainerProperty("dateEnvoi", Date.class, null);
        tableauSuiviEnvois.setColumnHeader("dateEnvoi", "Date");
        tableauSuiviEnvois.addContainerProperty("typeEnvoi", String.class, null);
        tableauSuiviEnvois.setColumnHeader("typeEnvoi", "Type d'envoi");
        tableauSuiviEnvois.addContainerProperty("listeCourrierEnvoi", String.class, null);
        tableauSuiviEnvois.setColumnHeader("listeCourrierEnvoi", "Liste courrier");
    }

    /** Initialise la vue principale. */
    private void initLayout() {
        suiviEnvoiLayout = new MVerticalLayout(tableauSuiviEnvois)
                        .withFullWidth()
                        .withMargin(true)
                        .withSpacing(true);

        this.setCaption("Suivi des envois");
        this.setContent(suiviEnvoiLayout);
    }
}
