package de.tekup.rest.data.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.repositories.AddressRepository;
import de.tekup.rest.data.repositories.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {
	
	
	private PersonRepository repos;
	private AddressRepository reposAddress;
	
	
	@Autowired
	public PersonServiceImpl(PersonRepository repos, AddressRepository reposAddress) {
		super();
		this.repos = repos;
		this.reposAddress = reposAddress;
	}

	@Override
	public PersonEntity createPersonEntity(PersonEntity entity) {
		// extraction of Address part 
		AddressEntity address = entity.getAddress();
		// saving in DB Address without Person part
		AddressEntity addressInBase = reposAddress.save(address);
		// Add Id of Address in the Person 
		entity.setAddress(addressInBase);
		// save of Person with Address
		PersonEntity newEntity = repos.save(entity);
		// Add in Address The Saved Person (With Id)
		address.setPerson(newEntity);
		// Resave Address
		addressInBase = reposAddress.save(address);
		System.out.println(newEntity);
		addressInBase = reposAddress.findById(1).orElse(null);
		
		System.out.println(addressInBase);
		System.out.println("address Person :"+addressInBase.getPerson());
		newEntity = repos.findById(1L).orElse(null);
		System.out.println(newEntity);
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
