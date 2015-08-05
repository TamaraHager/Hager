package kunde;

import java.io.Serializable;
import java.util.Scanner;

/**
 * @author Tamara Hager
 */
public class Adresse implements Serializable{ 
	private String name;
	private String strasse_hnr;
	private String plz;
	private String ort;
	
	// konstruktor
	public Adresse(){
	}
	
	
	/**
	 * @param name Name des Kunden
	 * @param strasse_hnr strasse und hausnummer des Kunden
	 * @param plz plz des Kunden
	 * @param ort wohnort des Kunden
	 */
	public Adresse(String name, String strasse_hnr, String plz, String ort) {
		this.name = name;
		this.strasse_hnr = strasse_hnr;
		this.plz = plz;
		this.ort = ort;
	}

	//Setter/Getter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrasse_hnr() {
		return strasse_hnr;
	}

	public void setStrasse_hnr(String strasse_hnr) {
		this.strasse_hnr = strasse_hnr;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	//toString
	public String toString() {
		return name + ", " + strasse_hnr	+ ", " + plz + ", " + ort;
	}

}
