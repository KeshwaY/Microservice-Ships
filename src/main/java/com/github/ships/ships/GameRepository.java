package com.github.ships.ships;

import org.springframework.data.mongodb.repository.MongoRepository;

interface GameRepository extends MongoRepository<Game, String> {
}
