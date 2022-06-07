package de.fhbielefeld.dvdverwaltung.gui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.gui.adressbuch.AdressbuchPanel;
import de.fhbielefeld.dvdverwaltung.gui.aufbewahrungsplatz.AufbewahrungsplatzPanel;
import de.fhbielefeld.dvdverwaltung.gui.ausleihen.AusleihePanel;
import de.fhbielefeld.dvdverwaltung.gui.dashboard.DashboardPanel;
import de.fhbielefeld.dvdverwaltung.gui.dvd.DVDPanel;
import de.fhbielefeld.dvdverwaltung.gui.serienfilme.SerienFilmePanel;

/**
 * Hauptansicht der DVD-Verwaltung. 
 * Definiert ein Container vom Typ JTabbedPane, welches Browser-ähnlich 
 * alle weiteren Ansichten über Tabs zugänglich macht und verwaltet
 * 
 * @author Jana Jagiello
 * @author Mohamed-Khalil El Ouafi
 *
 */
@SuppressWarnings("serial")
@Component
public class MainFrame extends JFrame {

	private JTabbedPane tabs;

	@Autowired
	public MainFrame(AufbewahrungsplatzPanel pAufbewahrung, DashboardPanel pDashboard, AdressbuchPanel pAdressbuch, AusleihePanel pAusleihen, DVDPanel pDVD, SerienFilmePanel pSerienFilme) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(620, 300);
		setTitle("DVD-Verwaltung");
		setLayout(new BorderLayout());
		setIconImage(new ImageIcon("./iconlibrary/AppIcon.png").getImage());
		
		tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		tabs.addTab("Dashboard", pDashboard);
		tabs.addTab("Serien & Filme", pSerienFilme);
		tabs.addTab("DVDs", pDVD);
		tabs.addTab("Ausleihen", pAusleihen);
		tabs.addTab("Aufbewahrungsplätze", pAufbewahrung);
		tabs.addTab("Adressbuch", pAdressbuch);
		
		for (int i = 0; i < tabs.getTabCount(); i++)
			tabs.setIconAt(i, new ImageIcon("./iconlibrary/"+tabs.getTitleAt(i)+".png"));
			
		tabs.addChangeListener(c -> {
			switch (tabs.getSelectedIndex()) {
				case 0: pDashboard.refreshTables();
						break;
				case 1: pSerienFilme.refreshTables();
						break;
				case 2: pDVD.refreshTables();
						break;
				case 3: pAusleihen.refreshTables();
						break;
				case 4: pAufbewahrung.refreshTables();
						break;
				case 5: pAdressbuch.refreshTables();
						break;
			default: break;
			}
		});
		
		getContentPane().add(tabs, BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(null);
	}
	
}