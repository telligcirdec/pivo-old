package santeclair.portal.menu;

import com.vaadin.server.FontIcon;

public class MenuView {

    private String codeViewUi;
    private String libelleViewUi;
    private FontIcon iconViewUi;
    private Boolean openOnInitializationViewUi;
    
    /**
     * Constructeur
     */
    public MenuView(String codeViewUi, String libelleViewUi, FontIcon iconViewUi, Boolean openOnInitializationViewUi) {
        this.codeViewUi = codeViewUi;
        this.libelleViewUi = libelleViewUi;
        this.iconViewUi = iconViewUi;
        this.openOnInitializationViewUi = openOnInitializationViewUi;
    }
    
    /** Getters et Setters */
    
    public String getCodeViewUi() {
        return codeViewUi;
    }
    
    public void setCodeViewUi(String codeViewUi) {
        this.codeViewUi = codeViewUi;
    }
    
    public String getLibelleViewUi() {
        return libelleViewUi;
    }
    
    public void setLibelleViewUi(String libelleViewUi) {
        this.libelleViewUi = libelleViewUi;
    }
    
    public FontIcon getIconViewUi() {
        return iconViewUi;
    }
    
    public void setIconViewUi(FontIcon iconViewUi) {
        this.iconViewUi = iconViewUi;
    }
    
    public Boolean getOpenOnInitializationViewUi() {
        return openOnInitializationViewUi;
    }
    
    public void setOpenOnInitializationViewUi(Boolean openOnInitializationViewUi) {
        this.openOnInitializationViewUi = openOnInitializationViewUi;
    }
}
