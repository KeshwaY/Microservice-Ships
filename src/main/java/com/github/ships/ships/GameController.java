package com.github.ships.ships;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/games")
class GameController {

    private final GameService service;

    GameController(final GameService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<GameGetDTO> create() {
        return ResponseEntity.ok(service.create());
    }

    @GetMapping("/{id}")
    ResponseEntity<GameGetDTO> get(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(service.get(id));
    }
}
