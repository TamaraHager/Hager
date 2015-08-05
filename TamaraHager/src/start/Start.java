package start;

import gui.WeinvertriebFrame;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import bestellung.BestellID;
import bestellung.Bestellung;
import kunde.*;

/**
 * @author Tamara Hager
 * @version 3.0 
 * JAVA SE 1.7 letzte Änderung: 19.6.15
 * 
 *         Ein kleines Programm, welches den beispielhaften Weinvertrieb unterstützt.
 *         
 *         Es können Kunden angelegt werden und zu diesem Bestellungen.
 *         
 *         Im bereitgelegten Ordner "csv_dat_Datei" liegen vorbereitete Dateien zum einlesen
 *         und speichern der Kundenliste mit Bestellungen.
 */
public class Start {

	/**
	 * @param args
	 *  Als erstes wird die LinkedList AKunde mit beispielhaften Kunden befüllt und 
	 *  danach öffnet sich das Startfenster mit einer Menüleiste.
	 */
	public static void main(String[] args) {
	
		// befüllt die Liste mit Daten
		Kundenverwaltung.getInstance().erzeugeKundenAutomatisch();
		// Haupfenster
		WeinvertriebFrame fenster = new WeinvertriebFrame();

		fenster.setVisible(true); //Fenster ist sichtbar
		fenster.setLocation(200, 200); 
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Programm beenden, wenn das Fenster geschlossen wird
		
	}

}// end of class
