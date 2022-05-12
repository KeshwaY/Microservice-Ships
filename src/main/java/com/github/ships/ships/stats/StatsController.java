package com.github.ships.ships.stats;

import com.github.ships.ships.stats.gamestats.GameResultsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("api/v1/stats")
class StatsController {

    private final GameResultsService gameResultsService;

    StatsController(GameResultsService gameResultsService) {
        this.gameResultsService = gameResultsService;
    }

    @PostMapping
    public ResponseEntity<String> postStat() {
        String s = gameResultsService.postStat();

        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> getStats() {
        String s = gameResultsService.getStats();

        return new ResponseEntity<>(s, HttpStatus.OK);
    }

}