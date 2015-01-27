package santeclair.lunar.framework.service.email;

import java.io.File;

import javax.activation.DataSource;
import javax.mail.internet.InternetAddress;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMailMessage;

/**
 * Interface listant les m�thodes utiles � la cr�ation et la manipulation des {@link MimeMailMessage}.
 * 
 * @author tducloyer
 * 
 */
public interface EmailService {

    /**
     * Envoie un message
     * 
     * @param message : Le message � envoyer
     * @throws EmailServiceException
     */
    public void envoyer(MimeMailMessage message) throws EmailServiceException;

    /**
     * Cr�e un message vide
     * 
     * @return Le message � enrichir
     * @throws EmailServiceException
     */
    public MimeMailMessage creerMessage() throws EmailServiceException;

    /**
     * Cr�e un message vide avec l'encoding pass� en param�tre
     * 
     * @param encoding : type d'encodage
     * @return Le message � enrichir
     * @throws EmailServiceException
     */
    public MimeMailMessage creerMessage(String encoding) throws EmailServiceException;

    /**
     * Assignation de l'exp�diteur au message
     * 
     * @param message : Le message � traiter
     * @param expediteur : L'adresse email de l'exp�diteur
     * @return Le message enrichi
     * @throws EmailServiceException
     */
    public MimeMailMessage setExpediteur(MimeMailMessage message, String expediteur) throws EmailServiceException;

    /**
     * Assignation de l'exp�diteur au message
     * 
     * @param message : Le message � traiter
     * @param expediteur : L'adresse email de l'exp�diteur
     * @param expediteurLibelle : Libell� de l'exp�diteur
     * @return Le message enrichi
     * @throws EmailServiceException
     */
    public MimeMailMessage setExpediteur(MimeMailMessage message, String expediteur, String expediteurLibelle) throws EmailServiceException;

    /**
     * Ajout d'un destinataire au message
     * 
     * @param message : Le message � traiter
     * @param destinataire : L'adresse email du destinataire
     * @return Le message enrichi
     * @throws EmailServiceException
     */
    public MimeMailMessage addDestinataire(MimeMailMessage message, String destinataire) throws EmailServiceException;

    /**
     * (R�)initialise le ou les destinataire(s).
     * 
     * @param message : Le message � traiter
     * @param destinataire : La ou les adresse(s) email du ou des destinataire(s).
     *            Doit �tre != null. Si on veut effacer la liste, on passera un tableau de String vides.
     * @throws EmailServiceException
     */
    public void setDestinataires(MimeMailMessage message, String... destinataires) throws EmailServiceException;

    /**
     * (R�)initialise le ou les destinataire(s).
     * 
     * @param message : Le message � traiter
     * @param destinataire : La ou les adresse(s) email du ou des destinataire(s).
     *            Doit �tre != null. Si on veut effacer la liste, on passera un tableau de InternetAddress vides.
     * @throws EmailServiceException
     */
    public void setDestinataires(MimeMailMessage message, InternetAddress... destinataires) throws EmailServiceException;

    /**
     * Ajout d'une copie au message
     * 
     * @param message : Le message � traiter
     * @param copie : L'adresse email � rajouter en copie
     * @return Le message enrichi
     * @throws EmailServiceException
     */
    public MimeMailMessage addCopie(MimeMailMessage message, String copie) throws EmailServiceException;

    /**
     * (R�)initialise le ou les destinataire(s) en copie.
     * 
     * @param message : Le message � traiter
     * @param destinataire : La ou les adresse(s) email du ou des destinataire(s) en copie.
     *            Doit �tre != null. Si on veut effacer la liste, on passera un tableau de String vides.
     * @throws EmailServiceException
     */
    public void setCopies(MimeMailMessage message, String... copies) throws EmailServiceException;

    /**
     * Ajout d'une copie cach�e au message
     * 
     * @param message : Le message � traiter
     * @param copieCachee : L'adresse email � rajouter en copie cach�e
     * @return Le message enrichi
     * @throws EmailServiceException
     */
    public MimeMailMessage addCopieCachee(MimeMailMessage message, String copieCachee) throws EmailServiceException;

    /**
     * Assignation d'un sujet au message
     * 
     * @param message : Le message � traiter
     * @param sujet : Le sujet du mail
     * @return Le message enrichi
     * @throws EmailServiceException
     */
    public MimeMailMessage setSujet(MimeMailMessage message, String sujet) throws EmailServiceException;

    /**
     * Assignation d'un corps en texte brut au message
     * 
     * @param message : Le message � traiter
     * @param corps : Le corps du mail en texte brut
     * @return Le message enrichi
     * @throws EmailServiceException
     */
    public MimeMailMessage setCorps(MimeMailMessage message, String corps) throws EmailServiceException;

    /**
     * Assignation d'un corps en HTML au message
     * 
     * @param message : Le message � traiter
     * @param corpsHtml : Le corps du mail en HTML
     * @return Le message enrichi
     * @throws EmailServiceException
     */
    public MimeMailMessage setCorpsHtml(MimeMailMessage message, String corpsHtml) throws EmailServiceException;

    /**
     * Assignation d'un corps mixte texte brut / HTML au message
     * 
     * @param message : Le message � traiter
     * @param corpsTexte : Le corps du mail en texte brut
     * @param corpsHtml Le corps du mail en HTML
     * @return Le message enrichi
     * @throws EmailServiceException
     */
    public MimeMailMessage setCorpsMixte(MimeMailMessage message, String corpsTexte, String corpsHtml) throws EmailServiceException;

    /**
     * Ajout d'un fichier attach� au message
     * 
     * @param message : Le message � traiter
     * @param nomAttachement : Le nom � assigner au fichier attach�
     * @param attachement : Le fichier � joindre au message
     * @return Le message enrichi
     * @throws EmailServiceException
     */
    public MimeMailMessage addAttachement(MimeMailMessage message, String nomAttachement, File attachement) throws EmailServiceException;

    /**
     * Ajout d'une pi�ce jointe au message, � partir d'une javax.activation.DataSource.
     * 
     * @param message : Le message � traiter
     * @param nomAttachement : Le nom � assigner au fichier attach�
     * @param attachement : La javax.activation.DataSource.
     * @return Le message enrichi
     * @throws EmailServiceException
     */
    public MimeMailMessage addAttachement(MimeMailMessage message, String nomAttachement, DataSource attachement) throws EmailServiceException;

    /**
     * Ajoute une image au mail pour affichage dans son body.
     * 
     * @param pathImage
     * @param nomImage correspond � la balise cid dans le HTML du mail.
     */
    public void addImage(MimeMailMessage message, String pathImage, String nomImage) throws EmailServiceException;

    /**
     * Ajoute une image au mail a partir d'un InputStreamResource
     * 
     * @param message
     * @param inputStreamImage
     * @param nomImage
     * @throws EmailServiceException
     */
    public void addImage(MimeMailMessage message, ByteArrayResource byteArrayResource, String nomImage, String mimeType) throws EmailServiceException;

    /**
     * Marque le message comme �tant important.
     * Sous Notes, le flag "important" apparait sous la forme d'un point d'exclamation.
     */
    public void marquerImportant(MimeMailMessage message) throws EmailServiceException;
}
