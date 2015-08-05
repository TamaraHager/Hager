package menu;

import gui.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;










import bestellung.Bestellung;
import bestellung.Bestellverwaltung;
import kunde.AKunde;
import kunde.Endverbraucher;
import kunde.GesellschaftMLiz;
import kunde.Grossverbraucher;
import kunde.Kundenverwaltung;
import util.*;

/**
 * @author Hager
 *erstellt eine Mn�leiste mit den Oberpunkten: Datei, Kunde, Bestellung, Hilfe.
 */
public class MenuLeiste extends JMenuBar {

	private WeinvertriebFrame fenster;

	
	/**
	 * @param fenster
	 * //Konstruktor
	 * erstellt die Men�leiste
	 */
	public MenuLeiste(WeinvertriebFrame fenster) {
		super();
		this.fenster = fenster;
		
		// Create menu bar.
		JMenuBar menuBar = new JMenuBar();
		// Hinzuf�gen von Men�s
		JMenu menuFile = new JMenu("Datei");
		JMenu menuKunde = new JMenu("Kunde");
		JMenu menuHelp = new JMenu("Hilfe");
		JMenu menuOrder = new JMenu("Bestellung");

		menuBar.add(menuFile);
		menuBar.add(menuKunde);
		menuBar.add(menuOrder);
		menuBar.add(menuHelp);

		this.add(menuFile);
		this.add(menuKunde);
		this.add(menuOrder);
		this.add(menuHelp);

	// Hinzuf�gen von Men�eintr�gen in das Dateimen�------------------------------
		JMenuItem menuItemFileOpen = new JMenuItem("�ffnen");
		JMenuItem menuItemFileSave = new JMenuItem("Sichern");
		JMenuItem menuItemFileOpenBi = new JMenuItem("�ffnen (bin�r)");
		JMenuItem menuItemFileSaveBi = new JMenuItem("Sichern (bin�r)");
		JMenuItem menuItemFileExit = new JMenuItem("Beenden");

		menuFile.add(menuItemFileOpen);
		menuFile.add(menuItemFileSave);
		menuFile.add(menuItemFileSaveBi);
		menuFile.add(menuItemFileOpenBi);
		menuFile.addSeparator();
		menuFile.add(menuItemFileExit);

		// ActionListener: �ffnen
		menuItemFileOpen.addActionListener(new OpenCsvFile()); 

		// ActionListener: Sichern
		menuItemFileSave.addActionListener(new SaveCsvFile()); 
				
		//ActionListener: Speichern bin�r
		menuItemFileSaveBi.addActionListener(new SaveBinaryFile());
		
		//ActionListener: �ffnen bin�r 
		menuItemFileOpenBi.addActionListener(new OpenBinaryFile());
		
		// ActionListener: Beenden
		menuItemFileExit.addActionListener(new ActionListener() {
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 * 
			 */
			public void actionPerformed(ActionEvent arg3) {
				//Nachfrage ob Programm beendet werden soll
				if (JOptionPane.showConfirmDialog(null,
						"M�chten Sie das Programm beenden?", "Beenden",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

						//Antwort: Ja,
						//Nachfrage ob die Kunden gespeichert werden sollen
							if(JOptionPane.showConfirmDialog(null,
									"M�chten Sie ihre Kunden speichern?", "Speichern",
									JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								
								//Antwort: ja
								//Ob sie als Csv gespeichert werden sollen?
								if(JOptionPane.showConfirmDialog(null,
										"M�chten Sie das als .csv speichern?", "Speichern",
										JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
									//Antwort: Ja
										saveCsv();
									}else{
										//Nein, dann bin�r
										saveObject();
									}
								}else{
									//Nein - Programm beenden
									System.exit(0);
								}
				}else{
					//close OptionPane
				}
			}
		});

	// Hinzuf�gen von Men�eintr�gen in das Kundenmen�------------------
		JMenuItem menuItemKundeNew = new JMenuItem("Neu");
		JMenuItem menuItemKundeChange = new JMenuItem("�ndern");
		JMenuItem menuItemKundeDelete = new JMenuItem("L�schen");

		menuKunde.add(menuItemKundeNew);
		menuKunde.add(menuItemKundeChange);
		menuKunde.add(menuItemKundeDelete);

		// ActionListener: Neu
		menuItemKundeNew.addActionListener(new ActionListener() {
			//�ffnet neues Fenster, worin ein neuer Kunde angelegt werden kann
			public void actionPerformed(ActionEvent arg0) {
				CreateUserFrame kf = new CreateUserFrame();
				kf.setVisible(true);
				kf.setSize(500, 300);
				kf.setLocation(100, 100);
			}
		});

		// ActionListener: �ndern
		menuItemKundeChange.addActionListener(new ActionListener() {
			//�ffnet ein neues Fenster, worin die Kundendaten ge�ndert werden k�nnen
			public void actionPerformed(ActionEvent arg1) {
				ChangeUserFrame cu = new ChangeUserFrame();
				cu.setVisible(true);
			}
		});

		// ActionListener: L�schen
		menuItemKundeDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				int index = Kundenverwaltung.getInstance().getSelection();
				
				//Nachfrage, ob der kunde wirklich gel�scht werden soll
				if (JOptionPane.showConfirmDialog(null,
						"M�chten Sie den Kunden wirklich l�schen?", "L�schen",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					//Ja
					Kundenverwaltung.getInstance().removeKunde(index);
				} else {
					// no option
				}		
			}
		});

	//Hinzuf�gen von Men�eintr�gen in das Bestellungsmen�----------------------
		JMenuItem menuItemOrderNew = new JMenuItem("neu");
		JMenuItem menuItemOrderChange = new JMenuItem("�ndern");
		JMenuItem menuItemOrderDelete = new JMenuItem("l�schen");
		
		menuOrder.add(menuItemOrderNew);
		menuOrder.add(menuItemOrderChange);
		menuOrder.add(menuItemOrderDelete);
		
		//ActionListener: Neu
		menuItemOrderNew.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//selektierter Kunde
				int index = Kundenverwaltung.getInstance().getSelection();
				AKunde selektierterKunde = Kundenverwaltung.getInstance().getOneKundeFromKundenListe(index);
				//neues Fenster- Bestellung �ndern
				NewOrderFrame nof = new NewOrderFrame(selektierterKunde);
				nof.setVisible(true);
			}
		});
		
		//ActionListener: �ndern
				menuItemOrderChange.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						try{
							ChangeOrderFrame cof = new ChangeOrderFrame(); //neues Fenster
							cof.setVisible(true);
						}catch(Exception e){
							//wenn keine Bestellung ausgew�hlt wurde
							JOptionPane.showMessageDialog(null, "Bitte w�hlen Sie eine Bestellung aus.", "Warnung", 
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				
		//ActionListener: l�schen
				menuItemOrderDelete.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						try{
							//Selektierter Kunde
						int index = Kundenverwaltung.getInstance().getSelection();
						AKunde selektierterKunde = Kundenverwaltung.getInstance().getOneKundeFromKundenListe(index);
						//selektierte Bestellung
						int rowindex = selektierterKunde.getBv().getRowSelection();
						String bestellId = selektierterKunde.getBv().getSelectedId();
						//Bestellung an dieser Stelle(Reihenindex, 1. Spalte) l�schen
						selektierterKunde.getBv().removeBestellung(rowindex, bestellId);
						}catch(Exception e){
							//Wenn keine Bestellung ausgew�hlt wurde
							JOptionPane.showMessageDialog(null, "Bitte w�hlen Sie eine Bestellung aus.", "Warnung", 
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});		
				
		// Hinzuf�gen von Men�eintr�gen in das Hilfemen�
		JMenuItem menuItemHelpAuthor = new JMenuItem("Autor/in");
		JMenuItem menuItemHelpInfo = new JMenuItem("Information");

		menuHelp.add(menuItemHelpInfo);
		menuHelp.add(menuItemHelpAuthor);

		menuItemHelpInfo.addActionListener(new ActionListener() {
			//Informationen �ber den Verzeichnispfad der Bilder
			public void actionPerformed(ActionEvent e) {
				Info i = new Info();
			}
		});
		menuItemHelpAuthor.addActionListener(new ActionListener() {
			//Informationen �ber den Autor
			public void actionPerformed(ActionEvent e) {
				Author ar = new Author();	
			}
		});
				
	}//end of constr.
	
	/**
	 * �ffnet eine .dat Datei, die Kundendaten enth�lt.
	 * @throws IOException
	 */
	public void openObject() throws IOException{
		
		JFileChooser dateiWahl = new JFileChooser();
		//erm�glicht nach .dat zu filtern
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma Delimited (*.dat)", "dat");
		dateiWahl.addChoosableFileFilter(filter);
		dateiWahl.setFileFilter(filter);
		dateiWahl.setMultiSelectionEnabled(false);
		dateiWahl.setCurrentDirectory(new File("."));
		
		int retValue = dateiWahl.showOpenDialog(fenster);

		if (retValue == JFileChooser.APPROVE_OPTION) {
		
			File kundeDatei = dateiWahl.getSelectedFile();
			String filename = kundeDatei.getAbsolutePath();
			// Datei einlesen ---
					LinkedList<AKunde> k1 = (LinkedList<AKunde>) DateiIO.readObject(kundeDatei);
					for(int i= 0; i<k1.size(); i++){
						Kundenverwaltung.getInstance().addKunde(k1.get(i));
				}
		}
		
	}
	/**
	 * speichert die Kunden und deren Bestellungen in der .dat datei
	 */
	public void saveObject(){
	
		LinkedList<AKunde> kundenliste; 
		 kundenliste = Kundenverwaltung.getInstance().getKundenListe();
		
		JFileChooser dateiWahl = new JFileChooser();
		//erm�glicht nach .dat zu filtern
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma Delimited (*.dat)", "dat");
		dateiWahl.addChoosableFileFilter(filter);
		dateiWahl.setFileFilter(filter);
		dateiWahl.setMultiSelectionEnabled(false);
		dateiWahl.setCurrentDirectory(new File("."));
		
		int retValue = dateiWahl.showOpenDialog(fenster);

		if (retValue == JFileChooser.APPROVE_OPTION) {
			File kundeDatei = dateiWahl.getSelectedFile();
			String filename = kundeDatei.getAbsolutePath();
			
			// Datei einlesen ---
			DateiIO.writeObject(kundeDatei, kundenliste);
		}
	}
	
	/**
	 * Speichert die Kundenliste und Bestellungen in der csv datei.
	 */
	public void saveCsv() {
		LinkedList<AKunde> kListe = Kundenverwaltung.getInstance().getKundenListe();
		
		JFileChooser dateiWahl = new JFileChooser();
		//erm�glicht nach .csv zu filtern
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma Delimited (*.csv)", "csv");
		dateiWahl.addChoosableFileFilter(filter);
		dateiWahl.setFileFilter(filter);
		dateiWahl.setMultiSelectionEnabled(false);
		dateiWahl.setCurrentDirectory(new File("."));
	
		int retValue = dateiWahl.showOpenDialog(fenster);

		if (retValue == JFileChooser.APPROVE_OPTION) {
			File kundeDatei = dateiWahl.getSelectedFile(); //Dateipfad
			String filename = kundeDatei.getAbsolutePath();
			DateiIO.writeCSV(filename, kListe);
		}
	}

	/**
	 * �ffnet die csv datei, worin sich Kunden befinden.
	 */
	public void openCSV() {

		JFileChooser fc = new JFileChooser();
		//erm�glicht nach .csv zu filtern
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma Delimited (*.csv)", "csv");
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		fc.setMultiSelectionEnabled(false);
		fc.setCurrentDirectory(new File("."));

		int retValue = fc.showOpenDialog(fenster);

		if (retValue == JFileChooser.APPROVE_OPTION) {

			File kundeDatei = fc.getSelectedFile(); //ausgew�hlte Datei

			// Datei einlesen ---
			LinkedList<AKunde> zeilenliste = DateiIO.dateiLesenZeilenweise(kundeDatei.getAbsolutePath());
		}
	}

	/**
	 * @author Hager
	 * �ffnet die  CSVdatei, die ausgew�hlt wurde und l�d sie in das Programm.
	 *
	 */
	private class OpenCsvFile implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			openCSV();
		}
	}
	/**
	 * @author Hager
	 * Speichert die Kunden und deren Bestellungen in einer csv Datei.
	 */
	private class SaveCsvFile implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("Im Ordner csv_dat_datei liegt eine csv Datei bereitgestellt.");
			saveCsv();
			
		}
	}
	/**
	 * @author Hager
	 *Speichert die Kunden und deren Bestellungen in einer dat Datei.
	 */
	private class SaveBinaryFile implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			saveObject();
		}
	}
	/**
	 * @author Hager
	 *�ffnet die  dat datei, die ausgew�hlt wurde und l�d sie in das Programm.
	 */
	private class OpenBinaryFile implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			try {
				openObject();
			} catch (IOException e) {
				//Datei konnte nicht ge�ffnet werden.
				final JOptionPane optionPane = new JOptionPane(
						"Fehler Datei konnte nicht ge�ffnet werden",
					    JOptionPane.ERROR_MESSAGE,
					    JOptionPane.OK_OPTION);
				e.printStackTrace();
			}
		}
	}
	
}//end of class

