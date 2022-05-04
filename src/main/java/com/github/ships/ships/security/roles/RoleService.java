package com.github.ships.ships.security.roles;

import com.github.ships.ships.GenericResponseDto;
import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.ResourceAlreadyExistsException;
import com.github.ships.ships.GenericCRUDService;
import com.github.ships.ships.security.authorities.Authority;
import com.github.ships.ships.security.authorities.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
class RoleService implements GenericCRUDService<RoleDTO, RoleDTO, RoleDTO, GenericResponseDto> {

    private final RoleRepository repository;
    private final AuthorityRepository authorityRepository;
    private final RoleMapper mapper;

    RoleService(RoleRepository repository, AuthorityRepository authorityRepository, RoleMapper mapper) {
        this.repository = repository;
        this.authorityRepository = authorityRepository;
        this.mapper = mapper;
    }

    @Override
    public GenericResponseDto create(RoleDTO roleDTO) {
        if (repository.findByName(roleDTO.getName()).isPresent()) throw new ResourceAlreadyExistsException();
        Role role = mapper.roleDtoToRole(roleDTO);
        Collection<Authority> authorities = findAuthorities(roleDTO.getAuthorities());
        role.setAuthorities(authorities);
        repository.save(role);
        return new GenericResponseDto("Successfully created role with name: " + role.getName());
    }

    private Collection<Authority> findAuthorities(Collection<String> authorities) {
        return authorities.stream()
                .map(a -> authorityRepository.findByName(a).orElseThrow(NotFoundException::new))
                .toList();
    }

    @Override
    public RoleDTO get(String name) {
        Role role = repository.findByName(name).orElseThrow(NotFoundException::new);
        return mapper.roleToRoleDto(role);

    }

    @Override
    public Collection<RoleDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::roleToRoleDto)
                .toList();
    }

    // TODO: fix logic
    @Override
    public RoleDTO update(String name, RoleDTO roleDTO) {
        Role role = repository.findByName(name).orElse(mapper.roleDtoToRole(roleDTO));
        if (repository.findByName(roleDTO.getName()).isPresent()) throw new ResourceAlreadyExistsException();
        role.setName(roleDTO.getName());
        role.setGrade(roleDTO.getGrade());
        Collection<Authority> authorities = findAuthorities(roleDTO.getAuthorities());
        role.setAuthorities(authorities);
        return mapper.roleToRoleDto(repository.save(role));

    }

    @Override
    public GenericResponseDto delete(String name) {
        Role role = repository.findByName(name).orElseThrow(NotFoundException::new);
        repository.delete(role);
        return new GenericResponseDto("Successfully deleted role with name: " + role.getName());

    }
}
