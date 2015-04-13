package santeclair.portal.reclamation.demande.document.recherche.form;

import java.io.Serializable;

import org.vaadin.viritin.layouts.MHorizontalLayout;

import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;

public class ResultatRecherche implements Serializable {
    
    /** Serial Version UID */
    private static final long serialVersionUID = 1L;

    /** Boutons d'action */
    private MHorizontalLayout buttonLayout;

    /** Dto de demande de document */
    private DemandeDocumentDto dto;

    /**
     * Constructeur
     * @param dto
     */
    public ResultatRecherche(DemandeDocumentDto dto) {
        this.dto = dto;
    }

    /**
     * Méthode de lecture de l'attribut : {@link ResultatRecherche#buttonLayout}
     * @return buttonLayout
     */
    public MHorizontalLayout getButtonLayout() {
        return buttonLayout;
    }

    /**
     * Méthode d'écriture de l'attribut : {@link ResultatRecherche#buttonLayout}
     * 
     * @param buttonLayout
     */
    public void setButtonLayout(MHorizontalLayout buttonLayout) {
        this.buttonLayout = buttonLayout;
    }

    /**
     * Méthode de lecture de l'attribut : {@link ResultatRecherche#dto}
     * 
     * @return dto
     */
    public DemandeDocumentDto getDto() {
        return dto;
    }

    /**
     * Méthode d'écriture de l'attribut : {@link ResultatRecherche#dto}
     * 
     * @param dto
     */
    public void setDto(DemandeDocumentDto dto) {
        this.dto = dto;
    }
}
