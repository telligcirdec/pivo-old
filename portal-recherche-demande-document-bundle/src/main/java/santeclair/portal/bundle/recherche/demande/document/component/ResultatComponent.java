package santeclair.portal.bundle.recherche.demande.document.component;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;

import java.util.Date;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;

import santeclair.portal.event.EventDictionaryConstant;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Component(name = "santeclair.portal.bundle.recherche.demande.document.component.ResultatComponent")
public class ResultatComponent extends Panel {

    private String sessionId;
    private Integer tabHash;

    private static final long serialVersionUID = 8369775167208351407L;

    @Publishes(name = "myComponenentPublisher", topics = "ResultatDemandeDocument")
    private Publisher myComponenentPublisher;

    private Button boutonExporter;

    private Table tableauResultDemande;
    private VerticalLayout tableResultLayout;

    public ResultatComponent() {
        initExporter();
        initTableauResultDemandeDocument();
        initLayout();
    }

    /** Initialise le bouton exporter. */
    private void initExporter() {
        boutonExporter = new Button("Export Excel");
        boutonExporter.setStyleName(ValoTheme.BUTTON_PRIMARY);
        boutonExporter.addStyleName(ValoTheme.BUTTON_LARGE);
        boutonExporter.setIcon(FontAwesome.DOWNLOAD);
    }

    /** Initialise le tableau de resultats de demandes de document. */
    private void initTableauResultDemandeDocument() {
        tableauResultDemande = new Table();
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

        tableResultLayout = new VerticalLayout();
        tableResultLayout.setSpacing(true);
        tableResultLayout.setMargin(true);
        tableResultLayout.setSizeFull();
        tableResultLayout.addComponent(boutonExporter);
        tableResultLayout.addComponent(tableauResultDemande);
        tableResultLayout.setComponentAlignment(boutonExporter, Alignment.TOP_RIGHT);
        tableResultLayout.setExpandRatio(boutonExporter, 1);
        tableResultLayout.setExpandRatio(tableauResultDemande, 10);
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
     * Créer un bouton pour la consultation d'un devis.
     * 
     * @param devis le devis a consulter
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

    @Property(name = PROPERTY_KEY_PORTAL_SESSION_ID)
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Property(name = PROPERTY_KEY_TAB_HASH)
    public void setTabHash(Integer tabHash) {
        this.tabHash = tabHash;
    }
    
    @Subscriber(name = "test", filter = "(&(" + EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID + "=*)(" + EventDictionaryConstant.PROPERTY_KEY_TAB_HASH + "=*)("
                    + EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME + "=rechercheSuccessfull)(listeDemandeDocument=*))", topics = "RechercheDemandeDocument")
    public void setTableauRechercheItemsList(Event event) {

//        BeanContainer<String, ResultatRechercheForm> dataSource = new BeanContainer<String, ResultatRechercheForm>(ResultatRechercheForm.class);
//        dataSource.addNestedContainerBean("dto");
//        dataSource.setBeanIdProperty("dto.id");
//        dataSource.addAll(resultatRecherches);
//
//        tableauResultDemande.setContainerDataSource(dataSource);
//        tableauResultDemande.sort();

        // Ie8CssFontHack.showFonts();
    }

}
