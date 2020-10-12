package de.tekup.rest.data.endpoints;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.services.PersonService;

@RestController
@RequestMapping("/api/persons")
public class PersonRest {

	private PersonService service;

	@Autowired
	public PersonRest(PersonService service) {
		super();
		this.service = service;
	}

	@GetMapping
	public List<PersonEntity> getAll() {
		return service.getAllPersonEntities();
	}

	@PostMapping
	public PersonEntity createPerson(@RequestBody PersonEntity person) {
		return service.createPersonEntity(person);
	}

	@GetMapping("/{id}")
	public PersonEntity getPersonById(@PathVariable("id") long id) {
		return service.getPersonEntityById(id);
	}
	
	@DeleteMapping("/{id}")
	public PersonEntity deletePersonById(@PathVariable("id") long id) {
		return service.deletePersonEntityById(id);
	}
	
	@PutMapping("/{id}")
	public PersonEntity updatePerson(@PathVariable("id") long id, @RequestBody PersonEntity newPerson) {
		return service.modifyPersonEntity(id, newPerson);
	}
	

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);

	}

}
