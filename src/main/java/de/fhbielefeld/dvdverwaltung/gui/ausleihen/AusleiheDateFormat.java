package de.fhbielefeld.dvdverwaltung.gui.ausleihen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * 
 * Stellt die entsprechende Datenformattierung für die Ausleihen bereit.
 * 
 * @author Nick Norberts
 *
 */
@SuppressWarnings("serial")
public class AusleiheDateFormat extends AbstractFormatter {
	
	public static final String STR_AUSLEIHEDATE_FORMAT = "dd. MMM yyyy";
	private final SimpleDateFormat SIMPLE_AUSLEIHEDATE_FORMAT = new SimpleDateFormat(STR_AUSLEIHEDATE_FORMAT);

	@Override
	public String valueToString(Object value) throws ParseException {
		if (value == null)
			return null;
		
		Calendar cal = (Calendar) value;
		return SIMPLE_AUSLEIHEDATE_FORMAT.format(cal.getTime());
	}
	
	@Override
	public Object stringToValue(String text) throws ParseException {
		return SIMPLE_AUSLEIHEDATE_FORMAT.parseObject(text);
	}

}