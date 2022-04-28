package com.github.ships.ships.fleet;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FleetRepository extends MongoRepository<Fleet, String> {

    @Query("{ 'gameId' : ?0, 'playerId' : ?1 }")
    List<Fleet> getSpecifiedFleets(String gameId, int playerId);

}
