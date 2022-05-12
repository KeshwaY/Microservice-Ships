package com.github.ships.ships.stats;

import com.github.ships.ships.users.User;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.List;

@Service
public class StatsService {

    private final statsRepository statsRepository;

    StatsService(statsRepository gameResultRepository) {
        this.statsRepository = gameResultRepository;
    }

    public String createStat(User player) {
        PlayerStats playerStats = new PlayerStats(player);
        statsRepository.save(playerStats);
        Logger.info(String.format("Statistics for new user %s are created", playerStats.getPlayer()));
        return "create stat";
    }

    public String getStats(User player) {
        PlayerStats playerStats = statsRepository.findByPlayer(player).get();
        List<PlayerStats> gameResults = statsRepository.findAll();
        playerStats = gameResults.get(0);
        statsRepository.save(playerStats);
        Logger.info(String.format("Statistics provided for user: %s - winnings: %d",
                playerStats.getPlayer().getName(), playerStats.getWinnings()));
        return String.format("%s - winnings: %d", playerStats.getPlayer().getName(), playerStats.getWinnings());
    }

    public String updateStats(User player) {
        PlayerStats playerStats = statsRepository.findByPlayer(player).get();
        List<PlayerStats> gameResults = statsRepository.findAll();
        playerStats = gameResults.get(0);
        playerStats.increment();
        statsRepository.save(playerStats);
        Logger.info(String.format("Statistics updated for user: %s - winnings: %d",
                playerStats.getPlayer().getName(), playerStats.getWinnings()));
        return String.format("%s - winnings: %d", playerStats.getPlayer().getName(), playerStats.getWinnings());
    }
}
