package santeclair.portal.reclamation.demande.document.recherche.component.sub;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_VIEW_UI_CODE;

import java.util.ArrayList;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.vaadin.viritin.button.DownloadButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MVerticalLayout;

import santeclair.portal.event.EventDictionaryConstant;
import santeclair.portal.reclamation.demande.document.recherche.form.ResultatRechercheForm;
import santeclair.portal.utils.component.SubComponent;
import santeclair.portal.utils.component.SubComponentInit;
import santeclair.portal.utils.component.SubComponentInitProperty;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;

@SubComponent
@Component
@Provides(specifications = {ResultatComponent.class})
public class ResultatComponent extends Panel {

    private String sessionId;
    private Integer tabHash;
    private String moduleCode;
    private String viewCode;

    private static final long serialVersionUID = 8369775167208351407L;

    @Publishes(name = "myComponenentPublisher", topics = "ResultatDemandeDocument")
    private Publisher myComponenentPublisher;

    private MButton boutonExporter;

    private MTable<ResultatRechercheForm> tableauResultDemande;

    private MVerticalLayout tableResultLayout;
    
    List<ResultatRechercheForm> resultatsRecherche = new ArrayList<ResultatRechercheForm>();    

    /* (non-Javadoc)
     * @see santeclair.portal.reclamation.demande.document.recherche.component.ResultatComponentCallback#init(java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @SubComponentInit
    public void init(@SubComponentInitProperty(name = PROPERTY_KEY_PORTAL_SESSION_ID) String sessionId,
                    @SubComponentInitProperty(name = PROPERTY_KEY_TAB_HASH) Integer tabHash,
                    @SubComponentInitProperty(name = PROPERTY_KEY_MODULE_UI_CODE) String moduleCode,
                    @SubComponentInitProperty(name = PROPERTY_KEY_VIEW_UI_CODE) String viewCode) {

        this.sessionId = sessionId;
        this.tabHash = tabHash;
        this.moduleCode = moduleCode;
        this.viewCode = viewCode;

        resultatsRecherche.add(new ResultatRechercheForm());
        initExporter();
        initTableauResultDemandeDocument();
        initLayout();
    }

    /** Initialise le bouton exporter. */
    private void initExporter() {
        boutonExporter = new DownloadButton().withCaption("Export Excel")
                        .withStyleName(ValoTheme.BUTTON_PRIMARY)
                        .withStyleName(ValoTheme.BUTTON_LARGE)
                        .withIcon(FontAwesome.DOWNLOAD);
    }

    /** Initialise le tableau de resultats de demandes de document. */
    private void initTableauResultDemandeDocument() {
        
        tableauResultDemande = new MTable<ResultatRechercheForm>(resultatsRecherche).withFullWidth()
                        .withProperties("numeroDossier", "trigrammeDemandeur", "dateDemandeDocument", "nomBeneficiaire",
                                        "prenomBeneficiaire", "telephonePS", "etat", "buttonLayout")
                        .withColumnHeaders("Numéro de dossier", "Trigramme", "Date", "Nom Bénéficiaire", "Prénom Bénéficiaire", "Numéro de téléphone du PS",
                                        "Etat du dossier", "Action");
        
    }

    /** Initialise la vue principale. */
    private void initLayout() {
        tableResultLayout = new MVerticalLayout()
                        .withMargin(true)
                        .withSpacing(true)
                        .withFullWidth()
                        .with(boutonExporter, tableauResultDemande)
                        .withAlign(boutonExporter, Alignment.TOP_RIGHT);
        this.setCaption("Résultat de la recherche");
        this.setContent(tableResultLayout);
    }

    /** Construit les bouton action pour une ligne dans le tableau */
    private HorizontalLayout buildButtonLayout(final DemandeDocumentDto demandeDocumentDto) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addComponent(creerBtnConsulter(demandeDocumentDto));
        return buttonLayout;
    }

    /**
     * Créer un bouton pour la consultation d'une demande de document.
     * 
     * @param demandeDocumentDto la demande de document a consulter
     * @return le bouton
     */
    private Button creerBtnConsulter(final DemandeDocumentDto demandeDocumentDto) {
        Button btnConsulter = new Button();
        btnConsulter.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnConsulter.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        btnConsulter.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        btnConsulter.setIcon(FontAwesome.EDIT);
        btnConsulter.setDescription("Consultation");
        btnConsulter.setHtmlContentAllowed(true);
        btnConsulter.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                // Event
            }
        });
        return btnConsulter;
    }

    @Subscriber(name = "rechercheOk", filter = "(&(" + EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID + "=*)(" + EventDictionaryConstant.PROPERTY_KEY_TAB_HASH + "=*)("
                    + EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME + "=rechercheOk)(listeDemandesDocumentDto=*))", topics = "RechercheDemandeDocument")
    private void setTableauRechercheItemsList(org.osgi.service.event.Event event) {
        List<DemandeDocumentDto> listeDemandesDocumentDto = (List<DemandeDocumentDto>) event.getProperty("listeDemandesDocumentDto");

        resultatsRecherche.clear();
        for (final DemandeDocumentDto demandeDocumentDto : listeDemandesDocumentDto) {
            ResultatRechercheForm resultatRechercheForm = new ResultatRechercheForm(demandeDocumentDto);
            resultatRechercheForm.setButtonLayout(buildButtonLayout(demandeDocumentDto));
            resultatsRecherche.add(resultatRechercheForm);
        }

//        tableauResultDemande.setBeans(resultatsRecherche);

//        tableauResultDemande.sort();

//        tableauResultDemande.markAsDirty();
        tableauResultDemande.setImmediate(true);
        // Ie8CssFontHack.showFonts();
    }

}
