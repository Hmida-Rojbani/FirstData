package de.tekup.rest.data.endpoints;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.tekup.rest.data.dto.GameType;
import de.tekup.rest.data.dto.PersonReponse;
import de.tekup.rest.data.dto.PersonRequest;
import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.models.TelephoneNumberEntity;
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
	
	@GetMapping("/address")
	public List<AddressEntity> getAllAddress() {
		return service.getAllAddressEntities();
	}

	@PostMapping
	public PersonReponse createPerson(@Valid @RequestBody PersonRequest person) {
		return service.createPersonEntity(person);
	}

	@GetMapping("/{id}")
	public PersonReponse getPersonById(@PathVariable("id") long id) {
		return service.getPersonEntityById(id);
	}
	
	@GetMapping("/operator/{operator}")
	public List<PersonEntity> getAllPersonsByOperator(@PathVariable("operator") String operator) {
		return service.getAllPersonByPhoneOperator(operator);
	}
	
	@GetMapping("/average/age")
	public double getAverageAgeOfAllPersons() {
		return service.getAverageAge();
	}
	
	@GetMapping("/type/mostplayed")
	public List<PersonEntity> getAllPersonsForGameType() {
		return service.getPersonsForMostPlayedGameType();
	}
	
	@GetMapping("/type/number")
	public List<GameType> getAllGameTypesRank() {
		return service.getTypeAndGamesNumber();
	}
	
	@GetMapping("/operator/get/{operator}")
	public List<TelephoneNumberEntity> getAllPhoneByOperator(@PathVariable("operator") String operator) {
		return service.getByOprator(operator);
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
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		 // To Return 1 validation error
		//return new ResponseEntity<String>(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
		StringBuilder errors = new StringBuilder();
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			errors.append(error.getField() + ": "+ error.getDefaultMessage()+".\n");
		}
		return new ResponseEntity<String>(errors.toString(), HttpStatus.BAD_REQUEST);
	}

}
