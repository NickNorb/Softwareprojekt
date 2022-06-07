package de.fhbielefeld.dvdverwaltung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.Ausleihe;
import de.fhbielefeld.dvdverwaltung.repository.AusleiheRepository;

/**
 * Die Serviceklasse, welche die passenden Informationen zwischen TableModel und Datenbank übergibt
 * 
 * @author Nick Norberts
 *
 */

@Component
public class AusleiheService {
	
	private AusleiheRepository arepo;
	
	@Autowired
	public AusleiheService(AusleiheRepository arepo) {
		this.arepo = arepo;
	}
	
	public List<Ausleihe> getAusleiheListFromRepo() {
		return arepo.findAllByOrderByAusleiheId();
	}
	
	public void saveAusleiheInRepo(Ausleihe ausleihe) {
		arepo.save(ausleihe);
	}
	
	public void deleteAusleiheFromRepo(Ausleihe ausleihe) {
		arepo.delete(ausleihe);
	}	
	
}