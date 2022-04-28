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
        shotResult.setStatusOfLegalShot(StatusOfLegalShot.SUNK_SHIP);
        shotResult.setShipSunk(List.of(1, 3, 4));
        shotResult.setAdjWaterOfShipSunk(List.of(6, 2, 4, 9));
        return shotResult;
//        FleetPlaceShotPorcedure fleetPlaceShotPorcedure =
//                new FleetPlaceShotPorcedure(repository, shotPostDTO, shotResult);
//        return fleetPlaceShotPorcedure.perform();
    }

    public void createAndSaveFleets(int boardWidth, int boardHeight, Game game) {
        Fleet playerFleet = new Fleet(game.getId(), game.getFirstPlayerID(),
                                      boardWidth, boardHeight);
        Fleet enemyFleet = new Fleet(game.getId(), game.getSecondPlayerID(),
                                     boardWidth, boardHeight);
        repository.saveAll(List.of(playerFleet, enemyFleet));
    }
}
