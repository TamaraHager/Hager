package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

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

import kunde.AKunde;
import kunde.Kundenverwaltung;
import bestellung.Bestellung;
import bestellung.WeinSorte;

/**
 * @author Hager
 *Es wird ein Fenster geladen, welches dem Benutzer ermöglicht die ausgewählte Bestellung
 *für den zuvor selektierten Kunden zu ändern. 
 */
public class ChangeOrderFrame extends JDialog {
	//Attribute
	private String radioTyp;
	private JTextField txtAnzahl;
	private JTextField txtPreis;
	private JComboBox comboBox;
	private JTextField txtRabatt;
	private JDialog bestellung;
	private AKunde selektierterKunde;
	private Bestellung selectedOrder;
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
	 * erstellt ein Fenster, dass die Inhalte der Bestellung anzeigt.
	 * Der Benutzer hat die Möglichkeit diese zu ändern.
	 */
	public ChangeOrderFrame() throws HeadlessException {
		
		//Der Index des selektierten Kunde
		int index = Kundenverwaltung.getInstance().getSelection();
		selektierterKunde = Kundenverwaltung.getInstance().getOneKundeFromKundenListe(index);
		//selektierte Bestellung
		int rowIndex = selektierterKunde.getBv().getRowSelection();
		Object bestellid = Kundenverwaltung.getInstance().getModel().getValueAt(rowIndex, 0);
		selectedOrder = (Bestellung) selektierterKunde.getBv().getBestellungListe().get(bestellid);
		
		//Fenster erstellen
		bestellung = new JDialog();
		bestellung.setTitle("Bestellung ändern");
		bestellung.setModal(true); // andere Fenster sind nicht anklickbar

		// ContentPane holen
		Container cpane = this.getContentPane();

		// LayoutManager der ContentPane für die Bereiche
		BorderLayout borderLayout = new BorderLayout();
		cpane.setLayout(borderLayout);

		//3 Panel konstruieren - je Bereich ein Panel
		JPanel pnlOben = new JPanel();
		JPanel pnlUnten = new JPanel();
		JPanel pnlMitte = new JPanel();
		
		// 3 Panel auf die ContentPane legen
		cpane.add(pnlOben, BorderLayout.NORTH);
		cpane.add(pnlUnten, BorderLayout.SOUTH);
		cpane.add(pnlMitte, BorderLayout.CENTER);

		// JPanel Eigenschaft: Ränder
		pnlOben.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		pnlUnten.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		pnlMitte.setBorder(BorderFactory.createLineBorder(Color.GREEN));

		// JPanel Eigenschaft: LayoutManager

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints constrRechts = new GridBagConstraints();
		pnlMitte.setLayout(grid);

	// Panel oben -------------------------------
		
		//Überschrift mit der BestellId von der ausgewählten Bestellung
		JLabel lbUeberschrift = new JLabel("Die Bestellung ändern von: " + selectedOrder.getBestellID());
		pnlOben.add(lbUeberschrift);
		
	//Panel Mitte
		
		//Namen der RadioButtons
		String [] vTypen = {"Palette", "Karton", "Flasche"};
		
		JRadioButton[] verpackung = new JRadioButton[3];
		ButtonGroup gruppe = new ButtonGroup();

		int yPos = 1;
		//RadioButtons mit Verpackungsnamen befüllen und untereinander setzen ins Panel
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
			// anbinden des Listener an das Quellobjekt
			verpackung[i].addActionListener(new Radiobtn());
		}
		
		//hole den Verpackungsname von der ausgewählten Bestellung
		String selectedVerpackung = selectedOrder.getVerpackung();
		int selectedTyp = 0;
		//setze int abhängig vom Verpackungsnamen
		if(selectedVerpackung.equals("Palette")){
			selectedTyp = 0;
		}if(selectedVerpackung.equals("Karton")){
			selectedTyp = 1;
		}if(selectedVerpackung.equals("Flasche")){
			selectedTyp = 2;
		}
		//die Verpackung von der ausgewählten Bestellung anzeigen
		verpackung[selectedTyp].setSelected(true);
		//Setze den Radiotyp auf die Verpackung von der ausgewählten Bestellung
		//solange dieser nicht überschrieben wird von der Klasse Radiobtn
		setRadioTyp(selectedVerpackung);
		
	//Alle Textfelder, Comboboxen, Radiobuttons-----------------------------
		JLabel lblWeinsorte = new JLabel("Weinsorte");
		GridBagConstraints gbc_lblWeinsorte = new GridBagConstraints();
		gbc_lblWeinsorte.insets = new Insets(0, 0, 5, 5);
		gbc_lblWeinsorte.anchor = GridBagConstraints.EAST;
		gbc_lblWeinsorte.gridx = 4;
		gbc_lblWeinsorte.gridy = 4;
		pnlMitte.add(lblWeinsorte, gbc_lblWeinsorte);
		
		comboBox = new JComboBox(WeinSorte.getWeinsorte());
		//die Weinsorte von der Bestellung
		String selectedWeinsorte = selectedOrder.getWein();
		//zeige diese an
		comboBox.setSelectedItem(selectedWeinsorte);
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
		
		Integer anzahl = selectedOrder.getAnzahl();
		txtAnzahl = new JTextField(anzahl.toString()); //Anzahl  von der ausgewählten Bestellung
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
		
		Double preis = selectedOrder.getEinzelPreis();
		txtPreis = new JTextField(preis.toString()); //Preis von der ausgewählten Bestellung
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
		
		rabatt = selektierterKunde.getRabatt();
		txtRabatt = new JTextField(rabatt.toString()); //Rabatt vom selektiereten Kunden
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
		btnOk.addActionListener(new Changebtn()); //ActionListener
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
	 * Es werden alle Werte die geändert wurden und unverändert sind geholt
	 * und an die Methode changeBestellung übergeben. 
	 * Darin wird später die Bestellung in die JTable hinzugefügt.
	 *
	 */
	private class Changebtn implements ActionListener{
		private int anzahl;
		private double preis;

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			//Werte aus den Textfeldern und Auswahl
			try{
				anzahl = Integer.parseInt(txtAnzahl.getText());
				preis = Double.parseDouble(txtPreis.getText());
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Bitte geben Sie nur Zahlen ein.", "Warnung", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			String weinsorte =  (String) comboBox.getSelectedItem();
			//Datum von der selektierten Bestellung
			Date datum = selectedOrder.getDatum();
			try{
				//je nachdem welche Kundetyp ausgewählt wurde
				switch(getRadioTyp()){
				case "p":
					//änder die Bestellung in der JTable und übergebe die selektierte Bestellung
					selektierterKunde.getBv().changeBestellung(selectedOrder, datum, anzahl, weinsorte, "Palette", preis, rabatt);
					break;
					
				case "k":
					selektierterKunde.getBv().changeBestellung(selectedOrder, datum, anzahl, weinsorte, "Karton", preis, rabatt);
					break;
		
				case "f":
					selektierterKunde.getBv().changeBestellung(selectedOrder, datum, anzahl, weinsorte, "Flasche", preis, rabatt);
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
