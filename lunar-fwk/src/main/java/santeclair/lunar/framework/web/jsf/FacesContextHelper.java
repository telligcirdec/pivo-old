package santeclair.lunar.framework.web.jsf;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Classe utilitaire pour les backing beans ayant besoin d'accéder au FacesContext
 * et à son contenu.
 * 
 * @author jfourmond
 * 
 */
public class FacesContextHelper {

    /**
     * Singleton.
     */
    private static FacesContextHelper facesContextHelper = new FacesContextHelper();

    /*=======================================================*
     *                code métier
     *=======================================================*/

    /**
     * Renvoie l'instance de FacesContextHelper.
     */
    public static FacesContextHelper getInstance() {
        return facesContextHelper;
    }

    /**
     * Renvoie l'instance courante du FacesContext.
     */
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Renvoie l'instance courante de l'ExternalContext.
     */
    public ExternalContext getExternalContext() {
        return getFacesContext().getExternalContext();
    }

    /**
     * Redirige vers la vue en parmètre.
     * Par exemple, "saisieLibre" (sans le .jsf).
     */
    public void redirect(String nomVue) {
        try {
            getExternalContext().redirect(nomVue + ".jsf");
            getFacesContext().responseComplete();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    /**
     * Ajoute un message au FacesContext.
     * 
     * @param clientId l'id du composant auquel associer le message dans la page XHTML.
     * @return
     */
    public void addMessage(String clientId, FacesMessage message) {
        getFacesContext().addMessage(clientId, message);
    }

    /**
     * Renvoie la liste de message au FacesContext.
     * 
     * @return tout les messages stocké dans le FacesContext
     */
    public List<FacesMessage> getMessageList() {
        return getFacesContext().getMessageList();
    }

    /**
     * Renvoie la liste de message au FacesContext fesant ciblant un composant en particulier.
     * 
     * @param clientId l'id du composant auquel sont associé les messages.
     * @return les messages associé à clientId
     */
    public List<FacesMessage> getMessageList(String clientId) {
        return getFacesContext().getMessageList(clientId);
    }

    /**
     * Renvoie le UIViewRoot associé à l'instance courante de FacesContext.
     * 
     * @return
     */
    public UIViewRoot getViewRoot() {
        return getFacesContext().getViewRoot();
    }

    /**
     * Renvoie le view map associé à l'instance courante de FacesContext.
     * 
     * @return
     */
    public Map<String, Object> getViewMap() {
        return getViewRoot().getViewMap();
    }

    /**
     * Permet d'utiliser un FacesContext mocké.
     * Méthode utilisée pour les tests uniquement.
     */
    public static void setInstance(FacesContextHelper facesContextHelper) {
        FacesContextHelper.facesContextHelper = facesContextHelper;
    }
}
