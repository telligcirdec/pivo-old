package santeclair.portal.reclamation.demande.document.recherche.component.sub;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_MODULE_UI_CODE;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_NAVIGATOR;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.vaadin.viritin.button.DownloadButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import santeclair.portal.event.utils.TabsEventUtil;
import santeclair.portal.reclamation.demande.document.recherche.EventConstant;
import santeclair.portal.reclamation.demande.document.recherche.form.ResultatRecherche;
import santeclair.portal.utils.component.SubComponent;
import santeclair.portal.utils.component.SubComponentInit;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.themes.ValoTheme;

@SubComponent
@Component
@Provides(specifications = {ResultatComponent.class})
public class ResultatComponent extends Panel {

    @Property(name = PROPERTY_KEY_PORTAL_SESSION_ID)
    private String sessionId;
    @Property(name = PROPERTY_KEY_MODULE_UI_CODE)
    private String moduleCode;

    private static final long serialVersionUID = 8369775167208351407L;

    private static final String MSG_NO_RESULTAT = "Aucun résultat pour cette recherche de demande de document";

    @Publishes(name = "resultatComponentPublisher", topics = TOPIC_NAVIGATOR, synchronous = true)
    private Publisher resultatComponentPublisher;

    private MButton boutonExporter;

    private Table tableauResultDemande;

    private MVerticalLayout tableResultLayout;
    private MVerticalLayout emptyTableauLayout;

    private Label labelEmptyTableau;

    @SubComponentInit
    public void init() {
        initEmptyTableau();
        initExporter();
        initTableauResultDemandeDocument();
        initLayout();
    }

    /** Initialise le bouton exporter. */
    private void initEmptyTableau() {
        labelEmptyTableau = new Label(MSG_NO_RESULTAT);
        labelEmptyTableau.setSizeUndefined();
    }

    /** Initialise le bouton exporter. */
    private void initExporter() {
        boutonExporter = new DownloadButton()
                        .withCaption("Export Excel")
                        .withStyleName(ValoTheme.BUTTON_PRIMARY)
                        .withStyleName(ValoTheme.BUTTON_LARGE)
                        .withIcon(FontAwesome.DOWNLOAD);
    }

    /** Initialise le tableau de resultats de demandes de document. */
    private void initTableauResultDemandeDocument() {

        tableauResultDemande = new Table();
        tableauResultDemande.setSizeFull();
        tableauResultDemande.setSelectable(false);
        tableauResultDemande.setImmediate(true);
        tableauResultDemande.setPageLength(0);
        tableauResultDemande.addContainerProperty("dto.numeroDossier", String.class, null);
        tableauResultDemande.setColumnHeader("dto.numeroDossier", "Numéro de dossier");
        tableauResultDemande.addContainerProperty("dto.trigrammeDemandeur", String.class, null);
        tableauResultDemande.setColumnHeader("dto.trigrammeDemandeur", "Trigramme");
        tableauResultDemande.addContainerProperty("dto.dateDemandeDocument", Date.class, null);
        tableauResultDemande.setConverter("dto.dateDemandeDocument", new StringToDateConverter(){
            
            private static final long serialVersionUID = -7198083299937576685L;

            @Override
            public DateFormat getFormat(Locale locale) {
                return new SimpleDateFormat("dd/MM/yyyy");
            }
        });
        tableauResultDemande.setColumnHeader("dto.dateDemandeDocument", "Date");
        tableauResultDemande.addContainerProperty("dto.nomBeneficiaire", String.class, null);
        tableauResultDemande.setColumnHeader("dto.nomBeneficiaire", "Nom Bénéficiaire");
        tableauResultDemande.addContainerProperty("dto.prenomBeneficiaire", String.class, null);
        tableauResultDemande.setColumnHeader("dto.prenomBeneficiaire", "Prénom Bénéficiaire");
        tableauResultDemande.addContainerProperty("dto.telephonePS", String.class, null);
        tableauResultDemande.setColumnHeader("dto.telephonePS", "Numéro de téléphone du PS");
        tableauResultDemande.addContainerProperty("dto.etat", String.class, null);
        tableauResultDemande.setColumnHeader("dto.etat", "Etat du dossier");
        tableauResultDemande.addContainerProperty("buttonLayout", HorizontalLayout.class, null);
        tableauResultDemande.setColumnHeader("buttonLayout", "Action");
        tableauResultDemande.setColumnAlignment("buttonLayout", Align.CENTER);
        tableauResultDemande.setSortContainerPropertyId("dto.numeroDossier");
        tableauResultDemande.setSortAscending(true);
    }

    /** Initialise la vue principale. */
    private void initLayout() {
        tableResultLayout = new MVerticalLayout(boutonExporter, tableauResultDemande)
                        .withMargin(true)
                        .withSpacing(true)
                        .withFullWidth()
                        .withAlign(boutonExporter, Alignment.TOP_RIGHT);
        emptyTableauLayout = new MVerticalLayout(labelEmptyTableau)
                        .withMargin(true)
                        .withSpacing(true)
                        .withFullWidth()
                        .withAlign(labelEmptyTableau, Alignment.TOP_CENTER);

        this.setCaption("Résultat de la recherche");
        this.setVisible(false);
    }

    /** Construit les bouton action pour une ligne dans le tableau */
    private MHorizontalLayout buildButtonLayout(final DemandeDocumentDto demandeDocumentDto) {
        return new MHorizontalLayout(creerBtnConsulter(demandeDocumentDto));
    }

    /**
     * Créer un bouton pour la consultation d'une demande de document.
     * 
     * @param demandeDocumentDto la demande de document a consulter
     * @return le bouton
     */
    private Button creerBtnConsulter(final DemandeDocumentDto demandeDocumentDto) {
        MButton btnConsulter = new MButton("Accéder à la demande")
                        .withStyleName(ValoTheme.BUTTON_PRIMARY)
                        .withStyleName(ValoTheme.BUTTON_ICON_ONLY)
                        .withStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED)
                        .withIcon(FontAwesome.EDIT)
                        .withListener(new ClickListener() {
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void buttonClick(ClickEvent event) {
                                HashMap<String, Object> mapParams = new HashMap<>();
                                mapParams.put("idDemandeDocument", demandeDocumentDto.getId());
                                resultatComponentPublisher.send(TabsEventUtil.getNewViewUiProps(sessionId, moduleCode, "DEMANDE", mapParams));
                            }
                        });
        return btnConsulter;
    }

    @Subscriber(name = EventConstant.EVENT_RECHERCHE_DEMANDE_DOCUMENT_OK, filter = "(&(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*)(" + PROPERTY_KEY_TAB_HASH + "=*)("
                    + PROPERTY_KEY_EVENT_NAME + "=" + EventConstant.EVENT_RECHERCHE_DEMANDE_DOCUMENT_OK + ")(" + EventConstant.PROPERTY_KEY_LISTE_DEMANDE_DOCUMENT + "=*))",
                    topics = EventConstant.TOPIC_RECHERCHE_DEMANDE_DOCUMENT)
    @SuppressWarnings("unchecked")
    private void setTableauRechercheItemsList(org.osgi.service.event.Event event) {
        List<DemandeDocumentDto> listeDemandesDocumentDto = (List<DemandeDocumentDto>) event.getProperty(EventConstant.PROPERTY_KEY_LISTE_DEMANDE_DOCUMENT);

        if (null != listeDemandesDocumentDto && !listeDemandesDocumentDto.isEmpty()) {
            List<ResultatRecherche> resultatsRecherche = new ArrayList<ResultatRecherche>();
            for (final DemandeDocumentDto demandeDocumentDto : listeDemandesDocumentDto) {
                ResultatRecherche resultatRecherche = new ResultatRecherche(demandeDocumentDto);
                resultatRecherche.setButtonLayout(buildButtonLayout(demandeDocumentDto));
                resultatsRecherche.add(resultatRecherche);
            }

            BeanContainer<String, ResultatRecherche> dataSource = new BeanContainer<String, ResultatRecherche>(ResultatRecherche.class);
            dataSource.addNestedContainerBean("dto");
            dataSource.setBeanIdProperty("dto.id");
            dataSource.addAll(resultatsRecherche);

            tableauResultDemande.setContainerDataSource(dataSource);
            tableauResultDemande.setVisibleColumns("dto.numeroDossier", "dto.trigrammeDemandeur", "dto.dateDemandeDocument",
                            "dto.nomBeneficiaire", "dto.prenomBeneficiaire", "dto.telephonePS", "dto.etat", "buttonLayout");

            tableauResultDemande.sort();
            this.setContent(tableResultLayout);
        } else {
            this.setContent(emptyTableauLayout);
        }
        this.setVisible(true);

        // Ie8CssFontHack.showFonts();
    }
}
