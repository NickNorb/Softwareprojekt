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

import de.fhbielefeld.dvdverwaltung.entity.Person;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PersonRepositoryTest {
	
	@Autowired
	private PersonRepository peRepo;

	@BeforeEach
	void setUp() throws Exception {
		
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
	}

    @Test
    void testLoadRepo() {
        assertThat(peRepo).isNotNull();
    }
    
    @Test
    void testFindPerson() {
        long id = 1;
          
        //Zum Herauslesen der letzten ID im Repository
        for(Person p : peRepo.findAll()) 
    	{
        	id = p.getPersonId();
      	}

        //Erstellung einer Kopie des Datensatzes
        Optional <Person> optionalPerson = peRepo.findById(id);
        
        Person resultPerson = (optionalPerson.isPresent()) ? (optionalPerson.get()) : (new Person());

        assertThat(id).isEqualTo(resultPerson.getPersonId());         
    }
    
	@Test
    void testDeletePerson()
    {
    	long id = 0;	
    	long repoCount = peRepo.count();	
    	
    	//Zum Herauslesen der letzten ID im Repository
    	for(Person p : peRepo.findAll())
    	{
    		id = p.getPersonId();
    	}
    	
    	peRepo.deleteById(id);  
    	
    	//Vergleich der Anzahl der Datensätze in dem Repository vor und nach dem Löschen
    	assertTrue(peRepo.count() == (repoCount-1));	
    }
}