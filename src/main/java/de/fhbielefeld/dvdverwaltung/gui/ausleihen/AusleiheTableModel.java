package de.fhbielefeld.dvdverwaltung.gui.ausleihen;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.Ausleihe;
import de.fhbielefeld.dvdverwaltung.service.AusleiheService;
import de.fhbielefeld.dvdverwaltung.service.DVDService;
import de.fhbielefeld.dvdverwaltung.service.PersonService;

/**
 * Definiert die Tabelle der Ausleihen.
 * Informationen bekommt das TableModel aus der Serviceklasse. An diese werden auch 
 * veränderungen weitergereicht, damit diese an die Datenbank übergeben werden
 * 
 * @author Nick Norberts
 *
 */
@SuppressWarnings("serial")
@Component
public class AusleiheTableModel extends AbstractTableModel {
	
	private AusleiheService as;
	private DVDService dvds;
	private PersonService ps;
	private List<Ausleihe> lines;
	String[] headers = {"DVD-Titel", "Ausgeliehen von", "Ausgeliehen am", "Ausgeliehen bis", "Dauer (Tage)", "Zurückgegeben?"};
	
	@Autowired
	public AusleiheTableModel(AusleiheService as, DVDService dvds, PersonService ps) {
		this.as = as;
		this.dvds = dvds;
		this.ps = ps;
		lines = as.getAusleiheListFromRepo();
	}
	
	public void updateTable() {
		lines = as.getAusleiheListFromRepo();
		super.fireTableDataChanged();
	}
	
	public void saveAusleihe(Ausleihe ausleihe) {
		as.saveAusleiheInRepo(ausleihe);
		updateTable();
	}
	
	public void deleteAusleihe(int pIndex) {
		lines.get(pIndex).getDvd().setVerfuegbar(true);
		dvds.saveDVDInRepo(lines.get(pIndex).getDvd());
		as.deleteAusleiheFromRepo(lines.get(pIndex));
		
//		Falls keine Ausleihen mehr zur Person -> zuletztAusgeliehenAm zurücksetzen
		if (ps.getAnzahlAusleihenPerson(lines.get(pIndex).getPerson().getPersonId()) == 0) {
			lines.get(pIndex).getPerson().setZuletztAusgeliehenAm(null);
			ps.savePersonInRepo(lines.get(pIndex).getPerson());			
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
		case 0: case 1: case 2: case 3: case 4: return String.class;
		case 5: return Boolean.class;
		default:
			return null;
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		switch (colIndex) {
		case 0:
			return lines.get(rowIndex).getDvd();
		case 1:
			return lines.get(rowIndex).getPerson();
		case 2:
			return lines.get(rowIndex).getDatAusleihe().format(DateTimeFormatter.ofPattern(AusleiheDateFormat.STR_AUSLEIHEDATE_FORMAT));
		case 3:
			return lines.get(rowIndex).getDatVorRueckgabe().format(DateTimeFormatter.ofPattern(AusleiheDateFormat.STR_AUSLEIHEDATE_FORMAT));
		case 4:
			return lines.get(rowIndex).getLeihdauer();
		case 5:
			return lines.get(rowIndex).getZurueckgegeben();
		default:
			return null;
		}
	}

//	Bei zurueckgegebenen Ausleihen -> Bearbeitung wird abgelehnt
	@Override
	public boolean isCellEditable(int rowIndex, int colIndex) { return (colIndex == 5 && !lines.get(rowIndex).getZurueckgegeben()); }
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int colIndex) {
		if(colIndex == 5) {
			lines.get(rowIndex).setZurueckgegeben(true);
			lines.get(rowIndex).getDvd().setVerfuegbar(true);
			
			as.saveAusleiheInRepo(lines.get(rowIndex));
			dvds.saveDVDInRepo(lines.get(rowIndex).getDvd());
			
			this.updateTable();
		}	

	}
	
	public boolean checkDuplicateHandynummer(Long ausleiheId) {
		for (Ausleihe p: lines) {
			if (p.getAusleiheId().equals(ausleiheId))
				return true;
		}
		
		return false;
	}
	
}