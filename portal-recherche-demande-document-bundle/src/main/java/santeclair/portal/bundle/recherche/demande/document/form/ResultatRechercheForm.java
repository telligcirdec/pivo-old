package santeclair.portal.bundle.recherche.demande.document.form;

import java.io.Serializable;

import santeclair.reclamation.demande.document.dto.DemandeDocumentDto;

import com.vaadin.ui.HorizontalLayout;

public class ResultatRechercheForm implements Serializable {
    
    /** Serial Version UID */
    private static final long serialVersionUID = 1L;

    /** Boutons d'action */
    private HorizontalLayout buttonLayout;

    /** Dto de demande de document */
    private DemandeDocumentDto dto;

    /**
     * Constructeur par d�faut.
     */
    public ResultatRechercheForm() {
    }

    public ResultatRechercheForm(DemandeDocumentDto dto) {
        this.dto = dto;
    }

    /**
     * M�thode de lecture de l'attribut : {@link ResultatRechercheForm#buttonLayout}
     * @return buttonLayout
     */
    public HorizontalLayout getButtonLayout() {
        return buttonLayout;
    }

    /**
     * M�thode d'�criture de l'attribut : {@link ResultatRechercheForm#buttonLayout}
     * 
     * @param buttonLayout
     */
    public void setButtonLayout(HorizontalLayout buttonLayout) {
        this.buttonLayout = buttonLayout;
    }

    /**
     * M�thode de lecture de l'attribut : {@link ResultatRechercheForm#dto}
     * 
     * @return dto
     */
    public DemandeDocumentDto getDto() {
        return dto;
    }

    /**
     * M�thode d'�criture de l'attribut : {@link ResultatRechercheForm#dto}
     * 
     * @param dto
     */
    public void setDto(DemandeDocumentDto dto) {
        this.dto = dto;
    }
}
