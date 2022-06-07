package de.fhbielefeld.dvdverwaltung.gui.aufbewahrungsplatz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.gui.dvd.DVDTableModel;
import de.fhbielefeld.dvdverwaltung.service.AufbewahrungsplatzService;

/**
 * Erstellt ein Panel, in dem die Aufbewahrungsplätze angezeigt werden.
 * 
 * @author Jana Jagiello
 *
 */
@SuppressWarnings("serial")
@Component
public class AufbewahrungsplatzPanel extends JPanel {
	
	private AufbewahrungsplatzTableModel apTableModel;

	public AufbewahrungsplatzPanel(AufbewahrungsplatzService apService, AufbewahrungsplatzTableModel apTableModel, DVDTableModel dvdTableModel) {
		this.apTableModel = apTableModel;
		this.apTableModel.updateTable();

		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		JButton addButton = new JButton("Aufbewahrungsplatz hinzufügen");
		addButton.addActionListener(e -> new AufbewahrungsplatzDialog(this.apTableModel).setVisible(true));
		panel.add(addButton);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		final int BEZEICHNUNG_COL_WIDTH=100, ANZDVDS_COL_WIDTH=550;
		
		JTable apTable = new JTable(this.apTableModel);
		apTable.getColumnModel().getColumn(0).setPreferredWidth(BEZEICHNUNG_COL_WIDTH);
		apTable.getColumnModel().getColumn(1).setPreferredWidth(ANZDVDS_COL_WIDTH);
		apTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		apTable.getTableHeader().setReorderingAllowed(false);
		
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		apTable.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		
		scrollPane.setViewportView(apTable);

		JButton deleteButton = new JButton("Aufbewahrungsplatz löschen");
		deleteButton.addActionListener(e -> {
			if (apTable.getRowCount() > 0 && apTable.getSelectedRow() > -1) {

				int userInput = JOptionPane.DEFAULT_OPTION;

				userInput = JOptionPane.showConfirmDialog(null,
						"Möchtest du den ausgewählten\n" + "Aufbewahrungsplatz wirklich " + "löschen?", "Löschvorgang:",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

				if (userInput == JOptionPane.YES_OPTION)
					this.apTableModel.deleteAufbewahrungsplatz(apTable.getSelectedRow());

			}
		});
		panel.add(deleteButton);

		JButton detailsButton = new JButton("DVDs anzeigen");
		detailsButton.addActionListener(e -> {
			if (apTable.getSelectedRow() == -1 || apTable.getRowCount() <= 0)
				return;

			String apTitle = apTable.getValueAt(apTable.getSelectedRow(), 0).toString();
			new AufbewahrungsplatzDialogDetail(apTitle, apService, dvdTableModel).setVisible(true);
		});
		panel.add(detailsButton);
	}
	
	public void refreshTables() { this.apTableModel.updateTable(); }

}