package de.fhbielefeld.dvdverwaltung.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import de.fhbielefeld.dvdverwaltung.entity.Aufbewahrungsplatz;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AufbewahrungsplatzRepositoryTest {

    @Autowired
    private AufbewahrungsplatzRepository apRepo;

    @BeforeEach
    void setUp() throws Exception {
    	
    	//Aufbewahrungsplätze anlegen und speichern
        Aufbewahrungsplatz a1 = Aufbewahrungsplatz.builder()
        		.bezeichnung("DVD Regal (blau)")
        		.build();
        
        Aufbewahrungsplatz a2 = Aufbewahrungsplatz.builder()
        		.bezeichnung("DVD Regal (grau)")
        		.build();
        
        Aufbewahrungsplatz a3 = Aufbewahrungsplatz.builder()
        		.bezeichnung("DVD Regal (schwarz)")
        		.build();

        apRepo.saveAll(Arrays.asList(a1, a2, a3));
    }

    @Test
    void testLoadRepo() {
    	//Prüfung, ob das Repository geladen werden kann und nicht leer ist
        assertThat(apRepo).isNotNull();
    }

    @Test
    void testFindAufbewahrungsplatz() {
    	long id = 0;
    	
    	//Zum Herauslesen der letzten ID im Repository
      	for(Aufbewahrungsplatz a : apRepo.findAll())
      	{
      		id = a.getAufbewahrungsplatzId();
      	}

      	//Erstellung einer Kopie des Datensatzes
        Optional<Aufbewahrungsplatz> optionalAP = apRepo.findById(id);
        
        Aufbewahrungsplatz resultAufbPla = (optionalAP.isPresent()) ? (optionalAP.get()) : (new Aufbewahrungsplatz());
        
        assertThat(id).isEqualTo(resultAufbPla.getAufbewahrungsplatzId());        
    }
    
    @Test
    void testDeleteAufbewahrungsplatz()
    {
    	long id = 0;	
    	long repoCount = apRepo.count();	
    	
    	//Zum Herauslesen der letzten ID im Repository
    	for(Aufbewahrungsplatz a : apRepo.findAll()) 
    	{
    		id = a.getAufbewahrungsplatzId();
    	}
    	
    	apRepo.deleteById(id);

    	//Vergleich der Anzahl der Datensätze in dem Repository vor und nach dem Löschen
    	assertTrue(apRepo.count() == (repoCount-1));	
    }

}