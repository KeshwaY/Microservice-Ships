package com.github.ships.ships.stats.userstats;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserStatsRepository extends MongoRepository<UserStats, String> {
}
