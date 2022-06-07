package de.fhbielefeld.dvdverwaltung.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.fhbielefeld.dvdverwaltung.entity.Ausleihe;

@SpringBootTest
class AusleiheRepositoryTest {

	@Autowired
	private AusleiheRepository arepo;
	
	Ausleihe a1 = Ausleihe.builder()
			.build();
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
