package de.fhbielefeld.dvdverwaltung.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.fhbielefeld.dvdverwaltung.entity.Person;

/**
 * Repository-Schnittstelle der Personen: Ermöglicht die Interaktion mit
 * der entsprechenden Datenbanktabelle bzw. alle CRUD-Operationen
 * auf die Personen im Adressbuch (Anlegen, Anzeigen, Bearbeitung & Löschen)
 * 
 * @author Linus Kliemann
 * 
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	
	List<Person> findAllByOrderByVornameAsc();
	
	List<Person> findAllByOrderByNachnameAsc();
	
	@Query(
			value = "SELECT COUNT(*) FROM tbl_ausleihen "
				  + "WHERE person_id = ?1 ",
			nativeQuery = true
	)
	Long getAnzahlAusleihenPersonID(Long apID);
	
}