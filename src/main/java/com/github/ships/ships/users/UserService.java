package com.github.ships.ships.users;

import com.github.ships.ships.GenericResponseDto;
import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.ResourceAlreadyExistsException;
import com.github.ships.ships.security.authorities.AuthorityRepository;
import com.github.ships.ships.security.roles.Role;
import com.github.ships.ships.security.roles.RoleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, RoleRepository roleRepository, AuthorityRepository authorityRepository, UserMapper mapper) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
        this.mapper = mapper;
    }

    public UserGetDTO create(UserPostDTO userPostDTO) {
        if(repository.findByEmail(userPostDTO.getEmail()).isPresent()) throw new ResourceAlreadyExistsException();
        User user = mapper.userPostDtoToUser(userPostDTO);
        user.setCreatedDate(LocalDateTime.now());
        user.setRole(getUserRole());
        return mapper.userToUserGetDTO(repository.save(user));
    }

    private Role getUserRole() {
        return roleRepository.findByAuthoritiesIsContaining(
                authorityRepository.findByName("BASIC_USER")
                        .orElseThrow(NotFoundException::new))
                .orElseThrow(NotFoundException::new
        );
    }

    public UserGetDTO get(String email) {
        User user = repository.findByEmail(email).orElseThrow(NotFoundException::new);
        return mapper.userToUserGetDTO(user);
    }

    public GenericResponseDto delete(String email) {
        User user = repository.findByEmail(email).orElseThrow(NotFoundException::new);
        repository.delete(user);
        return new GenericResponseDto("Successfully deleted user with email: " + email);
    }

}
