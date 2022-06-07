package de.fhbielefeld.dvdverwaltung.gui.serienfilme;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.SerieFilm;
import de.fhbielefeld.dvdverwaltung.service.SerieFilmService;

/**
 * Definiert die Tabelle der Serien und Filme.
 * Informationen bekommt das TableModel aus der Serviceklasse. An diese werden auch 
 * veränderungen weitergereicht, damit diese an die Datenbank übergeben werden
 * 
 * @author Mohamed-Khalil El Ouafi
 *
 */
@SuppressWarnings("serial")
@Component
public class SerieFilmTableModel extends AbstractTableModel {
	
	private SerieFilmService sfs;
	private List<SerieFilm> lines;
	String[] headers = {"Typ", "Titel"};
	
	@Autowired
	public SerieFilmTableModel(SerieFilmService sfs) {
		this.sfs = sfs;
		lines = sfs.getSerieFilmListFromRepo();
	}
	
	public void updateTable() {
		lines = sfs.getSerieFilmListFromRepo();
		super.fireTableDataChanged();
	}
	
	public void saveSerieFilm(SerieFilm serieFilm) {
		sfs.saveSerieFilmInRepo(serieFilm);
		this.updateTable();
	}
	
	/**
	 * Löscht die ausgewählte Serie oder Film unter der Bedingung, dass
	 * diese(r) sich im Löschzustand befindet (keine zugehörige DVD(s) besitzt)
	 * 
	 * @param sfIndex Index der angeklickten Serie oder Film im TableModel
	 */	
	public void deleteSerieFilm(int sfIndex){
		try {
			sfs.deleteSerieFilm(lines.get(sfIndex));
			this.updateTable();			
		} catch (IllegalStateException isExc) {
			JOptionPane.showMessageDialog(null, "Löschen fehlgeschlagen:\n"
											  + "Bitte vorab die zugehörigen DVDs löschen"
											  , "Fehler:"
											  , JOptionPane.ERROR_MESSAGE);
			return;
		} catch (Exception parentExc) {
			parentExc.printStackTrace();
			return;
		}
	}	

	@Override
	public int getRowCount() { return lines.size(); }

	@Override
	public int getColumnCount() { return headers.length; }
	
	@Override
	public String getColumnName(int col) { return headers[col]; }
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) { return (columnIndex == 1); }

	@Override
	public Class<?> getColumnClass(int colIndex) {
		switch (colIndex) {
		case 0: case 1: return String.class;
		default:
			return null;
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		switch (colIndex) {
		case 0:
			return lines.get(rowIndex).getTyp();
		case 1:
			return lines.get(rowIndex).getTitel();
		default:
			return null;
		}
	}
	
	/**
	 * Bearbeitet die ausgewählte Serie oder Film unter den Bedingungen, dass
	 * die Bearbeitung nur den Titel betrifft, die Bearbeitung eine 
	 * valide Länge hat (keine Leer-Eingaben als Titel zulässig) und die Bearbeitung keine
	 * Inkonsistenzen in Form von duplizierten Serien oder Filme, verursacht.
	 * Bei Verstoß gegen eine oder mehrere Bedingungen wird die Bearbeitung abgelehnt.
	 * 
	 * @param aValue Benutzereingabe
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (aValue.toString().trim().length() <= 0 || checkDuplicateTitel(aValue.toString()))
			return;
				
		lines.get(rowIndex).setTitel(aValue.toString());
		sfs.saveSerieFilmInRepo(lines.get(rowIndex));
	}
	
	public boolean checkDuplicateTitel(String titel) {
		for (SerieFilm sf: lines) {
			if(sf.getTitel().equalsIgnoreCase(titel))
				return true;
		}
		
		return false;
	}
	
}