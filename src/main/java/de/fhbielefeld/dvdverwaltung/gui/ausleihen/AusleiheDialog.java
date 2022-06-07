package de.fhbielefeld.dvdverwaltung.gui.ausleihen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import de.fhbielefeld.dvdverwaltung.entity.Ausleihe;
import de.fhbielefeld.dvdverwaltung.entity.DVD;
import de.fhbielefeld.dvdverwaltung.entity.Person;
import de.fhbielefeld.dvdverwaltung.service.DVDService;
import de.fhbielefeld.dvdverwaltung.service.PersonService;

/**
 * Definiert eine Eingabemaske, in welche Daten zur Erfassung einer Ausleihe
 * eingegeben werden können.
 * 
 * @author Leonardo Diekmann
 * @author Mohamed-Khalil El Ouafi
 *
 */
@SuppressWarnings("serial")
public class AusleiheDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JDatePanelImpl dtPanelAm, dtPanelBis;
	private JDatePickerImpl dtPickerAm, dtPickerBis;
	private UtilDateModel modelDtAm, modelDtBis;
	private Properties dtProps;

	public AusleiheDialog(PersonService pService, DVDService dvdService, AusleiheTableModel aTableModel) {
		
		setBounds(100, 100, 450, 250);
		super.setModal(true);
		super.setResizable(false);
		super.setTitle("Neue Ausleihe:");
		super.setIconImage(new ImageIcon("./iconlibrary/AppIcon.png").getImage());
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 102, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblTitel = new JLabel("DVD-Titel:");
		GridBagConstraints gbc_lblTitel = new GridBagConstraints();
		gbc_lblTitel.anchor = GridBagConstraints.EAST;
		gbc_lblTitel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitel.gridx = 1;
		gbc_lblTitel.gridy = 0;
		panel.add(lblTitel, gbc_lblTitel);

		JComboBox<Object> DVDComboBox = new JComboBox<>(dvdService.getVerfuegbareDVDListFromRepo().toArray());

		GridBagConstraints gbc_DVDComboBox = new GridBagConstraints();
		gbc_DVDComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_DVDComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_DVDComboBox.gridx = 2;
		gbc_DVDComboBox.gridy = 0;
		panel.add(DVDComboBox, gbc_DVDComboBox);

		JLabel lblVon = new JLabel("ausgeliehen von:");
		GridBagConstraints gbc_lblVon = new GridBagConstraints();
		gbc_lblVon.anchor = GridBagConstraints.EAST;
		gbc_lblVon.insets = new Insets(0, 0, 5, 5);
		gbc_lblVon.gridx = 1;
		gbc_lblVon.gridy = 1;
		panel.add(lblVon, gbc_lblVon);

		JComboBox<Object> PersonComboBox = new JComboBox<>(pService.getPersonListFromRepo().toArray());

		GridBagConstraints gbc_PersonComboBox = new GridBagConstraints();
		gbc_PersonComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_PersonComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_PersonComboBox.gridx = 2;
		gbc_PersonComboBox.gridy = 1;
		panel.add(PersonComboBox, gbc_PersonComboBox);

		JLabel lblAm = new JLabel("ausgeliehen am:");
		GridBagConstraints gbc_lblAm = new GridBagConstraints();
		gbc_lblAm.anchor = GridBagConstraints.EAST;
		gbc_lblAm.insets = new Insets(0, 0, 5, 5);
		gbc_lblAm.gridx = 1;
		gbc_lblAm.gridy = 2;
		panel.add(lblAm, gbc_lblAm);

		this.modelDtAm = new UtilDateModel();
		modelDtAm.setSelected(true);
		
		this.dtProps = new Properties();
		dtProps.put("text.today", "Heute");
		dtProps.put("text.day", "Tag");
		dtProps.put("text.month", "Monat");
		dtProps.put("text.year", "Jahr");
		
		modelDtAm.setSelected(true);
		this.dtPanelAm = new JDatePanelImpl(modelDtAm, dtProps);
		this.dtPickerAm = new JDatePickerImpl(dtPanelAm, new AusleiheDateFormat());
		dtPickerAm.getJFormattedTextField().setForeground(Color.BLACK);
		
		GridBagConstraints gbc_dtPickerAm = new GridBagConstraints();
		gbc_dtPickerAm.insets = new Insets(0, 0, 5, 5);
		gbc_dtPickerAm.fill = GridBagConstraints.HORIZONTAL;
		gbc_dtPickerAm.gridx = 2;
		gbc_dtPickerAm.gridy = 2;
		panel.add(dtPickerAm, gbc_dtPickerAm);

		JLabel lblBis = new JLabel("ausgeliehen bis:");
		GridBagConstraints gbc_lblBis = new GridBagConstraints();
		gbc_lblBis.anchor = GridBagConstraints.EAST;
		gbc_lblBis.insets = new Insets(0, 0, 5, 5);
		gbc_lblBis.gridx = 1;
		gbc_lblBis.gridy = 3;
		panel.add(lblBis, gbc_lblBis);
		
		this.modelDtBis = new UtilDateModel();
		this.dtPanelBis = new JDatePanelImpl(modelDtBis, dtProps);
		this.dtPickerBis = new JDatePickerImpl(dtPanelBis, new AusleiheDateFormat());		
		
		GridBagConstraints gbc_dtPickerBis = new GridBagConstraints();
		gbc_dtPickerBis.insets = new Insets(0, 0, 5, 5);
		gbc_dtPickerBis.fill = GridBagConstraints.HORIZONTAL;
		gbc_dtPickerBis.gridx = 2;
		gbc_dtPickerBis.gridy = 3;
		panel.add(dtPickerBis, gbc_dtPickerBis);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnHinzufuegen = new JButton("Hinzufügen");
				btnHinzufuegen.addActionListener(e -> {
						DVD selectedDVD = (DVD) DVDComboBox.getSelectedItem();
						Person selectedPerson = (Person) PersonComboBox.getSelectedItem();
						try {
//							Umwandlung der Datumeingaben von Date zu LocalDate
							LocalDate dtAusleihe = modelDtAm.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							LocalDate dtVorRueckgabe = modelDtBis.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							
//							Leihdauer in Tagen berechnen
							Long leihdauer = ChronoUnit.DAYS.between(dtAusleihe, dtVorRueckgabe);
							
							if (leihdauer <= 0) { throw new DateTimeException(null); }
							
							Ausleihe ausleihe = Ausleihe.builder()
															.person(selectedPerson)
															.dvd(selectedDVD)
															.leihdauer(leihdauer)
															.datAusleihe(dtAusleihe)
															.datVorRueckgabe(dtVorRueckgabe)
															.zurueckgegeben(false)
															.build();

							selectedDVD.setVerfuegbar(false);
							dvdService.saveDVDInRepo(selectedDVD);
							
//							Anpassung vom Ausleihenhinweis beim Personen-Löschen
							boolean moreRecentAusleihe = ((selectedPerson.getZuletztAusgeliehenAm() != null) && (ChronoUnit.DAYS.between(selectedPerson.getZuletztAusgeliehenAm(), dtAusleihe) > 0));
							
							if (selectedPerson.getZuletztAusgeliehenAm() == null || moreRecentAusleihe)
								selectedPerson.setZuletztAusgeliehenAm(dtAusleihe);
							
							pService.savePersonInRepo(selectedPerson);					
							
							aTableModel.saveAusleihe(ausleihe);
							super.dispose();
						} catch (DateTimeException dtExc) {
							dtPickerBis.setBorder(new LineBorder(Color.RED, 1));
							JOptionPane.showMessageDialog(null, "Ausleihe-Erstellen fehlgeschlagen:\n"
															  + "Das Ausleihdatum muss vor\n"
															  + "dem Rückgabedatum liegen"
															  , "Fehler:"
															  , JOptionPane.ERROR_MESSAGE);
							return;
						} catch(NullPointerException npExc) {
							JOptionPane.showMessageDialog(null, "Ausleihe-Erstellen fehlgeschlagen:\n"
															  + "Bitte ungültige Angaben überprüfen!"
															  , "Fehler:"
															  , JOptionPane.ERROR_MESSAGE);
							return;
						} catch (Exception exc) {
							exc.printStackTrace();
						}
				});
				btnHinzufuegen.setActionCommand("OK");
				buttonPane.add(btnHinzufuegen);
				getRootPane().setDefaultButton(btnHinzufuegen);
			}
			{
				JButton btnCancel = new JButton("Abbrechen");
				btnCancel.addActionListener(e -> super.dispose());
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}

			super.pack();
			super.setLocationRelativeTo(null);
			super.setVisible(true);
		}
	}

}