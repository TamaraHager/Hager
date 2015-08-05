package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import kunde.*;
import bestellung.Bestellung;
import bestellung.Bestellverwaltung;
import bestellung.WeinSorte;

/**
 *  @author Hager
 *Es wird ein Fenster geladen, welches dem Benutzer ermöglicht eine neue Bestellung
 *für den zuvor selektierten Kunden anzulegen. 
 */
public class NewOrderFrame extends JDialog {
	//Attribute
	private JDialog bestellung;
	private String radioTyp;
	private JTextField txtAnzahl;
	private JTextField txtPreis;
	private JComboBox comboBox;
	private JTextField txtRabatt;
	private AKunde kunde;
	private Double rabatt;
	
	//Getter/Setter
	public String getRadioTyp() {
		return radioTyp;
	}
	public void setRadioTyp(String radioTyp) {
		this.radioTyp = radioTyp;
	}
	/**
	 * Konstruktor
	 * @exception HeadlessException
	 * Ein Fenster wird erzeugt, worin der Kunde eine neue Bestellung anlegen kann
	 * für den selektierten Kunden.
	 */
	public NewOrderFrame(AKunde kunde) throws HeadlessException {
		this.kunde = kunde;
		
		bestellung = new JDialog();
		bestellung.setTitle("neue Bestellung");
		bestellung.setModal(true);

		// ContentPane holen
		Container cpane = this.getContentPane();

		// LayoutManager der ContentPane für die Bereiche
		BorderLayout borderLayout = new BorderLayout();
		cpane.setLayout(borderLayout);

		// 3 Panel konstruieren - je Bereich ein Panel
		JPanel pnlOben = new JPanel();
		JPanel pnlUnten = new JPanel();
		JPanel pnlMitte = new JPanel();
		
		// 3 Panel auf die ContentPane legen
		cpane.add(pnlOben, BorderLayout.NORTH);
		cpane.add(pnlUnten, BorderLayout.SOUTH);
		cpane.add(pnlMitte, BorderLayout.CENTER);

		// JPanel Eigenschaft: Ränder
		pnlOben.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		pnlUnten.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		pnlMitte.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

		// JPanel Eigenschaft: LayoutManager
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints constrRechts = new GridBagConstraints();
		pnlMitte.setLayout(grid);

	// Panel oben -------------------------------
		
		JLabel lbUeberschrift = new JLabel("Neue Bestellung für: " + kunde.getAnschrift().getName());
		pnlOben.add(lbUeberschrift);
	//Panel Mitte--------------------------------
		
		//Namen der Radiobuttons
		String [] vTypen = {"Palette", "Karton", "Flasche"};
		
		JRadioButton[] verpackung = new JRadioButton[3];
		ButtonGroup gruppe = new ButtonGroup();

		int yPos = 1;
		//RadioButtons mit Verpackungsnamen befüllen und untereinander setzen im Panel
		for (int i = 0; i < verpackung.length; i++) {
			verpackung[i] = new JRadioButton(vTypen[i]);

			gruppe.add(verpackung[i]); // Logik

			constrRechts.gridx = 5; // Spalte
			constrRechts.gridy = yPos; // Zeile
			yPos += 1;

			constrRechts.anchor = GridBagConstraints.WEST;
			// Button zusammen mit den Contraints an die Contentpane übergeben
			pnlMitte.add(verpackung[i], constrRechts);

			verpackung[i].setActionCommand(vTypen[i]); // AktionKommand
			// ActionListener
			verpackung[i].addActionListener(new Radiobtn());
		}
		
	//Alle Textfelder, Comboboxen, Radiobuttons------------------------------
		JLabel lblWeinsorte = new JLabel("Weinsorte");
		GridBagConstraints gbc_lblWeinsorte = new GridBagConstraints();
		gbc_lblWeinsorte.insets = new Insets(0, 0, 5, 5);
		gbc_lblWeinsorte.anchor = GridBagConstraints.EAST;
		gbc_lblWeinsorte.gridx = 4;
		gbc_lblWeinsorte.gridy = 4;
		pnlMitte.add(lblWeinsorte, gbc_lblWeinsorte);
		
		comboBox = new JComboBox(WeinSorte.getWeinsorte());
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 5;
		gbc_comboBox.gridy = 4;
		pnlMitte.add(comboBox, gbc_comboBox);
		
		JLabel lblAnzahl = new JLabel("Anzahl");
		GridBagConstraints gbc_lblAnzahl = new GridBagConstraints();
		gbc_lblAnzahl.anchor = GridBagConstraints.EAST;
		gbc_lblAnzahl.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnzahl.gridx = 4;
		gbc_lblAnzahl.gridy = 5;
		pnlMitte.add(lblAnzahl, gbc_lblAnzahl);
		
		txtAnzahl = new JTextField();
		GridBagConstraints gbc_txtAnzahl = new GridBagConstraints();
		gbc_txtAnzahl.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnzahl.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAnzahl.gridx = 5;
		gbc_txtAnzahl.gridy = 5;
		pnlMitte.add(txtAnzahl, gbc_txtAnzahl);
		txtAnzahl.setColumns(10);
		
		JLabel lblPreis = new JLabel("Preis");
		GridBagConstraints gbc_lblPreis = new GridBagConstraints();
		gbc_lblPreis.anchor = GridBagConstraints.EAST;
		gbc_lblPreis.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreis.gridx = 4;
		gbc_lblPreis.gridy = 6;
		pnlMitte.add(lblPreis, gbc_lblPreis);
		
		txtPreis = new JTextField();
		GridBagConstraints gbc_txtPreis = new GridBagConstraints();
		gbc_txtPreis.insets = new Insets(0, 0, 5, 5);
		gbc_txtPreis.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPreis.gridx = 5;
		gbc_txtPreis.gridy = 6;
		pnlMitte.add(txtPreis, gbc_txtPreis);
		txtPreis.setColumns(10);
		
		JLabel lblRabatt = new JLabel("Rabatt");
		GridBagConstraints gbc_lblRabatt = new GridBagConstraints();
		gbc_lblRabatt.anchor = GridBagConstraints.EAST;
		gbc_lblRabatt.insets = new Insets(0, 0, 5, 5);
		gbc_lblRabatt.gridx = 4;
		gbc_lblRabatt.gridy = 7;
		pnlMitte.add(lblRabatt, gbc_lblRabatt);
		
		rabatt = kunde.getRabatt();
		txtRabatt = new JTextField(rabatt.toString());
		txtRabatt.setEditable(false);
		GridBagConstraints gbc_txtRabatt = new GridBagConstraints();
		gbc_txtRabatt.insets = new Insets(0, 0, 5, 5);
		gbc_txtRabatt.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRabatt.gridx = 5;
		gbc_txtRabatt.gridy = 7;
		pnlMitte.add(txtRabatt, gbc_txtRabatt);
		txtRabatt.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 4;
		gbc_btnOk.gridy = 8;
		btnOk.addActionListener(new Savebtn());
		pnlUnten.add(btnOk, gbc_btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 5;
		gbc_btnCancel.gridy = 8;
		btnCancel.addActionListener(new Cancelbtn());
		pnlUnten.add(btnCancel, gbc_btnCancel);
		
		this.setLocation(500, 300);
		this.setModal(true);
		setSize(300, 300);
	}
	/**
	 * @author Hager
	 * Die Quelle holen, welche Radiobtn ausgewählt wurde.
	 * Danach den radiotyp festlegen, welcher in der Klasse
	 * Changebtn widerverwendet wird.
	 */
	private class Radiobtn implements ActionListener {
			
			public void actionPerformed(ActionEvent ev) {
		
				JRadioButton quellobj = (JRadioButton) ev.getSource();
				switch (quellobj.getActionCommand()) {
				
				case "Palette":
					setRadioTyp("p");
					break;
	
				case "Karton":
					setRadioTyp("k");
					break;
	
				case "Flasche":
					setRadioTyp("f");
					break;
				}
			}
	
	}//end of inner class
	
	/**
	 * @author Hager
	 * Die neue ausgefüllte Bestellung wird gespeichert in der HashMap.
	 * Alle eingetragenden Werte werden geholt und an die Methode neuBestellung
	 * übergeben.
	 *
	 */
	private class Savebtn implements ActionListener{
		
		private int anzahl;
		private double preis;

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			try{
				//Die EIngaben vom Benutzer holen
				anzahl = Integer.parseInt(txtAnzahl.getText());
				preis = Double.parseDouble(txtPreis.getText());
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "Bitte geben Sie nur Zahlen ein.", "Warnung", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String weinsorte =  (String) comboBox.getSelectedItem();
			//Hole das jetzige Datum
			Calendar calendar = Calendar.getInstance();
	        Date datum =  calendar.getTime();
			try{
				//je nachdem welche Verpackung ausgewählt wurde
				switch(getRadioTyp()){
				case "p":
					//Bestellung in der HashMap eintragen und dafür alle benötigten Werte übergeben
					kunde.getBv().neuBestellung(datum, anzahl, weinsorte, "Palette", preis, rabatt);
					break;
				case "k":
					kunde.getBv().neuBestellung(datum, anzahl, weinsorte, "Karton", preis, rabatt);
					break;
		
				case "f":
					kunde.getBv().neuBestellung(datum, anzahl, weinsorte, "Flasche", preis, rabatt);
					break;
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(bestellung,
					    "Bitte füllen Sie alle Felder aus!",
					    "Inane error",
					    JOptionPane.WARNING_MESSAGE);
				}
			//Fenster schließen
			setVisible(false);
			setModal(false);
			}	
	
	}//end of inner class
	/**
	 * @author Hager
	 *Das Fenster wird geschlossen.
	 */
	private class Cancelbtn implements ActionListener{
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
		setVisible(false);
		}
	}//end of inner class
	
}//end of class
