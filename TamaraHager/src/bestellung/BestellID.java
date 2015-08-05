package bestellung;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import kunde.KundenNummerVergeber;
import kunde.Kundenverwaltung;

/**
 * @author Hager
 * generiert eine eindeutige BestellId, diese wird automatisch erzeugt 
 * wenn eine Bestellung angelegt wird.
 *
 */
public class BestellID implements Serializable{

	// Singleton
	private static BestellID instance;

	private BestellID() { }

	public static BestellID getInstance () {
	    if (BestellID.instance == null) {
	      BestellID.instance = new BestellID();
	    }
	    return BestellID .instance;
	  }
	
	/**
	 * Generiert eine zufällige ID, die aus Buchstaben und Zahlen besteht
	 * @return zufällige ID
	 */
	public String createID(){
		String bestellid ="";
		// Array befüllen mit kleinen Buchstaben von a-z
		char[] smallAlphabet = new char[26];
		char a = 97; // erster Buchstabe (kleines a)
		for (int i = 0; i < smallAlphabet.length; i++, a++) {
			smallAlphabet[i] = a;
		}

		// 3 random small Letters
		for (int zahl = 0; zahl < 3; zahl++) {
			int smallLetter = (int) (Math.random() * 26);
			String s = Character.toString(smallAlphabet[smallLetter]);
			bestellid = bestellid.concat(s); // concat -> verbindet 2 Strings zu einem
		}
		// 3 random numbers
		for (int i = 0; i < 5; i++) {
			int zahl = (int) (Math.random() * 10);
			String s = Integer.toString(zahl);
			bestellid = bestellid.concat(s); // concat -> verbindet 2 Strings zu einem
		}
		
		return bestellid;
	}
	
	//alle eindeutigen IDs
	ArrayList<String> bestellIDs = new ArrayList<String>();
	
	/**
	 * erstellt eine zufällige ID. Danach wird geprüft ob diese bereits in der
	 * Liste bestellIDs vorhanden ist. Wenn ja, dann wir eine neue generiert und das so 
	 * lange bis diese sich noch nicht in der Liste vorhanden ist.
	 * Wenn diese nicht doppelt ist, dann wird diese in die Liste hinzugefügt
	 * und zurückgegeben.
	 * @return eindeutige ID
	 */
	public String addBestellID(){
		//generiere eine Id
		String id = createID();
	
		//prüfe ob die schon extistiert
		if (bestellIDs.contains(id) == true){ // wenn ja
			//dann erstelle eine neue id und übergebe diese
			id = addBestellID();
			
			//wenn diese nicht in der Liste existiert
		} else if(bestellIDs.contains(id) == false){
			//dann füge diese in die ArrayListe hinzu
			bestellIDs.add(id);
		}
		//gebe die eindeutige id zurück an die Klasse Bestellung
		return id;
	}
}// end of class
