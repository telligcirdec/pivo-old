package santeclair.portal.reclamation.demande.document.detail.component.sub;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;
import static santeclair.portal.event.EventDictionaryConstant.TOPIC_TABS;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextArea;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import santeclair.portal.event.utils.TabsEventUtil;
import santeclair.portal.reclamation.demande.document.detail.EventConstant;
import santeclair.portal.utils.component.SubComponent;
import santeclair.portal.utils.component.SubComponentInit;
import santeclair.portal.utils.notification.ValidationNotification;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;
import santeclair.reclamation.demande.document.dto.DocumentDto;
import santeclair.reclamation.demande.document.enumeration.BooleanEnum;
import santeclair.reclamation.demande.document.enumeration.DetailResultatAnalyseEnum;
import santeclair.reclamation.demande.document.enumeration.EtatDemandeEnum;
import santeclair.reclamation.demande.document.enumeration.NiveauIncidentEnum;
import santeclair.reclamation.demande.document.enumeration.ResultatAnalyseEnum;
import santeclair.reclamation.demande.document.enumeration.TypeDocumentEnum;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.themes.ValoTheme;

@SubComponent
@Component
@Provides(specifications = {TraitementDocumentsComponent.class})
public class TraitementDocumentsComponent extends Panel {

    private static final long serialVersionUID = 8369775167208351407L;

    private static final float TAILLE_XSMALL = 6;
    private static final float TAILLE_SMALL = 8;
    private static final float TAILLE_MIDDLE = 14;
    private static final float TAILLE_LARGE = 22;

    @Property(name = PROPERTY_KEY_PORTAL_SESSION_ID)
    private String sessionId;
    @Property(name = PROPERTY_KEY_TAB_HASH)
    private Integer tabHash;

    @Publishes(name = "tabsComponentPublisher", topics = TOPIC_TABS, synchronous = true)
    private Publisher tabsComponentPublisher;

    @Publishes(name = "demandeDocumentComponentPublisher", topics = EventConstant.TOPIC_DEMANDE_DOCUMENT, synchronous = true)
    private Publisher demandeDocumentComponentPublisher;

    private MTextArea commentaire;
    private ComboBox niveauIncident;

    private MButton boutonAnnuler;
    private MButton boutonAccederDossierNotes;
    private MButton boutonEnregistrerFermer;

    private Table tableauDocuments;

    private MVerticalLayout traitementDocumentsLayout;

    private DemandeDocumentDto demandeDocumentDto;

    @SubComponentInit
    public void init() {

        initAnnuler();
        initEnregistrer();
        initAccederDossierNotes();

        initTableauDocuments();

        initNiveauIncident();
        initCommentaire();

        initLayout();
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
                                tabsComponentPublisher.send(TabsEventUtil.getCloseTabsProps(sessionId, tabHash));
                            }
                        });
    }

    /** Initialise le bouton "Enregistrer". */
    private void initEnregistrer() {
        boutonEnregistrerFermer = new MButton("Enregistrer et fermer la fiche")
                        .withStyleName(ValoTheme.BUTTON_PRIMARY)
                        .withIcon(FontAwesome.SAVE)
                        .withListener(new ClickListener() {
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void buttonClick(ClickEvent event) {

                                ValidationNotification validation = controlAndUpdateDemandeDocumentDto();
                                if (validation.isError()) {
                                    validation.show();
                                } else {
                                    Dictionary<String, Object> props = new Hashtable<>();
                                    props.put(PROPERTY_KEY_EVENT_NAME, EventConstant.EVENT_ENREGISTRER_DEMANDE_DOCUMENT);
                                    props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
                                    props.put(PROPERTY_KEY_TAB_HASH, tabHash);
                                    props.put(EventConstant.PROPERTY_KEY_DEMANDE_DOCUMENT, demandeDocumentDto);
                                    demandeDocumentComponentPublisher.send(props);
                                }
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
        niveauIncident.setWidth(TAILLE_MIDDLE, Unit.EM);
        for (NiveauIncidentEnum niveauIncidentEnum : NiveauIncidentEnum.values()) {
            niveauIncident.addItem(niveauIncidentEnum);
            niveauIncident.setItemCaption(niveauIncidentEnum, niveauIncidentEnum.getCode());
        }
        niveauIncident.setVisible(false);
    }

    /** Initialise le champ "Commentaire". */
    private void initCommentaire() {
        commentaire = new MTextArea("Commentaire : ").withFullWidth();
    }

    /** Initialise le tableau de documents. */
    private void initTableauDocuments() {
        tableauDocuments = new Table();
        tableauDocuments.setPageLength(0);
        tableauDocuments.setSizeFull();
        tableauDocuments.setSortEnabled(false);

        tableauDocuments.addContainerProperty("typeDocument", String.class, null);
        tableauDocuments.setColumnHeader("typeDocument", "Type de justificatif");
        tableauDocuments.setColumnExpandRatio("typeDocument", 3);
        tableauDocuments.addContainerProperty("documentRecu", ComboBox.class, null);
        tableauDocuments.setColumnHeader("documentRecu", "Document reçu");
        tableauDocuments.setColumnAlignment("documentRecu", Align.CENTER);
        tableauDocuments.setColumnExpandRatio("documentRecu", 2);
        tableauDocuments.addContainerProperty("originalDemande", ComboBox.class, null);
        tableauDocuments.setColumnHeader("originalDemande", "Original demandé");
        tableauDocuments.setColumnAlignment("originalDemande", Align.CENTER);
        tableauDocuments.setColumnExpandRatio("originalDemande", 2);
        tableauDocuments.addContainerProperty("dateReception", DateField.class, null);
        tableauDocuments.setColumnHeader("dateReception", "Date de réception");
        tableauDocuments.setColumnAlignment("dateReception", Align.CENTER);
        tableauDocuments.setColumnExpandRatio("dateReception", 2);
        tableauDocuments.addContainerProperty("resultatAnalyse", ComboBox.class, null);
        tableauDocuments.setColumnHeader("resultatAnalyse", "Résultat analyse");
        tableauDocuments.setColumnAlignment("resultatAnalyse", Align.CENTER);
        tableauDocuments.setColumnExpandRatio("resultatAnalyse", 4);
        tableauDocuments.addContainerProperty("detailResultatAnalyse", ComboBox.class, null);
        tableauDocuments.setColumnHeader("detailResultatAnalyse", "Choisissez");
        tableauDocuments.setColumnAlignment("detailResultatAnalyse", Align.CENTER);
        tableauDocuments.setColumnExpandRatio("detailResultatAnalyse", 6);
    }

    /** Initialise la vue principale. */
    private void initLayout() {
        MHorizontalLayout boutonsLayout = new MHorizontalLayout(boutonEnregistrerFermer, boutonAnnuler)
                        .withFullWidth()
                        .withMargin(true)
                        .withSpacing(true)
                        .withAlign(boutonEnregistrerFermer, Alignment.MIDDLE_RIGHT);

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

        this.setCaption("Traitements des documents");
        this.setContent(traitementDocumentsLayout);
    }

    /** Initialise le menu deroulant "Résultat analyse" */
    private ComboBox initResultatAnalyse(final ComboBox detailResultatAnalyse, final TypeDocumentEnum typeDocument) {
        final ComboBox resultatAnalyse = new ComboBox();
        resultatAnalyse.setWidth(TAILLE_MIDDLE, Unit.EM);
        for (ResultatAnalyseEnum resultatAnalyseEnum : ResultatAnalyseEnum.values()) {
            resultatAnalyse.addItem(resultatAnalyseEnum);
            resultatAnalyse.setItemCaption(resultatAnalyseEnum, resultatAnalyseEnum.getLibelle());
        }
        resultatAnalyse.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                resultatAnalyse.setComponentError(null);
                detailResultatAnalyse.setComponentError(null);
                detailResultatAnalyse.removeAllItems();
                if (null != resultatAnalyse.getValue() && resultatAnalyse.getValue().equals(ResultatAnalyseEnum.NON_CONFORME)) {
                    detailResultatAnalyse.removeStyleName("invisible");
                    initDetailResultatAnalyseValues(detailResultatAnalyse, typeDocument.getDetailsResultatAnalyseEnum());
                } else if (null != resultatAnalyse.getValue() && resultatAnalyse.getValue().equals(ResultatAnalyseEnum.ABANDON)) {
                    detailResultatAnalyse.removeStyleName("invisible");
                    initDetailResultatAnalyseValues(detailResultatAnalyse, ResultatAnalyseEnum.ABANDON.getDetailsResultatAnalyseEnum());
                } else {
                    detailResultatAnalyse.addStyleName("invisible");
                    detailResultatAnalyse.setValue(null);
                }

            }
        });
        return resultatAnalyse;
    }

    /** Initialise le menu deroulant "Détail résultat analyse" */
    private ComboBox initDetailResultatAnalyse() {
        final ComboBox detailResultatAnalyse = new ComboBox();
        detailResultatAnalyse.setWidth(TAILLE_LARGE, Unit.EM);
        detailResultatAnalyse.addStyleName("invisible");
        detailResultatAnalyse.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                detailResultatAnalyse.setComponentError(null);
            }
        });
        return detailResultatAnalyse;
    }

    /** Initialise le menu deroulant "Original demandé" */
    private ComboBox initOriginalDemande() {
        final ComboBox originalDemande = new ComboBox();
        originalDemande.setWidth(TAILLE_XSMALL, Unit.EM);
        for (BooleanEnum booleanEnum : BooleanEnum.values()) {
            originalDemande.addItem(booleanEnum);
            originalDemande.setItemCaption(booleanEnum, booleanEnum.getLibelle());
        }
        originalDemande.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                originalDemande.setComponentError(null);
            }
        });
        return originalDemande;
    }

    /** Initialise le menu deroulant "Document recu" */
    private ComboBox initDocumentRecu(final ComboBox resultatAnalyse) {
        final ComboBox documentRecu = new ComboBox();
        documentRecu.setWidth(TAILLE_XSMALL, Unit.EM);
        for (BooleanEnum booleanEnum : BooleanEnum.values()) {
            documentRecu.addItem(booleanEnum);
            documentRecu.setItemCaption(booleanEnum, booleanEnum.getLibelle());
        }
        documentRecu.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                documentRecu.setComponentError(null);
                if (null != documentRecu.getValue() && documentRecu.getValue().equals(BooleanEnum.NON)) {
                    resultatAnalyse.setValue(ResultatAnalyseEnum.ABANDON);
                }
            }
        });
        return documentRecu;
    }

    /** Initialise le champ "Date réception" */
    private DateField initDateReception() {
        final DateField dateReception = new DateField();
        dateReception.setWidth(TAILLE_SMALL, Unit.EM);
        dateReception.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                dateReception.setComponentError(null);
            }
        });
        return dateReception;
    }

    /** Initialise les valeurs du menu deroulant "Détail résultat analyse" */
    private void initDetailResultatAnalyseValues(ComboBox detailResultatAnalyse, List<DetailResultatAnalyseEnum> listDetailResultatAnalyseEnum) {
        for (DetailResultatAnalyseEnum detailResultatAnalyseEnum : listDetailResultatAnalyseEnum) {
            detailResultatAnalyse.addItem(detailResultatAnalyseEnum);
            detailResultatAnalyse.setItemCaption(detailResultatAnalyseEnum, detailResultatAnalyseEnum.getLibelle());
        }
    }

    @Subscriber(name = EventConstant.EVENT_RECUPERER_DEMANDE_DOCUMENT_OK, filter = "(&(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*)(" + PROPERTY_KEY_TAB_HASH + "=*)("
                    + PROPERTY_KEY_EVENT_NAME + "=" + EventConstant.EVENT_RECUPERER_DEMANDE_DOCUMENT_OK + ")(" + EventConstant.PROPERTY_KEY_DEMANDE_DOCUMENT + "=*))",
                    topics = EventConstant.TOPIC_DEMANDE_DOCUMENT)
    private void setValueOfDemandeDocumentDto(org.osgi.service.event.Event event) {
        demandeDocumentDto = (DemandeDocumentDto) event.getProperty(EventConstant.PROPERTY_KEY_DEMANDE_DOCUMENT);

        commentaire.setValue(demandeDocumentDto.getCommentaire());
        if (demandeDocumentDto.getEtat().equals(EtatDemandeEnum.RECEPTION_COMPLETE) || demandeDocumentDto.getEtat().equals(EtatDemandeEnum.DOSSIER_ANALYSE)) {
            niveauIncident.setVisible(true);
            niveauIncident.setValue(demandeDocumentDto.getNiveauIncident());
        } else {
            niveauIncident.setValue(null);
        }

        Collections.sort(demandeDocumentDto.getDocumentsDto());

        for (DocumentDto documentDto : demandeDocumentDto.getDocumentsDto()) {
            ComboBox detailResultatAnalyse = initDetailResultatAnalyse();
            ComboBox resultatAnalyse = initResultatAnalyse(detailResultatAnalyse, documentDto.getTypeDocument());

            resultatAnalyse.setValue(documentDto.getResultatAnalyse());
            if (null != documentDto.getResultatAnalyse()) {
                if (documentDto.getResultatAnalyse().equals(ResultatAnalyseEnum.ABANDON)) {
                    initDetailResultatAnalyseValues(detailResultatAnalyse, ResultatAnalyseEnum.ABANDON.getDetailsResultatAnalyseEnum());
                } else if (documentDto.getResultatAnalyse().equals(ResultatAnalyseEnum.NON_CONFORME)) {
                    initDetailResultatAnalyseValues(detailResultatAnalyse, documentDto.getTypeDocument().getDetailsResultatAnalyseEnum());
                }
                detailResultatAnalyse.setValue(documentDto.getDetailResultatAnalyse());
            }

            ComboBox originalDemande = initOriginalDemande();
            originalDemande.setValue(documentDto.getOriginalDemande() != null ? BooleanEnum.byCode(documentDto.getOriginalDemande().toString()) : null);

            ComboBox documentRecu = initDocumentRecu(resultatAnalyse);
            documentRecu.setValue(documentDto.getDocumentRecu() != null ? BooleanEnum.byCode(documentDto.getDocumentRecu().toString()) : null);

            DateField dateReception = initDateReception();
            dateReception.setValue(documentDto.getDateReception());

            tableauDocuments.addItem(
                            new Object[]{documentDto.getTypeDocument().getLibelle(), documentRecu, originalDemande, dateReception, resultatAnalyse, detailResultatAnalyse},
                            documentDto.getId());
        }
    }

    private ValidationNotification controlAndUpdateDemandeDocumentDto() {
        ValidationNotification result = new ValidationNotification();

        demandeDocumentDto.setCommentaire(commentaire.getValue());
        demandeDocumentDto.setNiveauIncident((NiveauIncidentEnum) niveauIncident.getValue());
        for (DocumentDto documentDto : demandeDocumentDto.getDocumentsDto()) {
            Item item = tableauDocuments.getItem(documentDto.getId());
            String typeDocument = (String) item.getItemProperty("typeDocument").getValue();
            ComboBox documentRecuCombo = (ComboBox) item.getItemProperty("documentRecu").getValue();
            ComboBox originalDemandeCombo = (ComboBox) item.getItemProperty("originalDemande").getValue();
            ComboBox resultatAnalyseCombo = (ComboBox) item.getItemProperty("resultatAnalyse").getValue();
            ComboBox detailResultatAnalyseCombo = (ComboBox) item.getItemProperty("detailResultatAnalyse").getValue();
            DateField dateReceptionField = (DateField) item.getItemProperty("dateReception").getValue();

            result = controlDonnees(result, documentRecuCombo, originalDemandeCombo, resultatAnalyseCombo, detailResultatAnalyseCombo, dateReceptionField, typeDocument);

            documentDto.setDateReception(dateReceptionField.getValue());
            if (null != documentRecuCombo.getValue()) {
                documentDto.setDocumentRecu(Boolean.parseBoolean(((BooleanEnum) documentRecuCombo.getValue()).getCode()));
            }
            if (null != originalDemandeCombo.getValue()) {
                documentDto.setOriginalDemande(Boolean.parseBoolean(((BooleanEnum) originalDemandeCombo.getValue()).getCode()));
            }
            documentDto.setResultatAnalyse(((ResultatAnalyseEnum) resultatAnalyseCombo.getValue()));
            documentDto.setDetailResultatAnalyse(((DetailResultatAnalyseEnum) detailResultatAnalyseCombo.getValue()));

        }
        return result;
    }

    private ValidationNotification controlDonnees(ValidationNotification result, ComboBox documentRecuCombo, ComboBox originalDemandeCombo, ComboBox resultatAnalyseCombo,
                    ComboBox detailResultatAnalyseCombo, DateField dateReceptionField, String typeDocument) {
        if ((null != documentRecuCombo.getValue()
                        || null != originalDemandeCombo.getValue()
                        || null != resultatAnalyseCombo.getValue()
                        || null != detailResultatAnalyseCombo.getValue()
                        || null != dateReceptionField.getValue())
                        && (null == documentRecuCombo.getValue()
                                        || null == originalDemandeCombo.getValue()
                                        || null == resultatAnalyseCombo.getValue()
                                        || null == detailResultatAnalyseCombo.getValue()
                                        || null == dateReceptionField.getValue())) {
            boolean noValid = false;
            if (null == originalDemandeCombo.getValue()) {
                originalDemandeCombo.setComponentError(new UserError("Veuillez indiquer une valeur"));
                noValid = true;
            }
            if (null == documentRecuCombo.getValue()) {
                documentRecuCombo.setComponentError(new UserError("Veuillez indiquer une valeur"));
                noValid = true;
            }
            if (null == resultatAnalyseCombo.getValue()) {
                resultatAnalyseCombo.setComponentError(new UserError("Veuillez indiquer une valeur"));
                noValid = true;
            }
            if (null != resultatAnalyseCombo.getValue()) {
                ResultatAnalyseEnum resultatAnalyse = (ResultatAnalyseEnum) resultatAnalyseCombo.getValue();
                if (resultatAnalyse.equals(ResultatAnalyseEnum.ABANDON) || resultatAnalyse.equals(ResultatAnalyseEnum.NON_CONFORME)
                                && null == detailResultatAnalyseCombo.getValue()) {
                    detailResultatAnalyseCombo.setComponentError(new UserError("Veuillez indiquer une valeur"));
                    noValid = true;
                }
            }

            if (null == dateReceptionField.getValue()) {
                dateReceptionField.setComponentError(new UserError("Veuillez indiquer une valeur"));
                noValid = true;
            }
            if (noValid) {
                result.addMessage("Veuillez remplir tous les champs en rouge du document '" + typeDocument + "' avant d'enregistrer.");
            }
        }
        return result;
    }
}
