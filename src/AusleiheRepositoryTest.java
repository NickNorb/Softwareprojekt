package de.fhbielefeld.dvdverwaltung.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import de.fhbielefeld.dvdverwaltung.entity.Aufbewahrungsplatz;
import de.fhbielefeld.dvdverwaltung.entity.Ausleihe;
import de.fhbielefeld.dvdverwaltung.entity.DVD;
import de.fhbielefeld.dvdverwaltung.entity.Person;
import de.fhbielefeld.dvdverwaltung.entity.SerieFilm;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AusleiheRepositoryTest { 

    @Autowired
    private AusleiheRepository auslRepo;
    
    @Autowired
    private AufbewahrungsplatzRepository aufRepo;
    
    @Autowired
    private DVDRepository dRepo;
    
    @Autowired
    private PersonRepository peRepo;
    
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
    	
    	//Personen anlegen und speichern
    	Person p1 = Person.builder()
		.vorname("Max")
		.nachname("Mustermann")
		.handynummer("0123456789")
		.build();
    	
    	Person p2 = Person.builder()
		.vorname("Frank")
		.nachname("Meier")
		.handynummer("9812312124")
		.build();
    	
    	peRepo.saveAll(Arrays.asList(p1, p2));
    	
    	Ausleihe ausl1 = Ausleihe.builder()
    			.dvd(d1)
    			.datAusleihe(LocalDate.now())
    			.datVorRueckgabe(LocalDate.of(2023, 01, 01))
    			.leihdauer((long) (3))
    			.person(p1)
    			.zurueckgegeben(null)
    			.build();
    	
    	Ausleihe ausl2 = Ausleihe.builder()
    			.dvd(d2)
    			.datAusleihe(LocalDate.now())
    			.datVorRueckgabe(LocalDate.of(2023, 01, 01))
    			.leihdauer((long) (3))
    			.person(p2)
    			.zurueckgegeben(null)
    			.build();
    	
    	auslRepo.saveAll(Arrays.asList(ausl1, ausl2));
    	
    }

    @Test
    void testLoadRepo() {
    	//Prüfung, ob das Repository geladen werden kann und nicht leer ist
        assertThat(auslRepo).isNotNull();
    }

    @Test
    void testFindAusleihe() {
    	  long id = 0;
    	  
    	  //Zum Herauslesen der letzten ID im Repository
    	  for(Ausleihe a : auslRepo.findAll()) 
    	  {
      		id = a.getAusleiheId();
      	  }

    	  //Erstellung einer Kopie des Datensatzes
          Optional <Ausleihe> optionalAusleihe = auslRepo.findById(id);
        
          Ausleihe resultAusleihe = (optionalAusleihe.isPresent()) ? (optionalAusleihe.get()) : (new Ausleihe());

          assertThat(id).isEqualTo(resultAusleihe.getAusleiheId());  
         
    }
    
	@Test
    void testDeleteAusleihe()
    {
    	long id = 0;	
    	long repoCount = auslRepo.count();	
    	
    	//Zum Herauslesen der letzten ID im Repository
    	for(Ausleihe a : auslRepo.findAll()) 
    	{
    		id = a.getAusleiheId();
    	}
    	
    	auslRepo.deleteById(id);
    	
    	//Vergleich der Anzahl der Datensätze in dem Repository vor und nach dem Löschen
    	assertTrue(auslRepo.count() == (repoCount-1));	
    }

}