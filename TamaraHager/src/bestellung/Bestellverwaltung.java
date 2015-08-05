package bestellung;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import kunde.*;

import java.util.HashMap;

import table.JTableModell;
import kunde.*;

/**
 * @author Hager
 * verwaltet alle Bestellungen. Hier werden neue Bestellungen erstellt
 * sowie geändert. Zudem werden diese in die HashMap und JTable hinzugefügt.
 */
public class Bestellverwaltung implements Serializable {
	//Attribute
	int rowSelection;
	String selectedId;
	AKunde meinKunde = null;
	JTableModell model = Kundenverwaltung.getInstance().getModel();
	
	HashMap<String, Bestellung>  bestellungListe = new HashMap<String, Bestellung>(); //alle Bestellungen
	
	
	public Bestellverwaltung() { }

	public Bestellverwaltung(AKunde k) {
		super();
		this.meinKunde = k;
	} 
	//Getter/setter
	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public int getRowSelection() {
		return rowSelection;
	}

	public void setRowSelection(int rowSelection) {
		this.rowSelection = rowSelection;
	}

	public void setBestellungListe(HashMap<String, Bestellung> bestellungListe) {
		this.bestellungListe = bestellungListe;
	}
	
	public HashMap<String, Bestellung> getBestellungListe() {
		return bestellungListe;
	}
	
	/**
	 * erstellt eine Bestellung mit den eingebenen Daten vom Benutzer
	 * und berechnet den Bruttopreis abzüglich möglicher Rabatte
	 * und berechent danach den Nettopreis.
	 * @param datum
	 * @param anzahl der Flaschen/Kartons/Paletten
	 * @param wein Weindsorte
	 * @param verpackungseinheit
	 * @param einzelPreis
	 * @param rabatt vom Kunden
	 */
	public void neuBestellung(Date datum, int anzahl, String wein, String verpackungseinheit, double einzelPreis, double rabatt){
		double gesamtPreis = 0;
		Bestellung b = new Bestellung();
		
		b.setAnzahl(anzahl);
		b.setDatum(datum);
		b.setEinzelPreis(einzelPreis);

		if(verpackungseinheit.equals("Palette")){
			b.setVerpackung("Palette");
			gesamtPreis = (anzahl * 36)*einzelPreis; //Palette beinhaltet 36 Flaschen
		}else if(verpackungseinheit.equals("Karton")){
			b.setVerpackung("Karton");
			gesamtPreis = (anzahl * 12)*einzelPreis; //Karton beinhaltet 12 Flaschen
		}else if(verpackungseinheit.equals("Flasche")){
			b.setVerpackung("Flasche");
			gesamtPreis =einzelPreis * anzahl;
		}
		//Rabatt berechnen
		double rabattPreis = (gesamtPreis/100)*rabatt;
			//Bruttopreis minus Rabatt
		double preisMitRabatt = gesamtPreis - rabattPreis;
		b.setGesamtPreis(preisMitRabatt); //Bruttopreis mit Rabatt
		//Netto
		double gesamtPreisNetto = preisMitRabatt/1.07;
		b.setGesamtPreisNetto(gesamtPreisNetto);
		
		b.setRabatt(rabatt);
		b.setWein(wein); //Weinsorte
		//Bestellung in die HashMap und Jtable hinzufügen
		addBestellung(b);
		
	}
	
	/**
	 * Die unten aufgeführtenn Parameter werden übergeben von der geänderte Bestellung vom ausgewählten Kunden.
	 * Die BestellId wird übertragen, sowie das Erstelldatum und der Rabatt vom Kunden.
	 * Die geänderten Daten werden in die HashMap und JTable geladen.
	 * @param b Bestellung
	 * @param datum Datum von der geänderten Bestellung
	 * @param anzahl der Bestellungen
	 * @param wein Weinsorte
	 * @param verpackungseinheit
	 * @param einzelPreis ist der Bruttopreis einer Flasche
	 * @param rabatt vom Kunden
	 */
	public void changeBestellung(Bestellung b, Date datum, int anzahl, String wein, String verpackungseinheit, double einzelPreis, double rabatt){
		double gesamtPreis = 0;
		b.setAnzahl(anzahl);
		b.setDatum(datum);
		b.setEinzelPreis(einzelPreis);

		if(verpackungseinheit.equals("Palette")){
			b.setVerpackung("Palette");
			gesamtPreis = (anzahl * 36)*einzelPreis; //Palette beinhaltet 36 Flaschen
		}else if(verpackungseinheit.equals("Karton")){
			b.setVerpackung("Karton");
			gesamtPreis = (anzahl * 12)*einzelPreis; //Karton beinhaltet 12 Flaschen
		}else if(verpackungseinheit.equals("Flasche")){
			b.setVerpackung("Flasche");
			gesamtPreis =einzelPreis * anzahl;
		}
		//Rabatt berechnen
		double rabattPreis = (gesamtPreis/100)*rabatt;
			//Bruttopreis minus Rabatt
		double preisMitRabatt = gesamtPreis - rabattPreis;
		b.setGesamtPreis(preisMitRabatt); //Bruttopreis mit Rabatt
		//Netto
		double gesamtPreisNetto = preisMitRabatt/1.07;
		
		b.setGesamtPreisNetto(gesamtPreisNetto);
		
		b.setRabatt(rabatt);
		b.setWein(wein); //Weinsorte
		//geänderte Daten werden in die HashMap und JTable hinzugefügt
		addChangedBestellung(b);
	}
	/**
	 * fügt eine Bestellung in die HashMap und in die JTable bzw. in den Vector
	 * der Klasse JTableModell.
	 * @param b Bestellung
	 */
	public void addBestellung(Bestellung b){
		//Key, Value
		bestellungListe.put(b.getBestellID(), b);
		model.addRow(b);
	}
	
	/**
	 * fügt die geänderte Bestellung in die Liste und aktualisiert die JTable
	 * bzw die Klasse JTableModell
	 * @param b Bestellung
	 */
	public void addChangedBestellung(Bestellung b){
		//den Key von der alten/neuen Bestellung und deren Inhalt in die Liste einfügen
		bestellungListe.put(b.getBestellID(),b);
		//setze das Model zurück
		model.resetModel();
		
		//Befülle das Model neu mit Bestellungen die in der Liste sind
		Set <String> keyset = bestellungListe.keySet();
		Iterator <String> it = keyset.iterator();
		while(it.hasNext()){
			String key = it.next();
			Bestellung b1 = bestellungListe.get(key);
			model.addRow(b1);
		}
	}
	
	/**
	 * löscht die Bestellung in der HashMap und im JTableModel
	 * @param index wo sich die Bestellung befindet
	 * @param id der bestellung
	 */
	public void removeBestellung(int index, String id){
		bestellungListe.remove(id);
		model.delRow(index);
	}
	
	/**
	 * holt sich die länge der HashMap, die die Anzahl
	 * der Bestellungen widerspiegelt.
	 * @return anzahl der Bestellungen
	 */
	public int getAnzahlBestellungen(){
		int anzahl = bestellungListe.size();
		return anzahl;
	}
	
}
