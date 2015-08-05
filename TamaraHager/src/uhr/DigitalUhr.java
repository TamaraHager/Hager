package uhr;

import gui.WeinvertriebFrame;

import java.awt.Font;
import java.awt.Label;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * @author Hager
 * Die DigitalUhr ist Beobachter.
 * Sie beobachtet den Zeitgeber (Observable)
 */
public class DigitalUhr extends JPanel implements Observer{
   	private Zeitgeber zeitgeber;
   	private JLabel label;
   	WeinvertriebFrame frame;
   
   	/**
   	 * @param zeitgeber Oberserable
   	 * @param frame Haupfenster
   	 */
   	public DigitalUhr(Zeitgeber zeitgeber, WeinvertriebFrame frame) {
   		this.zeitgeber = zeitgeber;
   		this.frame = frame;
   		// Der Beobachter (hier die Uhr) 
   		// meldet sich beim Zeitgeber an. 
   		zeitgeber.addObserver(this); // als Beobachter anmelden beim Subjekt
   		
   	}
   
   	/**
   	 * die update - Methode wird indirekt aufgerufen,
   	 * sobald der Zeitgeber seine notifyObservers() Methode 
   	 * durchläuft.
   	 */
   	public void update(java.util.Observable arg0, Object arg1){  // wird aufgerufen, wenn vom Subjekt benachrichtigt
  
   		GregorianCalendar zeit;
   		String formattedTime;
   		
   		zeit = ((Zeitgeber)arg0).gibUhrzeit(); // die Uhrzeit holen
   		
   		//Uhrzeitbestandteile ermitteln
   		int std = zeit.get(Calendar.HOUR_OF_DAY);
   		int min = zeit.get(Calendar.MINUTE);
   		int sek = zeit.get(Calendar.SECOND);
   		
		formattedTime = " " + std + " : ";
		
		if(min < 10)
			formattedTime = formattedTime + "0" + min + " : ";
		else
			formattedTime = formattedTime + min + " : ";
		
		if(sek < 10)
			formattedTime = formattedTime + "0" + sek + " ";
		else
			formattedTime = formattedTime + sek + " ";
		//der Titel vom Hauptfenster
		frame.setTitle("Weinvertrieb " + formattedTime);
   	}

	
}
