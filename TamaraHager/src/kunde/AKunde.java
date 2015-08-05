package kunde;

import java.io.Serializable;

import bestellung.Bestellverwaltung;

/**
 * @author Tamara Hager
 *
 */
public abstract class AKunde implements Serializable{

	private String kundenNummer = KundenNummerVergeber.createKundenNummer();
	protected double rabatt;
	protected Adresse anschrift = new Adresse();
	protected String kundentyp;
	protected String kundenbild = "./pic/6.jpg";
	Bestellverwaltung bv = new Bestellverwaltung();
	
	// konstruktor
	public AKunde() {}

	/**
	 * @param rabatt Rabatt 
	 */
	public AKunde(double rabatt) {
		this.rabatt = rabatt;
	}

	// Getter/Setter
	public String getKundenBild() {
		return kundenbild;
	}
	public void setKundenBild(String bild) {
		this.kundenbild = bild;
	}

	public Bestellverwaltung getBv() {
		return bv;
	}

	public void setBv(Bestellverwaltung bv) {
		this.bv = bv;
	}

	public String getKundentyp() {
		return kundentyp;
	}

	public void setKundentyp(String kundentyp) {
		this.kundentyp = kundentyp;
	}
	public double getRabatt() {
		return rabatt;
	}

	public void setRabatt(double rabatt) {
		this.rabatt = rabatt;
	}

	public String getKundenNummer() {
		return kundenNummer;
	}

	public Adresse getAnschrift() {
		return anschrift;
	}

	public void setAnschrift(Adresse anschrift) {
		this.anschrift = anschrift;
	}

	//toString
	public String toString() {
		return "AKunde [rabatt=" + rabatt + ", anschrift=" + ", nummer="+ kundenNummer + "]";
	}
}//end of class
