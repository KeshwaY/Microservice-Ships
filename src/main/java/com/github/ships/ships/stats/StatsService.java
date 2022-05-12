package com.github.ships.ships.stats;

import com.github.ships.ships.users.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {

    private final statsRepository statsRepository;

    StatsService(statsRepository gameResultRepository) {
        this.statsRepository = gameResultRepository;
    }
    public String createStat(User player) {
        statsRepository.save(new PlayerStats(player));
        return "create stat";
    }
    public String getStats(User player) {
        PlayerStats playerStats = statsRepository.findByPlayer(player).get();
        List<PlayerStats> gameResults = statsRepository.findAll();
        playerStats = gameResults.get(0);
        playerStats.increment();
        statsRepository.save(playerStats);
        return "get stat";
    }

    public String updateStats(User player) {
        PlayerStats playerStats = new PlayerStats(player);
        playerStats.increment();
        statsRepository.save(playerStats);
        return "post stat";
    }
}
