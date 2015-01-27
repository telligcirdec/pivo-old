package santeclair.portal.vaadin.accueil.old.view;

import santeclair.portal.vaadin.deprecated.module.view.PresenterName;

public enum AccueilViewName implements PresenterName {
    MAIN("");

    private String libelle;

    private AccueilViewName(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public class Contantes {
        public static final String MAIN = "MAIN";
    }
}
