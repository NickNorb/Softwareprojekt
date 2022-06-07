package de.fhbielefeld.dvdverwaltung.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.fhbielefeld.dvdverwaltung.entity.Ausleihe;

/**
 * Repository-Schnittstelle der Ausleihe: Ermöglicht die Interaktion mit
 * der entsprechenden Datenbanktabelle
 * 
 * @author Nick Norberts
 * 
 */
@Repository
public interface AusleiheRepository extends JpaRepository<Ausleihe, Long> {
	
	List<Ausleihe> findAllByOrderByAusleiheId();
	
	List<Ausleihe> findByZurueckgegebenFalseOrderByDatVorRueckgabeAsc();
	
}