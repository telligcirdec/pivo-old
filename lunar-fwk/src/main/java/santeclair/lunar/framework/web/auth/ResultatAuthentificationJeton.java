/**
 * 
 */
package santeclair.lunar.framework.web.auth;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Retour de la demande d'authentification d'un jeton à destination du web service webext.
 * 
 * @author jfourmond
 * 
 */
@XmlRootElement
public class ResultatAuthentificationJeton {

    private DemandeAuthentificationJeton demandeAuthentificationJeton;

    private CodeRetour codeRetour;

    private List<AssureJeton> beneficiaires;

    private AssureJeton assureJeton;

    private String numeroContrat;

    private Integer idOrganisme;

    private String idPartenaire;
    
    private JetonSSO jetonSSO;

    /*=======================================================*
     *           	     code métier
     *=======================================================*/

    /**
     * @return true si le jeton est valide, false sinon.
     */
    public boolean isJetonValide() {
        return CodeRetour._00.equals(codeRetour);
    }

    /*=======================================================*
     *              	getters & setters
     *=======================================================*/

    /**
     * @return the demandeAuthentificationJeton
     */
    public DemandeAuthentificationJeton getDemandeAuthentificationJeton() {
        return demandeAuthentificationJeton;
    }

    /**
     * @param demandeAuthentificationJeton the demandeAuthentificationJeton to set
     */
    public void setDemandeAuthentificationJeton(
                    DemandeAuthentificationJeton demandeAuthentificationJeton) {
        this.demandeAuthentificationJeton = demandeAuthentificationJeton;
    }

    /**
     * @return the codeRetour
     */
    public CodeRetour getCodeRetour() {
        return codeRetour;
    }

    /**
     * @param codeRetour the codeRetour to set
     */
    public void setCodeRetour(CodeRetour codeRetour) {
        this.codeRetour = codeRetour;
    }

    /**
     * @return the beneficiaires
     */
    public List<AssureJeton> getBeneficiaires() {
        return beneficiaires;
    }

    /**
     * @param beneficiaires the beneficiaires to set
     */
    public void setBeneficiaires(List<AssureJeton> beneficiaires) {
        this.beneficiaires = beneficiaires;
    }

    /**
     * @return the numeroContrat
     */
    public String getNumeroContrat() {
        return numeroContrat;
    }

    /**
     * @param numeroContrat the numeroContrat to set
     */
    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    /**
     * @return the assureJeton
     */
    public AssureJeton getAssure() {
        return assureJeton;
    }

    /**
     * @param assureJeton the assureJeton to set
     */
    public void setAssure(AssureJeton assureJeton) {
        this.assureJeton = assureJeton;
    }

    /**
     * @return
     */
    public Integer getIdOrganisme() {
        return idOrganisme;
    }

    /**
     * @param idOrganisme
     */
    public void setIdOrganisme(Integer idOrganisme) {
        this.idOrganisme = idOrganisme;
    }

    /**
     * @return the jetonSSO
     */
    public JetonSSO getJetonSSO() {
        return jetonSSO;
    }

    /**
     * @param jetonSSO the jetonSSO to set
     */
    public void setJetonSSO(JetonSSO jetonSSO) {
        this.jetonSSO = jetonSSO;
    }

    /**
     * @return the idPartenaire.
     */
	public String getIdPartenaire() {
		return idPartenaire;
	}

	/**
	 * @param idPartenaire the idPartenaire to set
	 */
	public void setIdPartenaire(String idPartenaire) {
		this.idPartenaire = idPartenaire;
	}

    
    
}
