package santeclair.portal.menu;

import com.vaadin.server.FontIcon;

public class MenuView {

    private final String codeViewUi;
    private final String libelleViewUi;
    private final FontIcon iconViewUi;
    private final Boolean openOnInitializationViewUi;

    /**
     * Constructeur
     */
    public MenuView(String codeViewUi, String libelleViewUi, FontIcon iconViewUi, Boolean openOnInitializationViewUi) {
        this.codeViewUi = codeViewUi;
        this.libelleViewUi = libelleViewUi;
        this.iconViewUi = iconViewUi;
        this.openOnInitializationViewUi = openOnInitializationViewUi;
    }

    /** Getters */

    public String getCodeViewUi() {
        return codeViewUi;
    }

    public String getLibelleViewUi() {
        return libelleViewUi;
    }

    public FontIcon getIconViewUi() {
        return iconViewUi;
    }

    public Boolean getOpenOnInitializationViewUi() {
        return openOnInitializationViewUi;
    }

}
