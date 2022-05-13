package com.github.ships.ships.stats;

import com.github.ships.ships.users.User;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

@Service
public class StatsService {

    private final statsRepository statsRepository;

    StatsService(statsRepository gameResultRepository) {
        this.statsRepository = gameResultRepository;
    }

    public String createStats(User player) {
        PlayerStats playerStats = new PlayerStats(player);
        statsRepository.save(playerStats);
        Logger.info(String.format("User %s statistics created", playerStats.getPlayer()));
        return "create stats";
    }

//    public String getStats() {
//        List<PlayerStats> stats = statsRepository.findAll();
//        StringBuilder sb = new StringBuilder();
//        stats.forEach(s -> {
//            sb.append(String.format("%s - winnings: %d", s.getPlayer().getName(), s.getWinnings()));
//            sb.append(System.lineSeparator());
//            Logger.info(String.format("Statistics provided"));
//        });
//        return String.format(sb.toString());
//    }

    public StatisticsGetDTO getStats() {
        Statistics statistics = new Statistics(statsRepository.findAll());
        Logger.info(String.format("Statistics provided"));
        return new StatisticsGetDTO(statistics.getStatistics());
    }

    public String updateStats(User player) {
        PlayerStats playerStats = statsRepository.findByPlayer(player).get();
        playerStats.increment();
        statsRepository.save(playerStats);
        Logger.info(String.format("User: %s statistics updated - winnings: %d",
                playerStats.getPlayer().getName(), playerStats.getWinnings()));
        return String.format("%s - winnings: %d", playerStats.getPlayer().getName(), playerStats.getWinnings());
    }
}
