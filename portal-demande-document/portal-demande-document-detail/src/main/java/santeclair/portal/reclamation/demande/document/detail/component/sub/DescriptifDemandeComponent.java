package santeclair.portal.reclamation.demande.document.detail.component.sub;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import santeclair.portal.reclamation.demande.document.detail.EventConstant;
import santeclair.portal.utils.component.SubComponent;
import santeclair.portal.utils.component.SubComponentInit;
import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;

@SubComponent
@Component
@Provides(specifications = {DescriptifDemandeComponent.class})
public class DescriptifDemandeComponent extends Panel {

    private static final long serialVersionUID = 8369775167208351407L;

    private static final float TAILLE_STANDARD = 24;

    @Property(name = PROPERTY_KEY_PORTAL_SESSION_ID)
    private String sessionId;
    @Property(name = PROPERTY_KEY_TAB_HASH)
    private Integer tabHash;

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

    private MVerticalLayout descriptifDemandeLayout;

    @SubComponentInit
    public void init() {

        initBeneficiaire();
        initPS();
        initNumeroDossier();
        initMotifDemande();
        initTrigrammeDemandeur();
        initOrganismeBeneficiaire();
        initNumeroContratBeneficiaire();
        initNomBeneficiaire();
        initPrenomBeneficiaire();
        initNomMagasinPS();
        initEnseigne();
        initTelephonePS();
        initCodePostalPS();
        initCommunePS();
        initEtatDossier();

        initLayout();
    }

    /** Initialise le label "Bénéficiaire". */
    private void initBeneficiaire() {
        beneficiaire = new Label("Bénéficiaire", ContentMode.HTML);
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
    private void initNomBeneficiaire() {
        nomBeneficiaire = new Label("<u>Nom du bénéficiaire</u> : ", ContentMode.HTML);
        nomBeneficiaire.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Prénom du bénéficiaire". */
    private void initPrenomBeneficiaire() {
        prenomBeneficiaire = new Label("<u>Prénom du bénéficiaire</u> : ", ContentMode.HTML);
        prenomBeneficiaire.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Organisme du bénéficiaire". */
    private void initOrganismeBeneficiaire() {
        organismeBeneficiaire = new Label("<u>Organisme</u> : ", ContentMode.HTML);
        organismeBeneficiaire.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Numéro de contrat du bénéficiaire". */
    private void initNumeroContratBeneficiaire() {
        numeroContratBeneficiaire = new Label("<u>Numéro de contrat</u> : ", ContentMode.HTML);
        numeroContratBeneficiaire.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Numéro de dossier". */
    private void initNumeroDossier() {
        numeroDossier = new Label("<u>Numéro de dossier</u> : ", ContentMode.HTML);
        numeroDossier.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Motif de la demande". */
    private void initMotifDemande() {
        motifDemande = new Label("<u>Motif de la demande</u> : ", ContentMode.HTML);
        motifDemande.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Trigramme demandeur". */
    private void initTrigrammeDemandeur() {
        trigrammeDemandeur = new Label("<u>Trigramme demandeur</u> : ", ContentMode.HTML);
        trigrammeDemandeur.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Etat du dossier" */
    private void initEtatDossier() {
        etatDossier = new Label("<u>Etat du dossier</u> : ", ContentMode.HTML);
        etatDossier.setWidth(TAILLE_STANDARD + 1, Unit.EM);
    }

    /** Initialise le champ "Nom du magasin". */
    private void initNomMagasinPS() {
        nomMagasinPS = new Label("<u>Nom du magasin</u> : ", ContentMode.HTML);
        nomMagasinPS.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Enseigne". */
    private void initEnseigne() {
        enseignePS = new Label("<u>Enseigne</u> : ", ContentMode.HTML);
        enseignePS.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Téléphone". */
    private void initTelephonePS() {
        numeroTelephonePS = new Label("<u>Téléphone</u> : ", ContentMode.HTML);
        numeroTelephonePS.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Code postal". */
    private void initCodePostalPS() {
        codePostalPS = new Label("<u>Code postal</u> : ", ContentMode.HTML);
        codePostalPS.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise le champ "Commune". */
    private void initCommunePS() {
        communePS = new Label("<u>Commune</u> : ", ContentMode.HTML);
        communePS.setWidth(TAILLE_STANDARD, Unit.EM);
    }

    /** Initialise la vue principale. */
    private void initLayout() {
        MVerticalLayout verticalLayoutDescriptifLeft = new MVerticalLayout(numeroDossier, motifDemande, trigrammeDemandeur)
                        .withSpacing(true);

        MVerticalLayout verticalLayoutDescriptifRight = new MVerticalLayout(etatDossier)
                        .withSpacing(true)
                        .withAlign(etatDossier, Alignment.MIDDLE_LEFT);

        MHorizontalLayout horizontalLayoutTop = new MHorizontalLayout(verticalLayoutDescriptifLeft, verticalLayoutDescriptifRight)
                        .withFullWidth()
                        .withSpacing(true)
                        .withStyleName(ValoTheme.LAYOUT_WELL)
                        .withAlign(verticalLayoutDescriptifRight, Alignment.MIDDLE_LEFT);

        MVerticalLayout verticalLayoutBeneficiaire = new MVerticalLayout(beneficiaire, organismeBeneficiaire, numeroContratBeneficiaire, nomBeneficiaire, prenomBeneficiaire)
                        .withSpacing(true)
                        .withMargin(true)
                        .withStyleName(ValoTheme.LAYOUT_WELL);

        MHorizontalLayout horizontalLayoutCodePostalCommune = new MHorizontalLayout(codePostalPS, communePS)
                        .withFullWidth();

        MVerticalLayout verticalLayoutPS = new MVerticalLayout(ps, nomMagasinPS, enseignePS, numeroTelephonePS, horizontalLayoutCodePostalCommune)
                        .withSpacing(true)
                        .withMargin(true)
                        .withStyleName(ValoTheme.LAYOUT_WELL);

        MHorizontalLayout horizontalLayoutBottom = new MHorizontalLayout(verticalLayoutBeneficiaire, verticalLayoutPS)
                        .withFullWidth()
                        .withSpacing(true);

        descriptifDemandeLayout = new MVerticalLayout(horizontalLayoutTop, horizontalLayoutBottom)
                        .withMargin(true)
                        .withFullWidth();

        this.setCaption("Descriptif de la demande");
        this.setContent(descriptifDemandeLayout);
    }

    @Subscriber(name = EventConstant.EVENT_RECUPERER_DEMANDE_DOCUMENT_OK, filter = "(&(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*)(" + PROPERTY_KEY_TAB_HASH + "=*)("
                    + PROPERTY_KEY_EVENT_NAME + "=" + EventConstant.EVENT_RECUPERER_DEMANDE_DOCUMENT_OK + ")(" + EventConstant.PROPERTY_KEY_DEMANDE_DOCUMENT + "=*))",
                    topics = EventConstant.TOPIC_DEMANDE_DOCUMENT)
    private void setValueOfDemandeDocumentDto(org.osgi.service.event.Event event) {
        DemandeDocumentDto demandeDocumentDto = (DemandeDocumentDto) event.getProperty(EventConstant.PROPERTY_KEY_DEMANDE_DOCUMENT);
        numeroDossier.setValue(numeroDossier.getValue() + StringUtils.defaultString(demandeDocumentDto.getNumeroDossier()));
        motifDemande.setValue(motifDemande.getValue() + StringUtils.defaultString(demandeDocumentDto.getMotifDemande().getLibelle()));
        trigrammeDemandeur.setValue(trigrammeDemandeur.getValue() + StringUtils.defaultString(demandeDocumentDto.getTrigrammeDemandeur()));
        etatDossier.setValue(etatDossier.getValue() + StringUtils.defaultString(demandeDocumentDto.getEtat().getLibelle()));

        organismeBeneficiaire.setValue(organismeBeneficiaire.getValue() + StringUtils.defaultString(demandeDocumentDto.getOrganismeBeneficiaire()));
        numeroContratBeneficiaire.setValue(numeroContratBeneficiaire.getValue() + StringUtils.defaultString(demandeDocumentDto.getNumeroContratBeneficiaire()));
        nomBeneficiaire.setValue(nomBeneficiaire.getValue() + StringUtils.defaultString(demandeDocumentDto.getNomBeneficiaire()));
        prenomBeneficiaire.setValue(prenomBeneficiaire.getValue() + StringUtils.defaultString(demandeDocumentDto.getPrenomBeneficiaire()));

        nomMagasinPS.setValue(nomMagasinPS.getValue() + StringUtils.defaultString(demandeDocumentDto.getNomMagasinPS()));
        enseignePS.setValue(enseignePS.getValue() + StringUtils.defaultString(demandeDocumentDto.getEnseignePS()));
        numeroTelephonePS.setValue(numeroTelephonePS.getValue() + StringUtils.defaultString(demandeDocumentDto.getTelephonePS()));
        codePostalPS.setValue(codePostalPS.getValue() + StringUtils.defaultString(demandeDocumentDto.getCodePostalPS()));
        communePS.setValue(communePS.getValue() + StringUtils.defaultString(demandeDocumentDto.getCommunePS()));

    }
}
