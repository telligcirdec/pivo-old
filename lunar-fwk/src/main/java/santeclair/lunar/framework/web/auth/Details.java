package santeclair.lunar.framework.web.auth;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

/**
 * Détails de la garantie hospitalière
 * @author pchaussalet
 */
public class Details implements Serializable {
	private static final long serialVersionUID = 4557152172519035633L;
	private String forfaitJournalier;
	private String fraisSejour;
	private String chambreParticuliere;
	private String fraisAccompagnant;
	private String primeMaternite;
	private String transport;

	   @XmlElement(name="chambreparticuliere")
	public String getChambreParticuliere() {
		return this.chambreParticuliere;
	}
	public void setChambreParticuliere(String chambreParticuliere) {
		this.chambreParticuliere = chambreParticuliere;
	}

	@XmlElement(name="forfaitjournalier")
	public String getForfaitJournalier() {
		return this.forfaitJournalier;
	}

	public void setForfaitJournalier(String forfaitJournalier) {
		this.forfaitJournalier = forfaitJournalier;
	}

	@XmlElement(name="fraisaccompagnant")
	public String getFraisAccompagnant() {
		return this.fraisAccompagnant;
	}

	public void setFraisAccompagnant(String fraisAccompagnement) {
		this.fraisAccompagnant = fraisAccompagnement;
	}

	@XmlElement(name="fraissejour")
	public String getFraisSejour() {
		return this.fraisSejour;
	}

	public void setFraisSejour(String fraisSejour) {
		this.fraisSejour = fraisSejour;
	}

	@XmlElement(name="primematernite")
	public String getPrimeMaternite() {
		return this.primeMaternite;
	}
	public void setPrimeMaternite(String primeMaternite) {
		this.primeMaternite = primeMaternite;
	}

	public String getTransport() {
		return this.transport;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
}

