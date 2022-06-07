package de.fhbielefeld.dvdverwaltung.gui.dvd;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.DVD;
import de.fhbielefeld.dvdverwaltung.entity.SerieFilm;
import de.fhbielefeld.dvdverwaltung.service.DVDService;

/**
 * Definiert die Tabelle der DVDs.
 * Informationen bekommt das TableModel aus der Serviceklasse. An diese werden auch 
 * veränderungen weitergereicht, damit diese an die Datenbank übergeben werden
 * 
 * @author Mohamed-Khalil El Ouafi
 * @author Nick Norberts
 *
 */
@SuppressWarnings("serial")
@Component
public class DVDTableModel extends AbstractTableModel {
	
	private DVDService dvds;
	private List<DVD> lines;
	String[] headers = {"Titel", "Aufbewahrungsplatz", "Staffel", "Gesehen?", "Gesehen bis"};
	
	@Autowired
	public DVDTableModel(DVDService dvds) {
		this.dvds = dvds;
		lines = dvds.getDVDListFromRepo();
	}

	@Override
	public int getRowCount() { return lines.size(); }

	@Override
	public int getColumnCount() { return headers.length; }
	
	@Override
	public String getColumnName(int col) { return headers[col]; }	
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 0: return lines.get(rowIndex).getSerieFilm().getTitel();
			case 1: return lines.get(rowIndex).getAufbewahrungsplatz().getBezeichnung();
			case 2: return lines.get(rowIndex).getStaffel();
			case 3: return lines.get(rowIndex).getGesehen();
			case 4: return lines.get(rowIndex).getGesehenBis();
		default: return null;
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int colIndex) {
		switch (colIndex) {
		case 3:
			return true;
		case 4:
			return (!lines.get(rowIndex).getGesehen() && lines.get(rowIndex).getSerieFilm().getTyp().equals(SerieFilm.SERIE_DVD));
		default:
			return false;
		}
	}	
	
	@Override
	public Class<?> getColumnClass(int col) {
		switch(col) {
			case 0: case 1: return String.class;
			case 3: return Boolean.class;
			case 2: case 4: return Integer.class;
		default: return null;
		}
	}	
	
	public void updateTable() {
		lines = dvds.getDVDListFromRepo();
		fireTableDataChanged();
	}
	
	public void saveDVD(DVD dvd) {
		dvds.saveDVDInRepo(dvd);
		updateTable();
	}
	
	/**
	 * Löscht die ausgewählte DVD unter der Bedingung, dass
	 * diese sich im Löschzustand befindet (derzeit nicht ausgeliehen ist)
	 * 
	 * @param dvdIndex Index der ausgewählten DVD im TableModel
	 */	
	public void deleteDVD(int dvdIndex) {
		try {
			dvds.deleteDVDFromRepo(lines.get(dvdIndex));
			this.updateTable();
		} catch (IllegalStateException isExc) {
			JOptionPane.showMessageDialog(null, "Löschen fehlgeschlagen:\n"
											  + "Die DVD ist derzeit ausgeliehen!"
											  , "Fehler:"
											  , JOptionPane.ERROR_MESSAGE);
			return;
		} catch (Exception parentExc) {
			parentExc.printStackTrace();
			return;
		}
	}

	/**
	 * Bearbeitet die ausgewählte DVD unter der Bedingung, dass
	 * die Bearbeitung entweder die boolsche Spalte „Gesehen“ 
	 * oder die Spalte des Serien-Sehfortschrittes „Gesehen bis“ betrifft.
	 * Bei Verstoß gegen die Bedingung ist keine Bearbeitung möglich.
	 * 
	 * @param aValue Benutzereingabe
	 */	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int colIndex) {
		boolean isZuEndeGesehen = lines.get(rowIndex).getGesehen();
		
		switch (colIndex) {
			case 3:
//				Falls isGesehen vorher auf true gesetzt war -> ändere es zu false
				if (isZuEndeGesehen) {
					lines.get(rowIndex).setGesehen(false);
					lines.get(rowIndex).setZuletztGesehenAm(null);
				} else if (!isZuEndeGesehen) {
					lines.get(rowIndex).setGesehen(true);
					lines.get(rowIndex).setGesehenBis(null);
					lines.get(rowIndex).setZuletztGesehenAm(LocalDate.now());
				}
				dvds.saveDVDInRepo(lines.get(rowIndex));
				this.updateTable();
				break;
			case 4:
				if (aValue == null) {
					lines.get(rowIndex).setGesehenBis(null);
					lines.get(rowIndex).setZuletztGesehenAm(null);
				} else if (aValue.toString().trim().length() > 0) {
					lines.get(rowIndex).setGesehenBis(Integer.parseInt(aValue.toString()));
					lines.get(rowIndex).setZuletztGesehenAm(LocalDate.now());
				}
				dvds.saveDVDInRepo(lines.get(rowIndex));
				this.updateTable();
				break;
		default:
			break;
		}
	}	
	
	/**
	 * Beschränkt die DVD-Tabellenansicht auf den ausgewählten Aufbewahrungsplatz.
	 */	
	public void restrictTableViewToPlatzID(Long apID) {
		lines = dvds.getDVDsInPlatzID(apID);
		super.fireTableDataChanged();
	}
	
	public boolean isDuplicateDVD(DVD pDVD) {
		for (DVD dvd: lines) {
			boolean bothDVDsMovies = (pDVD.getSerieFilm().getTyp().equals(SerieFilm.MOVIE_DVD) 
									  && dvd.getSerieFilm().getTyp().equals(SerieFilm.MOVIE_DVD));
			boolean bothDVDsSeries = (pDVD.getSerieFilm().getTyp().equals(SerieFilm.SERIE_DVD) 
									  && dvd.getSerieFilm().getTyp().equals(SerieFilm.SERIE_DVD));
			
			if (bothDVDsMovies)
				if (pDVD.getSerieFilm().getTitel().equals(dvd.getSerieFilm().getTitel()))
					return true;
			
			if (bothDVDsSeries)
				if (pDVD.getSerieFilm().getTitel().equals(dvd.getSerieFilm().getTitel()) && (pDVD.getStaffel() == dvd.getStaffel()))
					return true;
		}
		
		return false;
	}

}