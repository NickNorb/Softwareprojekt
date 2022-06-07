package de.fhbielefeld.dvdverwaltung.gui.aufbewahrungsplatz;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import de.fhbielefeld.dvdverwaltung.entity.Aufbewahrungsplatz;

/**
 * Erzeugt den Dialog, um einen Aufbewahrungsplatz anzulegen.
 * 
 * @author Jana Jagiello
 *
 */
@SuppressWarnings("serial")
public class AufbewahrungsplatzDialog extends JDialog {

	private final JPanel contentPanel;

	public AufbewahrungsplatzDialog(AufbewahrungsplatzTableModel apTableModel) {

		setIconImage(new ImageIcon("./iconlibrary/AppIcon.png").getImage());
		setModal(true);
		setResizable(false);
		setTitle("Neuer Aufbewahrungsplatz:");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(15, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel bezLabel = new JLabel("Bezeichnung:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		contentPanel.add(bezLabel, gbc_lblNewLabel_1);
		
		JTextField tfBezeichnung = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		contentPanel.add(tfBezeichnung, gbc_textField);
		tfBezeichnung.setColumns(30);
		
		JPanel pnlButtons = new JPanel();
		getContentPane().add(pnlButtons, BorderLayout.SOUTH);
		pnlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton btnOk = new JButton("Hinzufügen");
		JButton btnAbbrechen = new JButton("Abbrechen");
		pnlButtons.add(btnOk);
		pnlButtons.add(btnAbbrechen);
		btnAbbrechen.addActionListener(e -> super.dispose());
		
		btnOk.addActionListener(e -> {
			tfBezeichnung.setText(tfBezeichnung.getText().trim());
			
			if (tfBezeichnung.getText().length() <= 0) {
				tfBezeichnung.setBorder(BorderFactory.createLineBorder(Color.RED));
				return;
			}

			if (apTableModel.isBezeichnungInputDuplicate(tfBezeichnung.getText())) {
				tfBezeichnung.setBorder(BorderFactory.createLineBorder(Color.RED));
				JOptionPane.showMessageDialog(null, "Erstellen fehlgeschlagen:\n"
												   +"Aufbewahrungsplatz \""+tfBezeichnung.getText()+"\"\n"
												   +"existiert bereits!"
												   , "Fehler:"
												   , JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			apTableModel.saveAufbewahrungsplatz(Aufbewahrungsplatz.builder().bezeichnung(tfBezeichnung.getText()).build());
			dispose();
		});
		
		getRootPane().setDefaultButton(btnOk);
		
		pack();
		setLocationRelativeTo(null);
	}
}