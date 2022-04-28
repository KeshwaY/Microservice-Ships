package com.github.ships.ships.fleet;

import com.github.ships.ships.shot.ShotPostDTO;
import com.github.ships.ships.shot.ShotResult;

import java.util.List;

class FleetPlaceShotPorcedure {

    private final FleetRepository repository;
    private final ShotPostDTO shotPostDTO;
    private final ShotResult shotResult;

    public FleetPlaceShotPorcedure(FleetRepository repository, ShotPostDTO shotPostDTO, ShotResult shotResult) {
        this.repository = repository;
        this.shotPostDTO = shotPostDTO;
        this.shotResult = shotResult;
    }

    public ShotResult perform() {
        List<Fleet> fleets = getFleetByGameAndPlayerIDs();
        if(fleets.isEmpty()) {
            throw new RuntimeException();
        }
        Fleet fleet = fleets.get(0);



        return null;
    }

    private List<Fleet> getFleetByGameAndPlayerIDs() {
        String gameId = shotPostDTO.getGameId();
        int playerId = shotPostDTO.getPlayerId();
        return repository.getSpecifiedFleets(gameId, playerId);
    }
}
