package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.StreamCorruptedException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Set;

import javax.swing.JOptionPane;

import bestellung.Bestellung;
import kunde.*;

/**
 * 
 * @author Hager & Bui
 *
 *         Hilfsklasse für Dateihandling
 */
public class DateiIO {

	/**
	 * Liest die Kundendaten und deren Bestellungen in die csv datei
	 * @param filename
	 * @param kListe
	 */
	public static void writeCSV(String filename, LinkedList<AKunde> kListe){
		// Datei einlesen ---
				PrintStream out = null;
				try {
					out = new PrintStream(new BufferedOutputStream(new FileOutputStream(filename))); //OutputStream
					
						for (AKunde k : kListe) {
							
							if(k.getKundentyp().equals("ev")){ //Endverbraucher
								//Kundentyp, Name, Strasse, Plz, Ort, Rabbatt, Bonität, Kundenbild, Anzahl Bestellungen
							out.print(k.getKundentyp() + ";" 
									+ k.getAnschrift().getName()  + ";" + k.getAnschrift().getStrasse_hnr() + ";" + k.getAnschrift().getPlz() + ";" + k.getAnschrift().getOrt()+ ";" 
									+ k.getRabatt() + ";" +((Endverbraucher)k).getBonitaet()+ ";" + k.getKundenBild()+ ";"+ k.getBv().getAnzahlBestellungen() + ";");
			
									bestellungSpeichern(k, out);
									out.println();
									
							}if(k.getKundentyp().equals("gv")){ //Großverbraucher
								
								//Kundentyp, Name, Strasse, Plz, Ort, Rabbatt, Bonität, Kundenbild, Anzahl Bestellungen
								out.print(k.getKundentyp() + ";" 
									+ k.getAnschrift().getName()  + ";" + k.getAnschrift().getStrasse_hnr() + ";" + k.getAnschrift().getPlz() + ";" + k.getAnschrift().getOrt()+ ";" 
									+ k.getRabatt() + ";" +((Grossverbraucher)k).getStatus()+ ";" + k.getKundenBild()+ ";"+ k.getBv().getAnzahlBestellungen() + ";");
								
									bestellungSpeichern(k, out);
							
									out.println();
									
							}if(k.getKundentyp().equals("gml")){//Gesellschaft
								
									//Kundentyp, Name, Strasse, Plz, Ort, Rabbatt, Vertrieb
									out.print(k.getKundentyp() + ";" 
											+ k.getAnschrift().getName()  + ";" + k.getAnschrift().getStrasse_hnr() + ";" + k.getAnschrift().getPlz() + ";" + k.getAnschrift().getOrt()+ ";" 
											+ k.getRabatt() + ";" +((GesellschaftMLiz)k).getVertrieb()+ ";" + k.getKundenBild() + ";"+ k.getBv().getAnzahlBestellungen() + ";");
									
									bestellungSpeichern(k, out);
									out.println();
						}//end if
					}//end for
				} catch (IOException e) {
					System.out.println("Fehler Datei= " + filename + " konnte nicht geöffnet werden");
					final JOptionPane optionPane = new JOptionPane(
							"Fehler Datei= " + filename + " konnte nicht geöffnet werden",
						    JOptionPane.ERROR_MESSAGE, JOptionPane.OK_OPTION);
				}
				if (out != null) {
					//Schließen
					out.close();
				}
	}
	/**
	 * Durchläuft die Bestellungsliste vom jeweiligen Kunden
	 * und holt sich die Werte aus der Bestellung.
	 * @param k Kunde
	 * @param out 
	 */
	public static void bestellungSpeichern(AKunde k, PrintStream out){
		Set <String> keyset = k.getBv().getBestellungListe().keySet();
		Iterator <String> it = keyset.iterator();
		while(it.hasNext()){
			String key = it.next();
			Bestellung b = k.getBv().getBestellungListe().get(key);
			//Datum
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			Date today = b.getDatum();      
			String reportDate = df.format(today);
			//BestellId, Datum, Anzahl, Verpackung, Weinsorte, EinzelPreis, Rabatt
				out.print(b.getBestellID() + ";" + reportDate+ ";" + b.getAnzahl() + ";" + b.getVerpackung()+ ";" + b.getWein() + ";" + b.getEinzelPreis()
						+ ";" +  b.getRabatt() +";");
		}//end while
	}

	/**
	 * @param dateiname
	 *            - aus der Datei soll gelesen werden
	 * @return LinkedList<String> - Liste der gelesenen Zeilen
	 */
	public static LinkedList<AKunde> dateiLesenZeilenweise(String dateiname) {

		File datei = new File(dateiname);
		String zeile = null; // hier soll eingelesen werden
		BufferedReader in = null;
		 AKunde kundeEV = null;
		 AKunde kundeGV = null;
		 AKunde kundeGML = null;
		LinkedList<AKunde> zeilenListe = new LinkedList();

		// Stream öffnen
		try {
			in = new BufferedReader(new FileReader(datei));
		} catch (FileNotFoundException e) {
			System.out.println("Fehler Datei= " + dateiname + " konnte nicht geöffnet werden");
			e.printStackTrace();
		}

		// Stream lesen
		try {
			while ((zeile = in.readLine()) != null) {
				String[] token = zeile.split(";");
				if (token[0].equals("ev")) {
					
					kundeEV = new Endverbraucher(Double.parseDouble(token[5]),Boolean.parseBoolean(token[6]));
					kundeEV.setKundentyp("ev");
					
					kundeUndBestellungEinlesen(kundeEV, token);
					
					// die gelesene Zeile abspeichern in die Liste
					Kundenverwaltung.getInstance().addKunde(kundeEV);
					
				} if (token[0].equals("gv")){ 
					
					 kundeGV = new Grossverbraucher(Double.parseDouble(token[5]),Integer.parseInt(token[6])); 
					 kundeGV.setKundentyp("gv");
					 
					 kundeUndBestellungEinlesen(kundeGV, token);
				
					// die gelesene Zeile abspeichern in die Liste
					 Kundenverwaltung.getInstance().addKunde(kundeGV);
					 
				} if(token[0].equals("gml")){ //Gesellschaft
					
					 kundeGML = new GesellschaftMLiz(Double.parseDouble(token[5]),Integer.parseInt(token[6])); //Neuer Kunde
					 kundeGML.setKundentyp("gml");
					
					 kundeUndBestellungEinlesen(kundeGML, token);
					 
					// die gelesene Zeile abspeichern in die Liste
					Kundenverwaltung.getInstance().addKunde(kundeGML);
				}
				
			}
		} catch (IOException e) {
			
			System.out.println("Fehler Datei= " + dateiname + " konnte nicht gelesen werden");
			e.printStackTrace();
		}

		// Stream schließen
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// die Liste der gelesenen Zeilen zurückgeben
		return zeilenListe;
		
	}// end of methode

	/**
	 * Die CSV datei wird ausgelesen.
	 * @param kunde
	 * @param token
	 */
	public static void kundeUndBestellungEinlesen(AKunde kunde, String[] token){
		// Kundennummer, Name, Strasse, Plz, Ort
		 Adresse anschrift3 = (new Adresse(token[1], token[2],token[3], token[4])); 
		 kunde.setAnschrift(anschrift3); 
		//Bild
		 String bildpfad =token[7]; 
		 kunde.setKundenBild(bildpfad);
		 //Anzahl der Bestellungen vom Kunden
			int a = Integer.parseInt(token[8]);
			int anzahl = (a*6)+ 10;
		try{
			 for (int i = 10; i< anzahl; ){
					
					String string = token[i]; //Datum
					DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.GERMAN);
					Date date = null;
					try {
						date = format.parse(string); //Date to String
					} catch (ParseException e) {
						System.out.println("Date konnte nicht verarbeitet werden.");
						
					}
					//Datum, Anzahl, Weinsorte, Verpackung, Einzelpreis, Rabatt
					kunde.getBv().neuBestellung(date, Integer.parseInt(token[(i+1)]), token[(i+3)], token[(i+2)], Double.parseDouble(token[(i+4)]),Double.parseDouble(token[(i+5)]));
					i = i+7;
			 }
		}catch(Exception e){
				System.out.println("ein Fehler ist aufgetreten.");
			}
		
	}
	/**
	 * @param filename- in diese Datei soll geschrieben werden
	 * @return Object, das gelesen wurde oder null, falls nicht(s) gelesen wurde
	 * @throws IOException 
	 */
	public static Object readObject(File filename) throws IOException {
		Object obj = null;
		 ObjectInputStream in = null;
		
		// Stream öffnen
				try {
					in = new ObjectInputStream(new BufferedInputStream(new FileInputStream( filename )));
				} 
				catch (FileNotFoundException e) {
					final JOptionPane optionPane = new JOptionPane(
							"Fehler Datei= " + filename + " konnte nicht geöffnet werden",
						    JOptionPane.ERROR_MESSAGE,
						    JOptionPane.OK_OPTION);
					e.printStackTrace();
				}	
					// Lesen des Objektes
					  try {
							obj = in.readObject();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}	
					try{	  
						  in.close(); // Stream schließen	  
					}catch(IOException exp){
						System.out.println( exp.getMessage());
					}
		  	
				  	return obj;
	}// end of methode

	/**
	 * @param filename
	 * @param obj - Objekt, das geschrieben werden soll
	 */
	public static void writeObject(File filename,Object obj) {
		
		try{
		//	datei.createNewFile();// Neue Datei erzeugen
	
			// Erzeugen des ObjectOutputStreams
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
	  
			out.writeObject(obj); // Objekt in den Stream schreiben
			
			out.flush();// Ausschreiben des Puffers auf die Festplatte
	  	
			out.close();// Ressourcen freigeben 	

	  	}
	  	catch(IOException exp){
	  		exp.getStackTrace();
			System.out.println( exp.getMessage());
		}
	  }
}// end of class
