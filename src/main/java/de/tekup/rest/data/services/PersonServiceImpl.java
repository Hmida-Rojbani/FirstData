package de.tekup.rest.data.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.GamesEntity;
import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.models.TelephoneNumberEntity;
import de.tekup.rest.data.repositories.AddressRepository;
import de.tekup.rest.data.repositories.GameRepository;
import de.tekup.rest.data.repositories.PersonRepository;
import de.tekup.rest.data.repositories.TelephoneRepository;

@Service
public class PersonServiceImpl implements PersonService {
	
	
	private PersonRepository repos;
	private AddressRepository reposAddress;
	private TelephoneRepository reposTelephone;
	private GameRepository  reposGames;
	
	@Autowired
	public PersonServiceImpl(PersonRepository repos, AddressRepository reposAddress, TelephoneRepository reposTelephone,
			GameRepository reposGames) {
		super();
		this.repos = repos;
		this.reposAddress = reposAddress;
		this.reposTelephone = reposTelephone;
		this.reposGames = reposGames;
	}



	@Override
	public PersonEntity createPersonEntity(PersonEntity entity) {
		// Saving Address
		AddressEntity addressEntity = entity.getAddress();
		AddressEntity addressInBase = reposAddress.save(addressEntity);
		//saving person
		entity.setAddress(addressInBase);
		PersonEntity newEntity = repos.save(entity);
		//saving phoneNumbers
		for (TelephoneNumberEntity phone : entity.getPhones()) {
			phone.setPerson(newEntity);
			reposTelephone.save(phone);
		}
		//saving games
		List<PersonEntity> persons;
		for (GamesEntity game : entity.getGames()) {
			if (game.getPersons() != null) {
				persons = game.getPersons();
			}else {
				persons = new ArrayList<>();
			}
			persons.add(newEntity);
			game.setPersons(persons);
			reposGames.save(game);
			
		}
		
		
		
		return newEntity;
	}

	

	@Override
	public List<PersonEntity> getAllPersonEntities() {
		
		return repos.findAll();
	}

	@Override
	public PersonEntity getPersonEntityById(long id) {
		
		PersonEntity entity;
		Optional<PersonEntity> opt = repos.findById(id);
		if(opt.isPresent())
			entity = opt.get();
		else
			throw new NoSuchElementException("Person with this id is not found");
		
		return entity;
	}

	//update Person 
	@Override
	public PersonEntity modifyPersonEntity(long id, PersonEntity newPerson) {
		// is there a better way ? (3 point pour DS)
		PersonEntity oldPerson = this.getPersonEntityById(id);
		if(newPerson.getName() != null)
			oldPerson.setName(newPerson.getName());
		if(newPerson.getDateOfBirth() != null)
			oldPerson.setDateOfBirth(newPerson.getDateOfBirth());
		AddressEntity oldAddress = oldPerson.getAddress();
		AddressEntity newAddress = newPerson.getAddress();
		if(newAddress != null) {
			// merge/fusion entre oldAddress et newAddress
			if(newAddress.getNumber() != 0)
				oldAddress.setNumber(newAddress.getNumber());
			if(newAddress.getStreet() != null)
				oldAddress.setStreet(newAddress.getStreet());
			if(newAddress.getCity() != null)
				oldAddress.setCity(newAddress.getCity());
		}
		
		
		// merge Phone
		
		List<TelephoneNumberEntity> oldPhones= oldPerson.getPhones();
		List<TelephoneNumberEntity> newPhones= newPerson.getPhones();
		if(newPhones != null) {
			for (int i = 0; i < newPhones.size(); i++) {
				TelephoneNumberEntity newPhone = newPhones.get(i);
				for (int j = 0; j < oldPhones.size(); j++) {
					TelephoneNumberEntity oldPhone = oldPhones.get(j);
					if(oldPhone.getId() == newPhone.getId()) {
						if(newPhone.getNumber() != null)
							oldPhone.setNumber(newPhone.getNumber());
						if(newPhone.getOperator() != null)
							oldPhone.setOperator(newPhone.getOperator());
						// break over Old loop
						break;
					}
				}
			}
		}
		
		// merge Games
		
		List<GamesEntity> oldGames = oldPerson.getGames();
		List<GamesEntity> newGames = newPerson.getGames();
		if(newGames != null) {
			for (GamesEntity newGame : newGames) {
				for (GamesEntity oldGame : oldGames) {
					if(oldGame.getId() == newGame.getId()) {
						if(newGame.getTitle() != null)
							oldGame.setTitle(newGame.getTitle());
						if(newGame.getType() != null)
							oldGame.setType(newGame.getType());
						// stop the loop
						break;
					}
						
				}
			}
		}
		
		
		
		return repos.save(oldPerson);
	}

	@Override
	public PersonEntity deletePersonEntityById(long id) {
		PersonEntity entity = this.getPersonEntityById(id);
		repos.deleteById(id);
		return entity;
	}

	@Override
	public List<AddressEntity> getAllAddressEntities() {
		return reposAddress.findAll();
	}
	
	// retourner les persons ayant un phone avec l'operateur donnèe
	public List<PersonEntity> getAllPersonByPhoneOperator(String operator){
		//Version 1
		/*List<PersonEntity> persons = repos.findAll();
		List<PersonEntity> personsWithOperator = new ArrayList<>();
		// filtrage avec L'operator
		for (PersonEntity person : persons) {
			for (TelephoneNumberEntity phone : person.getPhones()) {
				if(phone.getOperator().equalsIgnoreCase(operator)) {
					personsWithOperator.add(person);
					break;
				}
					
			}
		}*/
		//version 2
		/*Set<PersonEntity> personsWithOperator = new HashSet<>();
		List<TelephoneNumberEntity> phones = reposTelephone.findAll();
		for (TelephoneNumberEntity phone : phones) {
			if(phone.getOperator().equalsIgnoreCase(operator))
				personsWithOperator.add(phone.getPerson());
				
		return new ArrayList<>(personsWithOperator);
		}*/
		// version 3 Java 8
		
		List<PersonEntity> personsWithOperator 
							= reposTelephone.findAll()
											.stream()
											.filter(phone -> phone.getOperator().equalsIgnoreCase(operator))
											.map(phone -> phone.getPerson())
											.distinct()
											.collect(Collectors.toList());
		
		return personsWithOperator;
	}
	
	// Average age of all Persons
	
	
	//Persons who play the type of game the most played.
	
	// Display the games type and the number of games for each type;

}
