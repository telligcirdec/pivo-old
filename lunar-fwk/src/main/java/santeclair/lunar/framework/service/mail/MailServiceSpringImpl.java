package santeclair.lunar.framework.service.mail;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import santeclair.lunar.framework.service.AbstractService;

/**
 * Implémentation Spring d'un service de Mail.<br/>
 * 
 * Les appels sont redirigés vers la classe d'implémentation de Spring.<br/>
 * 
 * De par la nature du service, on bénéficie de comportements génériques offerts à tous les services.<br/>
 * 
 * @author Carl Azoury
 * @author cbitar
 * @deprecated Utiliser l'implémentation du service {@link santeclair.lunar.framework.service.email.EmailService}
 */
@Deprecated
public class MailServiceSpringImpl extends AbstractService implements MailService {

    private static final String MSG_ERROR_SEND_MAIL = "Une erreur est survenue lors de l'envoi d'un mail";

    /**
     * mail sender de Spring
     */
    private JavaMailSender mailSender;

    /**
     * Domaine SMTP Local (ex :santeclair.fr)
     */
    private String domaineSmtpLocal;

    /**
     * {@inheritDoc}
     */
    public SimpleMailMessage creerMail() {
        return new SimpleMailMessage();
    }

    /**
     * {@inheritDoc}
     */
    public SimpleMailMessage creerMail(String expediteur, String destinataire, String sujet, String corps) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(expediteur);
        mail.setTo(destinataire);
        mail.setSubject(sujet);
        mail.setText(corps);
        return mail;
    }

    /**
     * {@inheritDoc}
     */
    public MimeMessage creerMail(String expediteur, String destinataire, String replyto, String sujet, String corps, String type)
                    throws MailServiceException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            mimeMessage.setFrom(new InternetAddress(expediteur));
            mimeMessage.setReplyTo(new InternetAddress[] {new InternetAddress(replyto) });
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(destinataire));
            mimeMessage.setSubject(sujet);
            if (null == type) {
                type = "text/plain";
            }
            mimeMessage.setContent(corps, type);
        } catch (AddressException e) {
            logger.warn("Erreur de création d'une adresse", e);
            throw new MailServiceException(e);
        } catch (MessagingException e) {
            logger.warn("Erreur d'envoi du message", e);
            throw new MailServiceException(e);
        } catch (Exception e) {
            logger.error("Erreur de création du message", e);
            throw new MailServiceException(e);
        }
        return mimeMessage;
    }

    /**
     * {@inheritDoc}
     */
    public MimeMessage creerMail(String expediteur, String destinataire, String replyto, String sujet, String corps, String type, File[] files)
                    throws MailServiceException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            mimeMessage.setFrom(new InternetAddress(expediteur));
            mimeMessage.setReplyTo(new InternetAddress[] {new InternetAddress(replyto) });
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(destinataire));
            mimeMessage.setSubject(sujet);
            if (null == type) {
                type = "text/plain";
            }
            // création du multipart
            Multipart multipart = new MimeMultipart();
            // création part 1 qui est le corps du message
            MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
            mimeBodyPart1.setContent(corps + "\r\n", type);
            multipart.addBodyPart(mimeBodyPart1);
            // création part 2 qui sont les pièces jointes
            if (null != files) {
                for (File file : files) {
                    MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
                    DataSource dataSource = new FileDataSource(file);
                    mimeBodyPart2.setDataHandler(new DataHandler(dataSource));
                    mimeBodyPart2.setFileName(file.getName());
                    multipart.addBodyPart(mimeBodyPart2);
                }
            }
            // ajout du multipart au message
            mimeMessage.setContent(multipart);
        } catch (AddressException e) {
            logger.warn("Erreur de création d'une adresse", e);
            throw new MailServiceException(e);
        } catch (MessagingException e) {
            logger.warn("Erreur d'envoi du message", e);
            throw new MailServiceException(e);
        } catch (Exception e) {
            logger.error("Erreur de création du message", e);
            throw new MailServiceException(e);
        }
        return mimeMessage;
    }

    /**
     * {@inheritDoc}
     */
    public MimeMessageHelper creerMailAvecAttachement() throws MailServiceException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
        } catch (MessagingException me) {
            logger.warn(MSG_ERROR_SEND_MAIL, me);
            throw new MailServiceException(me);
        }
        return helper;
    }

    /**
     * {@inheritDoc}
     */
    public void envoyerMail(SimpleMailMessage unMail) throws MailServiceException {
        try {
            mailSender.send(unMail);
        } catch (MailException me) {
            logger.warn(MSG_ERROR_SEND_MAIL, me);
            throw new MailServiceException(me);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void envoyerMail(MimeMessage unMail) throws MailServiceException {
        try {
            mailSender.send(unMail);
        } catch (MailException me) {
            logger.warn(MSG_ERROR_SEND_MAIL, me);
            throw new MailServiceException(me);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void envoyerMailAvecAttachement(MimeMessageHelper unMail) throws MailServiceException {
        try {
            mailSender.send(unMail.getMimeMessage());
        } catch (MailException me) {
            logger.warn(MSG_ERROR_SEND_MAIL, me);
            throw new MailServiceException(me);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void envoyerMails(SimpleMailMessage[] desMails) throws MailServiceException {
        try {
            mailSender.send(desMails);
        } catch (MailException me) {
            logger.warn(MSG_ERROR_SEND_MAIL, me);
            throw new MailServiceException(me);
        }
    }

    /**
     * On ne verra aucun appel dans le code à cette méthode.<br/>
     * L'appel est fait automatiquement par le conteneur Spring.<br/>
     */
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * {@inheritDoc}
     */
    public String getDomaineSmtpLocal() {
        return domaineSmtpLocal;
    }

    public void setDomaineSmtpLocal(String domaineSmtpLocal) {
        this.domaineSmtpLocal = domaineSmtpLocal;
    }

    /**
     * {@inheritDoc}
     */
    public SimpleMailMessage creerMailAvecCopie(String expediteur, String destinataire, String copie, String sujet, String corps) {
        SimpleMailMessage mail = creerMail(expediteur, destinataire, sujet, corps);
        mail.setCc(copie);
        return mail;
    }

}
