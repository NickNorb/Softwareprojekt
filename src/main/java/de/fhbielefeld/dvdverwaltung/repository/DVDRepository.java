package de.fhbielefeld.dvdverwaltung.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.fhbielefeld.dvdverwaltung.entity.DVD;

/**
 * Repository-Schnittstelle der DVDs: Ermöglicht die Interaktion mit
 * der entsprechenden Datenbanktabelle bzw. alle CRUD-Operationen
 * auf die DVDs (Anlegen, Anzeigen, Bearbeitung & Löschen)
 * 
 * @author Linus Kliemann
 * @author Mohamed-Khalil El Ouafi
 * 
 */
@Repository
public interface DVDRepository extends JpaRepository<DVD, Long> {
	
	List<DVD> findAllByOrderBySerieFilmTitelAscStaffelAsc();
	
	/**
	 *
	 * @return alle DVDs aus dem Aufbewahrungsplatz
	 */	
	List<DVD> findByAufbewahrungsplatzAufbewahrungsplatzId(Long apID);

	/**
	 *
	 * @return die zehn zuletzt geschauten DVDs in chronologischer Reihenfolge
	 */
	List<DVD> findTop10ByZuletztGesehenAmIsNotNullOrderByZuletztGesehenAmDesc();
	
	List<DVD> findByVerfuegbarTrue();
	
}