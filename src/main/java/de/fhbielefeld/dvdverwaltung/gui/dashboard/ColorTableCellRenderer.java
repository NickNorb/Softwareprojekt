package de.fhbielefeld.dvdverwaltung.gui.dashboard;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Markiert die abgelaufenen Ausleihen rot.
 * @author Jana Jagiello
 *
 */
@SuppressWarnings("serial")
public class ColorTableCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
	private DashboardAusleiheTableModel dbaTableModel;

	public ColorTableCellRenderer(DashboardAusleiheTableModel dbaTableModel) {
		super();
		this.dbaTableModel = dbaTableModel;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if (dbaTableModel.isExpired(row)) {
			comp.setForeground(Color.RED);
		} else {
			comp.setForeground(Color.BLACK);
		}
		return comp;
	}
	

}
