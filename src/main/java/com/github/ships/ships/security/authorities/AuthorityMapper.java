package com.github.ships.ships.security.authorities;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface AuthorityMapper {
    @Mapping(target = "id", ignore = true)
    Authority authorityDtoToAuthority(AuthorityDTO postDTO);
    AuthorityDTO authorityToAuthorityDto(Authority authority);
}
