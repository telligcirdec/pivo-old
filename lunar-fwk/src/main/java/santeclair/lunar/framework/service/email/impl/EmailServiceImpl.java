package santeclair.lunar.framework.service.email.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import santeclair.lunar.framework.service.email.EmailService;
import santeclair.lunar.framework.service.email.EmailServiceException;

import com.google.common.base.Preconditions;

/**
 * Implémentation de l'interface {@link EmailService}.
 * 
 * @author tducloyer
 * 
 */
@Transactional
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private static final String PRIORITY_FLAG = "Importance";
    private static final String IMPORTANT = "1";

    private JavaMailSender javaMailSender;

    public EmailServiceImpl() {

    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /** {@inheritDoc} */
    public MimeMailMessage creerMessage() throws EmailServiceException {
        try {
            return new MimeMailMessage(new MimeMessageHelper(javaMailSender.createMimeMessage(), true));
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
    }

    /** {@inheritDoc} */
    public MimeMailMessage creerMessage(String encoding) throws EmailServiceException {
        try {
            return new MimeMailMessage(new MimeMessageHelper(javaMailSender.createMimeMessage(), true, encoding));
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
    }

    /** {@inheritDoc} */
    public MimeMailMessage setExpediteur(MimeMailMessage message, String expediteur) throws EmailServiceException {
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            helper.setFrom(expediteur);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
        return message;
    }

    /** {@inheritDoc} */
    public MimeMailMessage setExpediteur(MimeMailMessage message, String expediteur, String expediteurLibelle) throws EmailServiceException {
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            helper.setFrom(expediteur, expediteurLibelle);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailServiceException(e);
        }
        return message;
    }

    /** {@inheritDoc} */
    public MimeMailMessage addCopie(MimeMailMessage message, String copie) throws EmailServiceException {
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            helper.addCc(copie);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
        return message;
    }

    /** {@inheritDoc} */
    public void setCopies(MimeMailMessage message, String... copies) throws EmailServiceException {
        Preconditions.checkArgument(copies != null);
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            helper.setCc(copies);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
    }

    /** {@inheritDoc} */
    public MimeMailMessage addCopieCachee(MimeMailMessage message, String copieCachee) throws EmailServiceException {
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            helper.addBcc(copieCachee);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
        return message;
    }

    /** {@inheritDoc} */
    public void setDestinataires(MimeMailMessage message, String... destinataires) throws EmailServiceException {
        Preconditions.checkArgument(destinataires != null);
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            helper.setTo(destinataires);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
    }

    /** {@inheritDoc} */
    public void setDestinataires(MimeMailMessage message, InternetAddress... destinataires) throws EmailServiceException {
        Preconditions.checkArgument(destinataires != null);
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            helper.setTo(destinataires);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
    }

    /** {@inheritDoc} */
    public MimeMailMessage addDestinataire(MimeMailMessage message, String destinataire) throws EmailServiceException {
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            helper.addTo(destinataire);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
        return message;
    }

    /** {@inheritDoc} */
    public MimeMailMessage setCorps(MimeMailMessage message, String corps) throws EmailServiceException {
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            helper.setText(corps);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
        return message;
    }

    /** {@inheritDoc} */
    public MimeMailMessage setCorpsHtml(MimeMailMessage message, String corpsHtml) throws EmailServiceException {
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            helper.setText(corpsHtml, true);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
        return message;
    }

    /** {@inheritDoc} */
    public MimeMailMessage setSujet(MimeMailMessage message, String sujet) throws EmailServiceException {
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            helper.setSubject(sujet);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
        return message;
    }

    /** {@inheritDoc} */
    public MimeMailMessage addAttachement(MimeMailMessage message, String nomAttachement, File attachement) throws EmailServiceException {
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            if (!helper.isMultipart()) {
                helper = new MimeMessageHelper(helper.getMimeMessage(), true);
                message = new MimeMailMessage(helper);
            }
            helper.addAttachment(nomAttachement, attachement);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
        return message;
    }

    /** {@inheritDoc} */
    public MimeMailMessage addAttachement(MimeMailMessage message, String nomAttachement, DataSource attachement) throws EmailServiceException {

        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            if (!helper.isMultipart()) {
                helper = new MimeMessageHelper(helper.getMimeMessage(), true);
                message = new MimeMailMessage(helper);
            }
            helper.addAttachment(nomAttachement, attachement);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
        return message;
    }

    /** {@inheritDoc} */
    public void envoyer(MimeMailMessage message) throws EmailServiceException {
        MimeMessage mimeMessage = message.getMimeMessage();
        try {
            Assert.notNull(mimeMessage.getFrom());
            Assert.notNull(mimeMessage.getAllRecipients());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
    }

    /** {@inheritDoc} */
    public MimeMailMessage setCorpsMixte(MimeMailMessage message, String corpsTexte, String corpsHtml) throws EmailServiceException {
        MimeMessageHelper helper = message.getMimeMessageHelper();
        try {
            helper.setText(corpsTexte, corpsHtml);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
        return message;
    }

    /** {@inheritDoc} */
    public void addImage(MimeMailMessage message, String pathImage, String nomImage) {
        MimeMessageHelper mimeMessageHelper = message.getMimeMessageHelper();

        try {
            URL urlImage = ResourceUtils.getURL("classpath:" + pathImage);
            DataSource ds = new URLDataSource(urlImage);
            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.setDataHandler(new DataHandler(ds));
            mimeMessageHelper.addInline(nomImage, ds);
        } catch (Exception e) {
            throw new EmailServiceException(e);
        }
    }

    /** {@inheritDoc} */
    public void addImage(MimeMailMessage message, ByteArrayResource byteArrayResource, String nomImage, String mimeType) throws EmailServiceException {
        MimeMessageHelper mimeMessageHelper = message.getMimeMessageHelper();

        try {
            mimeMessageHelper.addInline(nomImage, byteArrayResource, mimeType);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void marquerImportant(MimeMailMessage message) throws EmailServiceException {
        try {
            MimeMessage mimeMessage = message.getMimeMessage();
            mimeMessage.addHeader(PRIORITY_FLAG, IMPORTANT);
        } catch (MessagingException e) {
            throw new EmailServiceException(e);
        }
    }

}
