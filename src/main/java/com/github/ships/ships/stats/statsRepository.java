package com.github.ships.ships.stats;

import com.github.ships.ships.users.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface statsRepository extends MongoRepository<PlayerStats, User> {
    Optional<PlayerStats> findByPlayer(User player);
}
