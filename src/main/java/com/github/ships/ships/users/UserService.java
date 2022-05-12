package com.github.ships.ships.users;

import com.github.ships.ships.GenericResponseDto;
import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.ResourceAlreadyExistsException;
import com.github.ships.ships.security.authorities.AuthorityRepository;
import com.github.ships.ships.security.roles.Role;
import com.github.ships.ships.security.roles.RoleRepository;
import com.github.ships.ships.stats.StatsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    private final StatsService statsService;

    public UserService(
            UserRepository repository,
            RoleRepository roleRepository,
            AuthorityRepository authorityRepository,
            PasswordEncoder passwordEncoder,
            UserMapper mapper, StatsService statsService) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.statsService = statsService;
    }

    public UserGetDTO create(UserPostDTO userPostDTO) {
        if(repository.findByEmail(userPostDTO.getEmail()).isPresent()) throw new ResourceAlreadyExistsException();
        User user = mapper.userPostDtoToUser(userPostDTO);
        user.setCreatedDate(LocalDateTime.now());
        user.setRole(getUserRole());
        user.setPassword(passwordEncoder.encode(userPostDTO.getPassword()));
        statsService.createStat(user);
        return mapper.userToUserGetDTO(repository.save(user));
    }

    public User getRawUser(String email) {
        return repository.findByEmail(email).orElseThrow(NotFoundException::new);
    }

    UserGetDTO get(String email) {
        User user = repository.findByEmail(email).orElseThrow(NotFoundException::new);
        return mapper.userToUserGetDTO(user);
    }

    GenericResponseDto delete(String email) {
        User user = repository.findByEmail(email).orElseThrow(NotFoundException::new);
        repository.delete(user);
        return new GenericResponseDto("Successfully deleted user with email: " + email);
    }

    private Role getUserRole() {
        return roleRepository.findByAuthoritiesIsContaining(
                        authorityRepository.findByName("BASIC_USER")
                                .orElseThrow(NotFoundException::new))
                .orElseThrow(NotFoundException::new
                );
    }

}
