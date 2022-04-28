package com.github.ships.ships.fleet;

import com.github.ships.ships.shot.StatusOfLegalShot;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Document
@Data
public class Fleet {

    @Id
    private String id;

    private String gameId;
    private Integer playerId;

    private int boardWidth;
    private int boardHeight;
    private List<Ship> ships;

    public Fleet(String gameId, int playerId, int boardWidth, int boardHeight) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.ships = FleetFactory.produceShips(playerId, boardWidth, boardHeight);
    }

    public StatusOfLegalShot placeShot(int cellId) {
        AtomicReference<StatusOfLegalShot> atomicShotResult = new AtomicReference<>(StatusOfLegalShot.HIT_WATER);
        ships.stream()
             .filter(ship -> ship.containsCellId(cellId))
             .findFirst()
             .ifPresent(ship -> atomicShotResult.set(ship.placeShot(cellId)));
        StatusOfLegalShot shotResult = atomicShotResult.get();
        if (shotResult == StatusOfLegalShot.SUNK_SHIP && !isAlive()) {
            shotResult = StatusOfLegalShot.SUNK_FLEET;
        }
        return shotResult;
    }

    public List<Integer> retrieveSunkShipMastsCellIDs(int cellId) {
        List<Integer> sunkShipAdjacentCellIDs = new ArrayList<>();
        ships.stream().filter(s -> s.containsCellId(cellId))
                      .findFirst()
                      .ifPresent(s -> sunkShipAdjacentCellIDs.addAll(s.retrieveMastsCellIDs()));
        return sunkShipAdjacentCellIDs;
    }

    public List<Integer> retrieveSunkShipAdjacentCellIDs(int cellId) {
        List<Integer> sunkShipAdjacentCellIDs = new ArrayList<>();
        ships.stream().filter(s -> s.containsCellId(cellId))
                      .findFirst()
                      .ifPresent(s -> sunkShipAdjacentCellIDs.addAll(s.retrieveAdjacentCellIDs(cellId)));
        return sunkShipAdjacentCellIDs;
    }

    public List<Integer> retrieveMastsCellIDs() {
        List<Integer> allMastsCellIDs = new ArrayList<>();
        ships.forEach(ship -> allMastsCellIDs.addAll(ship.retrieveMastsCellIDs()));
        return allMastsCellIDs;
    }

    private boolean isAlive() {
        return ships.stream()
                    .anyMatch(Ship::isAlive);
    }
}
