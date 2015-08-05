package bestellung;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Hager
 * Beinhaltet alle Attribute für eine Bestellung
 *
 */
public class Bestellung implements Serializable{

	private int anzahl; //anzahl der Flaschen unabhängig von der Verpackungseinheit
	private Date datum; //Bestelldatum
	private double einzelPreis; //Brutto ohne Rabattabzuug
	private double gesamtPreis;
	private double rabatt = 0.0; //Rabatt abhängig vom Kunden
	private double gesamtPreisNetto;
	private String wein; // zusammenbringen mit Preis pro flasche
	private String bestellID = BestellID.getInstance().addBestellID();
	private String verpackung;
	
	
	//Getter/Setter
	
	public int getAnzahl() {
		return anzahl;
	}
	public String getVerpackung() {
		return verpackung;
	}
	public void setVerpackung(String verpackung) {
		this.verpackung = verpackung;
	}
	public double getGesamtPreis() {
		return gesamtPreis;
	}
	public void setGesamtPreis(double gesamtPreis) {
		this.gesamtPreis = gesamtPreis;
	}
	public String getBestellID() {
		return bestellID;
	}
	public void setBestellID(String bestellID) {
		this.bestellID = bestellID;
	}
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public double getEinzelPreis() {
		return einzelPreis;
	}
	public void setEinzelPreis(double gesamtPreis) {
		this.einzelPreis = gesamtPreis;
	}
	public double getRabatt() {
		return rabatt;
	}
	public void setRabatt(double gewaehrterRabatt) {
		this.rabatt = gewaehrterRabatt;
	}
	public double getGesamtPreisNetto() {
		return gesamtPreisNetto;
	}
	public void setGesamtPreisNetto(double gesamtPreisNetto) {
		this.gesamtPreisNetto = gesamtPreisNetto;
	}
	
	public String getWein() {
		return wein;
	}
	public void setWein(String wein) {
		this.wein = wein;
	}
	
	//toString
	public String toString() {
		return "Bestellung [anzahl=" + anzahl + ", datum=" + datum
				+ ", einzelPreis=" + einzelPreis + ", gesamtPreis="
				+ gesamtPreis + ", gewaehrterRabatt=" + rabatt
				+ ", gesamtPreisNetto=" + gesamtPreisNetto + ", wein=" + wein
				+ ", bestellID=" + bestellID + "]";
	}

}
