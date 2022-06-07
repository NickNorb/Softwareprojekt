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
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

/**
 * Entitätsklasse der Serien und Filme (bzw. DVD-Inhalte): Definiert wie & 
 * wo SerieFilm-Instanzen in die entsprechende Datenbanktabelle
 * namens 'tbl_serien_filme' gemappt und persistiert werden.
 * 
 * @author Linus Kliemann
 * @author Leonardo Diekmann
 * 
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(
		name = "tbl_serien_filme",
		uniqueConstraints = {
				@UniqueConstraint(
						name = "serie_film_unique",
						columnNames = {"titel", "typ"}
				)				
				
		}		
)
public class SerieFilm {
	
	public static final String SERIE_DVD="Serie", MOVIE_DVD="Film";
	
	@Id
	@SequenceGenerator(
			name = "serien_filme_sequence",
			sequenceName = "serien_filme_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "serien_filme_sequence"
			
	)
	@Setter(value = AccessLevel.NONE)	
	private Long serieFilmId;
	
	@Column(
			name = "titel",
			nullable = false
	)
	private String titel;
	
	@Column(
			name = "typ",
			nullable = false
	)
	@Setter(value = AccessLevel.NONE)	
	private String typ;
	
	@Override
	public String toString() {
		return this.getTyp()+" - "+this.getTitel();
	}

}