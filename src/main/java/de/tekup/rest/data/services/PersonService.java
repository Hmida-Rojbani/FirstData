package de.tekup.rest.data.services;

import java.util.List;

import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.PersonEntity;

public interface PersonService {

	PersonEntity createPersonEntity(PersonEntity entity);
	List<PersonEntity> getAllPersonEntities();
	PersonEntity getPersonEntityById(long id);
	PersonEntity modifyPersonEntity(long id, PersonEntity entity);
	PersonEntity deletePersonEntityById(long id);
	List<AddressEntity> getAllAddressEntities();
	public List<PersonEntity> getAllPersonByPhoneOperator(String operator);
	
}
