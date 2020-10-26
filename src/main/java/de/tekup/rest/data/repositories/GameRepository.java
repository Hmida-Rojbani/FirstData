package de.tekup.rest.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tekup.rest.data.models.GamesEntity;

public interface GameRepository extends JpaRepository<GamesEntity, Integer>{

}
