package de.tekup.rest.data.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

	@Override
	public PersonEntity modifyPersonEntity(long id, PersonEntity entity) {
		PersonEntity oldEntity = this.getPersonEntityById(id);
		if(entity.getName() != null)
			oldEntity.setName(entity.getName());
		if(entity.getDateOfBirth() != null)
			oldEntity.setDateOfBirth(entity.getDateOfBirth());
		if(entity.getAddress() != null)
			oldEntity.setAddress(entity.getAddress());
		
		return repos.save(oldEntity);
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

}
