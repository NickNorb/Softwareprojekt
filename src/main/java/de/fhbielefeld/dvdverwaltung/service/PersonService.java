package de.fhbielefeld.dvdverwaltung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhbielefeld.dvdverwaltung.entity.Person;
import de.fhbielefeld.dvdverwaltung.repository.PersonRepository;

/**
 * Die Serviceklasse, welche die passenden Informationen zwischen TableModel und Datenbank übergibt
 * 
 * @author Nick Norberts
 * @author Linus Kliemann
 *
 */

@Component
public class PersonService{
	
	private PersonRepository prepo;
	
	@Autowired
	public PersonService(PersonRepository prepo) {
		this.prepo = prepo;
	}
	
	public List<Person> getPersonListFromRepo() {
		return prepo.findAllByOrderByNachnameAsc();
	}

	public void updateFromRepo() {
		prepo.findAllByOrderByVornameAsc();
	}
	
	public void savePersonInRepo(Person person) {
		prepo.save(person);
	}
	
	public void deletePersonFromRepo(Person person) throws IllegalStateException {
		if (cannotBeDeleted(person))
			throw new IllegalStateException();
		
		prepo.delete(person);
	}
	
	public boolean cannotBeDeleted(Person person) {
		return getAnzahlAusleihenPerson(person.getPersonId()) != 0;
	}

	public long getAnzahlAusleihenPerson(long personId) {
		return prepo.getAnzahlAusleihenPersonID(personId);
	}
	
}