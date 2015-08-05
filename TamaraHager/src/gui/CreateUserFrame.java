package gui;

import menu.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import util.*;
import kunde.*;

/**
 * @author Hager
 *	Der Benutzer kann hier einen Kunden neu erstellen. Indem dieser seine Anschrift angeben kann,
 *den Kundentyp festlegt und bei Wunsch ein Bild auswählt.
 */
public class CreateUserFrame extends JDialog {
	// Attribute
	private JLabel lbPicture = new JLabel();
	JTextField tfOrt;
	JTextField tfPlz;
	JTextField tfStreet;
	JTextField tfName;
	String RadioTyp;
	private JDialog kundenErstellen;
	
	//Getter/Setter
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
	private JTextField tfRabatt;
	

	
	/**Konstruktor
	 * @throws HeadlessException
	 */
	public CreateUserFrame() throws HeadlessException {
		
		kundenErstellen = new JDialog();
		kundenErstellen.setTitle("Kunden erstellen");
		kundenErstellen.setModal(true);

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
		
		JLabel lbUeberschrift = new JLabel("neuen Kunden anlegen");
		pnlOben.add(lbUeberschrift);

	// Panel links--------------------------------

		anschriftFormular();

	// Panel Mitte--------------------------------

		// Button

		final JButton btnPic = new JButton("Bild wählen");
		pnlMitte.add(btnPic);
		
		//Defaultbild
		File pic = new File(Kundenverwaltung.getInstance().getBild());
		ImageIcon thumbnail = PicTool.loadImageAsThumbNail(pic); //Größe festlegen
		lbPicture.setIcon(thumbnail);
		pnlMitte.add(lbPicture);

		// ActionListener: Bild wählen
		btnPic.addActionListener(new Bildwaehlen(this));
		
	// Panel rechts------------------------------
		
		String[] txtKundenTyp = { "Endverbraucher", "Großverbraucher", "Gesellschaft" };

		// 3 Radiobuttons erzeugen
		JRadioButton[] rbKundentyp = new JRadioButton[3];
		ButtonGroup gruppe = new ButtonGroup();

		int yPos = 10;
		//RadioButtons erzeugen und diese beschriften
		for (int i = 0; i < rbKundentyp.length; i++) {
			rbKundentyp[i] = new JRadioButton(txtKundenTyp[i]);

			gruppe.add(rbKundentyp[i]); // Logik

			constrRechts.gridx = 10; // Spalte
			constrRechts.gridy = yPos; // Zeile
			yPos += 10;

			constrRechts.anchor = GridBagConstraints.WEST;
			// Button zusammen mit den Contraints an die Contentpane übergeben
			pnlRechts.add(rbKundentyp[i], constrRechts);

			rbKundentyp[i].setActionCommand(txtKundenTyp[i]); // AktionKommand
			// anbinden des Listener an das Quellobjekt
			rbKundentyp[i].addActionListener(new Radiobtn());
		}
		
		//Fenstergröße automatisch und kein anderes Fenster ist bedienbar.
		pack();
		setModal(true);
		
	}//end of constr.


	
	/**
	 * // erstellt im linken Panel Textfelder, die der Benutzer alle Daten vom
	// Kunden eingeben muss und einen Kunden zu speichern.
	 */
	public void anschriftFormular() {
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 30, 30, 30, 0 };
		gbl_panel.rowHeights = new int[] { 23, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
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
		 tfName = new JTextField("");
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
		tfStreet = new JTextField("");
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

		 tfPlz = new JTextField("");
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

		tfOrt = new JTextField("");
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 3;
		pnlLinks.add(tfOrt, gbc_textField_3);
		tfOrt.setColumns(10);

		JLabel labelRabatt = new JLabel("Rabatt");
		GridBagConstraints gbc_lblRabatt = new GridBagConstraints();
		gbc_lblRabatt.insets = new Insets(0, 0, 5, 5);
		gbc_lblRabatt.anchor = GridBagConstraints.EAST;
		gbc_lblRabatt.gridx = 0;
		gbc_lblRabatt.gridy = 4;
		pnlLinks.add(labelRabatt, gbc_lblRabatt);

		tfRabatt = new JTextField("");
		GridBagConstraints gbc_txtRabatt = new GridBagConstraints();
		gbc_txtRabatt.insets = new Insets(0, 0, 5, 5);
		gbc_txtRabatt.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRabatt.gridx = 1;
		gbc_txtRabatt.gridy = 4;
		pnlLinks.add(tfRabatt, gbc_txtRabatt);
		tfRabatt.setColumns(10);
		
		JButton btnSave = new JButton("speichern");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 6;
		pnlLinks.add(btnSave, gbc_btnNewButton_1);
		btnSave.addActionListener(new Savebtn());
		
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
	 * Die eingebenen Daten werden herausgeholt.
	 * @return Adresse
	 */
	public Adresse anschriftBefuellen(){
		String name = tfName.getText();
		String strasse = tfStreet.getText();
		String ort = tfOrt.getText();
		String plz = tfPlz.getText();
		return(new Adresse(name, strasse, ort, plz ));
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
	 *Je nachdem welcher RadioButton gedrückt wurde,
	 *wird der Kundentyp festgelegt, worauf später beim speichern nocheinmal zugeriffen wird.
	 */
	private class Radiobtn implements ActionListener {
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent ev) {
	
			JRadioButton quellobj = (JRadioButton) ev.getSource();
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
 * Beim Speichern werden die eingeben daten geholt, sowie der index vom
 * ausgewählten Kunden. Diese werden an die Mthoden in der Kundenverwaltung 
 * übergeben. Hier muss jedoch zwischen den Kundentypen unterschieden werden.
 * Der Kundentyp wird von der zuvor aufgrufenen Klasse Radiobtn festgelegt.
 * Danach wird das Fenster geschlossen.
 *
 */
private class Savebtn implements ActionListener{
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try{
			String bild = Kundenverwaltung.getInstance().getBild();
			Double rabatt = Double.parseDouble(tfRabatt.getText());
			switch(getRadioTyp()){
			case "ev":
				Kundenverwaltung.getInstance().neuEndverbraucher(anschriftBefuellen(), bild, rabatt);
				break;
	
			case "gv":
				Kundenverwaltung.getInstance().neuGrossverbraucher(anschriftBefuellen(), bild, rabatt);
				break;
	
			case "gml":
				Kundenverwaltung.getInstance().neuGesellschaftMLiz(anschriftBefuellen(), bild, rabatt);
				break;
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(kundenErstellen,
				    "Bitte füllen Sie alle Felder aus!",
				    "Inane error",
				    JOptionPane.WARNING_MESSAGE);
			}
		//Fenster schließen
		setVisible(true);
		setModal(false);
		}	
	
}
/**
 * @author Hager
 *Nach klicken diesen Buttons, öffnet sich ein neues fenster,
 *worin der Benutzer sich ein Bild aussuchen kann.
 */
private class Bildwaehlen implements ActionListener{
	
	CreateUserFrame fenster;
	
	/**Konstruktor
	 * @param cf
	 */
	public Bildwaehlen(CreateUserFrame cf){
		this.fenster = cf;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		//Fenster, worin alle Kundenbilder angezeigt werden
		ChoosePictureFrame cp = new ChoosePictureFrame(fenster);
		cp.setVisible(true);
		cp.pack();
		
	}}
}//end of class