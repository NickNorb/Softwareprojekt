package de.fhbielefeld.dvdverwaltung.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.fhbielefeld.dvdverwaltung.entity.SerieFilm;
@SpringBootTest
class SerieFilmRepositoryTest {
	
	@Autowired
	private SerieFilmRepository srepo;
	
	SerieFilm sf1 = SerieFilm.builder()
			.serieFilmId(null)
			.titel(null)
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
