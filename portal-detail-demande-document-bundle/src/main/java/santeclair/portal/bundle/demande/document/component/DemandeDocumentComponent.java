package santeclair.portal.bundle.demande.document.component;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Requires;

import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;
import santeclair.reclamation.demande.document.dto.DocumentDto;
import santeclair.reclamation.demande.document.enumeration.DetailResultatAnalyseEnum;
import santeclair.reclamation.demande.document.enumeration.NiveauIncidentEnum;
import santeclair.reclamation.demande.document.enumeration.ResultatAnalyseEnum;
import santeclair.reclamation.demande.document.enumeration.TypeDocumentEnum;
import santeclair.reclamation.demande.document.webservice.DemandeDocumentWebService;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Component(name = "santeclair.portal.bundle.demande.document.component.DemandeDocumentComponent")
public class DemandeDocumentComponent extends VerticalLayout {

    private static final long serialVersionUID = 8369775167208351407L;

    private static final float TAILLE_STANDARD = 12;
    private static final float TAILLE_COMMENTAIRE = 100;
    
    @Requires
    private DemandeDocumentWebService demandeDocumentWebService;

    private Label beneficiaire;
    private Label ps;
    
    private Label numeroDossier;
    private Label motifDemande;
    private Label trigrammeDemandeur;

    private Label organismeBeneficiaire;
    private Label numeroContratBeneficiaire;
    private Label nomBeneficiaire;
    private Label prenomBeneficiaire;

    private Label numeroTelephonePS;
    private Label nomMagasinPS;
    private Label enseignePS;
    private Label codePostalPS;
    private Label communePS;

    private Label etatDossier;

    private TextArea commentaire;
    private ComboBox niveauIncident;

    private Button boutonAnnuler;
    private Button boutonAccederDossierNotes;
    private Button boutonEnregistrer;
    private Button boutonModifier;

    private Table tableauSuiviEnvois;
    private Table tableauDocuments;

    private VerticalLayout descriptifDemandeLayout;
    private VerticalLayout suiviEnvoisLayout;
    private VerticalLayout traitementDocumentsLayout;

    public DemandeDocumentComponent() {

        DemandeDocumentDto demandeDocumentDto = demandeDocumentWebService.rechercherDemandeDocumentParId(1000);
        
        initBeneficiaire();
        initPS();
        initNumeroDossier(demandeDocumentDto.getNumeroDossier());
        initMotifDemande(demandeDocumentDto.getMotifDemande().getLibelle());
        initTrigrammeDemandeur(demandeDocumentDto.getTrigrammeDemandeur());
        initOrganismeBeneficiaire(demandeDocumentDto.getOrganismeBeneficiaire());
        initNumeroContratBeneficiaire(demandeDocumentDto.getNumeroContratBeneficiaire());
        initNomBeneficiaire(demandeDocumentDto.getNomBeneficiaire());
        initPrenomBeneficiaire(demandeDocumentDto.getPrenomBeneficiaire());
        initNomMagasinPS(demandeDocumentDto.getNomMagasinPS());
        initEnseigne(demandeDocumentDto.getEnseignePS());
        initTelephonePS(demandeDocumentDto.getTelephonePS());
        initCodePostalPS(demandeDocumentDto.getCodePostalPS());
        initCommunePS(demandeDocumentDto.getCommunePS());
        initEtatDossier(demandeDocumentDto.getEtat().getLibelle());

        initAnnuler();
        initModifier();
        initEnregistrer();
        initAccederDossierNotes();
        
        // Appel WS pour le suivi d'envois
        initTableauSuiviEnvois();
        
        initTableauDocuments();
        for (DocumentDto documentDto : demandeDocumentDto.getDocumentsDto()) {
            ComboBox detailResultatAnalyse = initDetailResultatAnalyse();
            ComboBox resultatAnalyse = initResultatAnalyse(detailResultatAnalyse, documentDto.getTypeDocument());
//          setTableauDocumentsItemsList(List<DocumentForm> documents);
            
            
        }

        initNiveauIncident();
        initCommentaire();

        initLayout();
        
    }

    /** Initialise le label "Bénéficiaire". */
    private void initBeneficiaire() {
        beneficiaire = new Label("Bénéficiaire");
        beneficiaire.addStyleName(ValoTheme.LABEL_LARGE);
        beneficiaire.addStyleName(ValoTheme.LABEL_BOLD);
        beneficiaire.setWidth(TAILLE_STANDARD, Unit.EM);
    }
    
    /** Initialise le label "PS". */
    private void initPS() {
        ps = new Label("PS");
        ps.addStyleName(ValoTheme.LABEL_LARGE);
        ps.addStyleName(ValoTheme.LABEL_BOLD);
        ps.setWidth(TAILLE_STANDARD, Unit.EM);
    }
    
    /** Initialise le champ "Nom du bénéficiaire". */
    private void initNomBeneficiaire(String nomBeneficiaireDto) {
        nomBeneficiaire = new Label("Nom du bénéficiaire : " + nomBeneficiaireDto);
        nomBeneficiaire.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Prénom du bénéficiaire". */
    private void initPrenomBeneficiaire(String prenomBeneficiaireDto) {
        prenomBeneficiaire = new Label("Prénom du bénéficiaire : " + prenomBeneficiaireDto);
        prenomBeneficiaire.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Organisme du bénéficiaire". */
    private void initOrganismeBeneficiaire(String organismeBeneficiaireDto) {
        organismeBeneficiaire = new Label("Organisme : " + organismeBeneficiaireDto);
        organismeBeneficiaire.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Numéro de contrat du bénéficiaire". */
    private void initNumeroContratBeneficiaire(String numeroContratBeneficiaireDto) {
        numeroContratBeneficiaire = new Label("Numéro de contrat : " + numeroContratBeneficiaireDto);
        numeroContratBeneficiaire.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Numéro de dossier". */
    private void initNumeroDossier(String numeroDossierDto) {
        numeroDossier = new Label("Numéro de dossier : " + numeroDossierDto);
        numeroDossier.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Motif de la demande". */
    private void initMotifDemande(String motifDemandeDto) {
        motifDemande = new Label("Motif de la demande : " + motifDemandeDto);
        motifDemande.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Trigramme demandeur". */
    private void initTrigrammeDemandeur(String trigrammeDemandeurDto) {
        trigrammeDemandeur = new Label("Trigramme demandeur : " + trigrammeDemandeurDto);
        trigrammeDemandeur.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Etat du dossier" */
    private void initEtatDossier(String etatDossierDto) {
        etatDossier = new Label("Etat du dossier : " + etatDossierDto);
        etatDossier.setWidth(TAILLE_STANDARD + 1, Unit.EM);
    }

    /** Initialise le champ "Nom du magasin". */
    private void initNomMagasinPS(String nomMagasinPSDto) {
        nomMagasinPS = new Label("Nom du magasin : " + nomMagasinPSDto);
        nomMagasinPS.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Enseigne". */
    private void initEnseigne(String enseignePSDto) {
        enseignePS = new Label("Enseigne : " + enseignePSDto);
        enseignePS.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Téléphone". */
    private void initTelephonePS(String telephonePSDto) {
        numeroTelephonePS = new Label("Téléphone : " + telephonePSDto);
        numeroTelephonePS.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Code postal". */
    private void initCodePostalPS(String codePostalPSDto) {
        codePostalPS = new Label("Code postal : " + codePostalPSDto);
        codePostalPS.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Commune". */
    private void initCommunePS(String communePSDto) {
        communePS = new Label("Commune : " + communePSDto);
        communePS.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le bouton "Annuler". */
    private void initAnnuler() {
        boutonAnnuler = new Button("Annuler");
        boutonAnnuler.addStyleName(ValoTheme.BUTTON_PRIMARY);
        boutonAnnuler.setIcon(FontAwesome.TIMES);
        boutonAnnuler.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
            }
        });
    }

    /** Initialise le bouton "Enregistrer". */
    private void initEnregistrer() {
        boutonEnregistrer = new Button("Enregistrer");
        boutonEnregistrer.addStyleName(ValoTheme.BUTTON_PRIMARY);
        boutonEnregistrer.setIcon(FontAwesome.SAVE);
        boutonEnregistrer.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                    DemandeDocumentDto demandeDocumentDto = new DemandeDocumentDto();
                    // Recuperer data form  vers dto
                    demandeDocumentDto = demandeDocumentWebService.enregistrerDemandeDocument(demandeDocumentDto);
                        
                    // Retour à la recherche 
            }
        });
    }

    /** Initialise le bouton "Modifier". */
    private void initModifier() {
        boutonModifier = new Button("Modifier");
        boutonModifier.addStyleName(ValoTheme.BUTTON_PRIMARY);
        boutonModifier.setIcon(FontAwesome.PENCIL);
        boutonModifier.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                
            }
        });
    }

    /** Initialise le bouton "Accéder au dossier d'origine Notes". */
    private void initAccederDossierNotes() {
        boutonAccederDossierNotes = new Button("Accéder au dossier d'origine Notes");
        boutonAccederDossierNotes.addStyleName(ValoTheme.BUTTON_PRIMARY);
        boutonAccederDossierNotes.setIcon(FontAwesome.FOLDER_OPEN);
        boutonAccederDossierNotes.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

            }
        });
    }

    /** Initialise le menu deroulant "Niveau de l'incident" */
    private void initNiveauIncident() {
        niveauIncident = new ComboBox("Niveau de l'incident : ");
        niveauIncident.setWidth(TAILLE_STANDARD + 1, Unit.EM);
        BeanContainer<String, NiveauIncidentEnum> enumContainer = new BeanContainer<String, NiveauIncidentEnum>(NiveauIncidentEnum.class);
        enumContainer.setBeanIdProperty("code");
        enumContainer.addAll(EnumSet.allOf(NiveauIncidentEnum.class));
        niveauIncident.setContainerDataSource(enumContainer);
        niveauIncident.setItemCaptionPropertyId("libelle");
    }

    /** Initialise le champ "Commentaire". */
    private void initCommentaire() {
        commentaire = new TextArea("Commentaire : ");
        commentaire.setWidth(TAILLE_COMMENTAIRE, Unit.PERCENTAGE);
    }

    /** Initialise le tableau de suivi des envois. */
    private void initTableauSuiviEnvois() {
        tableauSuiviEnvois = new Table();
        tableauSuiviEnvois.setPageLength(0);
        tableauSuiviEnvois.setSizeFull();
        tableauSuiviEnvois.addContainerProperty("dto.dateEnvoi", Date.class, null);
        tableauSuiviEnvois.setColumnHeader("dto.dateEnvoi", "Date");
        tableauSuiviEnvois.setColumnExpandRatio("dto.dateEnvoi", 1);
        tableauSuiviEnvois.addContainerProperty("dto.typeEnvoi", String.class, null);
        tableauSuiviEnvois.setColumnHeader("dto.typeEnvoi", "Type d'envoi");
        tableauSuiviEnvois.setColumnExpandRatio("dto.typeEnvoi", 1);
        tableauSuiviEnvois.addContainerProperty("dto.listeCourrierEnvoi", String.class, null);
        tableauSuiviEnvois.setColumnHeader("dto.listeCourrierEnvoi", "Liste courrier");
        tableauSuiviEnvois.setColumnExpandRatio("dto.listeCourrierEnvoi", 2);
    }
    
    /** Initialise le tableau de documents. */
    private void initTableauDocuments() {
        tableauDocuments = new Table();
        tableauDocuments.setPageLength(0);
        tableauDocuments.setSizeFull();
        
        tableauDocuments.addContainerProperty("dto.typeDocument", String.class, null);
        tableauDocuments.setColumnHeader("dto.typeDocument", "Type de justificatif");
        tableauDocuments.setColumnWidth("dto.typeDocument", 210);
        tableauDocuments.addContainerProperty("dto.documentRecu", ComboBox.class, null);
        tableauDocuments.setColumnHeader("dto.documentRecu", "Document reçu");
        tableauDocuments.setColumnWidth("dto.documentRecu", 140);
        tableauDocuments.addContainerProperty("dto.originalDemande", ComboBox.class, null);
        tableauDocuments.setColumnHeader("dto.originalDemande", "Original demandé");
        tableauDocuments.setColumnWidth("dto.originalDemande", 140);
        tableauDocuments.addContainerProperty("dto.dateReception", DateField.class, null);
        tableauDocuments.setColumnHeader("dto.dateReception", "Date de réception");
        tableauDocuments.setColumnWidth("dto.dateReception", 180);
        tableauDocuments.addContainerProperty("dto.resultatAnalyse", ComboBox.class, null);
        tableauDocuments.setColumnHeader("dto.resultatAnalyse", "Résultat analyse");
        tableauDocuments.setColumnExpandRatio("dto.resultatAnalyse", 2);
        tableauDocuments.addContainerProperty("dto.detailResultatAnalyse", ComboBox.class, null);
        tableauDocuments.setColumnHeader("dto.detailResultatAnalyse", "Choisissez");
        tableauDocuments.setColumnExpandRatio("dto.detailResultatAnalyse", 3);
        tableauDocuments.setSortEnabled(false);
    }
    
    /** Initialise la vue principale. */
    private void initLayout() {
        this.setSizeFull();
        this.setMargin(true);
        this.setSpacing(true);

        VerticalLayout verticalLayoutLeft = new VerticalLayout();
        verticalLayoutLeft.setMargin(true);
        verticalLayoutLeft.setSpacing(true);
        verticalLayoutLeft.addComponent(numeroDossier);
        verticalLayoutLeft.addComponent(motifDemande);
        verticalLayoutLeft.addComponent(trigrammeDemandeur);
        
        HorizontalLayout horizontalLayoutTop = new HorizontalLayout(verticalLayoutLeft, etatDossier);
        horizontalLayoutTop.setComponentAlignment(etatDossier, Alignment.MIDDLE_LEFT);
        horizontalLayoutTop.setSizeFull();
        horizontalLayoutTop.setMargin(true);
        horizontalLayoutTop.setSpacing(true);

        Panel beneficiairePanel = new Panel();
        VerticalLayout verticalLayoutLeft2 = new VerticalLayout();
        verticalLayoutLeft2.setMargin(true);
        verticalLayoutLeft2.setSpacing(true);
        verticalLayoutLeft2.setStyleName(ValoTheme.LAYOUT_WELL);
        verticalLayoutLeft2.addComponent(beneficiaire);
        verticalLayoutLeft2.addComponent(organismeBeneficiaire);
        verticalLayoutLeft2.addComponent(numeroContratBeneficiaire);
        verticalLayoutLeft2.addComponent(nomBeneficiaire);
        verticalLayoutLeft2.addComponent(prenomBeneficiaire);
        beneficiairePanel.setContent(verticalLayoutLeft2);

        Panel psPanel = new Panel();
        VerticalLayout verticalLayoutRight2 = new VerticalLayout();
        verticalLayoutRight2.setMargin(true);
        verticalLayoutRight2.setSpacing(true);
        verticalLayoutRight2.setStyleName(ValoTheme.LAYOUT_WELL);
        verticalLayoutRight2.addComponent(ps);
        verticalLayoutRight2.addComponent(nomMagasinPS);
        verticalLayoutRight2.addComponent(enseignePS);
        verticalLayoutRight2.addComponent(numeroTelephonePS);
        verticalLayoutRight2.addComponent(codePostalPS);
        verticalLayoutRight2.addComponent(communePS);
        psPanel.setContent(verticalLayoutRight2);
        
        HorizontalLayout horizontalLayoutBottom = new HorizontalLayout(beneficiairePanel, psPanel);
        horizontalLayoutBottom.setComponentAlignment(beneficiairePanel, Alignment.MIDDLE_LEFT);
        horizontalLayoutBottom.setComponentAlignment(psPanel, Alignment.MIDDLE_LEFT);
        horizontalLayoutBottom.setSizeFull();
        horizontalLayoutBottom.setMargin(true);
        horizontalLayoutBottom.setSpacing(true);

        Panel descriptifDemandePanel = new Panel("Descriptif de la demande");
        descriptifDemandeLayout = new VerticalLayout();
        descriptifDemandeLayout.setSizeFull();
        descriptifDemandeLayout.setMargin(true);
        descriptifDemandeLayout.addComponent(horizontalLayoutTop);
        descriptifDemandeLayout.addComponent(horizontalLayoutBottom);
        descriptifDemandePanel.setContent(descriptifDemandeLayout);

        Panel suiviEnvoisPanel = new Panel("Suivi des envois");
        suiviEnvoisLayout = new VerticalLayout();
        suiviEnvoisLayout.setMargin(true);
        suiviEnvoisLayout.addComponent(tableauSuiviEnvois);
        suiviEnvoisPanel.setContent(suiviEnvoisLayout);

        Panel traitementDocumentsPanel = new Panel("Traitements des documents");
        
        HorizontalLayout boutonsLayout = new HorizontalLayout(boutonModifier, boutonAnnuler);
        boutonsLayout.setMargin(true);
        boutonsLayout.setSpacing(true);
        boutonsLayout.setComponentAlignment(boutonModifier, Alignment.MIDDLE_RIGHT);
        boutonsLayout.setSizeFull();
        
        HorizontalLayout commentaireNiveauIncidentLayout = new HorizontalLayout(commentaire, niveauIncident);
        commentaireNiveauIncidentLayout.setMargin(true);
        commentaireNiveauIncidentLayout.setSpacing(true);
        commentaireNiveauIncidentLayout.setSizeFull();
        commentaireNiveauIncidentLayout.setExpandRatio(commentaire, 2);
        commentaireNiveauIncidentLayout.setExpandRatio(niveauIncident, 1);

        traitementDocumentsLayout = new VerticalLayout();
        traitementDocumentsLayout.setSizeFull();
        traitementDocumentsLayout.setMargin(true);
        traitementDocumentsLayout.setSpacing(true);
        traitementDocumentsLayout.addComponent(boutonAccederDossierNotes);
        traitementDocumentsLayout.addComponent(tableauDocuments);
        traitementDocumentsLayout.addComponent(commentaireNiveauIncidentLayout);
        traitementDocumentsLayout.addComponent(boutonsLayout);
        traitementDocumentsPanel.setContent(traitementDocumentsLayout);

        this.setWidth("100%");
        this.addComponent(descriptifDemandePanel);
        this.addComponent(suiviEnvoisPanel);
        this.addComponent(traitementDocumentsPanel);
    }
    
    /** Initialise le menu deroulant "Résultat analyse" */
    private ComboBox initResultatAnalyse(final ComboBox detailResultatAnalyse, final TypeDocumentEnum typeDocument) {
        final ComboBox resultatAnalyse = new ComboBox();
        resultatAnalyse.setWidth(TAILLE_STANDARD + 1, Unit.EM);
        BeanContainer<String, ResultatAnalyseEnum> enumContainer = new BeanContainer<String, ResultatAnalyseEnum>(ResultatAnalyseEnum.class);
        enumContainer.setBeanIdProperty("code");
        enumContainer.addAll(EnumSet.allOf(ResultatAnalyseEnum.class));
        resultatAnalyse.setContainerDataSource(enumContainer);
        resultatAnalyse.setItemCaptionPropertyId("libelle");
        resultatAnalyse.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                if (resultatAnalyse.getValue().equals(ResultatAnalyseEnum.NON_CONFORME.getCode())) {
                     setDetailResultatAnalyse(detailResultatAnalyse, typeDocument.getDetailsResultatAnalyseEnum());
                }  
            }
        });
        return resultatAnalyse;
    }
    
    /** Initialise le menu deroulant "Détail résultat analyse" */
    private ComboBox initDetailResultatAnalyse() {
        ComboBox detailResultatAnalyse = new ComboBox();
        detailResultatAnalyse.setWidth(TAILLE_STANDARD + 1, Unit.EM);
        detailResultatAnalyse.setVisible(false);
        return detailResultatAnalyse;
    }
    
    private ComboBox setDetailResultatAnalyse(ComboBox detailResultatAnalyse, List<DetailResultatAnalyseEnum> detailsResultatAnalyse) {
        detailResultatAnalyse.setVisible(true);
        BeanContainer<String, DetailResultatAnalyseEnum> enumContainer = new BeanContainer<String, DetailResultatAnalyseEnum>(DetailResultatAnalyseEnum.class);
        enumContainer.setBeanIdProperty("code");
        enumContainer.addAll(detailsResultatAnalyse);
        detailResultatAnalyse.setContainerDataSource(enumContainer);
        detailResultatAnalyse.setItemCaptionPropertyId("libelle");
        return detailResultatAnalyse;
    }
}
