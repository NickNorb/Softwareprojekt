package de.fhbielefeld.dvdverwaltung.gui.serienfilme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;

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

import de.fhbielefeld.dvdverwaltung.entity.SerieFilm;

/**
 * Definiert eine Eingabemaske, über welche Serien/Filme erfasst werden können.
 * 
 * 
 * @author Leonardo Diekmann
 * @author Mohamed-Khalil El Ouafi
 *
 */
@SuppressWarnings("serial")
public class SerienFilmeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textTitel;
	private JComboBox<Object> typeComboBox;

	public SerienFilmeDialog(SerieFilmTableModel tableModel) {
		setBounds(100, 100, 330, 210);
		getContentPane().setLayout(new BorderLayout());
		super.setModal(true);
		super.setResizable(false);
		super.setTitle("Neue(r) Serie oder Film:");
		super.setIconImage(new ImageIcon("./iconlibrary/AppIcon.png").getImage());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelCenter = new JPanel();
			contentPanel.add(panelCenter, BorderLayout.CENTER);
			GridBagLayout gbl_panelCenter = new GridBagLayout();
			gbl_panelCenter.columnWidths = new int[]{0, 0};
			gbl_panelCenter.rowHeights = new int[]{21, 41, 0};
			gbl_panelCenter.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panelCenter.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
			panelCenter.setLayout(gbl_panelCenter);
			{
				JPanel panelrdBtn = new JPanel();
				panelrdBtn.setMaximumSize(new Dimension(1000, 1000));
				panelrdBtn.setBackground(SystemColor.menu);
				GridBagConstraints gbc_panelrdBtn = new GridBagConstraints();
				gbc_panelrdBtn.insets = new Insets(0, 0, 5, 0);
				gbc_panelrdBtn.fill = GridBagConstraints.HORIZONTAL;
				gbc_panelrdBtn.gridx = 0;
				gbc_panelrdBtn.gridy = 0;
				panelCenter.add(panelrdBtn, gbc_panelrdBtn);
				panelrdBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			}
			{
				JPanel panel = new JPanel();
				GridBagConstraints gbc_panel = new GridBagConstraints();
				gbc_panel.fill = GridBagConstraints.BOTH;
				gbc_panel.gridx = 0;
				gbc_panel.gridy = 1;
				panelCenter.add(panel, gbc_panel);
				GridBagLayout gbl_panel = new GridBagLayout();
				gbl_panel.columnWidths = new int[]{66, 0, 0, 0};
				gbl_panel.rowHeights = new int[]{0, 0, 0};
				gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
				gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
				panel.setLayout(gbl_panel);
				{
					JLabel lblTitel = new JLabel("Titel:");
					GridBagConstraints gbc_lblTitel = new GridBagConstraints();
					gbc_lblTitel.insets = new Insets(0, 0, 5, 5);
					gbc_lblTitel.anchor = GridBagConstraints.EAST;
					gbc_lblTitel.gridx = 0;
					gbc_lblTitel.gridy = 0;
					panel.add(lblTitel, gbc_lblTitel);
				}
				{
					textTitel = new JTextField();
					GridBagConstraints gbc_textTitel = new GridBagConstraints();
					gbc_textTitel.insets = new Insets(0, 0, 5, 5);
					gbc_textTitel.fill = GridBagConstraints.HORIZONTAL;
					gbc_textTitel.gridx = 1;
					gbc_textTitel.gridy = 0;
					panel.add(textTitel, gbc_textTitel);
					textTitel.setColumns(10);
				}
				{
					JLabel lblType = new JLabel("Typ:");
					GridBagConstraints gbc_lblStaffel = new GridBagConstraints();
					gbc_lblStaffel.anchor = GridBagConstraints.EAST;
					gbc_lblStaffel.insets = new Insets(0, 0, 0, 5);
					gbc_lblStaffel.gridx = 0;
					gbc_lblStaffel.gridy = 1;
					panel.add(lblType, gbc_lblStaffel);
				}
				{
					typeComboBox = new JComboBox<>(new String[] {SerieFilm.MOVIE_DVD, SerieFilm.SERIE_DVD});
					GridBagConstraints gbc_typeComboBox = new GridBagConstraints();
					gbc_typeComboBox.insets = new Insets(0, 0, 0, 5);
					gbc_typeComboBox.fill = GridBagConstraints.HORIZONTAL;
					gbc_typeComboBox.gridx = 1;
					gbc_typeComboBox.gridy = 1;
					panel.add(typeComboBox, gbc_typeComboBox);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnHinzufuegen = new JButton("Hinzufügen");
				
				btnHinzufuegen.addActionListener(e -> {
					
//					Prüft das Feld "Titel" auf Inhalt
					textTitel.setBorder(null);
					if (textTitel.getText().trim().length() <= 0) {
						textTitel.setBorder(BorderFactory.createLineBorder(Color.RED));
						return;
					}
					
//					Prüft auf Duplikate
					if(tableModel.checkDuplicateTitel(textTitel.getText())) {
						textTitel.setBorder(BorderFactory.createLineBorder(Color.RED));
						JOptionPane.showMessageDialog(null, "Erstellen fehlgeschlagen:\n"
														  + "Film oder Serie existiert bereits!"
														  , "Fehler:"
														  , JOptionPane.ERROR_MESSAGE);
						return;
					}

					SerieFilm sf = SerieFilm.builder()
												.titel(textTitel.getText())
												.typ((String) typeComboBox.getSelectedItem())
												.build();
					
					tableModel.saveSerieFilm(sf);
					tableModel.updateTable();
					super.dispose();
				});
				btnHinzufuegen.setActionCommand("OK");
				buttonPane.add(btnHinzufuegen);
				
				super.getRootPane().setDefaultButton(btnHinzufuegen);
			}
			{
				JButton cancelButton = new JButton("Abbrechen");
				cancelButton.addActionListener(e -> super.dispose());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			super.pack();
			super.setLocationRelativeTo(null);
		}
	}

}
