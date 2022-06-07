package de.fhbielefeld.dvdverwaltung.gui.adressbuch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import de.fhbielefeld.dvdverwaltung.entity.Person;

/**
 * Definiert eine Eingabemaske, in welche Daten zur Erfassung einer Person
 * eingegeben werden können.
 * 
 * @author Nick Norberts
 * @author Jana Jagiello
 *
 */
@SuppressWarnings("serial")
public class AdressbuchDialog extends JDialog {

	private JPanel contentPane;
	private JTextField textFieldVorname;
	private JTextField textFieldNachname;
	private JTextField textFieldHandynummer;

	public AdressbuchDialog(PersonTableModel tableModel) {
		setBounds(100, 100, 340, 172);
		setModal(true);
		setTitle("Neue Person:");
		setResizable(false);
		setIconImage(new ImageIcon("./iconlibrary/AppIcon.png").getImage());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton btnClose = new JButton("Abbrechen");
		btnClose.addActionListener(e -> AdressbuchDialog.this.dispose());

		JButton btnNewEntry = new JButton("Hinzufügen");
		btnNewEntry.addActionListener(e -> {

			textFieldVorname.setBorder(null);
			textFieldNachname.setBorder(null);
			textFieldHandynummer.setBorder(null);
			//markiert Eingabefelder rot, falls nötig
			if (textFieldVorname.getText().trim().length() <= 0) {
				textFieldVorname.setBorder(BorderFactory.createLineBorder(Color.RED));
				return;
			} else if (textFieldNachname.getText().trim().length() <= 0) {
				textFieldNachname.setBorder(BorderFactory.createLineBorder(Color.RED));
				return;
			} else if (textFieldHandynummer.getText().trim().length() <= 0) {
				textFieldHandynummer.setBorder(BorderFactory.createLineBorder(Color.RED));
				return;
			} else if (tableModel.isPhoneInputDuplicate(textFieldHandynummer.getText().trim())) {
				textFieldHandynummer.setBorder(BorderFactory.createLineBorder(Color.RED));
				return;
			} else if (!tableModel.isValidPhoneInput(textFieldHandynummer.getText().trim())) {
				textFieldHandynummer.setBorder(BorderFactory.createLineBorder(Color.RED));
				return;
			}

			Person person = Person.builder().handynummer(textFieldHandynummer.getText().trim())
											.vorname(textFieldVorname.getText().trim())
											.nachname(textFieldNachname.getText().trim())
											.build();

			tableModel.savePerson(person);
			tableModel.updateTable();
			super.dispose();
		});
		panel.add(btnNewEntry);
		panel.add(btnClose);
		getRootPane().setDefaultButton(btnNewEntry);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 25, 25, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblNewLabel = new JLabel("Vorname:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);

		textFieldVorname = new JTextField();
		GridBagConstraints gbc_textFieldVorname = new GridBagConstraints();
		gbc_textFieldVorname.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldVorname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldVorname.gridx = 1;
		gbc_textFieldVorname.gridy = 0;
		panel_1.add(textFieldVorname, gbc_textFieldVorname);
		textFieldVorname.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Nachname:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);

		textFieldNachname = new JTextField();
		GridBagConstraints gbc_textFieldNachname = new GridBagConstraints();
		gbc_textFieldNachname.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldNachname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNachname.gridx = 1;
		gbc_textFieldNachname.gridy = 1;
		panel_1.add(textFieldNachname, gbc_textFieldNachname);
		textFieldNachname.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Handynummer:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);

		textFieldHandynummer = new JTextField();
		GridBagConstraints gbc_textFieldHandynummer = new GridBagConstraints();
		gbc_textFieldHandynummer.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHandynummer.gridx = 1;
		gbc_textFieldHandynummer.gridy = 2;
		panel_1.add(textFieldHandynummer, gbc_textFieldHandynummer);
		textFieldHandynummer.setColumns(10);
		tableModel.updateTable();

		pack();
		setLocationRelativeTo(null);
	}

}