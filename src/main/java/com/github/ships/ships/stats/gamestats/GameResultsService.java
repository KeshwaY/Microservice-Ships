package com.github.ships.ships.stats.gamestats;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameResultsService {

    private final GameResultRepository gameResultRepository;

    GameResultsService(GameResultRepository gameResultRepository) {
        this.gameResultRepository = gameResultRepository;
    }

    public String postStat() {
        GameResult gameResult = new GameResult();
        gameResultRepository.save(gameResult);
        System.out.println("post stat");
        return "post stat";
    }
    public String getStats() {
        List<GameResult> gameResults = gameResultRepository.findAll();
        System.out.println("get stat");
        return "get stat";
    }
}
