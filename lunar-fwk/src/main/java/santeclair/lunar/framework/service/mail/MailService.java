package santeclair.lunar.framework.service.mail;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * Interface d'un service de Mail.
 * 
 * Ce service à pour but de créer un mail, par la méthode <code>creerMail</code><br/>
 * 
 * Puis ensuite, avec une des méthodes d'envoi correspondante, l'envoyer.<br/>
 * 
 * @author cazoury
 * @author cbitar
 * @deprecated Utiliser le service {@link santeclair.lunar.framework.service.email.EmailService}
 */
@Deprecated
public interface MailService {

    /**
     * Retourne une instance vide de Mail, à compléter.<br/>
     * 
     * @return SimpleMailMessage
     */
    public SimpleMailMessage creerMail();

    /**
     * Retourne une instance de Mail initialisée avec les valeurs passées en paramètre.<br/>
     * 
     * @param expediteur adresse de l'expéditeur
     * @param destinataire adresse du destinataire
     * @param sujet sujet du message
     * @param corps contenu du message
     * @return SimpleMailMessage
     */
    public SimpleMailMessage creerMail(String expediteur, String destinataire, String sujet, String corps);

    /**
     * Retourne une instance de Mail initialisée avec les valeurs passées en paramètre.<br/>
     * 
     * @param expediteur adresse de l'expéditeur
     * @param destinataire adresse du destinataire
     * @param copie adresse de la copie
     * @param sujet sujet du message
     * @param corps contenu du message
     * @return SimpleMailMessage
     */
    public SimpleMailMessage creerMailAvecCopie(String expediteur, String destinataire, String copie, String sujet, String corps);

    /**
     * Retourne un <code>MimeMessage</code> initialisée avec les valeurs passées en paramètre.<br/>
     * 
     * @param expediteur adresse de l'expéditeur
     * @param destinataire adresse du destinataire
     * @param replyto adresse de retour
     * @param sujet sujet du message
     * @param corps contenu du message
     * @param type type du contenu "text/plain", "text/html", etc.
     * @return un <code>MimeMessage</code>
     * @throws MailServiceException
     */
    public MimeMessage creerMail(String expediteur, String destinataire, String replyto, String sujet, String corps, String type)
                    throws MailServiceException;

    /**
     * Retourne un <code>MimeMessage</code> initialisée avec les valeurs passées en paramètre.<br/>
     * 
     * @param expediteur adresse de l'expéditeur
     * @param destinataire adresse du destinataire
     * @param replyto adresse de retour
     * @param sujet sujet du message
     * @param corps contenu du message
     * @param type type du contenu "text/plain", "text/html", etc.
     * @param files array de fichiers en pièces jointes
     * @return un <code>MimeMessage</code>
     * @throws MailServiceException
     */
    public MimeMessage creerMail(String expediteur, String destinataire, String replyto, String sujet, String corps, String type, File[] files)
                    throws MailServiceException;

    /**
     * Service d'envoi d'un seul Mail.
     * 
     * @param unMail le mail à envoyer
     */
    public void envoyerMail(SimpleMailMessage unMail) throws MailServiceException;

    /**
     * Service d'envoi d'un seul Mail avec piece jointe
     * 
     * @param unMail le mail à envoyer
     * @throws MailServiceException
     */
    public void envoyerMail(MimeMessage unMail) throws MailServiceException;

    /**
     * Service d'envoi d'une série de Mails.
     * 
     * @param desMails Array de mails à envoyer
     */
    public void envoyerMails(SimpleMailMessage[] desMails) throws MailServiceException;

    /**
     * Retourne un maildu type MimeMessageHelper
     * 
     * @return un <code>MimeMessage</code>
     * @throws MailServiceException
     */
    public MimeMessageHelper creerMailAvecAttachement() throws MailServiceException;

    /**
     * Service d'envoi d'un mail avec attachement
     * 
     * @param unMail le mail à envoyer
     * @throws MailServiceException
     */
    public void envoyerMailAvecAttachement(MimeMessageHelper unMail) throws MailServiceException;

    /**
     * Retourne le domaine SMTP
     * 
     * @return un <code>MimeMessage</code>
     */
    public String getDomaineSmtpLocal();

}
