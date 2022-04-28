package com.github.ships.ships;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @Valid @RequestBody GamePostDTO gamePostDTO
    ) {
        return ResponseEntity.ok(service.createGame(gamePostDTO));
    }
}
