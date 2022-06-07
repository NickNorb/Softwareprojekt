package de.fhbielefeld.dvdverwaltung.gui.dashboard;

import java.time.LocalDate;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.Ausleihe;
import de.fhbielefeld.dvdverwaltung.service.DashboardService;

/**
 * Definiert die aktuell ausgeliehen DVDs.
 * 
 * @author Jana Jagiello
 *
 */
@SuppressWarnings("serial")
@Component
public class DashboardAusleiheTableModel extends AbstractTableModel {

	private DashboardService dbservice;
	private List<Ausleihe> lines;
	private String[] headers = {"Titel", "Ausgeliehen von"};
	
	@Autowired
	public DashboardAusleiheTableModel(DashboardService dbservice) {
		this.dbservice = dbservice;
		updateTable();
	}
	
	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public int getRowCount() {
		return lines.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0: return lines.get(rowIndex).getDvd();
		case 1: return lines.get(rowIndex).getPerson();
		default: return null;
		}
	}

	@Override
	public String getColumnName(int col) {
		return headers[col];
	}
	
	public void updateTable() {
		lines = dbservice.getOnlyCurrentAusleihen();
		fireTableDataChanged();
	}
	
	public boolean isExpired(int rowIndex) { 
		return lines.get(rowIndex).getDatVorRueckgabe().isBefore(LocalDate.now()); 
	}
}
