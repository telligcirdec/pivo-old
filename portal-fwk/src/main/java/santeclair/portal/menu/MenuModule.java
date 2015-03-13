package santeclair.portal.menu;

import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;

public class MenuModule {

    private String codeModuleUi;
    private String libelleModuleUi;
    private FontIcon iconModuleUi;
    private Integer displayOrderModuleUi;
    private Boolean isCloseableModuleUi;
    private Boolean severalTabsAllowedModuleUi;
    private List<MenuView> menuViews;
    
    /**
     * Constucteur
     */
    public MenuModule(String codeModuleUi, String libelleModuleUi, String iconModuleUi, Integer displayOrderModuleUi, Boolean isCloseableModuleUi, Boolean severalTabsAllowedModuleUi, List<MenuView> menuViews) {
        this.codeModuleUi = codeModuleUi;
        this.libelleModuleUi = libelleModuleUi;
        this.displayOrderModuleUi = displayOrderModuleUi;
        this.isCloseableModuleUi = isCloseableModuleUi;
        this.severalTabsAllowedModuleUi = severalTabsAllowedModuleUi;
        if (iconModuleUi != null && !iconModuleUi.isEmpty()) {
            this.iconModuleUi = FontAwesome.valueOf(iconModuleUi);
        }
        this.menuViews = menuViews;
    }
    
    /** Getters et Setters */
    
    public String getCodeModuleUi() {
        return codeModuleUi;
    }
    
    public void setCodeModuleUi(String codeModuleUi) {
        this.codeModuleUi = codeModuleUi;
    }
    
    public String getLibelleModuleUi() {
        return libelleModuleUi;
    }
    
    public void setLibelleModuleUi(String libelleModuleUi) {
        this.libelleModuleUi = libelleModuleUi;
    }
    
    public FontIcon getIconModuleUi() {
        return iconModuleUi;
    }
    
    public void setIconModuleUi(FontIcon iconModuleUi) {
        this.iconModuleUi = iconModuleUi;
    }
    
    public Integer getDisplayOrderModuleUi() {
        return displayOrderModuleUi;
    }
    
    public void setDisplayOrderModuleUi(Integer displayOrderModuleUi) {
        this.displayOrderModuleUi = displayOrderModuleUi;
    }
    
    public Boolean getIsCloseableModuleUi() {
        return isCloseableModuleUi;
    }
    
    public void setIsCloseableModuleUi(Boolean isCloseableModuleUi) {
        this.isCloseableModuleUi = isCloseableModuleUi;
    }
    
    public Boolean getSeveralTabsAllowedModuleUi() {
        return severalTabsAllowedModuleUi;
    }
    
    public void setSeveralTabsAllowedModuleUi(Boolean severalTabsAllowedModuleUi) {
        this.severalTabsAllowedModuleUi = severalTabsAllowedModuleUi;
    }
    
    public List<MenuView> getMenuViews() {
        return menuViews;
    }
    
    public void setMenuViews(List<MenuView> menuViews) {
        this.menuViews = menuViews;
    }
}
