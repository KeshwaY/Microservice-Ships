package com.github.ships.ships.fleet;

import com.github.ships.ships.Board;
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
        initFleet();
    }

    public ShotResult placeShot(ShotResult shotResult, ShotPostDTO shotPostDTO) {
        FleetPlaceShotPorcedure fleetPlaceShotPorcedure =
                new FleetPlaceShotPorcedure(repository, shotPostDTO, shotResult);
        return fleetPlaceShotPorcedure.perform();
    }

    private void initFleet() {
        //TODO get rid of hardcoded board size
        Fleet playerfleet = new Fleet(1,10, 10);
        Fleet enemyFleet = new Fleet(2,10, 10);
        repository.saveAll(List.of(playerfleet, enemyFleet));
    }
}
