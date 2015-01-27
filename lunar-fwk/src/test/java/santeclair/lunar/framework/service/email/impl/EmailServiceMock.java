package santeclair.lunar.framework.service.email.impl;

import java.io.File;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMailMessage;

import santeclair.lunar.framework.service.email.EmailServiceException;

/**
 * Mock pour le service d'envoi de mail défini dans lunar.
 * On garde les vraies méthodes de EmailServiceImpl, en mockant juste la méthode d'envoi du mail.
 * 
 * @author jfourmond
 * 
 */
public class EmailServiceMock extends EmailServiceImpl {

    private Logger logger = LoggerFactory.getLogger(EmailServiceMock.class);

    public int countEnvoyer = 0;
    public int countAddAttachement = 0;
    public int countMailTo = 0;
    public MimeMailMessage message = null;
    public String mailFrom = null;
    public String[] mailTo = null;
    public String mailCc = null;
    public String subject = null;

    /** {@inheritDoc} */
    @Override
    public void envoyer(MimeMailMessage message) throws EmailServiceException {
        countEnvoyer++;
        this.message = message;
        logger.info("Simulation de l'envoi de mail");

        try {
            this.subject = message.getMimeMessageHelper().getMimeMessage().getSubject();
            Address[] adressesTo = message.getMimeMessage().getRecipients(Message.RecipientType.TO);
            mailTo = new String[adressesTo.length];
            mailFrom = message.getMimeMessage().getFrom()[0].toString();
            for (int i = 0; i < adressesTo.length; i++) {
                mailTo[i] = adressesTo[i].toString();
                countMailTo++;
            }
            Address[] adressesCc = message.getMimeMessage().getRecipients(Message.RecipientType.CC);
            if (adressesCc != null && adressesCc.length > 0) {
                this.mailCc = adressesCc[0].toString();
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public MimeMailMessage addAttachement(MimeMailMessage message,
                    String nomAttachement, File attachement)
                    throws EmailServiceException {
        countAddAttachement++;

        return message;
    }

}
