package com.github.ships.ships;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<UserGetDTO> create(
            @Valid @RequestBody UserPostDTO userPostDTO
    ) {
        return new ResponseEntity<>(service.create(userPostDTO), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<UserGetDTO> get(
            @RequestParam String email
    ) {
        return ResponseEntity.ok(service.get(email));
    }

    @DeleteMapping
    ResponseEntity<GenericResponseDto> delete(
            @RequestParam String email
    ) {
        return ResponseEntity.ok(service.delete(email));
    }

}
