package com.github.ships.ships.security.roles;

import com.github.ships.ships.security.authorities.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
    Optional<Role> findByAuthoritiesIsContaining(Collection<Authority> authorities);
    Optional<Role> findByAuthoritiesIsContaining(Authority authorities);
}
