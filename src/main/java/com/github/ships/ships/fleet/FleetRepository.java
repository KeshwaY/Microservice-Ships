package com.github.ships.ships.fleet;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FleetRepository extends MongoRepository<Fleet, String> {
}
