package com.github.ships.ships.fleet;

import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.shot.ShotPostDTO;
import com.github.ships.ships.shot.ShotResult;
import com.github.ships.ships.shot.StatusOfLegalShot;

import java.util.List;

public class FleetPlaceShotProcedure {

    private final FleetRepository repository;
    private final ShotPostDTO shotPostDTO;
    private final ShotResult shotResult;

    public FleetPlaceShotProcedure(FleetRepository repository,
                                   ShotPostDTO shotPostDTO,
                                   ShotResult shotResult) {
        this.repository = repository;
        this.shotPostDTO = shotPostDTO;
        this.shotResult = shotResult;
    }

    public ShotResult perform() {
        Fleet fleet = getFleetByGameAndPlayerIDs();
        StatusOfLegalShot statusOfLegalShot = placeShotInFleet(fleet);
        shotResult.setStatusOfLegalShot(statusOfLegalShot);
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
