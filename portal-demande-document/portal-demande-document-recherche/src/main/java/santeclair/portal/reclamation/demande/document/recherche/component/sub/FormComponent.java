package santeclair.portal.reclamation.demande.document.recherche.component.sub;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.button.PrimaryButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import santeclair.portal.reclamation.demande.document.recherche.EventConstant;
import santeclair.portal.reclamation.demande.document.recherche.form.RechercheForm;
import santeclair.portal.utils.component.SubComponent;
import santeclair.portal.utils.component.SubComponentInit;
import santeclair.portal.utils.notification.ValidationNotification;
import santeclair.reclamation.demande.document.enumeration.EtatDemandeEnum;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;

@SubComponent
@Component
@Provides(specifications = {FormComponent.class})
public class FormComponent extends Panel {

    @Property(name = PROPERTY_KEY_PORTAL_SESSION_ID) 
    private String sessionId;
    @Property(name = PROPERTY_KEY_TAB_HASH) 
    private Integer tabHash;

    private static final long serialVersionUID = 8369775167208351407L;

    private static final float TAILLE_STANDARD = 14;

    private static final String MSG_ERROR_SAISIE = "Vous devez renseigner au moins un critère pour effectuer une recherche";
    private static final String MSG_ERROR_SAISIE_NOM = "Le nom du bénéficiaire doit contenir au moins 3 caractères";
    private static final String MSG_ERROR_SAISIE_PRENOM = "Le prénom du bénéficiaire doit contenir au moins 3 caractères";
    private static final String MSG_ERROR_SAISIE_TELEPHONE = "Le téléphone PS doit contenir 10 caractères numérique";

    @Publishes(name = "formComponentPublisher", topics = EventConstant.TOPIC_RECHERCHE_DEMANDE_DOCUMENT, synchronous = true)
    private Publisher formComponentPublisher;

    private MTextField nomBeneficiaire;
    private MTextField prenomBeneficiaire;
    private MTextField telephonePS;
    private MTextField numeroDossier;
    private MTextField trigrammeDemandeur;
    private DateField dateDebut;
    private DateField dateFin;
    private ComboBox etatDossier;
    private MButton boutonRechercher;
    private RechercheForm form;

    @SubComponentInit
    public void init() {

        initDateDebut();
        initDateFin();
        initEtatDossier();
        initNomBeneficiaire();
        initNumeroDossier();
        initTelephonePS();
        initPrenomBeneficiaire();
        initRechercher();
        initTrigrammeDemandeur();
        initFocusOrder();
        initLayout();
        initForm();
    }

    /** Initialise le champ nom du bénéficiaire. */
    private void initNomBeneficiaire() {
        nomBeneficiaire = new MTextField("Nom du bénéficiaire : ").withWidth(TAILLE_STANDARD, Unit.EM).withNullRepresentation("");
    }

    /** Initialise le champ prénom du bénéficiaire. */
    private void initPrenomBeneficiaire() {
        prenomBeneficiaire = new MTextField("Prénom du bénéficiaire : ").withWidth(TAILLE_STANDARD, Unit.EM).withNullRepresentation("");
    }

    /** Initialise le champ téléphone du PS. */
    private void initTelephonePS() {
        telephonePS = new MTextField("Téléphone du PS : ").withWidth(TAILLE_STANDARD, Unit.EM).withNullRepresentation("");
    }

    /** Initialise le champ numéro de dossier. */
    private void initNumeroDossier() {
        numeroDossier = new MTextField("Numéro de dossier : ").withWidth(TAILLE_STANDARD, Unit.EM).withNullRepresentation("");
    }

    /** Initialise le champ trigramme demandeur. */
    private void initTrigrammeDemandeur() {
        trigrammeDemandeur = new MTextField("Trigramme demandeur : ").withWidth(TAILLE_STANDARD, Unit.EM).withNullRepresentation("");
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
        etatDossier.setWidth(TAILLE_STANDARD, Unit.EM);
        for (EtatDemandeEnum etatDemandeEnum : EtatDemandeEnum.values()) {
            etatDossier.addItem(etatDemandeEnum);
            etatDossier.setItemCaption(etatDemandeEnum, etatDemandeEnum.getLibelle());
        }
    }

    /** Initialise le bouton rechercher. */
    private void initRechercher() {
        boutonRechercher = new PrimaryButton("Rechercher")
                        .withIcon(FontAwesome.SEARCH)
                        .withStyleName(ValoTheme.BUTTON_PRIMARY)
                        .withListener(new ClickListener() {

                            private static final long serialVersionUID = 1L;

                            @Override
                            public void buttonClick(ClickEvent event) {
                                ValidationNotification validation = controleDonnees();
                                if (validation.isError()) {
                                    validation.show();
                                } else {
                                    Dictionary<String, Object> props = new Hashtable<>();
                                    props.put(PROPERTY_KEY_EVENT_NAME, EventConstant.EVENT_RECHERCHER_DEMANDE_DOCUMENT);
                                    props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
                                    props.put(PROPERTY_KEY_TAB_HASH, tabHash);
                                    props.put(EventConstant.PROPERTY_KEY_FORM, form);
                                    formComponentPublisher.send(props);
                                }
                            }
                        });
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

        MFormLayout formLayoutLeft = new MFormLayout(nomBeneficiaire, telephonePS, trigrammeDemandeur, dateDebut);

        MFormLayout formLayoutCenter = new MFormLayout(prenomBeneficiaire, numeroDossier, etatDossier, dateFin);

        MVerticalLayout verticalLayoutRight = new MVerticalLayout(boutonRechercher)
        .withAlign(boutonRechercher, Alignment.MIDDLE_CENTER)
        ;

        MHorizontalLayout horizontalLayout = new MHorizontalLayout(formLayoutLeft, formLayoutCenter, verticalLayoutRight)
                        .withMargin(true)
                        .withFullWidth()
                        .withAlign(formLayoutLeft, Alignment.MIDDLE_CENTER)
                        .withAlign(formLayoutCenter, Alignment.MIDDLE_CENTER)
                        .withAlign(verticalLayoutRight, Alignment.MIDDLE_CENTER);

        this.setCaption("Recherche de demandes de document");
        this.setContent(new MVerticalLayout(horizontalLayout).withFullWidth().withMargin(true));

    }

    private void initForm() {
        form = new RechercheForm();
        BeanItem<RechercheForm> beanItem = new BeanItem<RechercheForm>(form);
        FieldGroup binder = new FieldGroup(beanItem);
        binder.setBuffered(false);
        binder.bindMemberFields(this);
    }

    /** Controle les données du formulaire de recherche. */
    private ValidationNotification controleDonnees() {
        ValidationNotification result = new ValidationNotification();

        if (StringUtils.isBlank(form.getNomBeneficiaire())
                        && StringUtils.isBlank(form.getPrenomBeneficiaire())
                        && StringUtils.isBlank(form.getNumeroDossier())
                        && StringUtils.isBlank(form.getTelephonePS())
                        && StringUtils.isBlank(form.getTrigrammeDemandeur())
                        && null == form.getDateDebut()
                        && null == form.getDateFin()
                        && null == form.getEtatDossier()) {
            result.addMessage(MSG_ERROR_SAISIE);
        } else {
            if (!StringUtils.isBlank(form.getNomBeneficiaire()) && form.getNomBeneficiaire().length() <= 3) {
                result.addMessage(MSG_ERROR_SAISIE_NOM);
            }
            if (!StringUtils.isBlank(form.getPrenomBeneficiaire()) && form.getPrenomBeneficiaire().length() <= 3) {
                result.addMessage(MSG_ERROR_SAISIE_PRENOM);
            }
            if (!StringUtils.isBlank(form.getTelephonePS()) && (!StringUtils.isNumeric(form.getTelephonePS()) || form.getTelephonePS().length() != 10)) {
                result.addMessage(MSG_ERROR_SAISIE_TELEPHONE);
            }
            
        }
        return result;
    }
}
