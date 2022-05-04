package com.github.ships.ships.security.authorities;

import com.github.ships.ships.GenericResponseDto;
import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.ResourceAlreadyExistsException;
import com.github.ships.ships.GenericCRUDService;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

    // TODO: fix logic
    @Override
    public AuthorityDTO update(String name, AuthorityDTO authorityDTO) {
        Authority authority = repository.findByName(name).orElse(mapper.authorityDtoToAuthority(authorityDTO));
        if (repository.findByName(authorityDTO.getName()).isPresent()) throw new ResourceAlreadyExistsException();
        authority.setName(authorityDTO.getName());
        return mapper.authorityToAuthorityDto(repository.save(authority));
    }

    @Override
    public GenericResponseDto delete(String name) {
        Authority authority = repository.findByName(name).orElseThrow(NotFoundException::new);
        repository.delete(authority);
        return new GenericResponseDto("Successfully deleted authority with name: " + authority.getName());
    }
}
