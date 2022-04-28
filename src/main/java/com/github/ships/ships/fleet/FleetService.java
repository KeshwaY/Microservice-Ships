package com.github.ships.ships.fleet;

import com.github.ships.ships.shot.ShotPostDTO;
import com.github.ships.ships.shot.ShotResult;
import org.springframework.stereotype.Service;

@Service
class FleetService {

    private final FleetRepository repository;

    public FleetService(FleetRepository repository) {
        this.repository = repository;
    }

    public ShotResult placeShot(ShotPostDTO shotPostDTO, ShotResult shotResult) {
        FleetPlaceShotPorcedure fleetPlaceShotPorcedure =
                new FleetPlaceShotPorcedure(repository, shotPostDTO, shotResult);
        return fleetPlaceShotPorcedure.perform();
    }
}
