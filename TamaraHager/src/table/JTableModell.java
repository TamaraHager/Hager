package table;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import kunde.Kundenverwaltung;
import bestellung.Bestellung;
import bestellung.Bestellverwaltung;

/**
 * @author Hager
 * Die Daten aus der HashMap Bestellungsliste wird durch diese Klasse in der JTable angezeigt.
 *
 */
public class JTableModell extends AbstractTableModel {
	//Zeileninhalt und Spaltennamen
	Object[][] rowData;
	String[] columnNames = { "Nr.", "Datum", "Anzahl", "Verpackung", "Weinsorte", "Einzel-Preis", "Brutto", "Netto", "Rabatt" };

	private Vector<Vector<Object>> dataVec = new Vector<Vector<Object>>(); // Datenspeicher

	void makeData() {

		if(rowData != null){
			for (int i = 0; i < rowData.length; i++) {
				Vector<Object> rowVec = new Vector<Object>(); // Zeilen Vector

				for (int j = 0; j < rowData[i].length; j++) {
					rowVec.add(rowData[i][j]);
				}
				dataVec.add(rowVec);
			}	
		}
	}

	/**
	 * Konstruktor
	 */
	public JTableModell() {
		// Daten in den Datenvektor übernehmen		
			makeData();

	}

	// Anzahl der Spalten, beginnen bei 0
	public int getColumnCount() {
		Vector  rowVector = dataVec;
		int colCount = columnNames.length;
		return colCount;
	}
	
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 * // Anzahl der Zeilen
	 */
	public int getRowCount() {
		return dataVec.size();
	}

	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 * // liefert den inhalt einer zelle
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Vector rowVector = dataVec.elementAt(rowIndex);
		Object feldwert = rowVector.elementAt(columnIndex);
		
		switch(columnIndex){
			case  0: return feldwert.toString();
			case  1: return feldwert.toString();
			case  2: return feldwert;
			case  3: return feldwert.toString();
			case  4: return feldwert.toString();
			case  5: return new Double(feldwert.toString());
			case  6: return new Double(feldwert.toString());
			case  7: return feldwert.toString();
			case  8: return new Double(feldwert.toString());
		}
		
		return feldwert;
	}

	/*
	 * (non-Javadoc) custom column names
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/**
	 * eigene Methode: eine neue Zeile wird übernommen und im Datenvector
	 * abgespeichert
	 */
	public void addRow(Bestellung b) {
		Vector<Object> rowVec = new Vector<Object>();		
		
	//ID
		rowVec.add(new String(b.getBestellID()));
	//Datum
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date today = b.getDatum();      
		String reportDate = df.format(today);
		rowVec.add(new String(reportDate));
	//Anzahl
		rowVec.add(new Integer(b.getAnzahl()));
	//Verpackung
		rowVec.add(new String(b.getVerpackung()));
		rowVec.add(new String(b.getWein()));
	//Preise
		rowVec.add(new Double(b.getEinzelPreis()));
		rowVec.add(new Double(b.getGesamtPreis()));
		DecimalFormat f = new DecimalFormat("0.000"); //runden auf 3 Nachkommastellen
		rowVec.add(new String(f.format(b.getGesamtPreisNetto())));
	//Rabatt vom Kunden
		rowVec.add(new Double(b.getRabatt()));
		
		dataVec.add(rowVec); // Datenspeicher aufgefüllt
		
		// der JTable Bescheid sagen
		fireTableDataChanged();
	}

	/**
	 * eigene Methode: eine Zeile aus dem Datenmodell löschen
	 * 
	 * @param index
	 */
	public void delRow(int index) {
		dataVec.removeElementAt(index); // Datenspeicher leeren
		// der JTable Bescheid sagen
		fireTableDataChanged();
	}
	
	/**
	 * eigene Methode: die den Vector leert.
	 */
	public void resetModel(){
		dataVec.clear();
		fireTableDataChanged(); //Jtable informieren
	}

}
