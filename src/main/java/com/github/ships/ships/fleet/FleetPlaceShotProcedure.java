package com.github.ships.ships.fleet;

import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.shot.ShotPostDTO;
import com.github.ships.ships.shot.ShotResult;
import com.github.ships.ships.shot.StatusOfLegalShot;

import java.util.EnumMap;
import java.util.List;

public class FleetPlaceShotProcedure {

    private final FleetRepository repository;
    private final ShotPostDTO shotPostDTO;
    private final ShotResult shotResult;

    private final EnumMap<StatusOfLegalShot, Runnable> shotStatuses;

    public FleetPlaceShotProcedure(FleetRepository repository,
                                   ShotPostDTO shotPostDTO,
                                   ShotResult shotResult) {
        this.repository = repository;
        this.shotPostDTO = shotPostDTO;
        this.shotResult = shotResult;
        shotStatuses = initShotStatuses();
    }

    private EnumMap<StatusOfLegalShot, Runnable> initShotStatuses() {
        EnumMap<StatusOfLegalShot, Runnable> shotStatuses = new EnumMap<>(StatusOfLegalShot.class);
        shotStatuses.put(StatusOfLegalShot.HIT_WATER,
                () -> shotResult.setStatusOfLegalShot(StatusOfLegalShot.HIT_WATER));
        shotStatuses.put(StatusOfLegalShot.HIT_MAST,
                () -> shotResult.setStatusOfLegalShot(StatusOfLegalShot.HIT_MAST));
        shotStatuses.put(StatusOfLegalShot.SUNK_FLEET,
                () -> shotResult.setStatusOfLegalShot(StatusOfLegalShot.SUNK_FLEET));
        shotStatuses.put(StatusOfLegalShot.SUNK_SHIP,
                () -> shotResult.setStatusOfLegalShot(StatusOfLegalShot.SUNK_SHIP));
        return shotStatuses;
    }

    public ShotResult perform() {
        Fleet fleet = getFleetByGameAndPlayerIDs();
        StatusOfLegalShot statusOfLegalShot = placeShotInFleet(fleet);
        shotStatuses.get(statusOfLegalShot);
        if (shotResult.getStatusOfLegalShot() == StatusOfLegalShot.SUNK_SHIP) {
            defineSunkShipMastsCellIDs(fleet);
            defineSunkShipAdjacentCellIDs(fleet);
        }
        repository.save(fleet);
        return shotResult;
    }

    private StatusOfLegalShot placeShotInFleet(Fleet fleet) {
        int cellIdToPlaceShot = shotPostDTO.getCellIdToPlaceShot();
        return fleet.placeShot(cellIdToPlaceShot);
    }

    private void defineSunkShipMastsCellIDs(Fleet fleet) {
        int cellIdToPlaceShot = shotPostDTO.getCellIdToPlaceShot();
        List<Integer> shipSunk = fleet.retrieveSunkShipMastsCellIDs(cellIdToPlaceShot);
        shotResult.setShipSunk(shipSunk);
    }

    private void defineSunkShipAdjacentCellIDs(Fleet fleet) {
        int cellIdToPlaceShot = shotPostDTO.getCellIdToPlaceShot();
        List<Integer> adjWaterOfShipSunk = fleet.retrieveSunkShipAdjacentCellIDs(cellIdToPlaceShot);
        shotResult.setAdjWaterOfShipSunk(adjWaterOfShipSunk);
    }

    private Fleet getFleetByGameAndPlayerIDs() {
        String gameId = shotPostDTO.getGameId();
        int playerId = shotPostDTO.getPlayerId();
        List<Fleet> fleets = repository.getSpecifiedFleets(gameId, playerId);
        if (fleets.isEmpty()) {
            throw new NotFoundException();
        }
        return fleets.get(0);
    }
}
