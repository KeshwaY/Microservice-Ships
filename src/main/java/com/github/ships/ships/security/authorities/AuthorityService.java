package com.github.ships.ships.security.authorities;

import com.github.ships.ships.GenericResponseDto;
import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.ResourceAlreadyExistsException;
import com.github.ships.ships.abstraction.GenericCRUDService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AuthorityService implements GenericCRUDService<AuthorityDTO, AuthorityDTO, AuthorityDTO, GenericResponseDto> {

    private final AuthorityRepository repository;
    private final AuthorityMapper mapper;

    public AuthorityService(AuthorityRepository repository, AuthorityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public GenericResponseDto create(AuthorityDTO authorityDTO) {
        if (repository.findByName(authorityDTO.getName()).isPresent()) throw new ResourceAlreadyExistsException();
        Authority authority = mapper.authorityDtoToAuthority(authorityDTO);
        repository.save(authority);
        return new GenericResponseDto("Successfully created authority with name: " + authority.getName());
    }

    @Override
    public AuthorityDTO get(String name) {
        Authority authority = repository.findByName(name).orElseThrow(NotFoundException::new);
        return mapper.authorityToAuthorityDto(authority);
    }

    @Override
    public Collection<AuthorityDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::authorityToAuthorityDto)
                .toList();
    }

    @Override
    public AuthorityDTO update(String name, AuthorityDTO authorityDTO) {
        Optional<Authority> optionalAuthority = repository.findByName(name);
        Authority authority;
        if (optionalAuthority.isPresent()) {
            authority = optionalAuthority.get();
            authority.setName(authorityDTO.getName());
        } else {
            authority = mapper.authorityDtoToAuthority(authorityDTO);
        }
        return mapper.authorityToAuthorityDto(repository.save(authority));
    }

    @Override
    public GenericResponseDto delete(String name) {
        Authority authority = repository.findByName(name).orElseThrow(NotFoundException::new);
        repository.delete(authority);
        return new GenericResponseDto("Successfully deleted authority with name: " + authority.getName());
    }
}
