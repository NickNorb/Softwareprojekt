package de.fhbielefeld.dvdverwaltung.gui.dashboard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.springframework.stereotype.Component;

/**
 * Erstellt ein Panel, in dem das Dashboard angezeigt wird.
 * 
 * @author Jana Jagiello
 * 
 */
@SuppressWarnings("serial")
@Component
public class DashboardPanel extends JPanel {
	
	private JTable dvdTable, ausleihenTable;
	private DashboardDVDTableModel dvdTableModel;
	private DashboardAusleiheTableModel ausleiheTableModel;

	public DashboardPanel(DashboardDVDTableModel dvdTableModel, DashboardAusleiheTableModel ausleiheTableModel) {
		this.dvdTableModel = dvdTableModel;
		this.ausleiheTableModel = ausleiheTableModel;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel zgLabel = new JLabel("zuletzt gesehen:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(zgLabel, gbc_lblNewLabel);
		zgLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel aALabel = new JLabel("aktuell ausgeliehen:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		add(aALabel, gbc_lblNewLabel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		add(scrollPane_1, gbc_scrollPane_1);
		
		dvdTable = new JTable(dvdTableModel);
		dvdTable.getTableHeader().setReorderingAllowed(false);
		dvdTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dvdTable.setEnabled(false);
		scrollPane_1.setViewportView(dvdTable);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 1;
		gbc_scrollPane_2.gridy = 1;
		add(scrollPane_2, gbc_scrollPane_2);
		
		ausleihenTable = new JTable(ausleiheTableModel);
		ausleihenTable.getTableHeader().setReorderingAllowed(false);
		ausleihenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ausleihenTable.setEnabled(false);
		ausleihenTable.setDefaultRenderer(Object.class, new ColorTableCellRenderer(ausleiheTableModel));
		
		scrollPane_2.setViewportView(ausleihenTable);
	}
	
	public void refreshTables() {
		this.dvdTableModel.updateTable();
		this.ausleiheTableModel.updateTable();
	}

}