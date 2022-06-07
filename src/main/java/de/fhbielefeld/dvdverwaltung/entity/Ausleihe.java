package de.fhbielefeld.dvdverwaltung.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entitätsklasse der Ausleihen: Definiert wie & wo Ausleihe-Instanzen 
 * in die entsprechende Datenbanktabelle namens 'tbl_ausleihen' gemappt 
 * und persistiert werden.
 * 
 * @author Linus Kliemann
 * @author Leonardo Diekmann
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
		name = "tbl_ausleihen"
)
public class Ausleihe {
	
	@Id
	@SequenceGenerator(
			name = "ausleihen_sequence",
			sequenceName = "ausleihen_sequence",
			allocationSize = 1
			
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "ausleihen_sequence"
			
	)
	@Setter(value = AccessLevel.NONE)
	private Long ausleiheId;
	
	@ManyToOne(
			optional = false,
			fetch = FetchType.EAGER
	)
	@JoinColumn(
			name = "person_id",
			referencedColumnName = "personId"
	)
	private Person person;
	
	@ManyToOne(
			optional = false,
			fetch = FetchType.EAGER
	)
	@JoinColumn(
			name = "dvd_id",
			referencedColumnName = "dvdId"
	)	
	private DVD dvd;
	
	@Column(
			nullable = false
	)
	private Long leihdauer;
	
	@Column(
			nullable = false
	)	
	private LocalDate datAusleihe;
	
	@Column(
			nullable = false
	)
	private LocalDate datVorRueckgabe;
	
	private Boolean zurueckgegeben;

}
