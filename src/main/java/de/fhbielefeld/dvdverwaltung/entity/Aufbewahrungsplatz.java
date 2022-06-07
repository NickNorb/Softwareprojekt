package de.fhbielefeld.dvdverwaltung.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entitätsklasse der Aufbewahrungsplätze: Definiert wie & wo Aufbewahrungsplatz-Instanzen 
 * in die entsprechende Datenbanktabelle namens 'tbl_aufbewahrungsplaetze' gemappt 
 * und persistiert werden.
 * 
 * @author Linus Kliemann
 * @author Mohamed-Khalil El Ouafi
 * 
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Table(
		name = "tbl_aufbewahrungsplaetze",
		uniqueConstraints = {
				@UniqueConstraint(
						name = "bezeichnung_unique",
						columnNames = "bezeichnung"
				)
		}
)
public class Aufbewahrungsplatz {
	
	@Id
	@SequenceGenerator(
			name = "aufbewahrungsplaetze_sequence",
			sequenceName = "aufbewahrungsplaetze_sequence",
			allocationSize = 1
			
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "aufbewahrungsplaetze_sequence"
			
	)
	@Setter(value = AccessLevel.NONE)
	private Long aufbewahrungsplatzId;
	
	@Column(
			name = "bezeichnung",
			nullable = false
	)
	private String bezeichnung;
	
	@Override
	public String toString() { return this.getBezeichnung(); }

}