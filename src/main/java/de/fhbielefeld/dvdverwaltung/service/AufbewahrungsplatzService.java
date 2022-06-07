package de.fhbielefeld.dvdverwaltung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.Aufbewahrungsplatz;
import de.fhbielefeld.dvdverwaltung.repository.AufbewahrungsplatzRepository;

/**
 * Die Serviceklasse, welche die passenden Informationen zwischen TableModel und Datenbank übergibt
 * 
 * @author Nick Norberts
 * @author Jana Jagiello
 *
 */

@Component
public class AufbewahrungsplatzService{

	private AufbewahrungsplatzRepository aprepo;

	@Autowired
	public AufbewahrungsplatzService(AufbewahrungsplatzRepository aprepo) {
		this.aprepo = aprepo;
	}

	public List<Aufbewahrungsplatz> getAufbewahrungsplatzListFromRepo() {
		return aprepo.findAllByOrderByBezeichnungAsc();
	}

	public void saveAufbewahrungsplatz(Aufbewahrungsplatz aufbewahrungsplatz) {
		aprepo.save(aufbewahrungsplatz);
	}

	public void deleteAufbewahrungsplatz(Aufbewahrungsplatz aufbewahrungsplatz)  throws IllegalStateException {
		if (!canBeDeleted(aufbewahrungsplatz))
			throw new IllegalStateException();
		
		aprepo.delete(aufbewahrungsplatz);
	}

	public boolean canBeDeleted(Aufbewahrungsplatz aufbewahrungsplatz) {
		return getAnzahlDVDs(aufbewahrungsplatz.getAufbewahrungsplatzId()) == 0;
	}
	
	public long getAnzahlDVDs(long aufbewahrungsplatzID) {
		return aprepo.getAnzahlDVDsImPlatzID(aufbewahrungsplatzID);
	}
	
	public Aufbewahrungsplatz getApFromRepoByBez(String apBez) {
		return aprepo.findByBezeichnung(apBez);
	}
	
}