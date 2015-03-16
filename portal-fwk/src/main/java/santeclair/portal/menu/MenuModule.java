package santeclair.portal.menu;

import java.util.List;

import com.vaadin.server.FontIcon;

public class MenuModule {

    private final String codeModuleUi;
    private final String libelleModuleUi;
    private final FontIcon iconModuleUi;
    private final Integer displayOrderModuleUi;
    private final Boolean isCloseableModuleUi;
    private final Boolean severalTabsAllowedModuleUi;
    private final List<MenuView> menuViews;

    /**
     * Constructeur
     */

    public MenuModule(String codeModuleUi, String libelleModuleUi, FontIcon iconModuleUi, Integer displayOrderModuleUi, Boolean isCloseableModuleUi,
                      Boolean severalTabsAllowedModuleUi, List<MenuView> menuViews) {
        this.codeModuleUi = codeModuleUi;
        this.libelleModuleUi = libelleModuleUi;
        this.iconModuleUi = iconModuleUi;
        this.displayOrderModuleUi = displayOrderModuleUi;
        this.isCloseableModuleUi = isCloseableModuleUi;
        this.severalTabsAllowedModuleUi = severalTabsAllowedModuleUi;
        this.menuViews = menuViews;
    }

    /** Getters */

    public String getCodeModuleUi() {
        return codeModuleUi;
    }

    public String getLibelleModuleUi() {
        return libelleModuleUi;
    }

    public FontIcon getIconModuleUi() {
        return iconModuleUi;
    }

    public Integer getDisplayOrderModuleUi() {
        return displayOrderModuleUi;
    }

    public Boolean getIsCloseableModuleUi() {
        return isCloseableModuleUi;
    }

    public Boolean getSeveralTabsAllowedModuleUi() {
        return severalTabsAllowedModuleUi;
    }

    public List<MenuView> getMenuViews() {
        return menuViews;
    }

    public void removeMenuView(String codeViewUi) {
        for (MenuView menuView : menuViews) {
            if (menuView.getCodeViewUi().equals(codeViewUi)) {
                menuViews.remove(menuView);
                break;
            }
        }
    }
}
