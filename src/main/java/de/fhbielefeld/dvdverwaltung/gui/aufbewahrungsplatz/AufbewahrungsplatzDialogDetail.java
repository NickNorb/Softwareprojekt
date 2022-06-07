package de.fhbielefeld.dvdverwaltung.gui.aufbewahrungsplatz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import de.fhbielefeld.dvdverwaltung.gui.dvd.DVDTableModel;
import de.fhbielefeld.dvdverwaltung.service.AufbewahrungsplatzService;

/**
 * 
 * Erzeugt einen Dialog, welcher die DVD-Titel der an dem ausgewählten Aufbewahrungsplatz befindlichen DVDs anzeigt. 
 * @author Jana Jagiello
 * @author Mohamed-Khalil El Ouafi
 *
 */
@SuppressWarnings("serial")
public class AufbewahrungsplatzDialogDetail extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable apDVDTable;

	public AufbewahrungsplatzDialogDetail(String apTitle, AufbewahrungsplatzService apService, DVDTableModel dvdTableModel) {
		setBounds(100, 100, 505, 300);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setIconImage(new ImageIcon("./iconlibrary/AppIcon.png").getImage());
		
		dvdTableModel.restrictTableViewToPlatzID(apService.getApFromRepoByBez(apTitle).getAufbewahrungsplatzId());
		apDVDTable = new JTable(dvdTableModel);
		apDVDTable.getTableHeader().setReorderingAllowed(false);
		apDVDTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		apDVDTable.getColumnModel().removeColumn(apDVDTable.getColumnModel().getColumn(1));
		apDVDTable.getColumnModel().removeColumn(apDVDTable.getColumnModel().getColumn(2));
		apDVDTable.getColumnModel().removeColumn(apDVDTable.getColumnModel().getColumn(2));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(apDVDTable);
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setTitle("Detailansicht - Aufbewahrungsplatz: \""+apTitle+"\"");
		setModal(true);
		setLocationRelativeTo(null);
	}

}