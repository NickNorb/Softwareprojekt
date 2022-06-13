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

import de.fhbielefeld.dvdverwaltung.entity.SerieFilm;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class SerieFilmRepositoryTest {
	
	@Autowired
	private SerieFilmRepository seFiRepo;

	@BeforeEach
	void setUp() throws Exception {
		
    	//Film/Serie anlegen und speichern
    	SerieFilm sf1 = SerieFilm.builder()
    			.titel("The Big Bang Theory")
    			.typ("Serie")
    			.build();
    	
    	SerieFilm sf2 = SerieFilm.builder()
    			.titel("Ghostbusters")
    			.typ("Film")
    			.build();
    	
    	seFiRepo.saveAll(Arrays.asList(sf1, sf2));
	}
	
    @Test
    void testLoadRepo() {
        assertThat(seFiRepo).isNotNull();
    }
    
    @Test
    void testFindSerieFilm() {
    	long id = 0;
	  
    	for(SerieFilm sf : seFiRepo.findAll()) 
    	{
    		id = sf.getSerieFilmId();
    	}
	
    	//Erstellung einer Kopie des Datensatzes
    	Optional <SerieFilm> optionalSerieFilm = seFiRepo.findById(id);

    	SerieFilm resultSerieFilm = (optionalSerieFilm.isPresent()) ? (optionalSerieFilm.get()) : (new SerieFilm());

    	assertThat(id).isEqualTo(resultSerieFilm.getSerieFilmId());  
         
    }
    
	@Test
    void testDeleteSerieFilm()
    {
		long id = 0;	
    	long repoCount = seFiRepo.count();	
    	
    	//Zum Herauslesen der letzten ID im Repository
        for(SerieFilm sf : seFiRepo.findAll()) 
        {
    		id = sf.getSerieFilmId();
        }
    	
    	seFiRepo.deleteById(id);
    	
    	//Vergleich der Anzahl der Datensätze in dem Repository vor und nach dem Löschen
    	assertTrue(seFiRepo.count() == (repoCount-1));	
    }


}
