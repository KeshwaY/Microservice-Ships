package com.github.ships.ships.stats.gamestats;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@RequiredArgsConstructor @Getter @Setter
class GameStats {

    @Id
    private String id;

    private List<GameResult> gameResults;

    void addGameResult(GameResult gameResult) {
        gameResults.add(gameResult);
    }

}
