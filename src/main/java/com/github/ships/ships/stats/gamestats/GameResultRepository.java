package com.github.ships.ships.stats.gamestats;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameResultRepository extends MongoRepository<GameResult, String> {
}
