package de.fhbielefeld.dvdverwaltung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.SerieFilm;
import de.fhbielefeld.dvdverwaltung.repository.SerieFilmRepository;

/**
 * Die Serviceklasse, welche die passenden Informationen zwischen TableModel und Datenbank übergibt
 * 
 * @author Nick Norberts
 *
 */

@Component
public class SerieFilmService {
	
	private SerieFilmRepository sfrepo;
	
	@Autowired
	public SerieFilmService(SerieFilmRepository sfrepo) {
		this.sfrepo = sfrepo;
	}
	
	public List<SerieFilm> getSerieFilmListFromRepo() {
		return sfrepo.findAllByOrderByTitelAsc();
	}
	
	public void saveSerieFilmInRepo(SerieFilm serieFilm) {
		sfrepo.save(serieFilm);
	}
	
	public void deleteSerieFilm(SerieFilm serieFilm) throws IllegalStateException {
		if (!canBeDeleted(serieFilm))
			throw new IllegalStateException();
		
		sfrepo.delete(serieFilm);
	}	
	
	public boolean canBeDeleted(SerieFilm serieFilm) {
		return (sfrepo.getAnzahlZugehoerigeDVDsBySfID(serieFilm.getSerieFilmId()) == 0);
	}
	
}