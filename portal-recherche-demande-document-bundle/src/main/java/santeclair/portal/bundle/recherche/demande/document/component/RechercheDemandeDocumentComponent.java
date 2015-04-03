package santeclair.portal.bundle.recherche.demande.document.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Requires;

import santeclair.portal.bundle.recherche.demande.document.form.RechercheForm;
import santeclair.portal.bundle.recherche.demande.document.form.ResultatRechercheForm;
import santeclair.reclamation.demande.document.dto.DemandeDocumentCriteresDto;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;
import santeclair.reclamation.demande.document.enumeration.EtatDemandeEnum;
import santeclair.reclamation.demande.document.webservice.DemandeDocumentWebService;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Component(name="santeclair.portal.bundle.recherche.demande.document.component.RechercheDemandeDocumentComponent")
public class RechercheDemandeDocumentComponent extends VerticalLayout {

    private static final long serialVersionUID = 8369775167208351407L;

    private static final float TAILLE_STANDARD = 12;

    private static final String MSG_ERROR = "Veuillez saisir au moins un critère de recherche.";

    @Requires
    private DemandeDocumentWebService demandeDocumentWebService;
    
    private TextField nomBeneficiaire;
    private TextField prenomBeneficiaire;
    private TextField telephonePS;
    private TextField numeroDossier;
    private TextField trigrammeDemandeur;
    private DateField dateDebut;
    private DateField dateFin;
    private ComboBox etatDossier;
    private Button boutonRechercher;
    private Button boutonExporter;
    
    private Table tableauResultDemande;
    private VerticalLayout formulaireRechercheLayout;
    private VerticalLayout tableResultLayout;

    private RechercheForm form;
    
    public RechercheDemandeDocumentComponent() {
        initDateDebut();
        initDateFin();
        initEtatDossier();
        initNomBeneficiaire();
        initNumeroDossier();
        initTelephonePS();
        initPrenomBeneficiaire();
        initRechercher();
        initExporter();;
        initTrigrammeDemandeur();
        initTableauResultDemandeDocument();
        initFocusOrder();
        
        initLayout();
        initForm();
    }
    
    /** Initialise le champ nom du bénéficiaire. */
    private void initNomBeneficiaire() {
        nomBeneficiaire = new TextField("Nom du bénéficiaire : ");
        nomBeneficiaire.setWidth(TAILLE_STANDARD, Unit.EM);
        nomBeneficiaire.setNullRepresentation("");
        nomBeneficiaire.setNullSettingAllowed(true);
    }

    /** Initialise le champ prénom du bénéficiaire. */
    private void initPrenomBeneficiaire() {
        prenomBeneficiaire = new TextField("Prénom du bénéficiaire : ");
        prenomBeneficiaire.setWidth(TAILLE_STANDARD, Unit.EM);
        prenomBeneficiaire.setNullRepresentation("");
        prenomBeneficiaire.setNullSettingAllowed(true);
    }
    
    /** Initialise le champ téléphone du PS. */
    private void initTelephonePS() {
        telephonePS = new TextField("Téléphone du PS : ");
        telephonePS.setWidth(TAILLE_STANDARD, Unit.EM);
        telephonePS.setNullRepresentation("");
        telephonePS.setNullSettingAllowed(true);
    }
    
    /** Initialise le champ numéro de dossier. */
    private void initNumeroDossier() {
        numeroDossier = new TextField("Numéro de dossier : ");
        numeroDossier.setWidth(TAILLE_STANDARD, Unit.EM);
        numeroDossier.setNullRepresentation("");
        numeroDossier.setNullSettingAllowed(true);
    }
    
    /** Initialise le champ trigramme demandeur. */
    private void initTrigrammeDemandeur() {
        trigrammeDemandeur = new TextField("Trigramme demandeur : ");
        trigrammeDemandeur.setWidth(TAILLE_STANDARD, Unit.EM);
        trigrammeDemandeur.setNullRepresentation("");
        trigrammeDemandeur.setNullSettingAllowed(true);
    }
    
    /** Initialise le champ date de début. */
    private void initDateDebut() {
        dateDebut = new DateField("Période de création du : ");
    }
    
    /** Initialise le champ date de fin. */
    private void initDateFin() {
        dateFin = new DateField(" au : ");
    }
    
    /** Initialise le menu deroulant "Etat du dossier" */
    private void initEtatDossier() {
        etatDossier = new ComboBox("Etat du dossier : ");
        etatDossier.setWidth(TAILLE_STANDARD + 1, Unit.EM);
        BeanContainer<String, EtatDemandeEnum> enumContainer = new BeanContainer<String, EtatDemandeEnum>(EtatDemandeEnum.class);
        enumContainer.setBeanIdProperty("code");
        enumContainer.addAll(EnumSet.allOf(EtatDemandeEnum.class));
        etatDossier.setContainerDataSource(enumContainer);
        etatDossier.setItemCaptionPropertyId("libelle");
    }
    
    /** Initialise le bouton rechercher. */
    private void initRechercher() {
        boutonRechercher = new Button("Rechercher");
        boutonRechercher.setClickShortcut(KeyCode.ENTER);
        boutonRechercher.addStyleName(ValoTheme.BUTTON_PRIMARY);
        boutonRechercher.setIcon(FontAwesome.SEARCH);
        boutonRechercher.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
//                ValidationNotification validation = controleDonnees();
//                if (validation.isError()) {
//                    validation.show();
//                } else {
                    DemandeDocumentCriteresDto criteresDto = new DemandeDocumentCriteresDto();
                    criteresDto.setDateDebut(form.getDateDebut());
                    criteresDto.setDateFin(form.getDateFin());
                    criteresDto.setEtatDossier(form.getEtatDossier());
                    criteresDto.setNomBeneficiaire(form.getNomBeneficiaire());
                    criteresDto.setNumeroDossier(form.getNumeroDossier());
                    criteresDto.setPrenomBeneficiaire(form.getPrenomBeneficiaire());
                    criteresDto.setTelephonePS(form.getTelephonePS());
                    criteresDto.setTrigrammeDemandeur(form.getTrigrammeDemandeur());
                    List<DemandeDocumentDto> listeDemandeDocument = demandeDocumentWebService.rechercherDemandesDocumentParCriteres(criteresDto);

                    List<ResultatRechercheForm> resultatRecherches = new ArrayList<ResultatRechercheForm>();
                    for (final DemandeDocumentDto demandeDocumentDto : listeDemandeDocument) {
                        ResultatRechercheForm resultatRechercheForm = new ResultatRechercheForm(demandeDocumentDto);
                        resultatRechercheForm.setButtonLayout(buildButtonLayout(demandeDocumentDto));
                        resultatRecherches.add(resultatRechercheForm);
                    }

                    setTableauRechercheItemsList(resultatRecherches);
//                }
            }
        });
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
    
    /** Initialisation de l'ordre de tabulation. */
    private void initFocusOrder() {
        nomBeneficiaire.setTabIndex(1);
        prenomBeneficiaire.setTabIndex(2);
        telephonePS.setTabIndex(3);
        numeroDossier.setTabIndex(4);
        trigrammeDemandeur.setTabIndex(5);
        etatDossier.setTabIndex(6);
        dateDebut.setTabIndex(7);
        dateFin.setTabIndex(8);

        boutonRechercher.setTabIndex(-1);
    }
    
    /** Initialise la vue principale. */
    private void initLayout() {
        this.setSizeFull();
        this.setMargin(true);
        this.setSpacing(true);
        
        FormLayout formLayoutLeft = new FormLayout();
        formLayoutLeft.addComponent(nomBeneficiaire);
        formLayoutLeft.addComponent(telephonePS);
        formLayoutLeft.addComponent(trigrammeDemandeur);
        formLayoutLeft.addComponent(dateDebut);

        FormLayout formLayoutCenter = new FormLayout();
        formLayoutCenter.addComponent(prenomBeneficiaire);
        formLayoutCenter.addComponent(numeroDossier);
        formLayoutCenter.addComponent(etatDossier);
        formLayoutCenter.addComponent(dateFin);

        FormLayout formLayoutRight = new FormLayout();
        formLayoutRight.addComponent(boutonRechercher);

        HorizontalLayout horizontalLayout = new HorizontalLayout(formLayoutLeft, formLayoutCenter, formLayoutRight);
        horizontalLayout.setComponentAlignment(formLayoutRight, Alignment.MIDDLE_CENTER);
        horizontalLayout.setSizeFull();

        Panel formPanel = new Panel("Recherche de demandes de document");
        formulaireRechercheLayout = new VerticalLayout();
        formulaireRechercheLayout.setSizeFull();
        formulaireRechercheLayout.setMargin(true);
        formulaireRechercheLayout.addComponent(horizontalLayout);
        formPanel.setContent(formulaireRechercheLayout);
        
        Panel resultPanel = new Panel("Résultat de la recherche");
        tableResultLayout = new VerticalLayout();
        tableResultLayout.setSpacing(true);
        tableResultLayout.setMargin(true);
        tableResultLayout.setSizeFull();
        tableResultLayout.addComponent(boutonExporter);
        tableResultLayout.addComponent(tableauResultDemande);
        tableResultLayout.setComponentAlignment(boutonExporter, Alignment.TOP_RIGHT);
        tableResultLayout.setExpandRatio(boutonExporter, 1);
        tableResultLayout.setExpandRatio(tableauResultDemande, 10);
        resultPanel.setContent(tableResultLayout);

        this.setWidth("100%");
        this.addComponent(formPanel);
        this.addComponent(resultPanel);
    }
    
    private void initForm() {
        form = new RechercheForm();
        BeanItem<RechercheForm> beanItem = new BeanItem<RechercheForm>(form);
        FieldGroup binder = new FieldGroup(beanItem);
        binder.setBuffered(false);
        binder.bindMemberFields(this);
    }
    
    public void setTableauRechercheItemsList(List<ResultatRechercheForm> resultatRecherches) {
       
        BeanContainer<String, ResultatRechercheForm> dataSource = new BeanContainer<String, ResultatRechercheForm>(ResultatRechercheForm.class);
        dataSource.addNestedContainerBean("dto");
        dataSource.setBeanIdProperty("dto.id");
        dataSource.addAll(resultatRecherches);

        tableauResultDemande.setContainerDataSource(dataSource);
        tableauResultDemande.sort();
        
//        Ie8CssFontHack.showFonts();
    }
    
//    /** Controle les données du formulaire de recherche. */
//    private ValidationNotification controleDonnees() {
//        ValidationNotification result = new ValidationNotification();
//
//        if (StringUtils.isBlank(form.getNomBeneficiaire()) 
//                && StringUtils.isBlank(form.getPrenomBeneficiaire())
//                && StringUtils.isBlank(form.getNumeroDossier())
//                && StringUtils.isBlank(form.getTelephonePS())
//                && StringUtils.isBlank(form.getTrigrammeDemandeur())
//                && null != form.getDateDebut()
//                && null != form.getDateFin()
//                && null != form.getEtatDossier()
//           ) {
//            result.addMessage(MSG_ERROR);
//        }
//        return result;
//    }
    
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
}
