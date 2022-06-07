package de.fhbielefeld.dvdverwaltung.gui.serienfilme;

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

import de.fhbielefeld.dvdverwaltung.gui.dvd.DVDDialog;
import de.fhbielefeld.dvdverwaltung.gui.dvd.DVDTableModel;
import de.fhbielefeld.dvdverwaltung.service.AufbewahrungsplatzService;
import de.fhbielefeld.dvdverwaltung.service.DVDService;
import de.fhbielefeld.dvdverwaltung.service.SerieFilmService;

/**
 * Definiert das Panel für die Serien/Filme.
 * Es wird eine Tabelle mit Informationen zu Serien/Filmen angezeigt.
 * Von hier aus können Serien/Filme gelöscht oder neu angelegt und bearbeitet werden.
 * Außerdem gibt es die Möglichkeit eine DVD anzulegen.
 * 
 * 
 * @author Leonardo Diekmann
 *
 */
@SuppressWarnings("serial")
@Component
public class SerienFilmePanel extends JPanel {

	private JTable tblSerienFilme;
	private SerieFilmTableModel sfTableModel;
	
	private DVDTableModel dvdTableModel;

	public SerienFilmePanel(SerieFilmTableModel sfTableModel, DVDTableModel tableModel, AufbewahrungsplatzService apService, SerieFilmService sfService, DVDService dvdService) {
		
		this.dvdTableModel = tableModel;
		this.dvdTableModel.updateTable();
	
		this.sfTableModel = sfTableModel;
		this.sfTableModel.updateTable();
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		final int TYP_COL_WIDTH=10, TITEL_COL_WIDTH=675;
		
		tblSerienFilme = new JTable(this.sfTableModel);
		tblSerienFilme.getColumnModel().getColumn(0).setPreferredWidth(TYP_COL_WIDTH);
		tblSerienFilme.getColumnModel().getColumn(1).setPreferredWidth(TITEL_COL_WIDTH);
		tblSerienFilme.getTableHeader().setReorderingAllowed(false);
		tblSerienFilme.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(tblSerienFilme);
		super.add(scrollPane, BorderLayout.CENTER);
		
		JButton btnAdd = new JButton("Serie/Film hinzuf\u00FCgen");
		btnAdd.addActionListener(e -> new SerienFilmeDialog(this.sfTableModel).setVisible(true));
		panel.add(btnAdd);
		
		JButton btnDelete = new JButton("Serie/Film l\u00F6schen");
		btnDelete.addActionListener(e -> {
			if (tblSerienFilme.getRowCount() > 0 && tblSerienFilme.getSelectedRow() > -1) {
				
				int userInput=JOptionPane.DEFAULT_OPTION;
				
				userInput = JOptionPane.showConfirmDialog(null, "Möchtest du den ausgewählten\n"
															  + "Film oder Serie wirklich "
															  + "löschen?", 
															  "Löschvorgang:", 
															  JOptionPane.YES_NO_OPTION, 
															  JOptionPane.ERROR_MESSAGE);
				
				if (userInput == JOptionPane.YES_OPTION)
					sfTableModel.deleteSerieFilm(tblSerienFilme.getSelectedRow());
			}
		});
		panel.add(btnDelete);
		
		JButton btnNewDVD = new JButton("DVD anlegen");
		btnNewDVD.addActionListener(e -> {
			if (apService.getAufbewahrungsplatzListFromRepo().size() == 0 || sfService.getSerieFilmListFromRepo().size() == 0) {
			    JOptionPane.showMessageDialog(null, "DVD erstellen fehlgeschlagen:\nBitte zuerst einen Aufbewahrungsplatz\nund Serie oder Film hinterlegen.", "Fehler:", JOptionPane.ERROR_MESSAGE);
			    return;
			  }
			            
			  new DVDDialog(this.dvdTableModel, apService, sfService, dvdService).setVisible(true);
		});
		panel.add(btnNewDVD);
	}
	
	public void refreshTables() { this.sfTableModel.updateTable(); }

}