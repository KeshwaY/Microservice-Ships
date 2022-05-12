package com.github.ships.ships.stats;

import com.github.ships.ships.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("api/v1/stats")
class StatsController {

    private final StatsService statsService;
    private final UserService userService;

    StatsController(StatsService gameResultsService, UserService userService) {
        this.statsService = gameResultsService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createStats() {
        String s = statsService.createStats(userService.getRawUser("rwar@s.s"));
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateStat() {
        String s = statsService.updateStats(userService.getRawUser("rwar@s.s"));
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> getStats() {
        String s = statsService.getStats(userService.getRawUser("rwar@s.s"));
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}
