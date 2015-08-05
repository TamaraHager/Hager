package gui;

import gui.*;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import kunde.AKunde;
import kunde.Adresse;
import kunde.Kundenverwaltung;
import util.PicTool;

/**
 * @author Hager
 *Die Klasse erstellt ein Fenster, welches ermöglicht den Benutzer den zuvor
 *ausgewählten Kunden aus der Liste, zu bearbeiten.
 */
public class ChangeUserFrame extends JDialog {
	// Attribute
	private JLabel lbPicture = new JLabel();
	JTextField tfOrt;
	JTextField tfPlz;
	JTextField tfStreet;
	JTextField tfName;
	String RadioTyp;
	AKunde selectedKunde;
	private JDialog kundendaten;
	int index;
	// ob der Kunde geändert wurde oder nicht
	String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRadioTyp() {
		return RadioTyp;
	}

	public void setRadioTyp(String radioTyp) {
		RadioTyp = radioTyp;
	}

	public JLabel getLbPicture() {
		return lbPicture;
	}

	public void setLbPicture(JLabel lbPicture) {
		this.lbPicture = lbPicture;
	}

	String[] txtKundenTyp = { "Endverbraucher", "Großverbraucher", "Gesellschaft" };

	// 5 Panel konstruieren - je Bereich ein Panel
	JPanel pnlOben = new JPanel();
	JPanel pnlUnten = new JPanel();
	JPanel pnlRechts = new JPanel();
	JPanel pnlLinks = new JPanel();
	JPanel pnlMitte = new JPanel();
	
	/**Konstruktor
	 * @throws HeadlessException
	 */
	public ChangeUserFrame() throws HeadlessException {
		
		// hole ausgewählten Kunden aus der Kundenverwaltung
		index = Kundenverwaltung.getInstance().getSelection();
		selectedKunde = Kundenverwaltung.getInstance().getOneKundeFromKundenListe(index);
		
		kundendaten = new JDialog();
		kundendaten.setTitle("Kundendaten ändern");
		kundendaten.setModal(true);

		// ContentPane holen
		Container cpane = this.getContentPane();

		// LayoutManager der ContentPane für die 5 Bereiche
		BorderLayout borderLayout = new BorderLayout();
		cpane.setLayout(borderLayout);

		// 5 Panel auf die ContentPane legen
		cpane.add(pnlOben, BorderLayout.NORTH);
		cpane.add(pnlUnten, BorderLayout.SOUTH);
		cpane.add(pnlRechts, BorderLayout.EAST);
		cpane.add(pnlLinks, BorderLayout.WEST);
		cpane.add(pnlMitte, BorderLayout.CENTER);

		// JPanel Eigenschaft: Ränder
		pnlOben.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		pnlUnten.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		pnlRechts.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		pnlLinks.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		pnlMitte.setBorder(BorderFactory.createLineBorder(Color.RED));

		// JPanel Eigenschaft: LayoutManager

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints constrRechts = new GridBagConstraints();
		pnlRechts.setLayout(grid);

	// Panel oben -------------------------------
		
		JLabel lbUeberschrift = new JLabel("Kunden bearbeiten");
		pnlOben.add(lbUeberschrift);

	// Panel links--------------------------------

		anschriftFormular();

	// Panel Mitte--------------------------------

		// Button Bild waählen
		final JButton btnPic = new JButton("Bild ändern");
		pnlMitte.add(btnPic);
		
		//Kundenbild vom ausgewählten Kunden anzeigen
		File pic = new File(selectedKunde.getKundenBild());
		ImageIcon thumbnail = PicTool.loadImageAsThumbNail(pic);
		lbPicture.setIcon(thumbnail);
		pnlMitte.add(lbPicture);

		// ActionListener: Bild wählen
		 btnPic.addActionListener(new BildAendern(this));
		 
	// Panel rechts------------------------------
		 
		String[] txtKundenTyp = { "Endverbraucher", "Großverbraucher","Gesellschaft" };

		// 3 Radiobuttons erzeugen
		JRadioButton[] rbKundentyp = new JRadioButton[3];
		ButtonGroup gruppe = new ButtonGroup();
		
		int yPos = 10;
		//erstellt die Radionsbuttons untereinander mit den Kundentypen
		for (int i = 0; i < rbKundentyp.length; i++) {
			rbKundentyp[i] = new JRadioButton(txtKundenTyp[i]);

			gruppe.add(rbKundentyp[i]); // Logik

			constrRechts.gridx = 0; // Spalte
			constrRechts.gridy = yPos; // Zeile
			yPos += 10;

			constrRechts.anchor = GridBagConstraints.WEST;
			// Button zusammen mit den Contraints an die Contentpane übergeben
			pnlRechts.add(rbKundentyp[i], constrRechts);

			rbKundentyp[i].setActionCommand(txtKundenTyp[i]); // AktionKommand
			// anbinden des Listener an das Quellobjekt
			rbKundentyp[i].addActionListener(new ChangedRadiobtn());
		}
		
		String selectedKundentyp = selectedKunde.getKundentyp();
		System.out.println("typ" + selectedKundentyp);
		int selectedKT = 0;
		//setze int abhängig vom Verpackungsnamen
		if(selectedKundentyp.equals("ev")){
			selectedKT = 0;
		}if(selectedKundentyp.equals("gv")){
			selectedKT = 1;
		}if(selectedKundentyp.equals("gml")){
			selectedKT = 2;
		}
		//die Verpackung von der ausgewählten Bestellung anzeigen
		rbKundentyp[selectedKT].setSelected(true);
		//Setze den Radiotyp auf die Verpackung von der ausgewählten Bestellung
		//solange dieser nicht überschrieben wird von der Klasse Radiobtn
		setRadioTyp(selectedKundentyp);
		//Fenstergröße festgelegt und nicht verstellbar und kein anderes ist auswählbar
		setSize(800, 250);
		setResizable(false);
		setModal(true);
		
	}// end of constr.

	/**
	 * erstellt im linken Panel Textfelder, wo der Benutzer die Kundendaten ändern kann.
	 */
	public void anschriftFormular() {
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 30, 30, 30, 0 };
		gbl_panel.rowHeights = new int[] { 23, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0,Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0, Double.MIN_VALUE };
		pnlLinks.setLayout(gbl_panel);

		JLabel labelName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		pnlLinks.add(labelName, gbc_lblName);

		// Textfeld wird erstellt
		// Text und Spaltenanzahl werden dabei direkt gesetzt
		tfName = new JTextField(selectedKunde.getAnschrift().getName());
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.EAST;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		pnlLinks.add(tfName, gbc_textField);
		tfName.setColumns(10);

		JLabel labelStreet = new JLabel("Strasse und Hausnr.:");
		GridBagConstraints gbc_lblStrasse = new GridBagConstraints();
		gbc_lblStrasse.insets = new Insets(0, 0, 5, 5);
		gbc_lblStrasse.anchor = GridBagConstraints.EAST;
		gbc_lblStrasse.gridx = 0;
		gbc_lblStrasse.gridy = 1;
		pnlLinks.add(labelStreet, gbc_lblStrasse);

		// Textfeld wird erstellt
		// Text und Spaltenanzahl werden dabei direkt gesetzt
		tfStreet = new JTextField(selectedKunde.getAnschrift().getStrasse_hnr());
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		pnlLinks.add(tfStreet, gbc_textField_1);
		tfStreet.setColumns(10);

		JLabel labelPlz = new JLabel("Plz:");
		GridBagConstraints gbc_lblPlz = new GridBagConstraints();
		gbc_lblPlz.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlz.anchor = GridBagConstraints.EAST;
		gbc_lblPlz.gridx = 0;
		gbc_lblPlz.gridy = 2;
		pnlLinks.add(labelPlz, gbc_lblPlz);

		tfPlz = new JTextField(selectedKunde.getAnschrift().getPlz());
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 2;
		pnlLinks.add(tfPlz, gbc_textField_2);
		tfPlz.setColumns(10);

		JLabel labelOrt = new JLabel("Ort");
		GridBagConstraints gbc_lblOrt = new GridBagConstraints();
		gbc_lblOrt.insets = new Insets(0, 0, 5, 5);
		gbc_lblOrt.anchor = GridBagConstraints.EAST;
		gbc_lblOrt.gridx = 0;
		gbc_lblOrt.gridy = 3;
		pnlLinks.add(labelOrt, gbc_lblOrt);

		tfOrt = new JTextField(selectedKunde.getAnschrift().getOrt());
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 3;
		pnlLinks.add(tfOrt, gbc_textField_3);
		tfOrt.setColumns(10);

		JButton btnSave = new JButton("ändern");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 6;
		pnlLinks.add(btnSave, gbc_btnNewButton_1);
		btnSave.addActionListener(new Changebtn()); //ActionListener
		
		JButton btnClose = new JButton("schließen");
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 5;
		pnlUnten.add(btnClose, gbc_btnNewButton_2);
		btnClose.addActionListener(new Closebtn());
	}

	/**
	 * holt sich die Eingaben vom Benutzer und übergibt diese der Adresse.
	 * @return Adresse
	 */
	public Adresse anschriftAendern() {
		System.out.println("Test: neue anschrift");
		String name = tfName.getText();
		String strasse = tfStreet.getText();
		String ort = tfOrt.getText();
		String plz = tfPlz.getText();
		return (new Adresse(name, strasse, ort, plz));
	}
	/**
	 * @author Hager
	 *Das Fenster wird geschlossen.
	 */
	private class Closebtn implements ActionListener{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
		setVisible(false);
		}
	}
	
	/**
	 * @author Hager
	 *Je nachdem welcher button ausgewählt wurde, wird der Kundentyp festgelegt.
	 *dieser wird später benötigt um, festzustellen was für ein Kunde angelegt werden soll.
	 *
	 */
	private class ChangedRadiobtn implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent ev) {
			System.out.println("test");
			JRadioButton quellobj = (JRadioButton) ev.getSource();
			System.out.println("Test2");
			switch (quellobj.getActionCommand()) {

			case "Endverbraucher":
				setRadioTyp("ev");
				break;

			case "Großverbraucher":
				setRadioTyp("gv");
				break;

			case "Gesellschaft":
				setRadioTyp("gml");
				break;
			}
		}
	}

	/**
	 * @author Hager
	 *	Wenn der Benutzer die Daten geändert hat, wird unterschieden, welcher kundentyp ausgewählt wurde,
	 *daruafhin werden die geänderten Daten geholt und der Index von dem Kunden in der Liste.
	 *Diese werden an die Methoden in der Kundenverwaltung übergeben, welche diese letztendlich den Kunden
	 *in der Liste ändern.
	 */
	private class Changebtn implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
		try{
			switch (getRadioTyp()) {
			case "ev":
				//die geänderten Daten undder Index wird übergeben
				selectedKunde.getBv();
				Kundenverwaltung.getInstance().changedEndverbraucher(anschriftAendern(), selectedKunde.getBv(), index);
				break;

			case "gv":
				Kundenverwaltung.getInstance().changedGrossverbraucher(anschriftAendern(), selectedKunde.getBv(), index);
				break;

			case "gml":
				Kundenverwaltung.getInstance().changedGesellschaftMLiz(anschriftAendern(), selectedKunde.getBv(), index);
				break;
				}
			}catch(Exception ex){
				//JOptionPane: Warnmeldung
				JOptionPane.showMessageDialog(kundendaten,
					    "Bitte füllen Sie alle Felder aus!",
					    "Fehlermeldung",
					    JOptionPane.WARNING_MESSAGE);
				}
			setVisible(true);
			setModal(false);
		}
	}//end of inner class

	/**
	 * @author Hager
	 * Beim drücken des Buttons bild ändern, öffnet sich ein neues Fenster, welches
	 * alle auswählbaren Bilder anzeigt.
	 */
	private class BildAendern implements ActionListener {
		ChangeUserFrame fenster;

		/**Der Benutzer kann auf bild ändern klicken, und danach öffnet sich ein Fenster
		 * worin er ein neues Bild auswählen kann.
		 * @param cuf
		 */
		public BildAendern(ChangeUserFrame cuf) {
			this.fenster = cuf;
		}
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			ChangePictureFrame cp = new ChangePictureFrame(fenster);
			cp.setVisible(true);
			cp.pack();

		}
	}//end of inner class
}// end of class
