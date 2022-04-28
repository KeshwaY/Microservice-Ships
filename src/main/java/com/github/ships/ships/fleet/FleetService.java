package com.github.ships.ships.fleet;

import com.github.ships.ships.Game;
import com.github.ships.ships.shot.ShotPostDTO;
import com.github.ships.ships.shot.ShotResult;
import org.springframework.stereotype.Service;

@Service
public class FleetService {

    private final FleetRepository repository;

    public FleetService(FleetRepository repository) {
        this.repository = repository;
    }

    public ShotResult placeShot(ShotResult shotResult, ShotPostDTO shotPostDTO) {
        // FleetPlaceShotProcedure didn't work.
        // Tried to replace it with a new one (NewProcedure),
        // but didn't succeed.
        NewProcedure newProcedure =
                new NewProcedure(repository, shotPostDTO, shotResult);
        return newProcedure.perform();
    }

    public Fleet createAndSaveFleet(int boardWidth, int boardHeight,
                                    Game game, int playerId) {
        Fleet fleet = new Fleet(game.getId(), playerId, boardWidth, boardHeight);
        repository.save(fleet);
        return fleet;
    }
}
