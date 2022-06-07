package de.fhbielefeld.dvdverwaltung.entity;

import java.time.LocalDate;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entitätsklasse der DVDs: Definiert wie & wo DVD-Instanzen 
 * in die entsprechende Datenbanktabelle namens 'tbl_dvds' gemappt 
 * und persistiert werden.
 * 
 * @author Linus Kliemann
 * @author Leonardo Diekmann
 * 
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Table(
		name = "tbl_dvds"
)
public class DVD {
	
	@Id
	@SequenceGenerator(
			name = "dvds_sequence",
			sequenceName = "dvds_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "dvds_sequence"
			
	)
	@Setter(value = AccessLevel.NONE)
	private Long dvdId;
	
	@ManyToOne(
			optional = false,
			fetch = FetchType.EAGER
	)
	@JoinColumn(
			name = "aufbewahrungsplatz_id",
			referencedColumnName = "aufbewahrungsplatzId"
	)
	private Aufbewahrungsplatz aufbewahrungsplatz;
	
	@ManyToOne(
			optional = false,
			fetch = FetchType.EAGER			
	)
	@JoinColumn(
			name = "serie_film_id",
			referencedColumnName = "serieFilmId"
	)
	private SerieFilm serieFilm;
	
	private Integer staffel;
	
	private Boolean gesehen;
	
	private Integer gesehenBis;
	
	private LocalDate zuletztGesehenAm;
	
	private Boolean verfuegbar;
	
	@Override
	public String toString() {
		switch (this.getSerieFilm().getTyp()) {
			case (SerieFilm.MOVIE_DVD):
				return this.getSerieFilm().getTitel();
			case (SerieFilm.SERIE_DVD):
				return this.getSerieFilm().getTitel()+" (S"+this.getStaffel()+")";
		default:
			return null;
		}
	}
	
}