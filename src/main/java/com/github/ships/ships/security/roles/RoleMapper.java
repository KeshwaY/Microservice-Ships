package com.github.ships.ships.security.roles;

import com.github.ships.ships.security.authorities.Authority;
import com.github.ships.ships.security.authorities.AuthorityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;

@Mapper(
        componentModel = "spring"
)
public interface RoleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    Role roleDtoToRole(RoleDTO role);

    @Mapping(target = "authorities", qualifiedByName = {"authoritiesToDto"})
    RoleDTO roleToRoleDto(Role role);

    @Named("authoritiesToDto")
    default Collection<String> authorities(Collection<Authority> authorities) {
        return authorities.stream()
                .map(Authority::getName)
                .toList();
    }
}
