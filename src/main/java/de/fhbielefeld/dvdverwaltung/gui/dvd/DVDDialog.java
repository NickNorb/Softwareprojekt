package de.fhbielefeld.dvdverwaltung.gui.dvd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;

import de.fhbielefeld.dvdverwaltung.entity.Aufbewahrungsplatz;
import de.fhbielefeld.dvdverwaltung.entity.DVD;
import de.fhbielefeld.dvdverwaltung.entity.SerieFilm;
import de.fhbielefeld.dvdverwaltung.service.AufbewahrungsplatzService;
import de.fhbielefeld.dvdverwaltung.service.DVDService;
import de.fhbielefeld.dvdverwaltung.service.SerieFilmService;

/**
 * Definiert eine Eingabemaske, in welche Daten zur Erfassung einer DVD eingegeben werden können.
 * 
 * @author Nick Norberts
 *
 */
@SuppressWarnings("serial")
public class DVDDialog extends JDialog {

	private JPanel contentPane;
	private JTextField textFieldGesehenBis, textFieldStaffel;

	public DVDDialog(DVDTableModel tableModel, AufbewahrungsplatzService apService, SerieFilmService sfService, DVDService dvdService) {
		super.setTitle("Neue DVD:");
		super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		super.setBounds(100, 100, 383, 177);
		super.setResizable(false);
		super.setModal(true);
		super.setIconImage(new ImageIcon("./iconlibrary/AppIcon.png").getImage());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		super.setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 25, 25, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel = new JLabel("Titel:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblAufbewahrungsplatz = new JLabel("Aufbewahrungsplatz:");
		GridBagConstraints gbc_lblAufbewahrungsplatz = new GridBagConstraints();
		gbc_lblAufbewahrungsplatz.anchor = GridBagConstraints.EAST;
		gbc_lblAufbewahrungsplatz.insets = new Insets(0, 0, 5, 5);
		gbc_lblAufbewahrungsplatz.gridx = 0;
		gbc_lblAufbewahrungsplatz.gridy = 1;
		panel_1.add(lblAufbewahrungsplatz, gbc_lblAufbewahrungsplatz);
		
		JComboBox<Object> apComboBox = new JComboBox<>(apService
														.getAufbewahrungsplatzListFromRepo()
														.toArray());
		   
		GridBagConstraints gbc_AufbewahrungsplatzComboBox = new GridBagConstraints();
		gbc_AufbewahrungsplatzComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_AufbewahrungsplatzComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_AufbewahrungsplatzComboBox.gridx = 1;
		gbc_AufbewahrungsplatzComboBox.gridy = 1;
		panel_1.add(apComboBox, gbc_AufbewahrungsplatzComboBox);
		
		JLabel lblStaffel = new JLabel("Serienstaffel:");
		GridBagConstraints gbc_lblStaffel = new GridBagConstraints();
		gbc_lblStaffel.anchor = GridBagConstraints.EAST;
		gbc_lblStaffel.insets = new Insets(0, 0, 0, 5);
		gbc_lblStaffel.gridx = 0;
		gbc_lblStaffel.gridy = 2;
		panel_1.add(lblStaffel, gbc_lblStaffel);
		
		textFieldStaffel = new JTextField(10);
		GridBagConstraints gbc_textFieldStaffel = new GridBagConstraints();
		gbc_textFieldStaffel.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldStaffel.gridx = 1;
		gbc_textFieldStaffel.gridy = 2;
		panel_1.add(textFieldStaffel, gbc_textFieldStaffel);
		
		JLabel lblGesehen = new JLabel("Zu Ende gesehen?");
		GridBagConstraints gbc_lblGesehen = new GridBagConstraints();
		gbc_lblGesehen.anchor = GridBagConstraints.EAST;
		gbc_lblGesehen.insets = new Insets(0, 0, 5, 5);
		gbc_lblGesehen.gridx = 0;
		gbc_lblGesehen.gridy = 3;
		panel_1.add(lblGesehen, gbc_lblGesehen);
		
		JCheckBox cbGesehen = new JCheckBox("");
		GridBagConstraints gbc_ChckbxGesehen = new GridBagConstraints();
		gbc_ChckbxGesehen.insets = new Insets(0, 0, 5, 0);
		gbc_ChckbxGesehen.gridx = 1;
		gbc_ChckbxGesehen.gridy = 3;
		panel_1.add(cbGesehen, gbc_ChckbxGesehen);		

		JLabel lblGesehenBis = new JLabel("Gesehen bis (Serienfolge):");
		GridBagConstraints gbc_lblGesehenBis = new GridBagConstraints();
		gbc_lblGesehenBis.anchor = GridBagConstraints.EAST;
		gbc_lblGesehenBis.insets = new Insets(0, 0, 0, 5);
		gbc_lblGesehenBis.gridx = 0;
		gbc_lblGesehenBis.gridy = 4;
		panel_1.add(lblGesehenBis, gbc_lblGesehenBis);
		
		textFieldGesehenBis = new JTextField(10);
		GridBagConstraints gbc_textFieldGesehenBis = new GridBagConstraints();
		gbc_textFieldGesehenBis.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldGesehenBis.gridx = 1;
		gbc_textFieldGesehenBis.gridy = 4;
		panel_1.add(textFieldGesehenBis, gbc_textFieldGesehenBis);

		JComboBox<Object> sfComboBox = new JComboBox<>(sfService
														.getSerieFilmListFromRepo()
														.toArray());
		SerieFilm preselectedSF = (SerieFilm) sfComboBox.getSelectedItem();
		if(preselectedSF.getTyp().equals(SerieFilm.MOVIE_DVD)) {
			this.disableSerieFields();				
		} else {
			this.enableSerieFields();				
		}		
		
		GridBagConstraints gbc_sfComboBox = new GridBagConstraints();
		gbc_sfComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_sfComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_sfComboBox.gridx = 1;
		gbc_sfComboBox.gridy = 0;
		
		
		panel_1.add(sfComboBox, gbc_sfComboBox);
		
		cbGesehen.addActionListener(e -> {
			SerieFilm clickedSF = (SerieFilm) sfComboBox.getSelectedItem();
			if(clickedSF.getTyp().equals(SerieFilm.SERIE_DVD) && !cbGesehen.isSelected()) {
				this.enableSerieFields();
				return;
			}
			
			textFieldGesehenBis.setText(null);
			textFieldGesehenBis.setBackground(Color.GRAY);
			textFieldGesehenBis.setEnabled(false);				
		});	
		
		sfComboBox.addActionListener(e -> {
			SerieFilm clickedSF = (SerieFilm) sfComboBox.getSelectedItem();
			if(clickedSF.getTyp().equals(SerieFilm.SERIE_DVD) && cbGesehen.isSelected()) {
				textFieldStaffel.setBackground(Color.WHITE);
				textFieldStaffel.setEnabled(true);
				return;
			}
			
			if(clickedSF.getTyp().equals(SerieFilm.SERIE_DVD)) {
				this.enableSerieFields();
				return;
			}

			this.disableSerieFields();				
		});

		
		JButton btnNewEntry = new JButton("Hinzufügen");
		btnNewEntry.addActionListener(e -> {
			SerieFilm selectedSF = (SerieFilm) sfComboBox.getSelectedItem();
			Aufbewahrungsplatz selectedAP = (Aufbewahrungsplatz) apComboBox.getSelectedItem();

			Integer inputGesehenBis=null, inputStaffel=null;
			try {
				if (selectedSF.getTyp().equals(SerieFilm.SERIE_DVD) 
					&& textFieldStaffel.getText().trim().length() <= 0 
					&& textFieldGesehenBis.getText().trim().length() <= 0
					&& !cbGesehen.isSelected())
						throw new NumberFormatException();
				
				inputGesehenBis = (selectedSF.getTyp().equals(SerieFilm.SERIE_DVD) && !cbGesehen.isSelected() 
								   && textFieldGesehenBis.getText().trim().length() > 0) 
										? (Integer.parseInt(textFieldGesehenBis.getText().trim())) 
										: (null);
				inputStaffel = (selectedSF.getTyp().equals(SerieFilm.SERIE_DVD) 
								&& textFieldStaffel.getText().trim().length() > 0) 
									? (Integer.parseInt(textFieldStaffel.getText().trim())) 
									: (null);
				
				if (inputStaffel == null && selectedSF.getTyp().equals(SerieFilm.SERIE_DVD)) {
					textFieldStaffel.setBorder(BorderFactory.createLineBorder(Color.RED));
					return;
				}
			} catch (NumberFormatException nfExc) {
				textFieldStaffel.setBorder(BorderFactory.createLineBorder(Color.RED));
				textFieldGesehenBis.setBorder(BorderFactory.createLineBorder(Color.RED));
				return;
			} catch (Exception parentExc) {
				parentExc.printStackTrace();
			}
			
			DVD dvd = DVD.builder()
							.aufbewahrungsplatz(selectedAP)
							.serieFilm(selectedSF)
							.staffel(inputStaffel)
							.gesehenBis(inputGesehenBis)
							.gesehen(cbGesehen.isSelected())
							.verfuegbar(true)
							.build();
			
			if (tableModel.isDuplicateDVD(dvd)) {
				JOptionPane.showMessageDialog(null, "Erstellen fehlgeschlagen:\n"
												  + "DVD zum Film oder Serienstaffel\n"
												  + "namens \""+selectedSF.getTitel()+"\"\n"
												  + "existiert bereits!"
												  , "Fehler:"
												  , JOptionPane.ERROR_MESSAGE);
				return;
			}			
			
			tableModel.saveDVD(dvd);
			super.dispose();
		});
		panel.add(btnNewEntry);
		super.getRootPane().setDefaultButton(btnNewEntry);
		
		JButton btnCancel = new JButton("Abbrechen");
		btnCancel.addActionListener(e -> super.dispose());
		panel.add(btnCancel);
		
		tableModel.updateTable();
		
		super.pack();
		super.setLocationRelativeTo(null);
	}
	
	private void disableSerieFields() {
		textFieldGesehenBis.setText(null);
		textFieldGesehenBis.setBackground(Color.GRAY);
		textFieldGesehenBis.setEnabled(false);				
		textFieldStaffel.setText(null);
		textFieldStaffel.setBackground(Color.GRAY);
		textFieldStaffel.setEnabled(false);
	}
	
	private void enableSerieFields() {
		textFieldGesehenBis.setBackground(Color.WHITE);
		textFieldGesehenBis.setEnabled(true);
		textFieldStaffel.setBackground(Color.WHITE);
		textFieldStaffel.setEnabled(true);
	}

}