package de.fhbielefeld.dvdverwaltung.gui.dashboard;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.DVD;
import de.fhbielefeld.dvdverwaltung.entity.SerieFilm;
import de.fhbielefeld.dvdverwaltung.gui.ausleihen.AusleiheDateFormat;
import de.fhbielefeld.dvdverwaltung.service.DashboardService;

/**
 * Definiert die zuletzt gesehenen DVDs.
 * 
 * @author Jana Jagiello
 *
 */
@SuppressWarnings("serial")
@Component
public class DashboardDVDTableModel extends AbstractTableModel {

	private DashboardService dbservice;
	private List<DVD> lines;

	@Autowired
	public DashboardDVDTableModel(DashboardService dbservice) {
		this.dbservice = dbservice;
		this.updateTable();
	}

	private String[] headers = { "Titel", "Gesehen bis", "Gesehen am" };

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public int getRowCount() {
		return lines.size();
	}

	@Override
	public String getColumnName(int col) {
		return headers[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return lines.get(rowIndex).toString();
		case 1:
			// Falls zu Ende geschaut
			if (lines.get(rowIndex).getGesehen())
				return "zu Ende";

			// Serie angefangen, aber noch nicht zu Ende geschaut
			if (lines.get(rowIndex).getSerieFilm().getTyp().equals(SerieFilm.SERIE_DVD)
					&& lines.get(rowIndex).getGesehenBis() != null)
				return "Folge: " + lines.get(rowIndex).getGesehenBis();

		case 2:
			return lines.get(rowIndex).getZuletztGesehenAm()
					.format(DateTimeFormatter.ofPattern(AusleiheDateFormat.STR_AUSLEIHEDATE_FORMAT));
		default:
			return null;
		}
	}

	public void updateTable() {
		lines = dbservice.getFirstTenDVDsSorted();
		super.fireTableDataChanged();
	}
}
