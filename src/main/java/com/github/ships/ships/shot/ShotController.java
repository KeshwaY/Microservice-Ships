package com.github.ships.ships.shot;

import com.github.ships.ships.fleet.ShotResultDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/shots")
public class ShotController {

    private final ShotService service;

    public ShotController(ShotService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<ShotResultDto> shot(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("game-id") String gameId,
            @RequestParam("cell-id") String cellId
    ) {
        return ResponseEntity.ok(service.shot(userDetails.getUsername(), gameId, cellId));
    }

}
