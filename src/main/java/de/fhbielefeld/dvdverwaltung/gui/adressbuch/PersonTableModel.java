package de.fhbielefeld.dvdverwaltung.gui.adressbuch;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.Person;
import de.fhbielefeld.dvdverwaltung.service.PersonService;

/**
 * Definiert die Tabelle des Adressbuches.
 * Informationen bekommt das TableModel aus der Serviceklasse. An diese werden auch 
 * veränderungen weitergereicht, damit diese an die Datenbank übergeben werden
 * 
 * @author Nick Norberts
 * @author Linus Kliemann
 *
 */
@SuppressWarnings("serial")
@Component
public class PersonTableModel extends AbstractTableModel {
	
	private PersonService ps;
	private List<Person> lines;
	String[] headers = {"Nachname", "Vorname", "Handynummer"};
	
	@Autowired
	public PersonTableModel(PersonService ps) {
		this.ps = ps;
		lines = ps.getPersonListFromRepo();
	}
	
	public void updateTable() {
		lines = ps.getPersonListFromRepo();
		fireTableDataChanged();
	}
	
	public void savePerson(Person person) {
		ps.savePersonInRepo(person);
		updateTable();
		fireTableDataChanged();
	}
	
	/**
	 * Löscht die ausgewählte Person vom Adressbuch unter der Bedingung, dass
	 * diese sich im Löschzustand befindet (keine Ausleihen besitzt)
	 * 
	 * @param pIndex Index der angeklickten Person im TableModel
	 */
	public void deletePerson(int pIndex) {
		try {	
			ps.deletePersonFromRepo(lines.get(pIndex));
			this.updateTable();
		} catch (IllegalStateException isExc) {
			JOptionPane.showMessageDialog(null, "Es sind noch Ausleihen\n"
											  + "auf \""+lines.get(pIndex).getNachname()+", "
											  + lines.get(pIndex).getVorname()+"\" verzeichnet.\n"
											  + "Bitte zuerst alle zugehörigen\n"
											  + "Ausleihen löschen!",
											    "Fehler:", 
											    JOptionPane.ERROR_MESSAGE);
			return;
		} catch (Exception parentExc) {
			parentExc.printStackTrace();
			return;
		}
	}	

	@Override
	public int getRowCount() { return lines.size(); }

	@Override
	public int getColumnCount() { return headers.length; }
	
	@Override
	public String getColumnName(int col) { return headers[col]; }

	@Override
	public Class<?> getColumnClass(int colIndex) {
		switch (colIndex) {
		case 0: case 1: case 2: return String.class;
		default:
			return null;
		}
	}

	public Person getPersonAtRow(int rowIndex) { return lines.get(rowIndex); }
	
	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		switch (colIndex) {
		case 0:
			return lines.get(rowIndex).getNachname();
		case 1:
			return lines.get(rowIndex).getVorname();
		case 2:
			return lines.get(rowIndex).getHandynummer();
		default:
			return null;
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int colIndex) {
		switch (colIndex) {
		case 0: case 2:  return true;
		default:
			return false;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int colIndex) {
		switch (colIndex) {
		case 0:
			if(aValue.toString().trim().length() <= 0) { return; }
			
			lines.get(rowIndex).setNachname(aValue.toString());
			ps.savePersonInRepo(lines.get(rowIndex));	
			break;
		case 2:
			if (aValue.toString().trim().length() <= 0 
				|| isPhoneInputDuplicate(aValue.toString()) 
				|| !isValidPhoneInput(aValue.toString()))
					return;
			
			lines.get(rowIndex).setHandynummer(aValue.toString());
			ps.savePersonInRepo(lines.get(rowIndex));
			break;
		default:
			break;
		}	

	}
	
	public boolean isPhoneInputDuplicate(String pHandynummer) {
		for (Person p: lines) {
			if (p.getHandynummer().equalsIgnoreCase(pHandynummer)) {
				JOptionPane.showMessageDialog(null, "Die eingegebene Nummer\nwurde bereits vergeben.", "Fehler:", JOptionPane.ERROR_MESSAGE);
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isValidPhoneInput(String pHandynummer) {
		try {
			Long.parseLong(pHandynummer);
			return true;
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "Die eingegebene Handynummer\nscheint ungültig zu sein", "Fehler:", JOptionPane.ERROR_MESSAGE);			
			return false;
		}		
	}
	
}