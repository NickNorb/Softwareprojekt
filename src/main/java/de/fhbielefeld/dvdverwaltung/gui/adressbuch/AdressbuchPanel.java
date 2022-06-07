package de.fhbielefeld.dvdverwaltung.gui.adressbuch;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.Person;
import de.fhbielefeld.dvdverwaltung.gui.ausleihen.AusleiheDateFormat;

/**
 * Definiert das Panel für das Adressbuch.
 * Es wird eine Tabelle mit Informationen zum Adressbuch angezeigt.
 * Von hier aus können Personeneinträge gelöscht oder neu angelegt und bearbeitet werden.
 * 
 * @author Nick Norberts
 * @author Linus Kliemann
 *
 */

@SuppressWarnings("serial")
@Component
public class AdressbuchPanel extends JPanel {

	private JTable abTable;
	private PersonTableModel personTableModel;

	public AdressbuchPanel(PersonTableModel tableModel) {
		this.personTableModel = tableModel;
		this.personTableModel.updateTable();
		setBounds(100, 100, 605, 344);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		abTable = new JTable(this.personTableModel);
		abTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		abTable.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(abTable);
		
		JButton btnDelete = new JButton("Person löschen");
		btnDelete.addActionListener(e -> {
			Person selectedPerson = tableModel.getPersonAtRow(abTable.getSelectedRow());
			String optionPaneHinweis = (selectedPerson.getZuletztAusgeliehenAm() == null) 
					? ("noch\nkeine Ausleihen getätigt.") 
					: ("seit\ndem "+selectedPerson.getZuletztAusgeliehenAm()
													 .format(DateTimeFormatter
															 	.ofPattern(AusleiheDateFormat.STR_AUSLEIHEDATE_FORMAT))
					 +" nichts mehr\nausgeliehen.");
			
			int userInput=JOptionPane.DEFAULT_OPTION;
			
			if (abTable.getRowCount() > 0 && abTable.getSelectedRow() > -1) {
				userInput = JOptionPane.showConfirmDialog(null, "Möchtest du die ausgewählte\n"
															  + "Person wirklich löschen?\n\n"
															  + "Hinweis: Die Person hat "+optionPaneHinweis, 
															    "Löschvorgang:", 
															    JOptionPane.YES_NO_OPTION, 
															    JOptionPane.ERROR_MESSAGE);				
			}
			
			if (userInput == JOptionPane.YES_OPTION)
				this.personTableModel.deletePerson(abTable.getSelectedRow());
			
		});
		
		JButton btnNewEntry = new JButton("Person hinzufügen");
		btnNewEntry.addActionListener(e -> new AdressbuchDialog(this.personTableModel).setVisible(true));
		
		panel.add(btnNewEntry);
		panel.add(btnDelete);
	}
	
	public void refreshTables() { this.personTableModel.updateTable(); }
		
}