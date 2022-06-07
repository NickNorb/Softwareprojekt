package de.fhbielefeld.dvdverwaltung.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.fhbielefeld.dvdverwaltung.entity.Aufbewahrungsplatz;

@SpringBootTest
class AufbewahrungsplatzRepositoryTest {

	@Autowired
	public AufbewahrungsplatzRepository repo;
	
	@Test
	public void saveAufbewahrungsplatz() {
		
		Aufbewahrungsplatz a1 = Aufbewahrungsplatz.builder()
				.bezeichnung("Kleiderschrank")
				.build();
		
		Aufbewahrungsplatz a2 = Aufbewahrungsplatz.builder()
				.bezeichnung("Schreibtisch")
				.build();
		Aufbewahrungsplatz a3 = Aufbewahrungsplatz.builder()
				.bezeichnung("Nachttisch")
				.build();
		Aufbewahrungsplatz a4 = Aufbewahrungsplatz.builder()
				.bezeichnung("Bibliothek")
				.build();
		Aufbewahrungsplatz a5 = Aufbewahrungsplatz.builder()
				.bezeichnung("Regal")
				.build();
		
		repo.saveAll(List.of(a1, a2, a3, a4, a5));
		
		//repo.save(a1);
		
		repo.findAll().forEach(a -> System.err.println(a));
		
	}


}
