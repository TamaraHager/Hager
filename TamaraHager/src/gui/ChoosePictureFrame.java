package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.Array;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import kunde.Kundenverwaltung;
import util.PicTool;

/**
 * @author Hager
 * In diesem Fenster werden alle Bilder angezeigt und davon kann sich der Benutzer eins aussuchen.
 */
public class ChoosePictureFrame extends JDialog {

	JComboBox box;

	
	/**Konstruktor
	 * erstellt das fenster und legt Panels an.
	 * @param fenster Create UserFrame
	 */
	public ChoosePictureFrame(CreateUserFrame fenster) {
		
		JDialog kundenBild = new JDialog();
		JPanel pnlOben = new JPanel();
		JPanel pnlUnten = new JPanel();
		JPanel pnlMitte = new JPanel();
		
		Container cpane = this.getContentPane();

		// LayoutManager der ContentPane
		GridBagLayout grid = new GridBagLayout();

		GridBagConstraints constrOben = new GridBagConstraints();
		cpane.add(pnlOben, BorderLayout.NORTH);
		pnlOben.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// 2 Panel auf die ContentPane legen
		cpane.add(pnlOben, BorderLayout.NORTH);
		cpane.add(pnlMitte, BorderLayout.CENTER);
		cpane.add(pnlUnten, BorderLayout.SOUTH);

		pnlOben.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		pnlUnten.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
	

		int picNumber;
		//erstellt die Kundenbilder und den dazugeh�rigen Text
		for (int i = 0; i < 11; i++) {
			picNumber = i;
			ImageIcon pic = new ImageIcon("./pic/" + picNumber + ".jpg");
			ImageIcon thumbnail = new ImageIcon(pic.getImage().getScaledInstance(-1, 90, Image.SCALE_DEFAULT));
			//Bildbeschriftung
			JLabel lblPic = new JLabel("Bild" + picNumber);
			lblPic.setIcon(thumbnail);
			pnlOben.add(lblPic);
		}
		//Die Felder in der Combobox
		Object[] auswahl = { "0", "1", "2", "3", "4","5", "6", "7", "8", "9", "10" };
		
		box = new JComboBox(auswahl);
		pnlMitte.add(box);

		//Button Ausw�hlen
		JButton btnAuswaehlen = new JButton("Ausw�hlen");
		btnAuswaehlen.setActionCommand("Ausw�hlen");
		btnAuswaehlen.addActionListener(new Buttons(fenster)); //ActionListener
		pnlUnten.add(btnAuswaehlen);

		//Button Sp�ter
		JButton btnCancel = new JButton("Sp�ter");
		btnCancel.setActionCommand("Sp�ter");
		btnCancel.addActionListener(new Buttons(fenster));	//ActionListener
		pnlUnten.add(btnCancel);
		
		//Fenstergr��e automatisch und anderen Fenster sind nicht ausw�hlbar.
		pack();
		setModal(true);
		
	}// end of constr.

	/**
	 * @author Hager
	 *ActionListener
	 *Wenn der Button Sp�ter gedr�ckt wurde, dann schlie�t sich das Fenster,
	 *wenn jedoch ausw�hlen gedr�ckt wurde, dann wird das ausgew�hlte Bild
	 *der SetBild() Methode �bergeben und daraufhin wird das Bild im 
	 *Fenster ChangeUser zum ausgew�hlten Bild ge�ndert. Danach wird ebenso
	 *das Kundenbild-Fenster geschlossen.
	 */
	private class Buttons implements ActionListener {

		CreateUserFrame fenster;
		/**
		 * @param fensterB CreateUserFrame
		 */
		public Buttons(CreateUserFrame fensterB ){
			this.fenster = fensterB;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent ev) {
			String action = ev.getActionCommand();
			if (action.equals("Sp�ter")) {
				setVisible(false);
			}
			if (action.equals("Ausw�hlen")) {
				//�bergebe das ausgew�hlte Bild
				Kundenverwaltung.getInstance().setBild("./pic/"+box.getSelectedItem()+".jpg");
				
				// Bild austauschen
				JLabel lbPicture = fenster.getLbPicture();
				File datei = new File(Kundenverwaltung.getInstance().getBild());
				ImageIcon pic =  PicTool.loadImageAsThumbNail(datei);
				lbPicture.setIcon(pic);
				setVisible(false);
			}

		}

	}
}// end of class
