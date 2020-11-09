package de.tekup.rest.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.models.TelephoneNumberEntity;

public interface TelephoneRepository extends JpaRepository<TelephoneNumberEntity, Integer> {

	public List<TelephoneNumberEntity> findByOperator (String operator);
	
	//@Query("select distinct(t.person) from TelephoneNumberEntity t "
	//		+ "where lower(t.operator) = lower(?1)")
	@Query("select distinct(t.person) from TelephoneNumberEntity t "
			+ "where lower(t.operator) = lower(:opt)")
	List<PersonEntity> getPersonsByOperator(@Param("opt") String operator);
}
