package de.fhbielefeld.dvdverwaltung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.Ausleihe;
import de.fhbielefeld.dvdverwaltung.entity.DVD;
import de.fhbielefeld.dvdverwaltung.repository.AusleiheRepository;
import de.fhbielefeld.dvdverwaltung.repository.DVDRepository;

/**
 * Die Serviceklasse, welche die passenden Informationen zwischen TableModel und Datenbank übergibt
 * @author Jana Jagiello
 *
 */
@Component
public class DashboardService {

	private DVDRepository dvdrepo;
	private AusleiheRepository arepo;
	
	@Autowired
	public DashboardService(DVDRepository dvdrepo, AusleiheRepository arepo) {
		this.dvdrepo = dvdrepo;
		this.arepo = arepo;
	}
	
	/**
	 * 
	 * @return die 10 zuletzt gesehenen DVDs sortiert nach absteigendem Datum
	 */
	public List<DVD> getFirstTenDVDsSorted() {
		return dvdrepo.findTop10ByZuletztGesehenAmIsNotNullOrderByZuletztGesehenAmDesc();
	}
	
	/**
	 * 
	 * @return die aktuellen Ausleihen
	 */
	public List<Ausleihe> getOnlyCurrentAusleihen() { 
		return arepo.findByZurueckgegebenFalseOrderByDatVorRueckgabeAsc(); 
	}
	
}

