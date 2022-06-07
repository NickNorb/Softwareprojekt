package de.fhbielefeld.dvdverwaltung.gui.dvd;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.service.AufbewahrungsplatzService;
import de.fhbielefeld.dvdverwaltung.service.DVDService;
import de.fhbielefeld.dvdverwaltung.service.SerieFilmService;

/**
 * Definiert das Panel für DVDs.
 * Es wird eine Tabelle mit Informationen zu den DVDs angezeigt.
 * Von hier aus können DVD Einträge gelöscht oder neu angelegt und bearbeitet werden.
 * 
 * @author Nick Norberts
 * @author Mohamed-Khalil El Ouafi
 *
 */

@SuppressWarnings("serial")
@Component
public class DVDPanel extends JPanel {

	private JTable tblDVDs;
	private DVDTableModel dvdTableModel;
	private DVDSearchBar dvdSearchBar;

	public DVDPanel(DVDTableModel tableModel, AufbewahrungsplatzService apService, SerieFilmService sfService, DVDService dvdService) {
		this.dvdTableModel = tableModel;
		this.dvdTableModel.updateTable();
		
		super.setBounds(100, 100, 605, 344);
		super.setBorder(new EmptyBorder(5, 5, 5, 5));
		super.setLayout(new BorderLayout());
		
		JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton btnNewEntry = new JButton("DVD hinzufügen");
		btnNewEntry.addActionListener(e -> {
			if (apService.getAufbewahrungsplatzListFromRepo().size() == 0 || sfService.getSerieFilmListFromRepo().size() == 0) {
				JOptionPane.showMessageDialog(null, "DVD erstellen fehlgeschlagen:\n"
											      + "Bitte zuerst einen Aufbewahrungsplatz\n"
											      + "und Serie oder Film hinterlegen."
											      , "Fehler:"
											      , JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			new DVDDialog(this.dvdTableModel, apService, sfService, dvdService).setVisible(true);
		});
		pnlBottom.add(btnNewEntry);

		JButton btndeleteDVD = new JButton("DVD löschen");
		btndeleteDVD.addActionListener(e -> {
			int userInput=JOptionPane.DEFAULT_OPTION;
			
			if (tblDVDs.getRowCount() > 0 && tblDVDs.getSelectedRow() > -1) {
				userInput = JOptionPane.showConfirmDialog(null, "Möchtest du die ausgewählte\n"
															  + "DVD wirklich löschen?", 
															    "Löschvorgang:", 
															    JOptionPane.YES_NO_OPTION, 
															    JOptionPane.ERROR_MESSAGE);				
			}
			if (userInput == JOptionPane.YES_OPTION)
					this.dvdTableModel.deleteDVD(tblDVDs.convertRowIndexToModel(tblDVDs.getSelectedRow()));
		});
		pnlBottom.add(btndeleteDVD);

		JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTop.add(dvdSearchBar = new DVDSearchBar(dvdTableModel));
		
		JScrollPane scrollPane = new JScrollPane();

		tblDVDs = new JTable(this.dvdTableModel);
		tblDVDs.getTableHeader().setReorderingAllowed(false);
		tblDVDs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblDVDs.setRowSorter(dvdSearchBar.getDvdRowSorter());
		scrollPane.setViewportView(tblDVDs);

		super.add(pnlTop, BorderLayout.NORTH);
		super.add(pnlBottom, BorderLayout.SOUTH);
		super.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void refreshTables() { this.dvdTableModel.updateTable(); }

}