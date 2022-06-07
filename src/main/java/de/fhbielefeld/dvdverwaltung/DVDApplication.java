package de.fhbielefeld.dvdverwaltung;

import java.awt.EventQueue;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import de.fhbielefeld.dvdverwaltung.gui.MainFrame;

/**
 * -- DVD-Verwaltung: Main-Klasse --
 *
 * © Fachhochschule Bielefeld - Softwareprojekt [SoSe22 - Projektgruppe 3]
 * Betreuer: Prof. Dr. Alexander Förster
 * Autoren: Kliemann, L., Jagiello, J., Norberts, N.,
 * 			Diekmann, L. & El Ouafi, M-K.
 * Alle Rechte vorbehalten.
 * 
 * Hinweis: Basis-Daten dürfen bei Bedarf über resources/data.sql erzeugt werden (Bedienungsanleitung auf der Datei durchlesen)
 *
 */
@SpringBootApplication
public class DVDApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext context = new SpringApplicationBuilder(DVDApplication.class)
													.headless(false)
													.run(args);
		
		EventQueue.invokeLater(() -> {
			MainFrame mainFrame = context.getBean(MainFrame.class);
			mainFrame.setVisible(true);
		});
		
	}
	
}