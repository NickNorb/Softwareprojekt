package de.fhbielefeld.dvdverwaltung.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.DVD;
import de.fhbielefeld.dvdverwaltung.repository.DVDRepository;

/**
 * Die Serviceklasse, welche die passenden Informationen zwischen TableModel und
 * Datenbank übergibt
 * 
 * @author Nick Norberts
 * @author Leonardo Diekmann
 *
 */

@Component
public class DVDService {

	private DVDRepository dvdrepo;

	@Autowired
	public DVDService(DVDRepository dvdrepo) {
		this.dvdrepo = dvdrepo;
	}

	public List<DVD> getDVDListFromRepo() {
		return dvdrepo.findAllByOrderBySerieFilmTitelAscStaffelAsc();
	}
	
	public List<DVD> getVerfuegbareDVDListFromRepo() {
		return dvdrepo.findByVerfuegbarTrue();
	}

	public List<DVD> getDVDsInPlatzID(Long apID) {
		return dvdrepo.findByAufbewahrungsplatzAufbewahrungsplatzId(apID);
	}

	/**
	 * Speichert eine DVD in der Datenbank. 
	 * Falls eine der Attribute "gesehenBis" oder "gesehen" gesetzt ist, wird der aktuelle Zeitpunkt in 
	 * das Attribut "zuletztGesehenAm" geschrieben, damit eine Sortierung nach zuletzt gesehenen DVDs möglich ist.
	 * 
	 * @param dvd
	 */
	public void saveDVDInRepo(DVD dvd) {
		if (dvd.getGesehenBis() != null || dvd.getGesehen() == true)
			dvd.setZuletztGesehenAm(LocalDate.now());

		dvdrepo.save(dvd);
	}
	
	private boolean cannotBeDeleted(DVD dvd) { return !(dvd.getVerfuegbar()); }

	public void deleteDVDFromRepo(DVD dvd) throws IllegalStateException {
		if (cannotBeDeleted(dvd))
			throw new IllegalStateException();
		
		dvdrepo.delete(dvd);
	}

}