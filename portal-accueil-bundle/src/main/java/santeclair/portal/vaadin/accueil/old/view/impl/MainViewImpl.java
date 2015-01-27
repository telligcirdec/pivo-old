package santeclair.portal.vaadin.accueil.old.view.impl;

import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import santeclair.lunar.framework.util.DateUtils;
import santeclair.lunar.framework.web.security.UtilisateurSanteclair;
import santeclair.portal.vaadin.accueil.old.view.MainView;
import santeclair.portal.vaadin.deprecated.module.api.SecurityCheck;

import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
public class MainViewImpl extends VerticalLayout implements MainView {

    private static final String REPERTOIRE_IMAGE = "META-INF/resources/";
    private static final String IMAGE_KO = "laissez_moi_entrer.jpg";
    private static final String IMAGE_OK = "Printemps.jpg";

    private static final String MESSAGE_KO = "<center>Vous ne semblez pas avoir l'authorisation d'utiliser les applications du portail.<br>"
                    + "Si cela vous semble anormal, merci de demander � l'informatique les droits d'acc�s n�cessaires.</center>";
    private static final String MESSAGE_OK_BIENVENU_1 = "<center>Bienvenue ";
    private static final String MESSAGE_OK_BIENVENU_2 = " sur le portail Sant�clair.<br>Nous sommes le ";
    private static final String MESSAGE_OK_BIENVENU_3 = ".<br>Vos applications sont disponibles sur le bandeau � gauche.<br>Paix et prosp�rit�.<br><center><br/><br/>";
    private static final String MESSAGE_OK_SERVICE = "<center><b>Nouvelle application Courrierclair !</b><br/>"
                    + "Courrierclair est la nouvelle plateforme d'�change de courrier, de la cr�ation du document au suivi de son envoi.<br/>"
                    + "Cette premi�re version dispose de fonctionnalit�s limit�es mais le meilleur est encore � venir.</center>"
                    + "<center><b>Nouvelle version de Devis chirurgie !</b><br/>"
                    + "Int�gration de Courrierclair dans l'envoi de courrier aux patients.<br/>"
                    + "Possibilit� d'afficher un aper�u du courrier avant de l'envoyer.<br/>"
                    + "Correction de bugs divers pour une utilisation toujours plus fluide.</center>"
                    + "<center>Devis chir �a d�chir 100 fois plus !<center>";

    private static final long serialVersionUID = 4575884346659088279L;

    @PostConstruct
    public void init() {
    }

    @Override
    @PostConstruct
    public void initView() {
        Label titre = new Label("<h1><center>Bienvenue sur le portail</center></h1>", ContentMode.HTML);
        UtilisateurSanteclair userSanteclair = SecurityCheck.getUtilisateurSanteclair();
        boolean accesUser = userSanteclair != null;

        Image imageAccueil = createImageAccueil(accesUser);
        Label messageAccueil = createMessageAccueil(accesUser);

        initLayout(titre, imageAccueil, messageAccueil);
    }

    private Image createImageAccueil(boolean accesUser) {
        final String source = (accesUser ? IMAGE_OK : IMAGE_KO);

        final InputStream in = this.getClass().getClassLoader().getResourceAsStream(REPERTOIRE_IMAGE + source);

        // Logo sant�clair
        StreamSource santeclaireLogo = null;
        santeclaireLogo = new StreamSource() {
            /** Serial Version UID */
            private static final long serialVersionUID = 1L;

            @Override
            public InputStream getStream() {
                return in;
            }
        };

        StreamResource resource = new StreamResource(santeclaireLogo, source);
        Image image = new Image(null, resource);
        if (!accesUser) {
            image.setAlternateText("S'il vous plait, laissez moi entrer...");
        }
        // image.setWidth(60, Unit.PERCENTAGE);
        image.setStyleName("image-logo");

        return image;
    }

    private Label createMessageAccueil(boolean accesUser) {
        if (accesUser) {
            StringBuilder messageOK = new StringBuilder(MESSAGE_OK_BIENVENU_1);
            messageOK.append(SecurityCheck.getUtilisateurSanteclair().getPrenom());
            messageOK.append(" ");
            messageOK.append(SecurityCheck.getUtilisateurSanteclair().getNom());
            messageOK.append(MESSAGE_OK_BIENVENU_2);
            messageOK.append(DateUtils.getDateDuJourFormattee());
            messageOK.append(MESSAGE_OK_BIENVENU_3);
            messageOK.append(MESSAGE_OK_SERVICE);
            return new Label(messageOK.toString(), ContentMode.HTML);
        } else {
            return new Label(MESSAGE_KO, ContentMode.HTML);
        }
    }

    private void initLayout(Label titre, Image imageAccueil, Label messageAccueil) {
        this.setMargin(true);
        this.setSpacing(true);
        this.addComponent(titre);
        this.addComponent(imageAccueil);
        this.setComponentAlignment(imageAccueil, Alignment.TOP_CENTER);
        this.addComponent(messageAccueil);
    }

    @Override
    public ComponentContainer getViewRoot() {
        return this;
    }
}
