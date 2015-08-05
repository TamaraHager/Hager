package kunde;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import javax.swing.DefaultListModel;

import table.JTableModell;
import bestellung.Bestellung;
import bestellung.Bestellverwaltung;

/**
 * @author Tamara
 *  verwaltet alle Kunden des Weinvertriebes.
 * Diese werden in einer LinkedList gespeichert. Dazu gehört die Anschrift, das Kundenbild
 * und die Kundennummer.
 *
 */
public class Kundenverwaltung implements Serializable {

	// LinkedList, die Kundendaten speichert
	private static LinkedList<AKunde> kundenListe = new LinkedList<AKunde>();
	
	//Modelle
	DefaultListModel dml = new DefaultListModel();
	private JTableModell model = new JTableModell();
	//Attribute
	private int selection;
	private String kundenTyp;
	//Das Bild bzw. der Pfad von dem Bild
	private String bild = "./pic/6.jpg";
	
	private static Kundenverwaltung instance;
	
	/**
	 * // Konstruktor
	 */
	private Kundenverwaltung() { }
	
	
	/**
	 * @return Kundenverwaltung.instance
	 * //Singleton
	 */
	public static Kundenverwaltung getInstance () {
	    if (Kundenverwaltung.instance == null) {
	      Kundenverwaltung.instance = new Kundenverwaltung ();
	    }
	    return Kundenverwaltung.instance;
	  }

	//Getter/Setter---------------------------------------------------

	public static LinkedList<AKunde> getKundenListe() {
		return kundenListe;
	}
	public static void setKundenListe(LinkedList<AKunde> kundenListe) {
		Kundenverwaltung.kundenListe = kundenListe;
	}
	public JTableModell getModel() {
		return model;
	}

	public void setModel(JTableModell model) {
		this.model = model;
	}
	
	public String getKundenTyp() {
		return kundenTyp;
	}

	public void setKundenTyp(String kundenTyp) {
		this.kundenTyp = kundenTyp;
	}
	
	public String getBild() {
		return bild;
	}

	public void setBild(String bild) {
		this.bild = bild;
	}
	
	public DefaultListModel getDml() {
		return dml;
	}

	public void setDml(DefaultListModel dml) {
		this.dml = dml;
	}
	
	public int getSelection() {
		return selection;
	}

	public void setSelection(int selection) {
		this.selection = selection;
	}
	

//Metoden----------------------------------------------------------
	
	/**
	 * @param index
	 * @return den Index aus der Kundenliste
	 */
	public AKunde getOneKundeFromKundenListe(int index){
		return kundenListe.get(index);
	}

	
	/**
	 * @param k Kunde
	 * // Kunde hinzufügen in Liste
	 */
	public void addKunde(AKunde k) {
		kundenListe.add(k);
		dml.addElement(k);
	}

	/**
	 * @param index
	 * @param k kunde
	 * fügt den Kunden in die Liste wieder hinzu.
	 */
	public void changeKunde(int index, AKunde k){
		System.out.println(k);
		dml.setElementAt(k, index);
		/*dml.clear();
		for(AKunde kunde: kundenListe){
			dml.addElement(kunde);
		}*/
	}
	
	/**
	 * Kunde aus der Liste entfernen
	 */
	public void removeKunde(int index) {
			kundenListe.remove(index);
			dml.remove(index);
			for(int i = 0; i < kundenListe.size(); i++){
			dml.get(i);
			}
	}
	/**
	 * legt den geänderten Kunden zu diesem Kundentyp hinzu.
	 * @param anschrift
	 * @param index
	 */
	public void changedGrossverbraucher(Adresse anschrift, Bestellverwaltung bv, int index){
		Grossverbraucher gv = new Grossverbraucher();
		gv.setKundentyp("gv");
		gv.setAnschrift(anschrift);
		gv.setBv(bv);
		changeKunde(index, gv);
	}
	/**legt den geänderten Kunden zu diesem Kundentyp hinzu.
	 * @param anschrift
	 * @param index
	 */
	public void changedEndverbraucher(Adresse anschrift, Bestellverwaltung bv, int index){
		Endverbraucher ev = new Endverbraucher();
		ev.setKundentyp("ev");
		ev.setAnschrift(anschrift);
		ev.setBv(bv);
		changeKunde(index, ev);
	}
	/**legt den geänderten Kunden zu diesem Kundentyp hinzu.
	 * @param anschrift
	 * @param index
	 */
	public void changedGesellschaftMLiz(Adresse anschrift, Bestellverwaltung bv, int index){
		GesellschaftMLiz gml = new GesellschaftMLiz();
		gml.setKundentyp("gml");
		gml.setAnschrift(anschrift);
		gml.setBv(bv);
		changeKunde(index, gml);
	}
	/**
	 * Grossverbraucher in die Liste hinzufügen mit Daten, die vom Benutzer
	 * einegeben wurden Es wird zudem der Kundentyp gv festgelegt.
	 */
	public static void neuGrossverbraucher(Adresse anschrift, String bild, double rabatt) {
		Grossverbraucher gv = new Grossverbraucher();
		gv.setKundentyp("gv");
		gv.setAnschrift(anschrift);
		gv.setKundenBild(bild);
		gv.setRabatt(rabatt);
		/*Calendar calendar = Calendar.getInstance();
		Date datum =  calendar.getTime();*/
		//gv.getBv().neuBestellung(datum, 6, "Dornfelder", "Flasche", 10.0, 0.0);
		Kundenverwaltung.getInstance().addKunde(gv);
	}

	/**
	 * Endverbraucher in die Liste hinzufügen mit Daten, die vom Benutzer
	 * einegeben wurden Es wird zudem der Kundentyp ev festgelegt.
	 */
	public static void neuEndverbraucher(Adresse anschrift, String bild, double rabatt) {
		Endverbraucher ev = new Endverbraucher();
		ev.setKundentyp("ev");
		ev.setAnschrift(anschrift);
		ev.setKundenBild(bild);
		ev.setRabatt(rabatt);
		Calendar calendar = Calendar.getInstance();
		Date datum =  calendar.getTime();
		//ev.getBv().neuBestellung(datum, 6, "Chardonnay", "Flasche", 10.0, 0.0);
		Kundenverwaltung.getInstance().addKunde(ev);
	}

	/**
	 * GesellschaftMLiz in die Liste hinzufügen mit Daten, die vom Benutzer
	 * eingeben wurden Es wird zudem der Kundentyp gml festgelegt.
	 */
	public static void neuGesellschaftMLiz(Adresse anschrift, String bild, double rabatt) {
		GesellschaftMLiz gml = new GesellschaftMLiz();
		gml.setKundentyp("gml");
		gml.setAnschrift(anschrift);
		gml.setKundenBild(bild);
		gml.setRabatt(rabatt);
		//gml.getBv().neuBestellung(datum, 6, "Riesling", "Flasche", 10.0, 0.0);
		//gml.getBv().neuBestellung(datum, 5, "Chardonnay", "Flasche", 10.0, 0.0);
		Kundenverwaltung.getInstance().addKunde(gml);
	}

	/**
	 * erzeugt automatisch drei Kunden.
	 */
	public void erzeugeKundenAutomatisch(){
		neuGesellschaftMLiz(new Adresse("Hans Meier","Berlinerstr 54", "10527", "Berlin"), "./pic/4.jpg", 0.0);
		neuGrossverbraucher(new Adresse("Anna Müller","Hambugerstr 54", "13427", "Hamburg"),"./pic/9.jpg", 2.0 );
		neuEndverbraucher(new Adresse("Klaus Peter","Münchnerstr 54", "19767", "München"), "./pic/8.jpg", 1.0);
		}
		
	
}//end of class
