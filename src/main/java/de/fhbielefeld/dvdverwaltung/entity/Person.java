package de.fhbielefeld.dvdverwaltung.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entitätsklasse der Personen: Definiert wie & wo Person-Instanzen 
 * in die entsprechende Datenbanktabelle namens 'tbl_personen' bzw. lt. Fallstudie „Adressbuch“ 
 * gemappt und persistiert werden.
 * 
 * @author Linus Kliemann
 * @author Mohamed-Khalil El Ouafi
 * 
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(
		name = "tbl_personen",
		uniqueConstraints = {
				@UniqueConstraint(
						name = "handynummer_unique",
						columnNames = "handynummer"
				)
		}
)
public class Person {
	
	@Id
	@SequenceGenerator(
			name = "personen_sequence",
			sequenceName = "personen_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "personen_sequence"
	)
	private Long personId;
	
	@Column(
			name = "handynummer",
			nullable = false
	)
	private String handynummer;

	@Column(
			nullable = false
	)
	private String vorname;
	
	@Column(
			nullable = false
	)
	private String nachname;
	
	private LocalDate zuletztAusgeliehenAm;
	
	@Override
	public String toString() { return this.getNachname()+", "+this.getVorname(); }

}