package santeclair.portal.reclamation.demande.document.recherche.component.sub;

import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID;
import static santeclair.portal.event.EventDictionaryConstant.PROPERTY_KEY_TAB_HASH;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.button.PrimaryButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import santeclair.portal.event.EventDictionaryConstant;
import santeclair.portal.reclamation.demande.document.recherche.form.RechercheForm;
import santeclair.portal.utils.component.SubComponent;
import santeclair.portal.utils.component.SubComponentInit;
import santeclair.portal.utils.component.SubComponentInitProperty;
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

    private String sessionId;
    private Integer tabHash;

    private static final long serialVersionUID = 8369775167208351407L;

    private static final float TAILLE_STANDARD = 12;

    private static final String MSG_ERROR = "Veuillez saisir au moins un crit�re de recherche.";

    @Publishes(name = "formComponentPublisher", topics = "RechercheDemandeDocument")
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
    public void init(@SubComponentInitProperty(name = PROPERTY_KEY_PORTAL_SESSION_ID) String sessionId, @SubComponentInitProperty(name = PROPERTY_KEY_TAB_HASH) Integer tabHash) {

        this.sessionId = sessionId;
        this.tabHash = tabHash;

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

    /** Initialise le champ nom du b�n�ficiaire. */
    private void initNomBeneficiaire() {
        nomBeneficiaire = new MTextField("Nom du b�n�ficiaire : ").withWidth(TAILLE_STANDARD, Unit.EM).withNullRepresentation("");
    }

    /** Initialise le champ pr�nom du b�n�ficiaire. */
    private void initPrenomBeneficiaire() {
        prenomBeneficiaire = new MTextField("Pr�nom du b�n�ficiaire : ").withWidth(TAILLE_STANDARD, Unit.EM).withNullRepresentation("");
    }

    /** Initialise le champ t�l�phone du PS. */
    private void initTelephonePS() {
        telephonePS = new MTextField("T�l�phone du PS : ").withWidth(TAILLE_STANDARD, Unit.EM).withNullRepresentation("");
    }

    /** Initialise le champ num�ro de dossier. */
    private void initNumeroDossier() {
        numeroDossier = new MTextField("Num�ro de dossier : ").withWidth(TAILLE_STANDARD, Unit.EM).withNullRepresentation("");
    }

    /** Initialise le champ trigramme demandeur. */
    private void initTrigrammeDemandeur() {
        trigrammeDemandeur = new MTextField("Trigramme demandeur : ").withWidth(TAILLE_STANDARD, Unit.EM).withNullRepresentation("");
    }

    /** Initialise le champ date de d�but. */
    private void initDateDebut() {
        dateDebut = new DateField("P�riode de cr�ation du : ");
    }

    /** Initialise le champ date de fin. */
    private void initDateFin() {
        dateFin = new DateField(" au : ");
    }

    /** Initialise le menu deroulant "Etat du dossier" */
    private void initEtatDossier() {
        etatDossier = new ComboBox("Etat du dossier : ");
        etatDossier.setWidth(TAILLE_STANDARD + 1, Unit.EM);
        for (EtatDemandeEnum etatDemandeEnum : EtatDemandeEnum.values()) {
            etatDossier.addItem(etatDemandeEnum);
            etatDossier.setItemCaption(etatDemandeEnum, etatDemandeEnum.getLibelle());
        }
    }

    /** Initialise le bouton rechercher. */
    private void initRechercher() {
        boutonRechercher = new PrimaryButton("Rechercher").withIcon(FontAwesome.SEARCH).withStyleName(ValoTheme.BUTTON_PRIMARY);
        boutonRechercher.addClickListener(new ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                ValidationNotification validation = controleDonnees();
              if (validation.isError()) {
                  validation.show();
              } else {
                  Dictionary<String, Object> props = new Hashtable<>();
                  props.put(EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME, "rechercherDemandeDocument");
                  props.put("sessionId", sessionId);
                  props.put("tabHash", tabHash);
                  props.put("form", form);
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

        MFormLayout formLayoutRight = new MFormLayout(boutonRechercher);

        MHorizontalLayout horizontalLayout = new MHorizontalLayout(formLayoutLeft, formLayoutCenter, formLayoutRight)
                        .withAlign(formLayoutRight, Alignment.MIDDLE_CENTER)
                        .withFullWidth();

        this.setCaption("Recherche de demandes de document");
        this.setContent(new MVerticalLayout().withFullWidth().withMargin(true).with(horizontalLayout));

    }

    private void initForm() {
        form = new RechercheForm();
        BeanItem<RechercheForm> beanItem = new BeanItem<RechercheForm>(form);
        FieldGroup binder = new FieldGroup(beanItem);
        binder.setBuffered(false);
        binder.bindMemberFields(this);
    }

    /** Controle les donn�es du formulaire de recherche. */
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
            result.addMessage(MSG_ERROR);
        }
        return result;
    }

//    @Override
//    public void rechercheSuccessfull(List<DemandeDocumentDto> listeDemandesDocumentDto) {
//        Dictionary<String, Object> props = new Hashtable<>();
//        props.put(EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME, "rechercheOk");
//        props.put(EventDictionaryConstant.PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
//        props.put(EventDictionaryConstant.PROPERTY_KEY_TAB_HASH, tabHash);
//        props.put("listeDemandesDocumentDto", listeDemandesDocumentDto);
//        formComponentPublisher.send(props);
//    }
//
//    @Override
//    public void rechercheFailed(String message) {
//
//    }

//    private class FormRechercherClickListener implements ClickListener {
//
//        private static final long serialVersionUID = -894998593011734769L;
//        
//        private final FormComponentCallback formComponentCallback;
//
//        public FormRechercherClickListener(FormComponentCallback formComponentCallback) {
//            this.formComponentCallback = formComponentCallback;
//        }
//
//        @Override
//        public void buttonClick(ClickEvent event) {
//            ValidationNotification validation = controleDonnees();
//            if (validation.isError()) {
//                validation.show();
//            } else {
//                Dictionary<String, Object> props = new Hashtable<>();
//                props.put(EventDictionaryConstant.PROPERTY_KEY_EVENT_NAME, "rechercherDemandeDocument");
//                props.put("form", form);
//                formComponentPublisher.send(props);
//            }
//        }
//
//    }

}