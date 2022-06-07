package de.fhbielefeld.dvdverwaltung.gui.ausleihen;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.service.DVDService;
import de.fhbielefeld.dvdverwaltung.service.PersonService;

import java.awt.FlowLayout;

/**
 * Definiert das Panel für die Ausleihen.
 * Es wird eine Tabelle mit Informationen zu Ausleihen angezeigt.
 * Von hier aus können Ausleihen gelöscht oder neu angelegt und bearbeitet werden.
 * 
 * @author Leonardo Diekmann
 *
 */

@SuppressWarnings("serial")
@Component
public class AusleihePanel extends JPanel {
	private JTable ausleihenTable;
	private AusleiheTableModel ausleihenTableModel;

	public AusleihePanel(AusleiheTableModel ausleihenTableModel, PersonService pService, DVDService dvdService) {
		this.ausleihenTableModel = ausleihenTableModel;
		this.ausleihenTableModel.updateTable();
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Tabelle = new JPanel();
		add(panel_Tabelle, BorderLayout.CENTER);
		panel_Tabelle.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_Tabelle.add(panel, BorderLayout.SOUTH);
		
		JButton btnAddAusleihe = new JButton("Ausleihe hinzufügen");
		btnAddAusleihe.addActionListener(e -> {
			if (pService.getPersonListFromRepo().size() == 0 || dvdService.getVerfuegbareDVDListFromRepo().size() == 0) {
				JOptionPane.showMessageDialog(null, "Ausleihe-Erstellen fehlgeschlagen:\n"
												  + "Keine DVDs oder Personen zur\n"
												  + "Ausleihe vorhanden!"
												  , "Fehler:"
												  , JOptionPane.ERROR_MESSAGE);
				return;
			}
			new AusleiheDialog(pService, dvdService, this.ausleihenTableModel);
		});
		panel.add(btnAddAusleihe);
		
		JButton btnDeleteAusleihe = new JButton("Ausleihe löschen");
		btnDeleteAusleihe.addActionListener(e -> {
			if (ausleihenTable.getRowCount() > 0 && ausleihenTable.getSelectedRow() > -1) {

				int userInput = JOptionPane.DEFAULT_OPTION;

				userInput = JOptionPane.showConfirmDialog(null,
						"Möchtest du die ausgewählte\n" + "Ausleihe wirklich " + "löschen?", "Löschvorgang:",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

				if (userInput == JOptionPane.YES_OPTION)
					this.ausleihenTableModel.deleteAusleihe(ausleihenTable.getSelectedRow());
			}
		});
		panel.add(btnDeleteAusleihe);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_Tabelle.add(scrollPane, BorderLayout.CENTER);
		
		ausleihenTable = new JTable(this.ausleihenTableModel);
		ausleihenTable.getTableHeader().setReorderingAllowed(false);
		ausleihenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(ausleihenTable);
		
		JScrollPane scrollPane_Ausleihen = new JScrollPane();
		add(scrollPane_Ausleihen, BorderLayout.NORTH);
	}
	
	public void refreshTables() { this.ausleihenTableModel.updateTable(); }

}
