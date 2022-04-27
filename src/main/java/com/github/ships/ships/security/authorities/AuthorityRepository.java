package com.github.ships.ships.security.authorities;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthorityRepository extends MongoRepository<Authority, String> {
    Optional<Authority> findByName(String name);
}
