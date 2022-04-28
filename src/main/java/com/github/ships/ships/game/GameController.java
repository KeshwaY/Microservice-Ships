package com.github.ships.ships.game;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameDto> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody GamePostDto gamePostDto
    ) {
        return new ResponseEntity<>(gameService.create(userDetails.getUsername(), gamePostDto), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<GameDto> join(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id
    ) {
        return new ResponseEntity<>(gameService.join(userDetails.getUsername(), id), HttpStatus.OK);
    }
}
