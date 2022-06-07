package de.fhbielefeld.dvdverwaltung.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.fhbielefeld.dvdverwaltung.entity.SerieFilm;

/**
 * Repository-Schnittstelle der Serien und Filme: Ermöglicht die Interaktion mit
 * der entsprechenden Datenbanktabelle bzw. alle CRUD-Operationen
 * auf die Serien und Filme (Anlegen, Anzeigen, Bearbeitung & Löschen)
 * 
 * @author Linus Kliemann
 * 
 */
@Repository
public interface SerieFilmRepository extends JpaRepository<SerieFilm, Long>{
	
	List<SerieFilm> findAllByOrderByTitelAsc();
	
	SerieFilm findByTitelAndTyp(String titel, String typ);
	
	@Query(
			value = "SELECT COUNT(dvd_id) FROM tbl_dvds "
				  + "WHERE serie_film_id = ?1",
			nativeQuery = true
	)
	Long getAnzahlZugehoerigeDVDsBySfID(Long sfID);	

}