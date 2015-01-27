package santeclair.lunar.framework.web.jsf;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;

import com.google.common.collect.Maps;

/**
 * Classe de mocks pour pour la classe FacesContextHelper.
 * Permet de tester des classes utilisant FacesContext sans �tre dans un environnement servlet.
 * 
 * @author jfourmond
 * 
 */
public class FacesContextHelperMock extends FacesContextHelper {

    private static String nextNavigationCase;
    /**
     * Singleton.
     */
    private static FacesContextHelper facesContextHelper = new FacesContextHelperMock();

    /**
     * Renvoie l'instance de FacesContextHelper.
     */
    public static FacesContextHelper getInstance() {
        return facesContextHelper;
    }

    /**
     * @return the nextNavigationCase
     */
    public static String getNextNavigationCase() {
        return nextNavigationCase;
    }

    /**
     * Renvoie le param�tre de requ�te correspondant au nom en param�tre.
     * 
     * @return
     */
    public void addMessage(String clientId, FacesMessage message) {
    }

    /**
     * Renvoie le UIViewRoot associ� � l'instance courante de FacesContext.
     * 
     * @return
     */
    public UIViewRoot getViewRoot() {
        return new UIViewRoot();
    }

    public Map<String, Object> getViewMap() {
        return Maps.newHashMap();
    }

    /** {@inheritDoc} */
    @Override
    public void redirect(String nomVue) {
        nextNavigationCase = nomVue;
    }

}
