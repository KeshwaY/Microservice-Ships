package com.github.ships.ships.users;

import com.github.ships.ships.GenericResponseDto;
import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.ResourceAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserGetDTO create(UserPostDTO userPostDTO) {
        if(repository.findByEmail(userPostDTO.getEmail()).isPresent()) throw new ResourceAlreadyExistsException();
        User user = mapper.userPostDtoToUser(userPostDTO);
        user.setCreatedDate(LocalDateTime.now());
        return mapper.userToUserGetDTO(repository.save(user));
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
