package santeclair.portal.bundle.notification;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * Notification personnalisé permettant de grouper facilement les messages d'erreur.
 * 
 * @author ldelemotte
 * 
 */
public class ValidationNotification {
    private static final String TITRE_PAR_DEFAUT = "Votre saisie comporte des erreurs";
    /** Liste de message d'erreur à afficher. */
    private final List<String> messages;

    /** Constructeur. */
    public ValidationNotification() {
        this.messages = new ArrayList<>();
    }

    /**
     * Ajoute un message d'erreur dans la liste à afficher.
     * 
     * @param message
     */
    public void addMessage(String message) {
        this.messages.add(message);
    }

    /**
     * Ajoute tous les messages d'un autre objet {@link ValidationNotification}
     * 
     * @param validation
     */
    public void addAllMessages(List<String> messages) {
        this.messages.addAll(messages);
    }

    /**
     * Ajoute tous les messages d'un autre objet {@link ValidationNotification}
     * 
     * @param validation
     */
    public void addAllMessages(ValidationNotification validation) {
        this.messages.addAll(validation.messages);
    }

    /**
     * Indique s'il y a des messages d'erreur à afficher
     * 
     * @return
     */
    public boolean isError() {
        return this.messages.size() > 0;
    }

    /**
     * Affiche les messages d'erreur stocké dans la liste puis vide cette liste.
     */
    public void show() {
        this.show(TITRE_PAR_DEFAUT);
    }

    /**
     * Affiche les messages d'erreur stocké dans la liste puis vide cette liste.
     * 
     * @param titre de l'encart de la notification
     */
    public void show(String titre) {
        this.show(titre, Type.WARNING_MESSAGE);
    }

    /**
     * Affiche les messages d'erreur stocké dans la liste puis vide cette liste.
     * 
     * @param titre de l'encart de la notification
     * @param type de notification
     */
    public void show(String titre, Type type) {
        if (this.messages.size() > 0) {
            StringBuilder sbMessage = new StringBuilder();
            sbMessage.append("<ul>");
            for (String message : this.messages) {
                sbMessage.append("<li>").append(message).append("</li>");
            }
            sbMessage.append("</ul>");
            Notification errors = new Notification(titre, sbMessage.toString(), type, true);
            errors.setDelayMsec(-1);
            errors.show(Page.getCurrent());
            this.messages.clear();
        }
    }

    /**
     * Permet d'afficher rapidement nu message d'erreur.
     * 
     * @param titre de l'encart de la notification
     * @param message d'erreur à afficher
     */
    public static void showOneMessage(String message) {
        ValidationNotification.showOneMessage(TITRE_PAR_DEFAUT, message);
    }

    /**
     * Permet d'afficher rapidement nu message d'erreur.
     * 
     * @param titre de l'encart de la notification
     * @param message d'erreur à afficher
     */
    public static void showOneMessage(String titre, String message) {
        ValidationNotification.showOneMessage(titre, message, Type.WARNING_MESSAGE);
    }

    /**
     * Permet d'afficher rapidement nu message d'erreur.
     * 
     * @param titre de l'encart de la notification
     * @param message d'erreur à afficher
     * @param type de notification
     */
    public static void showOneMessage(String titre, String message, Type type) {
        StringBuilder sbMessage = new StringBuilder();
        sbMessage.append("<ul>");
        sbMessage.append("<li>").append(message).append("</li>");
        sbMessage.append("</ul>");
        Notification errors = new Notification(titre, sbMessage.toString(), type, true);
        errors.setDelayMsec(-1);
        errors.show(Page.getCurrent());
    }

}
