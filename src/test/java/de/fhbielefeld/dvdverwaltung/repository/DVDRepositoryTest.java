package de.fhbielefeld.dvdverwaltung.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import de.fhbielefeld.dvdverwaltung.entity.Aufbewahrungsplatz;
import de.fhbielefeld.dvdverwaltung.entity.DVD;
import de.fhbielefeld.dvdverwaltung.entity.SerieFilm;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DVDRepositoryTest {
	
	@Autowired
	private DVDRepository dRepo;
	
	@Autowired
	private AufbewahrungsplatzRepository aufRepo;
	
	@Autowired
    private SerieFilmRepository fiSeRepo;

	@BeforeEach
	void setUp() throws Exception {
		
		//Aufbewahrungsplätze anlegen und speichern
      	Aufbewahrungsplatz auf1 = Aufbewahrungsplatz.builder()
				.bezeichnung("Schrank")
				.build();

    	Aufbewahrungsplatz auf2 = Aufbewahrungsplatz.builder()
				.bezeichnung("Büro")
				.build();
    	
    	aufRepo.saveAll(Arrays.asList(auf1, auf2));
    	
    	//Film/Serie anlegen und speichern
    	SerieFilm sf1 = SerieFilm.builder()
    			.titel("The Big Bang Theory")
    			.typ("Serie")
    			.build();
    	
    	SerieFilm sf2 = SerieFilm.builder()
    			.titel("Ghostbusters")
    			.typ("Film")
    			.build();
    	
    	fiSeRepo.saveAll(Arrays.asList(sf1, sf2));
    	
    	
    	//DVDs anlegen und speichern
    	DVD d1 = DVD.builder()
    			.serieFilm(sf1)
    			.aufbewahrungsplatz(auf1)
    			.gesehen(null)
    			.gesehenBis(null)
    			.build();
    	    	
    	DVD d2 = DVD.builder()
    			.serieFilm(sf2)
    			.aufbewahrungsplatz(auf2)
    			.gesehen(null)
    			.gesehenBis(null)
    			.build();
    	
    	dRepo.saveAll(Arrays.asList(d1, d2));
	}
	
    @Test
    void testLoadRepo() {
        assertThat(dRepo).isNotNull();
    }
    
    @Test
    void testFindDVD() {
    	
    	long id = 1;
          
        //Zum Herauslesen der letzten ID im Repository
        for(DVD d : dRepo.findAll()) 
    	{
        	id = d.getDvdId();
      	}
          
        //Erstellung einer Kopie des Datensatzes
        Optional <DVD> optionalAP = dRepo.findById(id);
        
        DVD resultDVD = (optionalAP.isPresent()) ? (optionalAP.get()) : (new DVD());

        assertThat(id).isEqualTo(resultDVD.getDvdId());  
         
    }
    
	@Test
    void testDeleteDVD()
    {
    	long id = 0;	
    	long repoCount = dRepo.count();	
    	
    	//Zum Herauslesen der letzten ID im Repository
    	for(DVD d : dRepo.findAll())
    	{
    		id = d.getDvdId();
    	}
    	
    	dRepo.deleteById(id);  	
    	
    	//Vergleich der Anzahl der Datensätze in dem Repository vor und nach dem Löschen
    	assertTrue(dRepo.count() == (repoCount-1));	
    }
}
