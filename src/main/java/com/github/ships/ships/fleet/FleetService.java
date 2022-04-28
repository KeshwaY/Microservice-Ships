package com.github.ships.ships.fleet;

import com.github.ships.ships.Game;
import com.github.ships.ships.shot.ShotPostDTO;
import com.github.ships.ships.shot.ShotResult;
import com.github.ships.ships.shot.StatusOfLegalShot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FleetService {

    private final FleetRepository repository;

    public FleetService(FleetRepository repository) {
        this.repository = repository;
    }

    public ShotResult placeShot(ShotResult shotResult, ShotPostDTO shotPostDTO) {
        FleetPlaceShotProcedure fleetPlaceShotProcedure =
                                new FleetPlaceShotProcedure(repository, shotPostDTO, shotResult);
        return fleetPlaceShotProcedure.perform();
    }

    public Fleet createAndSaveFleet(int boardWidth, int boardHeight,
                                    Game game, int playerId) {
        Fleet fleet = new Fleet(game.getId(), playerId, boardWidth, boardHeight);
        repository.save(fleet);
        return fleet;
    }
}
