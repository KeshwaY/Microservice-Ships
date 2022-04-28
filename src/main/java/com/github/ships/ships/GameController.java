package com.github.ships.ships;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1/games")
class GameController {

    private final GameService service;

    GameController(final GameService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<GameCreatedDTO> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody GamePostDTO gamePostDTO
    ) {
        return ResponseEntity.ok(service.create(userDetails.getUsername(), gamePostDTO));
    }

    @PostMapping("/join/{id}")
    ResponseEntity<GameCreatedDTO> join(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable String id
    ) {
        return ResponseEntity.ok(service.join(userDetails.getUsername(), id));
    }
}
