/* 
 * 			-------------------------- Bedienungsanleitung Datengenerierung: --------------------------
 * 			-> DB strukturieren (durch Ausführung der Spring Boot App) 
 * 			-> App stoppen
 *			-> letzte zwei Zeilen auf application.properties (init.mode und init.data) auskommentieren
 *			-> App wieder ausführen
 *			-> DB ist nun mit Daten 'popularisiert'
 *			-> App stoppen
 *			-> (nicht vergessen!) letzte zwei Zeilen auf application.properties wieder kommentieren
 *			-> App ausführen
 *			-> Basis-Daten sind betriebsbereit (für DVDs, Ausleihen etc.)
 *			-------------------------------------------------------------------------------------------
 *
 */

INSERT INTO tbl_aufbewahrungsplaetze
VALUES
	((SELECT nextval ('aufbewahrungsplaetze_sequence')), 'Bureau'),
	((SELECT nextval ('aufbewahrungsplaetze_sequence')), 'Kommode'),
	((SELECT nextval ('aufbewahrungsplaetze_sequence')), 'Bücherregal'),
	((SELECT nextval ('aufbewahrungsplaetze_sequence')), 'Nachttisch'),
	((SELECT nextval ('aufbewahrungsplaetze_sequence')), 'Regal');
	
INSERT INTO tbl_personen
VALUES
	((SELECT nextval ('personen_sequence')), '017810684658', 'Hildebrandt', 'Martha'),
	((SELECT nextval ('personen_sequence')), '021686465665', 'Kjellberg', 'Felix'),
	((SELECT nextval ('personen_sequence')), '065415610664', 'Lacroix', 'Zoé'),
	((SELECT nextval ('personen_sequence')), '053131645487', 'Vercetti', 'Thomas'),
	((SELECT nextval ('personen_sequence')), '065999798664', 'Schmidt', 'Karl');

INSERT INTO tbl_serien_filme
VALUES
	((SELECT nextval ('serien_filme_sequence')), 'The Mandalorian', 'Serie'),
	((SELECT nextval ('serien_filme_sequence')), 'The Pianist (Roman Polanski)', 'Film'),
	((SELECT nextval ('serien_filme_sequence')), 'Capernaum - City of Hope', 'Film'),
	((SELECT nextval ('serien_filme_sequence')), 'Casablanca', 'Film'),
	((SELECT nextval ('serien_filme_sequence')), 'Game of Thrones', 'Serie'),
	((SELECT nextval ('serien_filme_sequence')), 'Mr. Robot', 'Serie'),
	((SELECT nextval ('serien_filme_sequence')), 'Parasite', 'Film'),
	((SELECT nextval ('serien_filme_sequence')), 'Slumdog Millionaire', 'Film'),
	((SELECT nextval ('serien_filme_sequence')), 'Scarface', 'Film'),
	((SELECT nextval ('serien_filme_sequence')), 'Interstellar', 'Film'),
	((SELECT nextval ('serien_filme_sequence')), 'The Queen''s Gambit', 'Serie'),
	((SELECT nextval ('serien_filme_sequence')), 'Joker', 'Film'),
	((SELECT nextval ('serien_filme_sequence')), 'Banshee', 'Serie'),
	((SELECT nextval ('serien_filme_sequence')), 'The Shawshank Redemption', 'Film'),
	((SELECT nextval ('serien_filme_sequence')), 'Stranger Things', 'Serie'),
	((SELECT nextval ('serien_filme_sequence')), 'Avengers: Infinity War', 'Film');