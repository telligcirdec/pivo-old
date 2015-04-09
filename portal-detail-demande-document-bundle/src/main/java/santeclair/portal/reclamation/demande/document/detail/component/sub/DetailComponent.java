package santeclair.portal.reclamation.demande.document.detail.component.sub;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;

import java.util.Date;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextArea;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import santeclair.portal.utils.component.SubComponent;
import santeclair.portal.utils.component.SubComponentInit;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;
import santeclair.reclamation.demande.document.dto.DocumentDto;
import santeclair.reclamation.demande.document.enumeration.DetailResultatAnalyseEnum;
import santeclair.reclamation.demande.document.enumeration.NiveauIncidentEnum;
import santeclair.reclamation.demande.document.enumeration.ResultatAnalyseEnum;
import santeclair.reclamation.demande.document.enumeration.TypeDocumentEnum;
import santeclair.reclamation.demande.document.webservice.DemandeDocumentWebService;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

@SubComponent
@Component
@Provides(specifications = {DetailComponent.class})
public class DetailComponent extends MVerticalLayout {

    private static final long serialVersionUID = 8369775167208351407L;

    private static final float TAILLE_STANDARD = 12;
    
    @Property(name = PROPERTY_KEY_PORTAL_SESSION_ID) 
    private String sessionId;
    @Property(name = PROPERTY_KEY_TAB_HASH) 
    private Integer tabHash;
    @Property(name = "idDemandeDocument") 
    private Integer idDemandeDocument;

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

    private MTextArea commentaire;
    private ComboBox niveauIncident;

    private MButton boutonAnnuler;
    private MButton boutonAccederDossierNotes;
    private MButton boutonEnregistrer;
    private MButton boutonModifier;

    private Table tableauSuiviEnvois;
    private Table tableauDocuments;

    private MVerticalLayout descriptifDemandeLayout;
    private MVerticalLayout suiviEnvoisLayout;
    private MVerticalLayout traitementDocumentsLayout;

    @SubComponentInit
    public void init() {

        DemandeDocumentDto demandeDocumentDto = demandeDocumentWebService.rechercherDemandeDocumentParId(idDemandeDocument);

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
            // setTableauDocumentsItemsList(List<DocumentForm> documents);

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
        boutonAnnuler = new MButton("Annuler")
                        .withStyleName(ValoTheme.BUTTON_PRIMARY)
                        .withIcon(FontAwesome.TIMES)
                        .withListener(new ClickListener() {
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void buttonClick(ClickEvent event) {
                            }
                        });
    }

    /** Initialise le bouton "Enregistrer". */
    private void initEnregistrer() {
        boutonEnregistrer = new MButton("Enregistrer")
                        .withStyleName(ValoTheme.BUTTON_PRIMARY)
                        .withIcon(FontAwesome.SAVE)
                        .withListener(new ClickListener() {
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void buttonClick(ClickEvent event) {

                                DemandeDocumentDto demandeDocumentDto = new DemandeDocumentDto();
                                // Recuperer data form vers dto
                                demandeDocumentDto = demandeDocumentWebService.enregistrerDemandeDocument(demandeDocumentDto);

                                // Retour à la recherche
                            }
                        });
    }

    /** Initialise le bouton "Modifier". */
    private void initModifier() {
        boutonModifier = new MButton("Modifier")
                        .withStyleName(ValoTheme.BUTTON_PRIMARY)
                        .withIcon(FontAwesome.PENCIL)
                        .withListener(new ClickListener() {
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void buttonClick(ClickEvent event) {

                            }
                        });
    }

    /** Initialise le bouton "Accéder au dossier d'origine Notes". */
    private void initAccederDossierNotes() {
        boutonAccederDossierNotes = new MButton("Accéder au dossier d'origine Notes")
                        .withStyleName(ValoTheme.BUTTON_PRIMARY)
                        .withIcon(FontAwesome.FOLDER_OPEN)
                        .withListener(new ClickListener() {
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
        for (NiveauIncidentEnum niveauIncidentEnum : NiveauIncidentEnum.values()) {
            niveauIncident.addItem(niveauIncidentEnum);
            niveauIncident.setItemCaption(niveauIncidentEnum, niveauIncidentEnum.getLibelle());
        }
    }

    /** Initialise le champ "Commentaire". */
    private void initCommentaire() {
        commentaire = new MTextArea("Commentaire : ").withFullWidth();
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
        MVerticalLayout verticalLayoutLeft = new MVerticalLayout(numeroDossier, motifDemande, trigrammeDemandeur)
                        .withMargin(true)
                        .withSpacing(true);

        MHorizontalLayout horizontalLayoutTop = new MHorizontalLayout(verticalLayoutLeft, etatDossier)
                        .withFullWidth()
                        .withMargin(true)
                        .withSpacing(true)
                        .withAlign(etatDossier, Alignment.MIDDLE_LEFT);

        MVerticalLayout verticalLayoutLeft2 = new MVerticalLayout(beneficiaire, organismeBeneficiaire, numeroContratBeneficiaire, nomBeneficiaire, prenomBeneficiaire)
                        .withMargin(true)
                        .withSpacing(true)
                        .withStyleName(ValoTheme.LAYOUT_WELL);
        Panel beneficiairePanel = new Panel(verticalLayoutLeft2);

        MVerticalLayout verticalLayoutRight2 = new MVerticalLayout(ps, nomMagasinPS, enseignePS, numeroTelephonePS, codePostalPS, communePS)
                        .withMargin(true)
                        .withSpacing(true)
                        .withStyleName(ValoTheme.LAYOUT_WELL);
        Panel psPanel = new Panel(verticalLayoutRight2);

        MHorizontalLayout horizontalLayoutBottom = new MHorizontalLayout(beneficiairePanel, psPanel)
                        .withFullWidth()
                        .withMargin(true)
                        .withSpacing(true)
                        .withAlign(beneficiairePanel, Alignment.MIDDLE_LEFT)
                        .withAlign(psPanel, Alignment.MIDDLE_LEFT);

        descriptifDemandeLayout = new MVerticalLayout(horizontalLayoutTop, horizontalLayoutBottom)
                        .withFullWidth()
                        .withMargin(true);
        Panel descriptifDemandePanel = new Panel("Descriptif de la demande", descriptifDemandeLayout);

        suiviEnvoisLayout = new MVerticalLayout(tableauSuiviEnvois).withMargin(true);
        Panel suiviEnvoisPanel = new Panel("Suivi des envois", suiviEnvoisLayout);

        MHorizontalLayout boutonsLayout = new MHorizontalLayout(boutonModifier, boutonAnnuler)
                        .withFullWidth()
                        .withMargin(true)
                        .withSpacing(true)
                        .withAlign(boutonModifier, Alignment.MIDDLE_RIGHT);

        HorizontalLayout commentaireNiveauIncidentLayout = new MHorizontalLayout(commentaire, niveauIncident)
                        .withFullWidth()
                        .withMargin(true)
                        .withSpacing(true);
        commentaireNiveauIncidentLayout.setExpandRatio(commentaire, 2);
        commentaireNiveauIncidentLayout.setExpandRatio(niveauIncident, 1);

        traitementDocumentsLayout = new MVerticalLayout(boutonAccederDossierNotes, tableauDocuments, commentaireNiveauIncidentLayout, boutonsLayout)
                        .withFullWidth()
                        .withMargin(true)
                        .withSpacing(true);
        Panel traitementDocumentsPanel = new Panel("Traitements des documents", traitementDocumentsLayout);

        this.withFullWidth().withMargin(true).withSpacing(true).with(descriptifDemandePanel, suiviEnvoisPanel, traitementDocumentsPanel);
    }

    /** Initialise le menu deroulant "Résultat analyse" */
    private ComboBox initResultatAnalyse(final ComboBox detailResultatAnalyse, final TypeDocumentEnum typeDocument) {
        final ComboBox resultatAnalyse = new ComboBox();
        resultatAnalyse.setWidth(TAILLE_STANDARD + 1, Unit.EM);
        for (ResultatAnalyseEnum resultatAnalyseEnum : ResultatAnalyseEnum.values()) {
            resultatAnalyse.addItem(resultatAnalyseEnum);
            resultatAnalyse.setItemCaption(resultatAnalyseEnum, resultatAnalyseEnum.getLibelle());
        }
        resultatAnalyse.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                if (resultatAnalyse.getValue().equals(ResultatAnalyseEnum.NON_CONFORME)) {
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
        for (DetailResultatAnalyseEnum detailResultatAnalyseEnum : detailsResultatAnalyse) {
            detailResultatAnalyse.addItem(detailResultatAnalyseEnum);
            detailResultatAnalyse.setItemCaption(detailResultatAnalyseEnum, detailResultatAnalyseEnum.getLibelle());
        }
        return detailResultatAnalyse;
    }
}
