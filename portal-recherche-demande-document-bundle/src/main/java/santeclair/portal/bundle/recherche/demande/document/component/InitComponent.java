package santeclair.portal.bundle.recherche.demande.document.component;

import com.vaadin.ui.Component;

public interface InitComponent extends Component, Comparable<InitComponent>{

    public void init(String sessionId, Integer tabHash, String moduleCode, String viewCode);
 
    public int getDisplayOrder();
    
}
