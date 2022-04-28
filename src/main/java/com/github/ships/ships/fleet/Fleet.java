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
        this.ships = placeShipsHardcoded();
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

    /* Ships to be placed:
     * 1x   4-mast
     * 2x   3-mast
     * 3x   2-mast
     * 4x   1-mast */
    // TODO: replace with randomization
    private List<Ship> placeShipsHardcoded() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(List.of(22, 32, 42, 52), boardWidth, boardHeight));
        ships.add(new Ship(List.of(24, 25, 26), boardWidth, boardHeight));
        ships.add(new Ship(List.of(55, 56, 57), boardWidth, boardHeight));
        ships.add(new Ship(List.of(10, 20), boardWidth, boardHeight));
        ships.add(new Ship(List.of(84, 85), boardWidth, boardHeight));
        ships.add(new Ship(List.of(71, 81), boardWidth, boardHeight));
        ships.add(new Ship(List.of(1), boardWidth, boardHeight));
        ships.add(new Ship(List.of(4), boardWidth, boardHeight));
        ships.add(new Ship(List.of(39), boardWidth, boardHeight));
        ships.add(new Ship(List.of(89), boardWidth, boardHeight));
        return ships;
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
