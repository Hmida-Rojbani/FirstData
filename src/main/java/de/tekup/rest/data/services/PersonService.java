package de.tekup.rest.data.services;

import java.util.List;

import de.tekup.rest.data.dto.GameType;
import de.tekup.rest.data.dto.PersonReponse;
import de.tekup.rest.data.dto.PersonRequest;
import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.models.TelephoneNumberEntity;

public interface PersonService {

	PersonReponse createPersonEntity(PersonRequest entity);
	List<PersonEntity> getAllPersonEntities();
	PersonReponse getPersonEntityById(long id);
	PersonEntity modifyPersonEntity(long id, PersonEntity entity);
	PersonEntity deletePersonEntityById(long id);
	List<AddressEntity> getAllAddressEntities();
	public List<PersonEntity> getAllPersonByPhoneOperator(String operator);
	public double getAverageAge();
	public List<PersonEntity> getPersonsForMostPlayedGameType();
	public List<GameType> getTypeAndGamesNumber();
	public List<TelephoneNumberEntity> getByOprator(String operator);
	
}
