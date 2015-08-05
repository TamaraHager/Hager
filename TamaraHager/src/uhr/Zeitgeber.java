package uhr;

import java.util.*;

/**
 * 
 * Der Zeitgeber ist Observable 
 * Er wird beobachtet - hier von den Uhren.
 * Jede Uhr muss sich zuvor beim Zeitgeber anmelden
 * Wenn der Zeitgeber seine notifyObservers() Methode 
 * aufruft, werden alle Beobachter, die sich angeeldet 
 * haben, benachrichtigt (über die update-Methode)
 */
public class Zeitgeber extends Observable implements Runnable 
{
    private GregorianCalendar uhrzeit;
    Thread thread;
   
    public Zeitgeber() {  		
       	thread = new Thread(this);
       	thread.start();
    }
   
    public GregorianCalendar gibUhrzeit() {
    	return uhrzeit;
    }

    public void run() {
        while(true){
        	uhrzeit = new GregorianCalendar();
        	setChanged();
        	
        	if(hasChanged()) {
            	notifyObservers(); // alle Beobachter benachrichtigen
            	clearChanged();
            }
            try{
                thread.sleep(1000); // 1000 ms = 1 sec
            }catch (InterruptedException e){
                System.err.println(e.getMessage());
            }
        }
    }
}
