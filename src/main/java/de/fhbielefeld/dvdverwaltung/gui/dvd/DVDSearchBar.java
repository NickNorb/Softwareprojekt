package de.fhbielefeld.dvdverwaltung.gui.dvd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import lombok.Getter;

/**
 * 
 * Definiert ein Suchfeld, womit nach DVDs (gezielt) gesucht & gefiltert werden kann.
 * Das Suchfeld dient ebenfalls der Tabellenzeilen-Sortierung.
 * 
 * @author Mohamed-Khalil El Ouafi
 *
 */
@SuppressWarnings("serial")
public class DVDSearchBar extends JTextField {

	private final String FIELD_TEXT = "DVD suchen...";
	private final int FIELD_COLUMNS=15, INPUT_LIMIT=10, ICON_CONTENT_INSETMARGIN=5;
    private Border mBorder;
    private Icon mIcon;
    
    @Getter
	private TableRowSorter<TableModel> dvdRowSorter;
	
    public DVDSearchBar(DVDTableModel dvdTableModel) {

		this.dvdRowSorter = new TableRowSorter<TableModel>(dvdTableModel);
		
		super.setText(FIELD_TEXT);
		super.setForeground(Color.GRAY);
		super.setColumns(FIELD_COLUMNS);
		this.setIcon(new ImageIcon("./iconlibrary/Search.png"));
		
		
		super.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (DVDSearchBar.super.getText().equals(FIELD_TEXT)) {
		        	DVDSearchBar.super.setText(null);
		        	DVDSearchBar.super.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (DVDSearchBar.super.getText().isEmpty()) {
		        	DVDSearchBar.super.setForeground(Color.GRAY);
		        	DVDSearchBar.super.setText(FIELD_TEXT);
		        }
		    }
		    });	
		
		super.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = DVDSearchBar.super.getText();

                if (text.trim().length() == 0 || text.equalsIgnoreCase(FIELD_TEXT)) {
                	dvdRowSorter.setRowFilter(null);
                } else {
                	dvdRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = DVDSearchBar.super.getText();

                if (text.trim().length() == 0 || text.equalsIgnoreCase(FIELD_TEXT)) {
                	dvdRowSorter.setRowFilter(null);
                } else {
                	dvdRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
		
		super.addKeyListener(new KeyListener() {
			
//			Beschränkung der Suchfeld-Eingabe auf INPUT_LIMIT Zeichen
		    @Override
		    public void keyTyped(KeyEvent arg0) {
		        if(DVDSearchBar.super.getText().length()>=DVDSearchBar.this.INPUT_LIMIT)
		        	DVDSearchBar.super.setText(DVDSearchBar.super.getText().substring(0,INPUT_LIMIT-1));
		    }

			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) { }
		});;		
    	
    }

    @Override
    public void setBorder(Border border) {
        mBorder = border;

        if (mIcon == null) {
            super.setBorder(border);
        } else {
            Border margin = BorderFactory.createEmptyBorder(0, mIcon.getIconWidth() + ICON_CONTENT_INSETMARGIN, 0, 0);
            Border compound = BorderFactory.createCompoundBorder(border, margin);
            super.setBorder(compound);
        }
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (mIcon != null) {
            Insets iconInsets = mBorder.getBorderInsets(this);
            mIcon.paintIcon(this, graphics, iconInsets.left, iconInsets.top);
        }
    }    
    
    private void resetBorder() { this.setBorder(mBorder); }
    
    public void setIcon(Icon icon) {
        this.mIcon = icon;
        this.resetBorder();
    }	

}