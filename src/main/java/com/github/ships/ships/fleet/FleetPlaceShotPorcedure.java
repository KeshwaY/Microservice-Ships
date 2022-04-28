package com.github.ships.ships.fleet;

import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.shot.ShotPostDTO;
import com.github.ships.ships.shot.ShotResult;
import com.github.ships.ships.shot.StatusOfLegalShot;

import java.util.EnumMap;
import java.util.List;

class FleetPlaceShotPorcedure {

    private final FleetRepository repository;
    private final ShotPostDTO shotPostDTO;
    private final ShotResult shotResult;

    EnumMap<StatusOfLegalShot, Runnable> shotStatuses = new EnumMap<>(StatusOfLegalShot.class);

    public FleetPlaceShotPorcedure(FleetRepository repository, ShotPostDTO shotPostDTO, ShotResult shotResult) {
        this.repository = repository;
        this.shotPostDTO = shotPostDTO;
        this.shotResult = shotResult;
        shotStatuses.put(StatusOfLegalShot.HIT_WATER,
                () -> shotResult.setStatusOfLegalShot(StatusOfLegalShot.HIT_WATER));
        shotStatuses.put(StatusOfLegalShot.HIT_MAST, () -> shotResult.setStatusOfLegalShot(StatusOfLegalShot.HIT_MAST));
        shotStatuses.put(StatusOfLegalShot.SUNK_FLEET,
                () -> shotResult.setStatusOfLegalShot(StatusOfLegalShot.SUNK_FLEET));
        shotStatuses.put(StatusOfLegalShot.SUNK_SHIP,
                () -> shotResult.setStatusOfLegalShot(StatusOfLegalShot.SUNK_SHIP));
    }

    public ShotResult perform() {
        List<Fleet> fleets = getFleetByGameAndPlayerIDs();
        if (fleets.isEmpty()) {
            throw new NotFoundException();
        }
        Fleet fleet = fleets.get(0);
        shotStatuses.get(fleet.placeShot(shotPostDTO.getCellIdToPlaceShot()));
        if (shotResult.getStatusOfLegalShot() == StatusOfLegalShot.HIT_MAST) {
            retrieveSunkedShipMastsCellIDs(fleet);
            retrieveSunkedShipAdjacentsCellIDs(fleet);
        }
        return shotResult;
    }

    private void retrieveSunkedShipMastsCellIDs(Fleet fleet) {
        shotResult.setShipSunk(fleet.retrieveSunkedShipMastsCellIDs(shotPostDTO.getCellIdToPlaceShot()));
    }

    private void retrieveSunkedShipAdjacentsCellIDs(Fleet fleet) {
        shotResult.setAdjWaterOfShipSunk(fleet.retrieveSunkedShipAdjacentsCellIDs(shotPostDTO.getCellIdToPlaceShot()));
    }

    private List<Fleet> getFleetByGameAndPlayerIDs() {
        String gameId = shotPostDTO.getGameId();
        int playerId = shotPostDTO.getPlayerId();
        return repository.getSpecifiedFleets(gameId, playerId);
    }
}
