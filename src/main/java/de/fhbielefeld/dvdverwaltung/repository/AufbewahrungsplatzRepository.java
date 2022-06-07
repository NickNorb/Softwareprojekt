package de.fhbielefeld.dvdverwaltung.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.fhbielefeld.dvdverwaltung.entity.Aufbewahrungsplatz;

/**
 * Repository-Schnittstelle der Aufbewahrungsplätze: Ermöglicht die Interaktion mit
 * der entsprechenden Datenbanktabelle bzw. alle CRUD-Operationen
 * auf die Aufbewahrungsplätze (Anlegen, Anzeigen, Bearbeitung & Löschen)
 * 
 * @author Mohamed-Khalil El Ouafi
 * 
 */
@Repository
public interface AufbewahrungsplatzRepository extends JpaRepository<Aufbewahrungsplatz, Long>{
	
	List<Aufbewahrungsplatz> findAllByOrderByBezeichnungAsc();
	
	Aufbewahrungsplatz findByBezeichnung(String bezeichnung);
	
	/**
	 *
	 * @return Die Anzahl DVDs, die im Aufbewahrungsplatz liegen
	 */	
	@Query(
			value = "SELECT COUNT(dvd_id) FROM tbl_dvds "
				  + "WHERE aufbewahrungsplatz_id = ?1",
			nativeQuery = true
	)
	Long getAnzahlDVDsImPlatzID(Long apID);

}