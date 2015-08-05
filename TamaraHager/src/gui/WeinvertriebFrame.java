package gui;

import menu.*;
import kunde.*;
import uhr.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import bestellung.Bestellung;
import bestellung.Bestellverwaltung;
import menu.MenuLeiste;
import table.JTableModell;
import table.TableSorter;
import util.PicTool;

/**
 * @author Hager
 *  Das Hauptfenster mit einer Menüleiste. Beinhaltet eine
 *  Kundenliste, mit Infos über den Kunden. Zudem eine Tabelle mit Bestellungen.
 *
 */
public class WeinvertriebFrame extends JFrame {

	// Attribute
	private JLabel lbPicture = new JLabel();
	DefaultListModel listModel;
	private JRadioButton[] rbKundentyps;
	private JTableModell model;
	private TableSorter sorter;
	private static JTable table;
	private JList jlKundenList;
	private AKunde selektierterKunde;

	// Getter/Setter
	public JLabel getLbPicture() {
		return lbPicture;
	}

	public void setLbPicture(JLabel lbPicture) {
		this.lbPicture = lbPicture;
	}

	/**
	 * Konstruktor
	 * 
	 * @throws HeadlessException
	 *             erzeugt das Hauptfenster. Darin befindet sich die Kundenliste
	 *             und die Menüleiste.
	 */
	public WeinvertriebFrame() throws HeadlessException {
		
		//Zeitgeber instantiieren
		Zeitgeber zeitgeber = new Zeitgeber();
		DigitalUhr dig = new DigitalUhr(zeitgeber, this); // den Titel des Fensters setzen
		
		// MenueLeiste hinzufügen
		MenuLeiste menue = new MenuLeiste(this);

		// einbinden des Menüs in das Frame
		this.setJMenuBar(menue);
		
		// ContentPane holen
		Container cpane = this.getContentPane();

		// LayoutManager der ContentPane für die 5 Bereiche
		BorderLayout borderLayout = new BorderLayout();
		cpane.setLayout(borderLayout);

		// 5 Panel konstruieren - je Bereich ein Panel
		JPanel pnlOben = new JPanel();
		JPanel pnlUnten = new JPanel();
		JPanel pnlRechts = new JPanel();
		JPanel pnlLinks = new JPanel();
		JPanel pnlMitte = new JPanel();

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
		pnlOben.add(dig);
		// JPanel Eigenschaft: LayoutManager

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints constrRechts = new GridBagConstraints();
		pnlRechts.setLayout(grid);
		pnlUnten.setLayout(grid);

	// Panel oben -------------------------------

		JLabel lbUeberschrift = new JLabel("Weinvertrieb");
		pnlOben.add(lbUeberschrift);

	// Panel mitte -------------------------------

		File datei = new File("./pic/6.jpg"); // graues Bild(default)
		ImageIcon pic = PicTool.loadImageAsThumbNail(datei);
		lbPicture.setIcon(pic);
		pnlMitte.add(lbPicture);

	// Panel rechts -------------------------------

		String[] txtKundenTyp = { "Endverbraucher", "Großverbraucher", "Gesellschaft" };

		rbKundentyps = new JRadioButton[3];
		ButtonGroup gruppe = new ButtonGroup();

		int yPos = 10;
		// RadioButtons mit Kundennamen befüllen und untereinander setzen im Panel
		for (int i = 0; i < rbKundentyps.length; i++) {
			rbKundentyps[i] = new JRadioButton(txtKundenTyp[i]);

			gruppe.add(rbKundentyps[i]); // Logik

			constrRechts.gridx = 10; // Spalte
			constrRechts.gridy = yPos; // Zeile
			yPos += 10;

			constrRechts.anchor = GridBagConstraints.WEST;
			// Button zusammen mit den Contraints an die Contentpane übergeben
			pnlRechts.add(rbKundentyps[i], constrRechts);

			rbKundentyps[i].setActionCommand(txtKundenTyp[i]); // AktionKommand
			// RadioButtons sind nicht editierbar
			rbKundentyps[i].setEnabled(false);
		}

	// Panel links -------------------------------

		pnlLinks.setLayout(grid);
		GridBagConstraints constrLinks = new GridBagConstraints();

		// DefaultListModel
		listModel = Kundenverwaltung.getInstance().getDml();

		jlKundenList = new JList(listModel);
	
		// Scrollbalken
		JScrollPane scrPaneJList = new JScrollPane(jlKundenList);
		scrPaneJList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrPaneJList.getViewport().setView(jlKundenList);
		scrPaneJList.setPreferredSize(new Dimension(700, 160)); // weite, hoehe
		jlKundenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Selection Listener- welcher Kunde in der Liste ausgewählt wurde
		jlKundenList.addListSelectionListener(new ListSelectionAdapter( rbKundentyps));

		pnlLinks.add(scrPaneJList, constrLinks);

	// Panel Unten------------------------------
		
		model = Kundenverwaltung.getInstance().getModel();
		sorter = new TableSorter(model);
		table = new JTable(sorter);
		sorter.setTableHeader(table.getTableHeader());
		
		JLabel pnlUntenTitel = new JLabel("Bestellungen");
		JScrollPane scrollPane = new JScrollPane(table);
		pnlUnten.add(pnlUntenTitel);
		pnlUnten.add(scrollPane);
		
		// Konfiguration der Tabelle
		table.setRowHeight(24);
		table.setRowMargin(2);
		//Verhalten bei Zellbreitenaenderung durch Benutzer
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		table.setPreferredScrollableViewportSize(new Dimension(700, 200));

		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		// Farben
		table.setGridColor(Color.black);
		table.setSelectionForeground(Color.white);
		table.setSelectionBackground(Color.LIGHT_GRAY);

		// Selektionsmodus
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ListSelectionModel lsm = table.getSelectionModel();
		lsm.addListSelectionListener(new TableSelectionAdapter());

		// Fenstergröße, automatisch und nicht verstellbar
		this.pack();
		this.setResizable(false);

	} // end of konstruktor

	
	/**
	 * @author Hager
	 * holt sich den selektierten Kunden aus der Liste.
	 *
	 */
	private class ListSelectionAdapter implements ListSelectionListener {
		JRadioButton[] Kundentyp;
		

		/**
		 * @param liste
		 *            Jlist, die Kundenliste
		 * @param kundentyp
		 *            JRadioButton, die verschiedenen Kundentypen
		 */
		public ListSelectionAdapter(JRadioButton[] kundentyp) {
			// JList liste wird in meineListe abgespeichert
			
			this.Kundentyp = kundentyp;
		}

		/**
		 * Die Methode valueChanged wird aufgerufen, bei Auswahl der Werte
		 * 
		 * @param ev
		 *            ist das eingetretene Ereignis. Diese Metode holt sich den
		 *            index des ausgewählten Kunden und übergibt diesen in die
		 *            Methode setSelecetion. Dessweiteren wird der jeweilige
		 *            Kundentyp als RadioButton markiert und das Kundenbild
		 *            angezeigt, des selektiereten Kunden.
		 */
		public void valueChanged(ListSelectionEvent ev) {
			if (ev.getValueIsAdjusting() == false) {
				try {
					selektierterKunde = (AKunde) jlKundenList.getSelectedValue();
					// Index vom ausgewählten Kunden
					int selectedIndex = jlKundenList.getSelectedIndex();
					Kundenverwaltung.getInstance().setSelection(selectedIndex);
					radiobtnTyp(Kundentyp, selektierterKunde);
					showKundenbild(selektierterKunde);
					showOrder(selektierterKunde);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		/**
		 * Wenn ein Kunde selektiert wurde und existiert, dann wird das Model
		 * zurückgesetzt und neu Befüllt. Diese Daten stehen dann in der Tabelle.
		 * @param kunde selektierter
		 */
		public void showOrder(AKunde kunde){			
			// clear datavec
			if (selektierterKunde != null) {
				model.resetModel();
				
				HashMap<String, Bestellung> bes = kunde.getBv().getBestellungListe();
			
				Set <String> keyset = bes.keySet();
				Iterator <String> it = keyset.iterator();
				while(it.hasNext()){
					String key = it.next();
					Bestellung b1 = bes.get(key);
					model.addRow(b1);
				}
		}
			
		}
		/**
		 * @param selektierterKunde
		 *            Zum selektierten Kunde wird sein ausgewähltes Kundenbild
		 *            im mittigen Panel angezeigt.
		 */
		public void showKundenbild(AKunde selektierterKunde) {
			JLabel lbPicture = getLbPicture();
			if (selektierterKunde != null) {
				// das Bild vom ausgewählten Kunden holen
				File datei = new File(selektierterKunde.getKundenBild());
				ImageIcon pic = PicTool.loadImageAsThumbNail(datei);
				lbPicture.setIcon(pic);
			} else {
				// Defaultbild
				File datei = new File("./pic/6.jpg");
				ImageIcon pic = PicTool.loadImageAsThumbNail(datei);
				lbPicture.setIcon(pic);
			}
		}

		/**
		 * @param Kundentyp
		 *            RadioButton
		 * @param selektierterKunde
		 *            im linken Panel werden in Form von Radiobuttons der
		 *            jeweilige Kundentyp vom selektierten Kunden angezeigt.
		 */
		public void radiobtnTyp(JRadioButton[] Kundentyp, AKunde selektierterKunde) {
			if (selektierterKunde != null) {
				if (selektierterKunde.getKundentyp() != null) {

					if (selektierterKunde.getKundentyp().equals("ev")) {
						Kundentyp[0].setSelected(true); // Endverbraucher

					} else if (selektierterKunde.getKundentyp().equals("gv")) {
						Kundentyp[1].setSelected(true); // Grossverbraucher

					} else if (selektierterKunde.getKundentyp().equals("gml")) {
						Kundentyp[2].setSelected(true); // Gesellschaft
					}
				} else {

				}
			}
		}
	}// end of inner class

	/**
	 * @author Hager
	 * prüft ob eine Bestellung ausgewählt wurde und holt Sie die Zeile und Spalte
	 * der Auswahl.
	 *
	 */
	private class TableSelectionAdapter implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent ev) {
			int modelIndex = -1; 
			int row = -1;
			if (ev.getValueIsAdjusting())
				return;
			ListSelectionModel lsm = (ListSelectionModel) ev.getSource();
			if (lsm.isSelectionEmpty()) {
				//System.out.println("Zeilen nicht selektiert.");
			} else {
				int zeilenIndex = lsm.getMinSelectionIndex();
				row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				String bestId = (String) table.getValueAt(row, 0);
				selektierterKunde.getBv().setRowSelection(row);
				selektierterKunde.getBv().setSelectedId(bestId);
				modelIndex = sorter.modelIndex(zeilenIndex); // Achtung: Index gehört zum Modell
			}
		}// end of valueChanged
	}// end of inner class

}// end of class
