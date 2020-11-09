package de.tekup.rest.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.models.TelephoneNumberEntity;

public interface TelephoneRepository extends JpaRepository<TelephoneNumberEntity, Integer> {

}
