/**
 * 
 */
package santeclair.lunar.framework.service.email.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;

/**
 * Classe de tests unitaires pour EmailServiceImpl.
 * 
 * @author jfourmond
 * 
 */
public class EmailServiceImplUnitTest {

    /**
     * Teste la méthode setDestinataires.
     */
    @Test
    public void setDestinataires() {

        EmailServiceImpl emailServiceImpl = new EmailServiceImpl();
        emailServiceImpl.setJavaMailSender(new JavaMailSenderImpl());

        MimeMailMessage msg = emailServiceImpl.creerMessage();

        emailServiceImpl.addDestinataire(msg, "1@santeclair.fr");
        String mailTo = getMailAsString(msg, Message.RecipientType.TO);
        assertEquals("1@santeclair.fr", mailTo);

        emailServiceImpl.setDestinataires(msg, "2@santeclair.fr", "3@santeclair.fr");
        mailTo = getMailAsString(msg, Message.RecipientType.TO);
        assertEquals("2@santeclair.fr, 3@santeclair.fr", mailTo);

        emailServiceImpl.setDestinataires(msg, new String[]{});
        mailTo = getMailAsString(msg, Message.RecipientType.TO);
        assertEquals(null, mailTo);

        emailServiceImpl.setDestinataires(msg, "4@santeclair.fr");
        mailTo = getMailAsString(msg, Message.RecipientType.TO);
        assertEquals("4@santeclair.fr", mailTo);

        try {
            String[] destinataires = null;
            emailServiceImpl.setDestinataires(msg, destinataires);
            fail();
        } catch (IllegalArgumentException e) {
            /* normal */
        }
    }

    /**
     * Teste la méthode setCopies.
     */
    @Test
    public void setCopies() {

        EmailServiceImpl emailServiceImpl = new EmailServiceImpl();
        emailServiceImpl.setJavaMailSender(new JavaMailSenderImpl());

        MimeMailMessage msg = emailServiceImpl.creerMessage();

        emailServiceImpl.addCopie(msg, "1@santeclair.fr");
        String mailCc = getMailAsString(msg, Message.RecipientType.CC);
        assertEquals("1@santeclair.fr", mailCc);

        emailServiceImpl.setCopies(msg, "2@santeclair.fr", "3@santeclair.fr");
        mailCc = getMailAsString(msg, Message.RecipientType.CC);
        assertEquals("2@santeclair.fr, 3@santeclair.fr", mailCc);

        emailServiceImpl.setCopies(msg, new String[]{});
        mailCc = getMailAsString(msg, Message.RecipientType.CC);
        assertEquals(null, mailCc);

        emailServiceImpl.setCopies(msg, "4@santeclair.fr");
        mailCc = getMailAsString(msg, Message.RecipientType.CC);
        assertEquals("4@santeclair.fr", mailCc);

        try {
            String[] destinataires = null;
            emailServiceImpl.setCopies(msg, destinataires);
            fail();
        } catch (IllegalArgumentException e) {
            /* normal */
        }
    }

    /**
     * Renvoie sous forme de String la concaténation des adresses correspondant au RecipientType en paramètre.
     * 
     * @param msg
     * @param recipientType Message.RecipientType.TO ou Message.RecipientType.CC par exemple.
     * @return
     */
    String getMailAsString(MimeMailMessage msg, RecipientType recipientType) {

        String mail = "";
        try {
            Address[] adresses = msg.getMimeMessage().getRecipients(recipientType);
            mail = StringUtils.join(adresses, ", ");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mail;
    }

}
