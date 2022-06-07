package de.fhbielefeld.dvdverwaltung.gui.aufbewahrungsplatz;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.Aufbewahrungsplatz;
import de.fhbielefeld.dvdverwaltung.service.AufbewahrungsplatzService;
import lombok.Getter;

/**
 * Definiert die Tabelle der Aufbewahrungsplätze.
 * Informationen bekommt das TableModel aus der Serviceklasse. An diese werden auch 
 * veränderungen weitergereicht, damit diese an die Datenbank übergeben werden
 * 
 * @author Mohamed-Khalil El Ouafi
 * @author Jana Jagiello
 *
 */
@SuppressWarnings("serial")
@Component
public class AufbewahrungsplatzTableModel extends AbstractTableModel {

	@Getter
	private AufbewahrungsplatzService aps;
	
	private List<Aufbewahrungsplatz> lines;
	private final String[] headers = {"Bezeichnung", "Anzahl DVDs"};

	@Autowired
	public AufbewahrungsplatzTableModel(AufbewahrungsplatzService aps) {
		this.aps = aps;
		lines = aps.getAufbewahrungsplatzListFromRepo();
	}

	public void updateTable() {
		lines = aps.getAufbewahrungsplatzListFromRepo();
		super.fireTableDataChanged();
	}

	public void saveAufbewahrungsplatz(Aufbewahrungsplatz aufbewahrungsplatz) {
		aps.saveAufbewahrungsplatz(aufbewahrungsplatz);
		this.updateTable();
	}
	
	/**
	 * Löscht den ausgewählten Aufbewahrungsplatz unter der Bedingung, dass
	 * dieser sich im Löschzustand befindet (keine DVDs enthält)
	 * 
	 * @param apIndex Index des angeklickten Aufbewahrungsplatzes im TableModel
	 */
	public void deleteAufbewahrungsplatz(int apIndex) {
		try {	
			aps.deleteAufbewahrungsplatz(lines.get(apIndex));
		} catch (IllegalStateException isExc){
			JOptionPane.showMessageDialog(null, "Es befinden sich noch DVDs an diesem Ort.\n "
											  + "Verschiebe die DVDs vor dem Löschen.", 
											    "Fehler:", 
											    JOptionPane.ERROR_MESSAGE);
			return;
		} catch (Exception parentExc) {
			parentExc.printStackTrace();
			return;
		}
	
		this.updateTable();
	}

	@Override
	public int getRowCount() { return lines.size(); }
	@Override
	public int getColumnCount() { return headers.length; }
	
	@Override
	public String getColumnName(int col) { return headers[col]; }
	@Override
	public Class<?> getColumnClass(int colIndex) {
		switch (colIndex) {
		case 0:
			return String.class;
		case 1:
			return Long.class;
		default:
			return null;
		}
	}
	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		switch (colIndex) {
		case 0:
			return lines.get(rowIndex).getBezeichnung();
		case 1:
			return aps.getAnzahlDVDs(lines.get(rowIndex).getAufbewahrungsplatzId());
		default:
			return null;
		}
}
	
	@Override
	public boolean isCellEditable(int rowIndex, int colIndex) {
		switch (colIndex) {
		case 1:
			return false;
		default:
			return true;
		}
	}
	
	/**
	 * Wenn die eingegebene Bezeichnung schon vorliegt bzw. leer ist, wird sie nicht übernommen.
	 * Andernfalls wird sie gespeichert.
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int colIndex) {
		switch (colIndex) {
		case 0:
			if (aValue.toString().trim().length() <= 0 || isBezeichnungInputDuplicate(aValue.toString())) { 
				return; 
			}
			lines.get(rowIndex).setBezeichnung(aValue.toString());
			aps.saveAufbewahrungsplatz(lines.get(rowIndex));				
			break;
		default:
			break;
		}
	}
	
	public boolean isBezeichnungInputDuplicate(String pBezeichnung) {
		for (Aufbewahrungsplatz ap: lines) {
			if (ap.getBezeichnung().equalsIgnoreCase(pBezeichnung))
				return true;
		}
		
		return false;
	}	
	
}