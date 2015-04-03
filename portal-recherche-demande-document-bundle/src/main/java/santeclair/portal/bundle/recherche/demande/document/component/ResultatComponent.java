package santeclair.portal.bundle.recherche.demande.document.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.vaadin.viritin.button.DownloadButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MVerticalLayout;

import santeclair.portal.bundle.recherche.demande.document.form.ResultatRechercheForm;
import santeclair.portal.event.EventDictionaryConstant;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.themes.ValoTheme;

@Component(name="santeclair.portal.bundle.recherche.demande.document.component.ResultatComponent")
@Provides(specifications = {ResultatComponent.class})
public class ResultatComponent extends Panel {

    private String sessionId;
    private Integer tabHash;
    private String moduleCode;
    private String viewCode;

    
    private static final long serialVersionUID = 8369775167208351407L;

    //@Publishes(name = "myComponenentPublisher", topics = "ResultatDemandeDocument")
    private Publisher myComponenentPublisher;

    private MButton boutonExporter;

    private MTable tableauResultDemande;

    private MVerticalLayout tableResultLayout;

    /* (non-Javadoc)
     * @see santeclair.portal.bundle.recherche.demande.document.component.ResultatComponentCallback#init(java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
     */
    public void init(String sessionId, Integer tabHash, String moduleCode, String viewCode) {

        this.sessionId = sessionId;
        this.tabHash = tabHash;
        this.moduleCode = moduleCode;
        this.viewCode = viewCode;

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
        tableauResultDemande = new MTable();
        tableauResultDemande.setPageLength(0);
        tableauResultDemande.setSizeFull();
        tableauResultDemande.setSelectable(true);
        tableauResultDemande.addContainerProperty("dto.numeroDossier", String.class, null);
        tableauResultDemande.setColumnHeader("dto.numeroDossier", "Numéro de dossier");
        tableauResultDemande.setColumnWidth("dto.numeroDossier", 180);
        tableauResultDemande.addContainerProperty("dto.trigrammeDemandeur", String.class, null);
        tableauResultDemande.setColumnHeader("dto.trigrammeDemandeur", "Trigramme");
        tableauResultDemande.setColumnWidth("dto.trigrammeDemandeur", 130);
        tableauResultDemande.addContainerProperty("dto.dateDemandeDocument", Date.class, null);
        tableauResultDemande.setColumnHeader("dto.dateDemandeDocument", "Date");
        tableauResultDemande.setColumnWidth("dto.dateDemandeDocument", 150);
        tableauResultDemande.addContainerProperty("dto.nomBeneficiaire", String.class, null);
        tableauResultDemande.setColumnHeader("dto.nomBeneficiaire", "Nom Bénéficiaire");
        tableauResultDemande.setColumnExpandRatio("dto.nomBeneficiaire", 3);
        tableauResultDemande.addContainerProperty("dto.prenomBeneficiaire", String.class, null);
        tableauResultDemande.setColumnHeader("dto.prenomBeneficiaire", "Prénom Bénéficiaire");
        tableauResultDemande.setColumnExpandRatio("dto.prenomBeneficiaire", 2);
        tableauResultDemande.addContainerProperty("dto.telephonePS", String.class, null);
        tableauResultDemande.setColumnHeader("dto.telephonePS", "Numéro de téléphone du PS");
        tableauResultDemande.setColumnWidth("dto.telephonePS", 180);
        tableauResultDemande.addContainerProperty("dto.etat.code", String.class, null);
        tableauResultDemande.setColumnHeader("dto.etat.code", "Etat du dossier");
        tableauResultDemande.setColumnWidth("dto.etat.code", 150);
        tableauResultDemande.addContainerProperty("buttonLayout", HorizontalLayout.class, null);
        tableauResultDemande.setColumnHeader("buttonLayout", "Action");
        tableauResultDemande.setColumnWidth("buttonLayout", 100);
        tableauResultDemande.setColumnAlignment("buttonLayout", Align.CENTER);
        tableauResultDemande.setSortContainerPropertyId("dto.numeroDossier");
        tableauResultDemande.setSortAscending(false);
    }

    /** Initialise la vue principale. */
    private void initLayout() {
        tableResultLayout = new MVerticalLayout().withMargin(true)
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
        List<ResultatRechercheForm> resultatsRecherche = new ArrayList<ResultatRechercheForm>();
        for (final DemandeDocumentDto demandeDocumentDto : listeDemandesDocumentDto) {
            ResultatRechercheForm resultatRechercheForm = new ResultatRechercheForm(demandeDocumentDto);
            resultatRechercheForm.setButtonLayout(buildButtonLayout(demandeDocumentDto));
            resultatsRecherche.add(resultatRechercheForm);
        }
        BeanContainer<String, ResultatRechercheForm> dataSource = new BeanContainer<String, ResultatRechercheForm>(ResultatRechercheForm.class);
        dataSource.addNestedContainerBean("dto");
        dataSource.setBeanIdProperty("dto.id");
        dataSource.addAll(resultatsRecherche);

        tableauResultDemande.setContainerDataSource(dataSource);
        tableauResultDemande.sort();

        // Ie8CssFontHack.showFonts();
    }

}
