package santeclair.lunar.framework.service.mail;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
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
 * @deprecated utilisé la classe {@link santeclair.lunar.framework.service.email.impl.EmailServiceImpl} à la place.
 */
@Deprecated
public class StubMailService extends AbstractService implements MailService {

    /*
     * (non-Javadoc)
     * 
     * @see santeclair.solar.framework.service.mail.MailService#creerMail()
     */
    public SimpleMailMessage creerMail() {
        return new SimpleMailMessage();
    }

    /*
     * (non-Javadoc)
     * 
     * @see santeclair.solar.framework.service.mail.MailService#envoyerMail(org.springframework.mail.SimpleMailMessage)
     */
    public void envoyerMail(SimpleMailMessage unMail) throws MailServiceException {
    }

    /*
     * (non-Javadoc)
     * 
     * @see santeclair.solar.framework.service.mail.MailService#envoyerMails(org.springframework.mail.SimpleMailMessage[])
     */
    public void envoyerMails(SimpleMailMessage[] desMails) throws MailServiceException {
    }

    /*
     * (non-Javadoc)
     * 
     * @see santeclair.solar.framework.service.mail.MailService#creerMailAvecAttachement()
     */
    public MimeMessageHelper creerMailAvecAttachement() throws MailServiceException {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see santeclair.solar.framework.service.mail.MailService#envoyerMailAvecAttachement(org.springframework.mail.javamail.MimeMessageHelper)
     */
    public void envoyerMailAvecAttachement(MimeMessageHelper unMail) throws MailServiceException {
    }

    /*
     * (non-Javadoc)
     * 
     * @see santeclair.lunar.framework.service.mail.MailService#creerMail(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public SimpleMailMessage creerMail(String expediteur, String destinataire, String sujet, String corps) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see santeclair.lunar.framework.service.mail.MailService#getDomaineSmtpLocal()
     */
    public String getDomaineSmtpLocal() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see santeclair.lunar.framework.service.mail.MailService#envoyerMail(javax.mail.internet.MimeMessage)
     */
    public void envoyerMail(MimeMessage unMail) throws MailServiceException {
    }

    /*
     * (non-Javadoc)
     * 
     * @see santeclair.lunar.framework.service.mail.MailService#creerMail(java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    public MimeMessage creerMail(String expediteur, String destinataire, String replyto, String sujet, String corps, String type)
                    throws MailServiceException {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see santeclair.lunar.framework.service.mail.MailService#creerMail(java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.io.File[])
     */
    public MimeMessage creerMail(String expediteur, String destinataire, String replyto, String sujet, String corps, String type, File[] files)
                    throws MailServiceException {
        return null;
    }

    public SimpleMailMessage creerMailAvecCopie(String expediteur, String destinataire, String copie, String sujet, String corps) {
        return null;
    }

}
